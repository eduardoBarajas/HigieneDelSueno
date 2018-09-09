package com.barajasoft.higienedelsueo.Entidades;

public class Paciente {
    private String nombre;
    private String sexo;
    private String hora_dormir;
    private String hora_despertar;
    public Paciente(){}

    public Paciente(String nom,String sex,String dormir,String despertar){
        nombre = nom;
        sexo = sex;
        hora_dormir = dormir;
        hora_despertar = despertar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getHora_dormir() {
        return hora_dormir;
    }

    public void setHora_dormir(String hora_dormir) {
        this.hora_dormir = hora_dormir;
    }

    public String getHora_despertar() {
        return hora_despertar;
    }

    public void setHora_despertar(String hora_despertar) {
        this.hora_despertar = hora_despertar;
    }
}
