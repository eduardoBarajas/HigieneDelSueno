package com.barajasoft.higienedelsueo.Datos;

import com.barajasoft.higienedelsueo.Entidades.Paciente;

import java.util.LinkedList;
import java.util.List;

public class Sesion {
    private Paciente paciente;
    private Integer[] meds_cardiacos = new Integer []{0,0,0,0};
    private int tiene_preocupaciones = 0;
    private String[] nivel_estres = new String[]{"","","","",""};
    private int esta_drogado = 0;
    private static Sesion instance = null;
    private List<Double> dBSounds = new LinkedList<>();
    private List<Double> luxLevels = new LinkedList<>();
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
        esta_drogado = 0;
        luxLevels.clear();
        dBSounds.clear();
    }

    public boolean getEsta_drogado() {
        return esta_drogado==1;
    }

    public void setEsta_drogado(int esta_drogado) {
        this.esta_drogado = esta_drogado;
    }

    public void addMedicionLux(double m){
        luxLevels.add(m);
    }
    public void addMediciondB(double m){
        dBSounds.add(m);
    }

    public List<Double> getMedicionesLux(){
        return luxLevels;
    }
    public List<Double> getMedicionesdB(){
        return dBSounds;
    }
}
