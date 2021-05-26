package it.unibs.pgar.rovineperdute;

import java.util.*;

public class Mappa {

    private int numero_citta;
    private ArrayList<Citta> citta;
    String nome; //facoltativo

    public Mappa(int numero_citta, ArrayList<Citta> citta, String nome) {
        this.numero_citta = numero_citta;
        this.citta = citta;
        this.nome = nome;
    }

    public int getNumero_citta() {
        return numero_citta;
    }

    public void setNumero_citta(int numero_citta) {
        this.numero_citta = numero_citta;
    }

    public ArrayList<Citta> getCitta() {
        return citta;
    }

    public void setCitta(ArrayList<Citta> citta) {
        this.citta = citta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void creaMappa(){
        //lettura file ed ad ogni cilo si crea una citta

    }

    public double[][] creaPercorsoVeicoloUno (){

        double infinito = Double.POSITIVE_INFINITY;
        double[][] sentieri_veicolo_uno = new double[numero_citta][numero_citta];
        for(int i = 0; i < numero_citta; i++){
            for (int j = 0; j < numero_citta; j++){
                if(i == j){
                    sentieri_veicolo_uno[i][j] = 0;
                }else{
                    if(citta.get(i).getCollegamenti_citta().contains(citta.get(j).getID())){
                        sentieri_veicolo_uno[i][j] = calcolo_lunghezza_sentiero_uno(citta.get(i), citta.get(j));
                    }else{
                        sentieri_veicolo_uno[i][j] = infinito;
                    }
                }
            }
        }
        return sentieri_veicolo_uno;
    }

    public double[][] creaPercorsoVeicoloDue (){

        double infinito = Double.POSITIVE_INFINITY;
        double[][] sentieri_veicolo_due = new double[numero_citta][numero_citta];
        for(int i = 0; i < numero_citta; i++){
            for (int j = 0; j < numero_citta; j++){
                if(i == j){
                    sentieri_veicolo_due[i][j] = 0;
                }else{
                    if(citta.get(i).getCollegamenti_citta().contains(citta.get(j).getID())){
                        sentieri_veicolo_due[i][j] = calcolo_lunghezza_sentiero_due(citta.get(i), citta.get(j));
                    }else{
                        sentieri_veicolo_due[i][j] = infinito;
                    }
                }
            }
        }
        return sentieri_veicolo_due;
    }



    public double calcolo_lunghezza_sentiero_uno(Citta citta_uno, Citta citta_due){

        double lunghezza = 0;

        lunghezza = Math.sqrt(Math.pow((citta_uno.getCoordinate().getAscissa() - citta_due.getCoordinate().getAscissa()), 2)
                + Math.pow((citta_uno.getCoordinate().getOridinata() - citta_due.getCoordinate().getOridinata()) , 2));

        return lunghezza;
    }

    public double calcolo_lunghezza_sentiero_due(Citta citta_uno, Citta citta_due) {
        double lunghezza = 0;

        lunghezza = Math.abs(citta_uno.getCoordinate().getAltitudine() - citta_due.getCoordinate().getAltitudine());

        return lunghezza;
    }


}
