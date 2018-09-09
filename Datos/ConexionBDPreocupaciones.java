package com.barajasoft.higienedelsueo.Datos;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class ConexionBDPreocupaciones extends AsyncTask {

    private PreocupacionesDB db;

    public ConexionBDPreocupaciones(Context context){
        if(db == null){
            db = Room.databaseBuilder(context,PreocupacionesDB.class,"Preocupaciones").build();
        }
    }

    private List<PreocupacionEntity> getAllPreocupaciones(){
        return db.interfazDAOPreocupaciones().getAll();
    }

    private void addPreocupacion(String preocupacion){
        PreocupacionEntity p = new PreocupacionEntity();
        p.setPreocupacion(preocupacion);
        db.interfazDAOPreocupaciones().addPreocupacion(p);
    }

    private void deletePreocupacion(String preocupacion){
        PreocupacionEntity p = new PreocupacionEntity();
        p.setPreocupacion(preocupacion);
        db.interfazDAOPreocupaciones().deletePreocupacion(p);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Object returned = null;
        switch ((String)objects[0]){
            case "getAll":returned = getAllPreocupaciones();break;
            case "addPreocupacion":addPreocupacion((String)objects[1]);break;
            case "deletePreocupacion":deletePreocupacion((String)objects[1]);break;
        }
        return returned;
    }
}
