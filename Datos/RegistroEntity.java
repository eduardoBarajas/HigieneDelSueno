package com.barajasoft.higienedelsueo.Datos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Registros_Del_Sueno")
public class RegistroEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id_registro;
    @ColumnInfo(name="nombre_paciente")
    private String nombre;
    @ColumnInfo(name="sexo_paciente")
    private String sexo;
    @ColumnInfo(name="hora_dormir")
    private String dormir;
    @ColumnInfo(name="hora_despertar")
    private String despertar;
    @ColumnInfo(name="sustancias_dañinas")
    private int ingerio_sustancias;
    @ColumnInfo(name="ritmo_cardiaco")
    private int ritmo_cardiaco;
    @ColumnInfo(name="preocupaciones_al_dormir")
    private int preocupaciones;
    @ColumnInfo(name="hora_en_que_durmio")
    private String hora_en_que_durmio;
    @ColumnInfo(name="hora_en_que_desperto")
    private String hora_en_que_desperto;

    @NonNull
    public int getId_registro() {
        return id_registro;
    }

    public void setId_registro(@NonNull int id_registro) {
        this.id_registro = id_registro;
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

    public String getDormir() {
        return dormir;
    }

    public void setDormir(String dormir) {
        this.dormir = dormir;
    }

    public String getDespertar() {
        return despertar;
    }

    public void setDespertar(String despertar) {
        this.despertar = despertar;
    }

    public int getIngerio_sustancias() {
        return ingerio_sustancias;
    }

    public void setIngerio_sustancias(int ingerio_sustancias) {
        this.ingerio_sustancias = ingerio_sustancias;
    }

    public int getRitmo_cardiaco() {
        return ritmo_cardiaco;
    }

    public void setRitmo_cardiaco(int ritmo_cardiaco) {
        this.ritmo_cardiaco = ritmo_cardiaco;
    }

    public int getPreocupaciones() {
        return preocupaciones;
    }

    public void setPreocupaciones(int preocupaciones) {
        this.preocupaciones = preocupaciones;
    }

    public String getHora_en_que_durmio() {
        return hora_en_que_durmio;
    }

    public void setHora_en_que_durmio(String hora_en_que_durmio) {
        this.hora_en_que_durmio = hora_en_que_durmio;
    }

    public String getHora_en_que_desperto() {
        return hora_en_que_desperto;
    }

    public void setHora_en_que_desperto(String getHora_en_que_desperto) {
        this.hora_en_que_desperto = getHora_en_que_desperto;
    }
}
