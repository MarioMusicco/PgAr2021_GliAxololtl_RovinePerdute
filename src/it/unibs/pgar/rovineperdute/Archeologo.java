package it.unibs.pgar.rovineperdute;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;

public class Archeologo {

    private Team team;
    private Sentiero sentiero_ottimizzato;

    public Archeologo(Team team) {
        this.team = team;
    }

    public Sentiero getSentiero_ottimizzato() {
        return sentiero_ottimizzato;
    }

    public void setSentiero_ottimizzato(Sentiero sentiero_ottimizzato) {
        this.sentiero_ottimizzato = sentiero_ottimizzato;
    }

    /**
     * Metodo che in base al team e al suo veicolo restituisce il carburate utilizzato per un determinato percorso
     * @param citta_uno
     * @param citta_due
     * @return
     */
    public double carburanteUtilizzato(Citta citta_uno, Citta citta_due){
        if(team.equals(Team.Tonatiuh)){
            return calcolo_lunghezza_sentiero_tonatiuh(citta_uno, citta_due);
        }else{
            return calcolo_lunghezza_sentiero_metztli(citta_uno, citta_due);
        }

    }

    /**
     * calcolo del carbirante utilizzato tra due città dal team tonatiuh
     * @param citta_uno
     * @param citta_due
     * @return
     */
    public double calcolo_lunghezza_sentiero_tonatiuh(Citta citta_uno, Citta citta_due){

        double lunghezza = 0;

        lunghezza = Math.sqrt(Math.pow((citta_uno.getCoordinate().getAscissa() - citta_due.getCoordinate().getAscissa()), 2)
                + Math.pow((citta_uno.getCoordinate().getOridinata() - citta_due.getCoordinate().getOridinata()) , 2));

        return lunghezza;
    }

    /**
     * calcolo del carbirante utilizzato tra due città dal team metztli
     * @param citta_uno
     * @param citta_due
     * @return
     */
    public double calcolo_lunghezza_sentiero_metztli(Citta citta_uno, Citta citta_due) {
        double lunghezza = 0;

        lunghezza = Math.abs(citta_uno.getCoordinate().getAltitudine() - citta_due.getCoordinate().getAltitudine());

        return lunghezza;
    }

    /**
     * metodo che va a creare un xml contenete le info su carburante,
     * città attraversate dai team
     * @param altro_team
     */
    public void creaXML (Archeologo altro_team){
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

        try {

            xmlw.writeCharacters("\n");
            xmlw.writeStartElement("routes");

            scriviXML(xmlw, this.team, this.sentiero_ottimizzato);
            scriviXML(xmlw, altro_team.team, altro_team.sentiero_ottimizzato);

            xmlw.writeCharacters("\n");
            xmlw.writeEndElement();
            xmlw.writeEndDocument();

        }catch (Exception e) { // se c’è un errore viene eseguita questa parte
                System.out.println("Errore nella scrittura");
        }

    }

    /**
     * metodo che scrive effettivamente le
     * @param xmlw
     * @param team
     * @param sentiero
     * @throws XMLStreamException
     */
    private void scriviXML(XMLStreamWriter xmlw, Team team, Sentiero sentiero) throws XMLStreamException {


        xmlw.writeCharacters("\n    ");
        xmlw.writeStartElement("route");
        xmlw.writeAttribute("Team", String.valueOf(team));
        xmlw.writeAttribute("cost",Double.toString(sentiero.getDistanza()));
        xmlw.writeAttribute("cities", Integer.toString(sentiero.getCitta_toccate().size()));

        for(int i = 0; i < sentiero.getCitta_toccate().size(); i++){
            xmlw.writeCharacters("\n        ");
            xmlw.writeStartElement("city");
            xmlw.writeAttribute("id", Integer.toString(sentiero.getCitta_toccate().get(i).getID()));
            xmlw.writeAttribute("nome", String.valueOf(sentiero.getCitta_toccate().get(i).getNome()));
            xmlw.writeEndElement(); //forse è da commentare

        }

        xmlw.writeCharacters("\n    ");
        xmlw.writeEndElement(); //chiusura route

    }

}
