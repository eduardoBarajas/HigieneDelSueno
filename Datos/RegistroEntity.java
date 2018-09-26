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
    @ColumnInfo(name="edad")
    private int edad;
    @ColumnInfo(name="hora_dormir")
    private String dormir;
    @ColumnInfo(name="hora_despertar")
    private String despertar;
    @ColumnInfo(name="sustancias_dañinas")
    private int ingerio_sustancias;
    @ColumnInfo(name="ritmo_cardiaco1")
    private int ritmo_cardiaco1;
    @ColumnInfo(name="ritmo_cardiaco2")
    private int ritmo_cardiaco2;
    @ColumnInfo(name="ritmo_cardiaco3")
    private int ritmo_cardiaco3;
    @ColumnInfo(name="ritmo_cardiaco4")
    private int ritmo_cardiaco4;
    @ColumnInfo(name="sensacion_estres1")
    private String sensacion_estres1;
    @ColumnInfo(name="sensacion_estres2")
    private String sensacion_estres2;
    @ColumnInfo(name="sensacion_estres3")
    private String sensacion_estres3;
    @ColumnInfo(name="sensacion_estres4")
    private String sensacion_estres4;
    @ColumnInfo(name="preocupaciones_al_dormir")
    private int preocupaciones;
    @ColumnInfo(name="hora_en_que_durmio")
    private String hora_en_que_durmio;
    @ColumnInfo(name="hora_en_que_desperto")
    private String hora_en_que_desperto;
    @ColumnInfo(name="promedio_ruido")
    private Double promedio_ruido;
    @ColumnInfo(name="promedio_iluminacion")
    private Double promedio_iluminacion;
    @ColumnInfo(name="fecha")
    private String fecha;


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

    public Double getPromedio_ruido() {
        return promedio_ruido;
    }

    public void setPromedio_ruido(Double promedio_ruido) {
        this.promedio_ruido = promedio_ruido;
    }

    public Double getPromedio_iluminacion() {
        return promedio_iluminacion;
    }

    public void setPromedio_iluminacion(Double promedio_iluminacion) {
        this.promedio_iluminacion = promedio_iluminacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getRitmo_cardiaco1() {
        return ritmo_cardiaco1;
    }

    public void setRitmo_cardiaco1(int ritmo_cardiaco1) {
        this.ritmo_cardiaco1 = ritmo_cardiaco1;
    }

    public int getRitmo_cardiaco2() {
        return ritmo_cardiaco2;
    }

    public void setRitmo_cardiaco2(int ritmo_cardiaco2) {
        this.ritmo_cardiaco2 = ritmo_cardiaco2;
    }

    public int getRitmo_cardiaco3() {
        return ritmo_cardiaco3;
    }

    public void setRitmo_cardiaco3(int ritmo_cardiaco3) {
        this.ritmo_cardiaco3 = ritmo_cardiaco3;
    }

    public int getRitmo_cardiaco4() {
        return ritmo_cardiaco4;
    }

    public void setRitmo_cardiaco4(int ritmo_cardiaco4) {
        this.ritmo_cardiaco4 = ritmo_cardiaco4;
    }

    public String getSensacion_estres1() {
        return sensacion_estres1;
    }

    public void setSensacion_estres1(String sensacion_estres1) {
        this.sensacion_estres1 = sensacion_estres1;
    }

    public String getSensacion_estres2() {
        return sensacion_estres2;
    }

    public void setSensacion_estres2(String sensacion_estres2) {
        this.sensacion_estres2 = sensacion_estres2;
    }

    public String getSensacion_estres3() {
        return sensacion_estres3;
    }

    public void setSensacion_estres3(String sensacion_estres3) {
        this.sensacion_estres3 = sensacion_estres3;
    }

    public String getSensacion_estres4() {
        return sensacion_estres4;
    }

    public void setSensacion_estres4(String sensacion_estres4) {
        this.sensacion_estres4 = sensacion_estres4;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
