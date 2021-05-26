package it.unibs.pgar.rovineperdute;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class InterazioniXml {

    public void scritturaXML(){
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


    }


}
