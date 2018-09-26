package com.barajasoft.higienedelsueo.Datos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface InterfazDAORegistrosDelSueno {
    @Query("SELECT * FROM Registros_Del_Sueno")
    List<RegistroEntity> getAll();

    @Insert
    void insertRegistro(RegistroEntity registro);

    @Query("SELECT * FROM Registros_Del_Sueno WHERE nombre_paciente IN (:nombre)")
    List<RegistroEntity> getAllByName(String nombre);

    @Delete
    void delete(RegistroEntity registro);


}
