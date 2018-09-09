package com.barajasoft.higienedelsueo.Datos;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PreocupacionEntity.class},version = 1)
public abstract class PreocupacionesDB extends RoomDatabase{
    public abstract InterfazDAOPreocupaciones interfazDAOPreocupaciones();
}
