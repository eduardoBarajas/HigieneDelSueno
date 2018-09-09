package com.barajasoft.higienedelsueo.Datos;

import com.barajasoft.higienedelsueo.Entidades.Paciente;

public class Sesion {
    private Paciente paciente;
    private int tiene_preocupaciones = 0;
    private String nivel_estres = "";
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

    public String getNivel_estres() {
        return nivel_estres;
    }

    public void setNivel_estres(String nivel_estres) {
        this.nivel_estres = nivel_estres;
    }
}
