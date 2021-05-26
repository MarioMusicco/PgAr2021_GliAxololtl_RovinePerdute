package it.unibs.pgar.rovineperdute;

public class Coordinate {

    private double altitudine;
    private double oridinata;
    private double ascissa;

    public Coordinate(double altitudine, double oridinata, double ascissa) {
        this.altitudine = altitudine;
        this.oridinata = oridinata;
        this.ascissa = ascissa;
    }

    public double getAltitudine() {
        return altitudine;
    }

    public void setAltitudine(double altitudine) {
        this.altitudine = altitudine;
    }

    public double getOridinata() {
        return oridinata;
    }

    public void setOridinata(double oridinata) {
        this.oridinata = oridinata;
    }

    public double getAscissa() {
        return ascissa;
    }

    public void setAscissa(double ascissa) {
        this.ascissa = ascissa;
    }
}
