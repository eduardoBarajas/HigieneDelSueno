package com.barajasoft.higienedelsueo.Dialogos;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.barajasoft.higienedelsueo.Adaptadores.PreocupacionesRVAdapater;
import com.barajasoft.higienedelsueo.Datos.ConexionBDPreocupaciones;
import com.barajasoft.higienedelsueo.Datos.PreocupacionEntity;
import com.barajasoft.higienedelsueo.Listeners.DlgResult;
import com.barajasoft.higienedelsueo.Listeners.OperationFinished;
import com.barajasoft.higienedelsueo.R;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PreocupacionesDlg extends Dialog{
    List<PreocupacionEntity> preocupaciones = new LinkedList<>();
    PreocupacionesRVAdapater adapter;
    OperationFinished listener;
    public PreocupacionesDlg(@NonNull Activity context, DlgResult listener) {
        super(context);
        setContentView(R.layout.preocupaciones_dlg_layout);
        Button btnAddPreocupacion = findViewById(R.id.addPreocupacionBtn);

        try {
            preocupaciones = (List<PreocupacionEntity>) new ConexionBDPreocupaciones(context).execute("getAll").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        RecyclerView rv = findViewById(R.id.recyclerViewPreocupaciones);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        DlgResult lis = new DlgResult() {
            @Override
            public void result(String dlgTag, Object res) {
                if(dlgTag.equals("NewPreocupacionDialog")){
                    try {
                        preocupaciones = (List<PreocupacionEntity>) new ConexionBDPreocupaciones(context).execute("getAll").get();
                    } catch (Exception e) {
                        Log.e("error",e.getMessage());
                    }
                    adapter = new PreocupacionesRVAdapater(preocupaciones,this);
                    rv.setAdapter(adapter);
                }
                if(dlgTag.equals("PreocupacionesAdapter")){
                    Toast.makeText(context,"Seleccionaste una preocupacion!",Toast.LENGTH_SHORT).show();
                    listener.result("PreocupacionesDlg",res);
                    dismiss();

                }
            }
        };
//        listener = new OperationFinished() {
//            @Override
//            public void finished(String type) {
//                if(type.equals("Dialog")){
//                    try {
//                        preocupaciones = (List<PreocupacionEntity>) new ConexionBDPreocupaciones(context).execute("getAll").get();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                    adapter = new PreocupacionesRVAdapater(preocupaciones,listener);
//                    rv.setAdapter(adapter);
//                }
//                if(type.equals("Adapter")){
//                    Toast.makeText(context,"Seleccionaste una preocupacion!",Toast.LENGTH_SHORT).show();
//                    dismiss();
//                }
//            }
//        };
        if(preocupaciones==null){
            Log.e("ERROR","ESTABA VACIO AL PARECER");
            adapter = new PreocupacionesRVAdapater();
        }else{
            adapter = new PreocupacionesRVAdapater(preocupaciones,lis);
            Log.e("NO_ERROR","NO ESTABA VACIO AL PARECER");
        }
        rv.setAdapter(adapter);
        btnAddPreocupacion.setOnClickListener(e->{
            AddNewPreocupacionDlg dlg = new AddNewPreocupacionDlg(context,lis);
            dlg.show();
        });
    }
}
