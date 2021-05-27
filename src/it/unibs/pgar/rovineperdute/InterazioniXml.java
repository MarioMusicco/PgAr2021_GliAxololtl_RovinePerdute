package it.unibs.pgar.rovineperdute;

import javax.xml.stream.*;
import java.io.FileOutputStream;

public class InterazioniXml {

    public void scritturaXML(Sentiero sentiero){

        //metodo che stiamo facendo per il percorso

        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("Routes.xml"), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del writer:");
            System.out.println(e.getMessage());
        }


        try { // blocco try per raccogliere eccezioni
            xmlw.writeCharacters("\n");
            xmlw.writeStartElement("routes");
            xmlw.writeCharacters("\n");

            xmlw.writeStartElement("route");
            xmlw.writeAttribute("Team", String.valueOf(Team.Tonatiuh));
            xmlw.writeAttribute("cost",Double.toString(sentiero.getDistanza()));
            xmlw.writeAttribute("cities", Integer.toString(sentiero.getCitta_toccate().size()));
            xmlw.writeCharacters("\n        ");

            for(int i = 0; i < sentiero.getCitta_toccate().size(); i++){
                xmlw.writeStartElement("city");
                xmlw.writeAttribute("id", Integer.toString(sentiero.getCitta_toccate().get(i).getID()));
                xmlw.writeAttribute("nome", String.valueOf(sentiero.getCitta_toccate().get(i).getNome()));
                xmlw.writeEndElement(); //forse è da commentare
                xmlw.writeCharacters("\n        ");

            }

            xmlw.writeEndElement(); //chiusura route

            //si fa ricalcolare il sentiero con la matrice delle altitudini

            xmlw.writeStartElement("route");
            xmlw.writeAttribute("Team", String.valueOf(Team.Metztli));
            xmlw.writeAttribute("cost",Double.toString(sentiero.getDistanza()));
            xmlw.writeAttribute("cities", Integer.toString(sentiero.getCitta_toccate().size()));
            xmlw.writeCharacters("\n        ");

            for(int i = 0; i < sentiero.getCitta_toccate().size(); i++){
                xmlw.writeStartElement("city");
                xmlw.writeAttribute("id", Integer.toString(sentiero.getCitta_toccate().get(i).getID()));
                xmlw.writeAttribute("nome", String.valueOf(sentiero.getCitta_toccate().get(i).getNome()));
                xmlw.writeEndElement(); //forse è da commentare
                xmlw.writeCharacters("\n        ");

            }



        } catch (Exception e) { // se c’è un errore viene eseguita questa parte
            System.out.println("Errore nella scrittura");
        }

    }


}
