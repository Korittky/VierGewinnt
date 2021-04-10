package de.korittky.viergewinnt.model;

public enum Farbe {
    rot,
    gelb,
    leer;

    public Farbe andere() {
        return this.equals(Farbe.rot)?Farbe.gelb:Farbe.rot;
    }
}
