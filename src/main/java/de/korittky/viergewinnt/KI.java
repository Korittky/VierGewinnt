package de.korittky.viergewinnt;

import de.korittky.viergewinnt.model.Brett;
import de.korittky.viergewinnt.model.Farbe;

public class KI {
    private final static int MAX_TIEFE = 9;
    private final static double scores[] = {134217728,16777216,2097152,262144,32768,4096,512,64,8,1};

    public static int besterZug(Brett brett) {
        int j = 0;
        double best = -100000000;
        for (int i=0; i<Brett.BRETT_BREITE; i++) {
            double guete = comuterZug(brett, i, brett.getFarbe(), 0);

            brett.score[i]=guete;
            if (guete > best && brett.slotFrei(i)) {
                best = guete;
                j = i;
            }
        }
        return j;
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
