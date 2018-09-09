package com.barajasoft.higienedelsueo.Datos;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {RegistroEntity.class},version = 1)
public abstract class RegistrosDelSuenoDB extends RoomDatabase {
    public abstract InterfazDAORegistrosDelSueno registrosDelSuenoDao();
}
