package com.barajasoft.higienedelsueo.Datos;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.barajasoft.higienedelsueo.Entidades.Paciente;

import java.util.ArrayList;
import java.util.List;

public class ConexionBDRegistrosDelSueno extends AsyncTask {
    private RegistrosDelSuenoDB bd;
    public ConexionBDRegistrosDelSueno(Context context){
        if(bd==null)
            bd = Room.databaseBuilder(context,RegistrosDelSuenoDB.class,"Registros_Del_Sueno").fallbackToDestructiveMigration().build();
    }

    private List<RegistroEntity> getAllRegistrosByName(String nombre){
        return bd.registrosDelSuenoDao().getAllByName(nombre);
    }

    private List<RegistroEntity> getAllRegistros(){
        return bd.registrosDelSuenoDao().getAll();
    }

    private void addRegistro(RegistroEntity registro){
        bd.registrosDelSuenoDao().insertRegistro(registro);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Object returned = null;
        switch ((String)objects[0]){
            case "addRegistro":addRegistro((RegistroEntity) objects[1]);break;
            case "getAll":returned = getAllRegistros();break;
            case "getAllByName":returned = getAllRegistrosByName((String)objects[1]);break;
        }
        return returned;
    }
}
