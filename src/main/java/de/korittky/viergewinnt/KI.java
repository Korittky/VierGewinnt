package de.korittky.viergewinnt;

import de.korittky.viergewinnt.model.Brett;
import de.korittky.viergewinnt.model.Farbe;

import java.util.ArrayList;

/**
 * http://localhost:8080/replay/3,4,4,0,3,3,4,4,3,1,5,5,5,2,2,1,5,1,1,2,6,0,0,2,3,2,2,5,6,3,6,5
 * http://localhost:8080/replay/3,2,2,6,1,6,3,6,6,1,3,3,2,2,4,3,2,2,1,1,4,4,4
 * http://localhost:8080/replay/3,2,2,6,1,6,3,6,6,1,3,3,2,2,1,1,4,2,4,4,4
 * http://localhost:8080/replay/3,2,2,6,1,6,3,6,6,1,3,3,2,2,4,3,4,3,4,4,1
 */


public class KI {
    private final static int MAX_TIEFE = 8;
    private final static double scores[] = {134217728,16777216,2097152,262144,32768,4096,512,64,8,1};

    public static int besterZug(Brett brett) {
        int j = 0;
        double best = -200000000;
        ArrayList<Integer> bestSlots = new ArrayList<>(Brett.BRETT_BREITE);
        for (int i=0; i<Brett.BRETT_BREITE; i++) {
            double guete = comuterZug(brett, i, brett.getFarbe(), 0);

            brett.score[i]=guete;
            if (brett.slotFrei(i)) {
                if (guete > best) {
                    bestSlots.clear();
                    bestSlots.add(i);
                    best = guete;
                } else if (guete == best) {
                    // gleich gut
                    bestSlots.add(i);
                }
            }
        }

        // zufällig unter den besten Zügen einen aussuchen
        return bestSlots.get((int)(java.lang.Math.random() * bestSlots.size()));
    }

    private static double comuterZug(Brett brett, int slot, Farbe farbe, int tiefe) {
        if (tiefe > MAX_TIEFE) return 0;
        if (brett.versucheZug(farbe, slot)) {
            try {
                if (brett.pruefeGewinn(farbe,slot)) {
                    return scores[tiefe];
                }
                double sum = 0;
                for (int i=0; i<Brett.BRETT_BREITE; i++) {
                    double guete = menschZug(brett, i, farbe.andere(), tiefe + 1);
                    sum += guete;
                }
                return sum;
            } finally {
                brett.nimmVersuchtenZugZurueck(slot);
            }
        }
        return 0;
    }

    private static double menschZug(Brett brett, int slot, Farbe farbe, int tiefe) {
        if (tiefe > MAX_TIEFE) return 0;
        if (brett.versucheZug(farbe, slot)) {
            try {
                if (brett.pruefeGewinn(farbe,slot)) {
                    return -scores[tiefe];
                }
                double sum = 0;
                for (int i=0; i<Brett.BRETT_BREITE; i++) {
                    double guete = comuterZug(brett, i, farbe.andere(), tiefe + 1);
                    sum += guete;
                }
                return sum;
            } finally {
                brett.nimmVersuchtenZugZurueck(slot);
            }
        }
        return 0;
    }


}
