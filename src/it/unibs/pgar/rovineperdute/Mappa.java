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
                    if(xmlr.getLocalName().equals(Costanti.CITY)){
                        for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                            //System.out.printf(" => attributo %s->%s%n", xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
                            if(xmlr.getAttributeLocalName(i).equals(Costanti.NOME)){
                                name = xmlr.getAttributeValue(i);
                            }else if(xmlr.getAttributeLocalName(i).equals(Costanti.ID)){
                                id = Integer.valueOf(xmlr.getAttributeValue(i));
                            }else if(xmlr.getAttributeLocalName(i).equals(Costanti.LONGITUDINE)){
                                x = Double.valueOf(xmlr.getAttributeValue(i));
                            }else if(xmlr.getAttributeLocalName(i).equals(Costanti.LATITUDINE)){
                                y = Double.valueOf(xmlr.getAttributeValue(i));
                            }else if(xmlr.getAttributeLocalName(i).equals(Costanti.ALTITUDINE)){
                                z = Double.valueOf(xmlr.getAttributeValue(i));
                            }
                        }
                    }else if(xmlr.getLocalName().equals(Costanti.LINK)){
                        int numero = xmlr.getAttributeCount()-1;
                        collegamenti.add(Integer.valueOf(xmlr.getAttributeValue(numero)));
                    }

                    break;

                case XMLStreamConstants.END_ELEMENT: // fine di un elemento: stampa il nome del tag chiuso
                    if(xmlr.getLocalName().equals(Costanti.CITY)){
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
    public double[][] creaPercorso (Archeologo archeologo){

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
                        sentieri_veicolo[i][j] = archeologo.carburanteUtilizzato(archeologo, citta.get(i), citta.get(j));
                    }else{
                        sentieri_veicolo[i][j] = infinito;
                    }
                }
            }
        }
        for(int i = 0; i < numero_citta; i++){
            for(int j = 0; j < numero_citta; j++){
                System.out.print(String.format("%8.2f ", sentieri_veicolo[i][j] ));
            }
            System.out.println("");
        }
        return sentieri_veicolo;
    }

    public void percorsoMigliore (Archeologo archeologo){

        double[][] matrice = new double[numero_citta][numero_citta];
        matrice = creaPercorso(archeologo);

        double infinito = Double.POSITIVE_INFINITY;
        double[][] matrice_appoggio = new double[numero_citta][numero_citta];

        //metodo bellissimo
    }

//    public int metodoBello(double[][] matrice, int i, double lunghezza){
//
//
//    }


}
