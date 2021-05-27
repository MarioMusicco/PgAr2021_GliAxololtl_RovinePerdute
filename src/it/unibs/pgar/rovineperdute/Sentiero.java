package it.unibs.pgar.rovineperdute;

import java.util.ArrayList;

public class Sentiero {

    private ArrayList<Citta> citta_toccate = new ArrayList<>();
    private double distanza;

    public Sentiero() {
        this.distanza=0;
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

    public void addCitta_toccate(Citta citta){
        citta_toccate.add(citta);
    }
}
