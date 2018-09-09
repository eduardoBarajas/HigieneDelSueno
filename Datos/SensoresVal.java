package com.barajasoft.higienedelsueo.Datos;

public class SensoresVal {
    private double db;
    private float lux;
    private float posicionX;
    private float posicionY;

    private static final SensoresVal ourInstance = new SensoresVal();

    public static SensoresVal getInstance() {
        return ourInstance;
    }

    private SensoresVal() {
        db = 0;
        lux = 0;
        posicionX = 0;
        posicionY = 0;
    }

    public double getDb() {
        return db;
    }

    public void setDb(double db) {
        this.db = db;
    }

    public float getLux() {
        return lux;
    }

    public void setLux(float lux) {
        this.lux = lux;
    }

    public float getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }

    public float getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(float posicionY) {
        this.posicionY = posicionY;
    }
}
