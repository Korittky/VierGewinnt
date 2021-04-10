package de.korittky.viergewinnt.model;

import java.util.ArrayList;

public class Brett {

    public final static int BRETT_BREITE = 7;
    public final static int BRETT_HOEHE = 6;

    private int anzahlZuege = 0;
    private String zuege = "";
    private Farbe[][] feld = new Farbe[BRETT_HOEHE][BRETT_BREITE];
    private int[] hoehe = new int[BRETT_BREITE];
    private Farbe aktuelleFarbe = Farbe.rot;

    // ToDo in eigene Klasse
    public double[] score = new double[BRETT_BREITE];

    public Brett() {
        // initialisiere Datenstruktur
        for (int col = 0; col < BRETT_BREITE; col++) {
            score[col] = 0;
            hoehe[col] = 0;
            for (int row = 0; row < BRETT_HOEHE; row++) {
                feld[row][col] = Farbe.leer;
            }
        }
    }

    /**
     * Wandle Datenstruktur in leichter darstellbares Format
     * @return
     */
    public ArrayList<ArrayList<String>> getModel() {
        ArrayList<ArrayList<String>> brett = new ArrayList<ArrayList<String>>();
        for (int row = feld.length-1; row >= 0; row--) {
            ArrayList<String> zeile = new ArrayList<String>();
            for (int col = 0; col < feld[row].length; col++) {
                if (feld[row][col] == Farbe.leer) {
                    zeile.add("leer");
                } else if (feld[row][col] == Farbe.rot) {
                    zeile.add("rot");
                } else {
                    zeile.add("gelb");
                }
            }
            brett.add(zeile);
        }

        return brett;
    }


    public void naechsterSpieler() {
        aktuelleFarbe = aktuelleFarbe ==Farbe.gelb?Farbe.rot:Farbe.gelb;
    }

    public String getAktuelleFarbe() {
        return aktuelleFarbe ==Farbe.gelb?"gelb":"rot";
    }

    public ArrayList<String> getScore() {
        ArrayList<String> scorelist = new ArrayList<>();
        for (double s : score) {
            scorelist.add(String.format("%,.2f", s));
        }
        return scorelist;
    }

    public Farbe getFarbe() {
        return aktuelleFarbe;
    }

    public void spieleZug(int slot) {
        anzahlZuege++;
        zuege += zuege.length()==0?slot:","+slot;
        versucheZug(aktuelleFarbe, slot);
    }

    public boolean versucheZug(Farbe farbe, int slot) {
        if (slotFrei(slot)) {
            feld[hoehe[slot]][slot] = farbe;
            hoehe[slot]++;
            return true;
        } else {
            return false;
        }
    }

    public String getZuege() {
        return zuege;
    }

    public boolean slotFrei(int slot) {
        return hoehe[slot] < BRETT_HOEHE;
    }


    public void nimmVersuchtenZugZurueck(int slot) {
        hoehe[slot]--;
        feld[hoehe[slot]][slot] = Farbe.leer;
    }

    public boolean pruefeGewinn(Farbe farbe, int slot) {
        int h = hoehe[slot]-1;
        if (h>2) {
            // nach unten
            int z = 0;
            int j = h-1;
            while (j >= 0 && feld[j][slot] == farbe) {
                j--; z++;
            }
            if (z>2) return true;
        }

        // waagerecht
        int z = 0;
        // links
        int i = slot - 1;
        while (i >= 0 && feld[h][i] == farbe) {
            i--; z++;
        }
        // rechts
        i = slot + 1;
        while (i < BRETT_BREITE && feld[h][i] == farbe) {
            i++; z++;
        }
        if (z>2) return true;

        // Diagonal /
        z = 0;
        // links
        i = slot - 1;
        int j = h - 1;
        while (j >= 0 && i >= 0 && feld[j][i] == farbe) {
            j--; i--; z++;
        }
        // rechts
        i = slot + 1;
        j = h + 1;
        while (j < BRETT_HOEHE && i < BRETT_BREITE && feld[j][i] == farbe) {
            j++; i++; z++;
        }
        if (z>2) return true;

        // Diagonal \
        z = 0;
        // links
        i = slot - 1;
        j = h + 1;
        while (j < BRETT_HOEHE && i >= 0 && feld[j][i] == farbe) {
            j++; i--; z++;
        }
        // rechts
        i = slot + 1;
        j = h - 1;
        while (j >= 0 && i < BRETT_BREITE && feld[j][i] == farbe) {
            j--; i++; z++;
        }
        if (z>2) return true;
        return false;
    }

}
