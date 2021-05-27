package it.unibs.pgar.rovineperdute;

import java.util.ArrayList;

public class Sentiero {

    private ArrayList<Citta> citta_toccate = new ArrayList<>();
    private double distanza;

    public Sentiero(ArrayList<Citta> id_citta_toccate, double distanza) {
        this.citta_toccate = id_citta_toccate;
        this.distanza = distanza;
    }

    public ArrayList<Citta> getCitta_toccate() {
        return citta_toccate;
    }

    public void setCitta_toccate(ArrayList<Citta> citta_toccate) {
        this.citta_toccate = citta_toccate;
    }

    public double getDistanza() {
        return distanza;
    }

    public void setDistanza(double distanza) {
        this.distanza = distanza;
    }
}
