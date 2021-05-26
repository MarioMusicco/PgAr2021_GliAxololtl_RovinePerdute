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

    public Mappa() throws XMLStreamException {
        creaMappa();
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

    public void creaMappa() throws XMLStreamException {

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader("PgAr_Map_5.xml", new FileInputStream("PgAr_Map_5.xml"));
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }

        String name = null;
        int id = 0;
        double x = 0, y = 0, z = 0;
        ArrayList<Integer> collegamenti = new ArrayList<>();

        while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
            switch (xmlr.getEventType()) { // switch sul tipo di evento
                case XMLStreamConstants.START_DOCUMENT: // inizio del documento: stampa che inizia il documento
                    break;

                case XMLStreamConstants.START_ELEMENT: // inizio di un elemento: stampa il nome del tag e i suoi attributi
                    if(xmlr.getLocalName().equals("city")){
                        for (int i = 0; i < xmlr.getAttributeCount(); i++) {
                            //System.out.printf(" => attributo %s->%s%n", xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
                            if(xmlr.getAttributeLocalName(i).equals("name")){
                                name = xmlr.getAttributeValue(i);
                            }else if(xmlr.getAttributeLocalName(i).equals("id")){
                                id = Integer.valueOf(xmlr.getAttributeValue(i));
                            }else if(xmlr.getAttributeLocalName(i).equals("x")){
                                x = Double.valueOf(xmlr.getAttributeValue(i));
                            }else if(xmlr.getAttributeLocalName(i).equals("y")){
                                y = Double.valueOf(xmlr.getAttributeValue(i));
                            }else if(xmlr.getAttributeLocalName(i).equals("z")){
                                z = Double.valueOf(xmlr.getAttributeValue(i));
                            }
                        }
                    }else if(xmlr.getLocalName().equals("link")){
                        int numero = xmlr.getAttributeCount()-1;
                        collegamenti.add(Integer.valueOf(xmlr.getAttributeValue(numero)));
                    }

                    break;

                case XMLStreamConstants.END_ELEMENT: // fine di un elemento: stampa il nome del tag chiuso
                    if(xmlr.getLocalName().equals("city")){
                        Coordinate cord = new Coordinate(z, y, x);
                        Citta city = new Citta(cord, name, id, collegamenti);
                        citta.add(city);
                    }
                    break;
            }
            xmlr.next();
        }

    }

    public double[][] creaPercorso (Archeologo archeologo){

        double infinito = Double.POSITIVE_INFINITY;
        double[][] sentieri_veicolo_uno = new double[numero_citta][numero_citta];
        for(int i = 0; i < numero_citta; i++){
            for (int j = 0; j < numero_citta; j++){
                if(i == j){
                    sentieri_veicolo_uno[i][j] = 0;
                }else{
                    if(citta.get(i).getCollegamenti_citta().contains(citta.get(j).getID())){
                        sentieri_veicolo_uno[i][j] = calcolo_lunghezza_sentiero_uno(citta.get(i), citta.get(j));
                    }else{
                        sentieri_veicolo_uno[i][j] = infinito;
                    }
                }
            }
        }
        return sentieri_veicolo_uno;
    }

    public double[][] creaPercorsoVeicoloDue (){

        double infinito = Double.POSITIVE_INFINITY;
        double[][] sentieri_veicolo_due = new double[numero_citta][numero_citta];
        for(int i = 0; i < numero_citta; i++){
            for (int j = 0; j < numero_citta; j++){
                if(i == j){
                    sentieri_veicolo_due[i][j] = 0;
                }else{
                    if(citta.get(i).getCollegamenti_citta().contains(citta.get(j).getID())){
                        sentieri_veicolo_due[i][j] = calcolo_lunghezza_sentiero_due(citta.get(i), citta.get(j));
                    }else{
                        sentieri_veicolo_due[i][j] = infinito;
                    }
                }
            }
        }
        return sentieri_veicolo_due;
    }



    public double calcolo_lunghezza_sentiero_uno(Citta citta_uno, Citta citta_due){

        double lunghezza = 0;

        lunghezza = Math.sqrt(Math.pow((citta_uno.getCoordinate().getAscissa() - citta_due.getCoordinate().getAscissa()), 2)
                + Math.pow((citta_uno.getCoordinate().getOridinata() - citta_due.getCoordinate().getOridinata()) , 2));

        return lunghezza;
    }

    public double calcolo_lunghezza_sentiero_due(Citta citta_uno, Citta citta_due) {
        double lunghezza = 0;

        lunghezza = Math.abs(citta_uno.getCoordinate().getAltitudine() - citta_due.getCoordinate().getAltitudine());

        return lunghezza;
    }


}
