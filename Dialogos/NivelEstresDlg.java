package com.barajasoft.higienedelsueo.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.Toast;

import com.barajasoft.higienedelsueo.Datos.Sesion;
import com.barajasoft.higienedelsueo.Listeners.DlgResult;
import com.barajasoft.higienedelsueo.Listeners.OperationFinished;
import com.barajasoft.higienedelsueo.R;

public class NivelEstresDlg extends Dialog {
    public NivelEstresDlg(@NonNull Context context, DlgResult listener) {
        super(context);
        setContentView(R.layout.nivel_estres_dlg);
        Button btnMuyBajo = findViewById(R.id.btnEstresMuyBajo);
        btnMuyBajo.setOnClickListener(e->{
            //sesion.setNivel_estres("Muy Bajo");
            listener.result("NivelEstresDlg","Muy Bajo");
            Toast.makeText(context,"Se elegio muy bajo",Toast.LENGTH_SHORT).show();
            dismiss();
        });
        Button btnBajo = findViewById(R.id.BtnEstresBajo);
        btnBajo.setOnClickListener(e->{
            //sesion.setNivel_estres("Bajo");
            listener.result("NivelEstresDlg","Bajo");
            Toast.makeText(context,"Se elegio bajo",Toast.LENGTH_SHORT).show();
            dismiss();
        });
        Button btnIntermedio = findViewById(R.id.BtnEstresIntermedio);
        btnIntermedio.setOnClickListener(e->{
            //sesion.setNivel_estres("Intermedio");
            listener.result("NivelEstresDlg","Intermedio");
            Toast.makeText(context,"Se elegio intermedio",Toast.LENGTH_SHORT).show();
            dismiss();
        });
        Button btnAlto = findViewById(R.id.BtnEstresAlto);
        btnAlto.setOnClickListener(e->{
            //sesion.setNivel_estres("Alto");
            listener.result("NivelEstresDlg","Alto");
            Toast.makeText(context,"Se elegio alto",Toast.LENGTH_SHORT).show();
            dismiss();
        });
        Button btnMuyAlto = findViewById(R.id.BtnEstresMuyAlto);
        btnMuyAlto.setOnClickListener(e->{
            //sesion.setNivel_estres("Muy Alto");
            listener.result("NivelEstresDlg","Muy Alto");
            Toast.makeText(context,"Se elegio muy alto",Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }
}
