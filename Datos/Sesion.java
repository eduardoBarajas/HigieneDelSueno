package com.barajasoft.higienedelsueo.Datos;

import com.barajasoft.higienedelsueo.Entidades.Paciente;

public class Sesion {
    private Paciente paciente;
    private Integer[] meds_cardiacos = new Integer []{0,0,0,0};
    private int tiene_preocupaciones = 0;
    private String[] nivel_estres = new String[]{"","","","",""};
    private static Sesion instance = null;
    public static Sesion getInstance(){
        if(instance==null)
            instance = new Sesion();
        return instance;
    }
    private Sesion(){ }
    public void setPaciente(Paciente p){
        paciente = p;
    }
    public Paciente getPaciente(){
        return paciente;
    }

    public int getTiene_preocupaciones() {
        return tiene_preocupaciones;
    }

    public void setTiene_preocupaciones(int tiene_preocupaciones) {
        this.tiene_preocupaciones = tiene_preocupaciones;
    }

    public String[] getNivel_estres() {
        return nivel_estres;
    }

    public void setNivel_estres(int index,String nivel_estres) {
        this.nivel_estres[index] = nivel_estres;
    }

    public Integer[] getMeds_cardiacos() {
        return meds_cardiacos;
    }

    public void setMeds_cardiacos(int index, int ritmo) {
        this.meds_cardiacos[index] = ritmo;
    }

    public void resetSesion(){
        paciente = null;
        meds_cardiacos = new Integer[]{0,0,0,0};
        tiene_preocupaciones = 0;
        nivel_estres = new String[]{"","","",""};
    }
}
