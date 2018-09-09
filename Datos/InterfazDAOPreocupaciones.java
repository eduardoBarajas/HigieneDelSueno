package com.barajasoft.higienedelsueo.Datos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface InterfazDAOPreocupaciones {
    @Query("SELECT * FROM Preocupaciones")
    List<PreocupacionEntity> getAll();

    @Insert
    void addPreocupacion(PreocupacionEntity preocupacion);

    @Delete
    void deletePreocupacion(PreocupacionEntity preocupacion);
}
