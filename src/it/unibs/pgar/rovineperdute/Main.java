package it.unibs.pgar.rovineperdute;

import javax.xml.stream.XMLStreamException;

public class Main {

    public static void main(String[] args) throws XMLStreamException {

        Mappa mp = new Mappa(Costanti.scegliFileInput());

        Archeologo ar1 = new Archeologo(Team.Tonatiuh);
        Archeologo ar2 = new Archeologo(Team.Metztli);


        Sentiero st1= mp.percorsoMigliore(ar1);
        for(int i=0; i<st1.getCitta_toccate().size(); i++){
            System.out.println(st1.getCitta_toccate().get(i).getID());
        }
        System.out.println(st1.getDistanza());

        Sentiero st2= mp.percorsoMigliore(ar2);
        for(int i=0; i<st2.getCitta_toccate().size(); i++){
            System.out.println(st2.getCitta_toccate().get(i).getID());
        }
        System.out.println(st2.getDistanza());
    }
}
