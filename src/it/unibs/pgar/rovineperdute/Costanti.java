package it.unibs.pgar.rovineperdute;

import it.unibs.fp.mylib.InputDati;

public class Costanti {

    public static final String MAPPA_CINQUE_CITTA= "PgAr_Map_5.xml";
    public static final String MAPPA_DODICI_CITTA = "PgAr_Map_12.xml";
    public static final String MAPPA_CINQUANTA_CITTA = "PgAr_Map_50.xml";
    public static final String MAPPA_DUECENTO_CITTA = "PgAr_Map_200.xml";
    public static final String MAPPA_DUEMILA_CITTA = "PgAr_Map_2000.xml";
    public static final String MAPPA_DIECIMILA_CITTA = "PgAr_Map_10000.xml";
    public static final String MESSAGGIO_INPUT = "Premi 1 per la mappa con cinque città;\n" +
            "Premi 2 per la mappa con dodici città;\n" +
            "Premi 3 per la mappa con cinquanta città;\n" +
            "Premi 4 per la mappa con duecento città;\n" +
            "Premi 5 per la mappa con duemila città;\n" +
            "Premi 6 per la mappa con diecimila città;\n";
    public static final String MESSAGGIO_INPUT_ERRATO = "Il numero inserito deve essere uno di quelli indicati";
    public static final String ERRORE_NELL_INIZIALIZZAZIONE_DEL_READER = "Errore nell'inizializzazione del reader:";

    public static final String CITY = "city";
    public static final String LINK = "link";
    public static final String NOME = "name";
    public static final String ID = "id";
    public static final String LONGITUDINE = "x";
    public static final String LATITUDINE = "y";
    public static final String ALTITUDINE = "h";


    /**
     * Metodo per la scelta da parte dell'utente di quale mappa da utilizzare tra quelle disponibili
     * @return
     */
    public static String scegliFileInput(){

        String nome_file = null;
        boolean rifai = true;

        do {
            int scelta = InputDati.leggiIntero(MESSAGGIO_INPUT);
            switch (scelta) {
                case (1):
                    nome_file = MAPPA_CINQUE_CITTA;
                    rifai = false;
                    break;
                case (2):
                    nome_file = MAPPA_DODICI_CITTA;
                    rifai = false;
                    break;
                case (3):
                    nome_file = MAPPA_CINQUANTA_CITTA;
                    rifai = false;
                    break;
                case (4):
                    nome_file = MAPPA_DUECENTO_CITTA;
                    rifai = false;
                    break;
                case (5):
                    nome_file = MAPPA_DUEMILA_CITTA;
                    rifai = false;
                    break;
                case (6):
                    nome_file = MAPPA_DIECIMILA_CITTA;
                    rifai = false;
                    break;
                default:
                    System.out.println(MESSAGGIO_INPUT_ERRATO);
            }
        }while (rifai);
        return nome_file;
    }

}
