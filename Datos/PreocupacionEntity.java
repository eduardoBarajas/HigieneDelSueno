package com.barajasoft.higienedelsueo.Datos;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Preocupaciones")
public class PreocupacionEntity {
    @PrimaryKey
    @NonNull
    private String preocupacion;

    @NonNull
    public String getPreocupacion() {
        return preocupacion;
    }

    public void setPreocupacion(@NonNull String preocupacion) {
        this.preocupacion = preocupacion;
    }
}
