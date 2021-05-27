package it.unibs.pgar.rovineperdute;

import java.util.ArrayList;

public class Sentieri {
    private ArrayList<Integer> id_citta_toccate = new ArrayList<>();
    private double distanza;

    public Sentieri(ArrayList<Integer> id_citta_toccate, double distanza) {
        this.id_citta_toccate = id_citta_toccate;
        this.distanza = distanza;
    }

    public ArrayList<Integer> getId_citta_toccate() {
        return id_citta_toccate;
    }

    public void setId_citta_toccate(ArrayList<Integer> id_citta_toccate) {
        this.id_citta_toccate = id_citta_toccate;
    }

    public double getDistanza() {
        return distanza;
    }

    public void setDistanza(double distanza) {
        this.distanza = distanza;
    }
}
