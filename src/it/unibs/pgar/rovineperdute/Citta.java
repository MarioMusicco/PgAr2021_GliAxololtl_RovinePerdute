package it.unibs.pgar.rovineperdute;

import java.util.ArrayList;

public class Citta {

    private Coordinate coordinate;
    private String nome;
    private int ID;
    private ArrayList<Integer> collegamenti_citta= new ArrayList<>();

    public Citta(Coordinate coordinate, String nome, int ID, ArrayList<Integer> collegamenti_citta) {
        this.coordinate = coordinate;
        this.nome = nome;
        this.ID = ID;
        this.collegamenti_citta.addAll(collegamenti_citta);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Integer> getCollegamenti_citta() {
        return collegamenti_citta;
    }

    public void setCollegamenti_citta(ArrayList<Integer> collegamenti_citta) {
        this.collegamenti_citta = collegamenti_citta;
    }
}
