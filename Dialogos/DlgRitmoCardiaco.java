package com.barajasoft.higienedelsueo.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.barajasoft.higienedelsueo.Listeners.DlgResult;
import com.barajasoft.higienedelsueo.Listeners.OperationFinished;
import com.barajasoft.higienedelsueo.R;

public class DlgRitmoCardiaco extends Dialog {
    public DlgRitmoCardiaco(@NonNull Context context, DlgResult listener) {
        super(context);
        setContentView(R.layout.dlg_ritmo_cardiaco);
        Button btn = findViewById(R.id.btnAceptarRitmoCardiaco);
        btn.setOnClickListener(e->{
            listener.result("dlg_ritmo_cardiaco",String.valueOf(127));
            dismiss();
        });
    }
}
