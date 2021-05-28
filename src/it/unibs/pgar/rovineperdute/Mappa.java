package it.unibs.pgar.rovineperdute;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.*;

public class Mappa {

    private int numero_citta;
    private ArrayList<Citta> citta= new ArrayList<>();

    public Mappa(String file_input) throws XMLStreamException {
        creaMappa(file_input);
        this.numero_citta = citta.size();
    }

    public int getNumero_citta() {
        return numero_citta;
    }

    public void setNumero_citta(int numero_citta) {
        this.numero_citta = numero_citta;
    }

    public ArrayList<Citta> getCitta() {
        return citta;
    }

    public void setCitta(ArrayList<Citta> citta) {
        this.citta = citta;
    }

    /**
     * Metodo che procede con la lettura del file xml delle città scelto dall'utente.
     * procede con la lettura città per città e una volta che trova un tag di chiusura di una città, e quindi
     * alla lettua completa di una città, mi crea l'oggetto Citta che verrà poi inserito/aggiunto nella mappa
     * @param file_input
     * @throws XMLStreamException
     */
    public void creaMappa(String file_input) throws XMLStreamException {

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(file_input, new FileInputStream(file_input));
        } catch (Exception e) {
            System.out.println(Costanti.ERRORE_NELL_INIZIALIZZAZIONE_DEL_READER);
            System.out.println(e.getMessage());
        }

        //creo delle variabili che ciclo per ciclo verranno poi ritrascritte
        //queste variabili tengono in memoria i dati di una città letto dall'xml ed una volta letta tutta la città
        //mi servono per essere inserite nel costruttore di citta

        String name = null;
        int id = 0;
        double x = 0, y = 0, z = 0;
        ArrayList<Integer> collegamenti = new ArrayList<>();

        while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
            switch (xmlr.getEventType()) { // switch sul tipo di evento
                case XMLStreamConstants.START_DOCUMENT: // inizio del documento: stampa che inizia il documento
                    break;

                case XMLStreamConstants.START_ELEMENT: // inizio di un elemento: stampa il nome del tag e i suoi attributi
                    if(xmlr.getLocalName().equals(Costanti.TAG_CITY)){
                        for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                            //System.out.printf(" => attributo %s->%s%n", xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
                            if(xmlr.getAttributeLocalName(i).equals(Costanti.TAG_NAME)){
                                name = xmlr.getAttributeValue(i);
                            }else if(xmlr.getAttributeLocalName(i).equals(Costanti.TAG_ID)){
                                id = Integer.valueOf(xmlr.getAttributeValue(i));
                            }else if(xmlr.getAttributeLocalName(i).equals(Costanti.TAG_LONGITUDINE)){
                                x = Double.valueOf(xmlr.getAttributeValue(i));
                            }else if(xmlr.getAttributeLocalName(i).equals(Costanti.TAG_LATITUDINE)){
                                y = Double.valueOf(xmlr.getAttributeValue(i));
                            }else if(xmlr.getAttributeLocalName(i).equals(Costanti.TAG_ALTITUDINE)){
                                z = Double.valueOf(xmlr.getAttributeValue(i));
                            }
                        }
                    }else if(xmlr.getLocalName().equals(Costanti.TAG_LINK)){
                        int numero = xmlr.getAttributeCount()-1;
                        collegamenti.add(Integer.valueOf(xmlr.getAttributeValue(numero)));
                    }

                    break;

                case XMLStreamConstants.END_ELEMENT: // fine di un elemento: stampa il nome del tag chiuso
                    if(xmlr.getLocalName().equals(Costanti.TAG_CITY)){
                        Coordinate cord = new Coordinate(z, y, x);
                        Citta city = new Citta(cord, name, id, collegamenti);
                        citta.add(city);
                        collegamenti.clear();
                    }
                    break;
            }
            xmlr.next();
        }

    }

    /**
     * Metodo per la formazione del grafo del calcolo dei collegamenti tra le varie città
     * @param archeologo
     * @return matrice grafo
     */
    private double[][] creaPercorso (Archeologo archeologo){

        double infinito = Double.POSITIVE_INFINITY;
        double[][] sentieri_veicolo = new double[numero_citta][numero_citta];
        for(int i = 0; i < numero_citta; i++){
            for (int j = 0; j < numero_citta; j++){
                //se la riga e colonna corrisponde alla stessa città vuol dire che la distanza è zero
                if(i == j){
                    sentieri_veicolo[i][j] = 0;
                }else{
                    //controlla se la città presa (quella che corrisponde all'indice i) in considerazione possiede nei suoi collegamenti con le altre città
                    //l'id della seconda città che stiamo controllando (quella che corrisponde all'indice j)
                    if(citta.get(i).getCollegamenti_citta().contains(citta.get(j).getID())){
                        sentieri_veicolo[i][j] = archeologo.carburanteUtilizzato(citta.get(i), citta.get(j));
                    }else{
                        sentieri_veicolo[i][j] = infinito;
                    }
                }
            }
        }
        /*for(int i = 0; i < numero_citta; i++){
            for(int j = 0; j < numero_citta; j++){
                System.out.print(String.format("%8.2f ", sentieri_veicolo[i][j] ));
            }
            System.out.println("");
        }*/
        return sentieri_veicolo;
    }

    /**
     * metodo che restituisce il percorso migliore rispetto a tutti quelli possibili
     * adando a confrontare quello attualmente trovato con quello fin'ora ritenuto più corto.
     *
     * @param archeologo
     * @return
     */
    public Sentiero percorsoMigliore (Archeologo archeologo){

        double[][] matrice = new double[numero_citta][numero_citta];
        matrice = creaPercorso(archeologo);

        double infinito = Double.POSITIVE_INFINITY;


        int i=0;
        int j=0;
        boolean fine_percorsi_possibili= false;

        Sentiero sentiero_migliore= new Sentiero();
        Sentiero sentiero_alternativo= new Sentiero();
        sentiero_alternativo.addCitta_toccate(citta.get(j));
        ArrayList<Integer> iDToccati= new ArrayList<Integer>();
        iDToccati.add(i);



        while (!fine_percorsi_possibili) {

            if (i == numero_citta - 1) {//completato un sentiero vado a controllare che sia il sentiero migliore disponibile

                if(sentiero_migliore.getDistanza()==0){
                    sostituisciSentiero(sentiero_migliore, sentiero_alternativo);
                }else if(controlloPercorsoMigliore(sentiero_migliore, sentiero_alternativo)){
                    sostituisciSentiero(sentiero_migliore, sentiero_alternativo);
                }

                int[] convertire= ritornoSuiMieiPassi(sentiero_alternativo, matrice, iDToccati, i, j, infinito);
                int k= convertire[0];
                j= convertire[1];
                i= convertire[2];

                if(k== -1){//controllo che si assicura che ci siano altri sentieri disponibili oppure se abbiamop trovato tutti quelli possibili
                    fine_percorsi_possibili= true;
                }

            }else if (matrice[i][j] != 0 && matrice[i][j] != infinito){//andiamo ad aggiungere una città non visitata al mio sentiero alternativo
                if(!iDToccati.contains(j)){
                    sentiero_alternativo.addCitta_toccate(citta.get(j));
                    sentiero_alternativo.setDistanza(sentiero_alternativo.getDistanza() + matrice[i][j]);
                    iDToccati.add(j);
                    i = j;
                }


            }
            //caso dei vicoli ciechi
            if(j== numero_citta-1 && matrice[i][j]== infinito){
                int[] convertire= ritornoSuiMieiPassi(sentiero_alternativo, matrice, iDToccati, i, j, infinito);
                int k= convertire[0];
                j= convertire[1];
                i= convertire[2];
                if(k== -1){//controllo che si assicura che ci siano altri sentieri disponibili oppure se abbiamop trovato tutti quelli possibili
                    fine_percorsi_possibili= true;
                }
            }
            j++;
        }

        return sentiero_migliore;
    }

    /**
     * metodo di appoggio che controlla quala dei due sentieri sia il migliore.
     * ritorna true se quello da confrontare è meglio di quello vecchio,
     * ritorna false se quello vecchio è meglio di quello da confrontare;
     *
     * @param sent_migl
     * @param sent_conf
     * @return
     */
    private boolean controlloPercorsoMigliore (Sentiero sent_migl, Sentiero sent_conf){

        boolean migliore= false;

        if(sent_migl.getDistanza()> sent_conf.getDistanza()){
            migliore= true;
        }else if(sent_migl.getDistanza()== sent_conf.getDistanza()) {
            if (sent_migl.getCitta_toccate().size() > sent_conf.getCitta_toccate().size()) {
                migliore = true;
            } else if (sent_migl.getCitta_toccate().size()== sent_conf.getCitta_toccate().size() ){
                if(cittaMaggiore(sent_migl) < cittaMaggiore(sent_conf)){
                    migliore = true;
                }
            }
        }

        return migliore;
    }

    /**
     * metodo di appoggio che controlla quale sia l'id maggiore tra tutti quelli
     * delle città attraaversate da un sentiero
     *
     * @param sentiero
     * @return
     */
    private int cittaMaggiore(Sentiero sentiero){
        int IDmagg=0;

        for(int i=0; i<sentiero.getCitta_toccate().size()-1; i++){
            if(sentiero.getCitta_toccate().get(i).getID()>IDmagg)
                IDmagg= sentiero.getCitta_toccate().get(i).getID();
        }

        return IDmagg;
    }

    /**
     *  metodo di appoggio che in caso un team debba tornare indietro
     *  (dopo aver trovato un percorso valido oppure un vicolo cieco)
     *  permette di tornare in una città  già visitata e controlla se si possa
     *  andare ad altre città non ancora visitate.
     *
     *  ritorna una stringa che contiene la condizione di fine della ricerca dei percorsi(k)
     *  la riga della matrice su cui andremo a cercare la città non ancora visitata(i)
     *  e la colonna della matrice che identifica la città non ancora visitata(j)
     *
     * @param sentiero_alternativo
     * @param matrice
     * @param iDToccati
     * @param i
     * @param j
     * @param infinito
     * @return
     */
    private int[] ritornoSuiMieiPassi(Sentiero sentiero_alternativo, double matrice[][], ArrayList<Integer> iDToccati, int i, int j, double infinito){
        boolean fine_rimozoni= false;

        int k;
        for (k= sentiero_alternativo.getCitta_toccate().size()-2; k>=0; k--){

            for(int h= sentiero_alternativo.getCitta_toccate().get(k+1).getID(); h<numero_citta; h++){

                if(matrice[iDToccati.get(k)][h]!=0 && matrice[iDToccati.get(k)][h]!=infinito){
                    if(!iDToccati.contains(h)) {
                        fine_rimozoni = true;
                        i = iDToccati.get(k);
                        j = h-1;
                        break;
                    }
                }
            }
            sentiero_alternativo.setDistanza(sentiero_alternativo.getDistanza()- matrice[iDToccati.get(k)][iDToccati.get(k+1)]);
            sentiero_alternativo.getCitta_toccate().remove(k+1);
            iDToccati.remove(k+1);
            if(fine_rimozoni){
                break;
            }
        }

        int[] info_condensate= {k, j, i};
        return info_condensate;
    }

    /**
     * metodo che prende il sentiero appena creato e lo rende
     * quello migliore trovato fin'ora
     * @param sentiero_migliore
     * @param sentiero_alternativo
     */
    private void sostituisciSentiero(Sentiero sentiero_migliore, Sentiero sentiero_alternativo){
        sentiero_migliore.setDistanza(sentiero_alternativo.getDistanza());
        sentiero_migliore.getCitta_toccate().clear();
        sentiero_migliore.getCitta_toccate().addAll(sentiero_alternativo.getCitta_toccate());
    }
}
