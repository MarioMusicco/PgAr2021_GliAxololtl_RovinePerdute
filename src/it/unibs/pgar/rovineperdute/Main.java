package it.unibs.pgar.rovineperdute;

import javax.xml.stream.XMLStreamException;

public class Main {

    public static void main(String[] args) throws XMLStreamException {

        Mappa mp = new Mappa();
        for(int i = 0; i < mp.getNumero_citta(); i++){
            System.out.print(mp.getCitta().get(i).getID());
            System.out.println(mp.getCitta().get(i).getNome());
        }

        Archeologo ar1 = new Archeologo(Team.Tonatiuh);
        Archeologo ar2 = new Archeologo(Team.Metztli);
        mp.creaPercorso(ar1);
        System.out.println("");
        mp.creaPercorso(ar2);
    }
}
