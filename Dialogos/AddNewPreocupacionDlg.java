package com.barajasoft.higienedelsueo.Dialogos;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barajasoft.higienedelsueo.Datos.ConexionBDPreocupaciones;
import com.barajasoft.higienedelsueo.Datos.PreocupacionEntity;
import com.barajasoft.higienedelsueo.Listeners.OperationFinished;
import com.barajasoft.higienedelsueo.R;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddNewPreocupacionDlg extends Dialog {
    List<PreocupacionEntity> preocupaciones = new LinkedList<>();
    boolean exists = false;

    public AddNewPreocupacionDlg(@NonNull Activity context, OperationFinished listener) {
        super(context);
        try {
            preocupaciones = (List<PreocupacionEntity>) new ConexionBDPreocupaciones(context).execute("getAll").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.add_preocupacion_dlg);
        Button btnAgregar = findViewById(R.id.btnAgregarPreocupacionBD);
        EditText txtPreocupacion = findViewById(R.id.txtNewPreocupacion);
        btnAgregar.setOnClickListener(e->{
            if(txtPreocupacion.getText().toString().isEmpty()){
                Toast.makeText(context,"Debes agregar una preocupacion primero",Toast.LENGTH_SHORT).show();
            }else{
               if(preocupaciones!=null)
                   for(PreocupacionEntity p : preocupaciones){
                        if(p.getPreocupacion().equals(txtPreocupacion.getText().toString())){
                            exists = true;
                            break;
                        }
                   }
               if(exists){
                   Toast.makeText(context,"Esta preocupacion ya esta en la base de datos!",Toast.LENGTH_SHORT).show();
               }else{
                   new ConexionBDPreocupaciones(context).execute("addPreocupacion",txtPreocupacion.getText().toString());
                   Toast.makeText(context,"Se agrego esta preocupacion en la base de datos!",Toast.LENGTH_SHORT).show();
                   listener.finished("Dialog");
                   dismiss();
               }
            }
        });
    }
}
