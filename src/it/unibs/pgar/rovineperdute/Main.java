package it.unibs.pgar.rovineperdute;

import javax.xml.stream.XMLStreamException;

public class Main {

    public static void main(String[] args) throws XMLStreamException {

        Mappa mp = new Mappa(Costanti.scegliFileInput());

        Archeologo ar1 = new Archeologo(Team.Tonatiuh);
        Archeologo ar2 = new Archeologo(Team.Metztli);

        System.out.println("");
        mp.creaPercorso(ar2);
    }
}
