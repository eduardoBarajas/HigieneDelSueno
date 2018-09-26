package com.barajasoft.higienedelsueo.Dialogos;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.barajasoft.higienedelsueo.Adaptadores.PreocupacionesRVAdapater;
import com.barajasoft.higienedelsueo.Datos.PreocupacionEntity;
import com.barajasoft.higienedelsueo.Listeners.DlgResult;
import com.barajasoft.higienedelsueo.R;

import java.util.LinkedList;
import java.util.List;

public class PreocupacionesDlg extends Dialog{
    PreocupacionesRVAdapater adapter;
    public PreocupacionesDlg(@NonNull Activity context, DlgResult listener) {
        super(context);
        setContentView(R.layout.preocupaciones_dlg_layout);
        Button btnSinPreocupacions = findViewById(R.id.btnSinPreocupaciones);
        btnSinPreocupacions.setOnClickListener(e->{
            listener.result("PreocupacionesDlg","0");
            dismiss();
        });
        RecyclerView rv = findViewById(R.id.recyclerViewPreocupaciones);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        DlgResult lis = new DlgResult() {
            @Override
            public void result(String dlgTag, Object res) {
                if(dlgTag.equals("PreocupacionesAdapter")){
                    Toast.makeText(context,"Seleccionaste una preocupacion!",Toast.LENGTH_SHORT).show();
                    listener.result("PreocupacionesDlg",res);
                    dismiss();
                }
            }
        };
        adapter = new PreocupacionesRVAdapater(getPreocupaciones(),lis);
        rv.setAdapter(adapter);
    }

    private List<PreocupacionEntity> getPreocupaciones() {
        List<PreocupacionEntity> preocupaciones = new LinkedList<>();
        String[] p = new String[]{"Tengo que recoger a los niños","Cita con el medico","Ir al banco","La casa esta desordenada",
                "Tareas del dia siguiente","Tengo que hablar con un familiar", "Discusiones con amigos/pareja",
                "Quedan pocas horas hasta que suene el despertador"};
        for(String s: p){
            PreocupacionEntity e = new PreocupacionEntity();
            e.setPreocupacion(s);
            preocupaciones.add(e);
        }
        return preocupaciones;
    }
}
