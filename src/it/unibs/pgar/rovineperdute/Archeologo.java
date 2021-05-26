package it.unibs.pgar.rovineperdute;

public class Archeologo {

    private Team team;

    public Archeologo(Team team) {
        this.team = team;
    }

    /**
     * Metodo che in base al team e al suo veicolo restituisce il carburate utilizzato per un determinato percorso
     * @param ar
     * @param citta_uno
     * @param citta_due
     * @return
     */
    public double carburanteUtilizzato(Archeologo ar, Citta citta_uno, Citta citta_due){
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



}
