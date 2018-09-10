package com.barajasoft.higienedelsueo.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barajasoft.higienedelsueo.Datos.PreocupacionEntity;
import com.barajasoft.higienedelsueo.Datos.Sesion;
import com.barajasoft.higienedelsueo.Listeners.DlgResult;
import com.barajasoft.higienedelsueo.Listeners.OperationFinished;
import com.barajasoft.higienedelsueo.R;

import java.util.LinkedList;
import java.util.List;

public class PreocupacionesRVAdapater extends RecyclerView.Adapter<PreocupacionesRVAdapater.MyViewHolder>{
    List<PreocupacionEntity> preocupaciones = new LinkedList<>();
    DlgResult listener;

    public PreocupacionesRVAdapater(){ }

    public PreocupacionesRVAdapater(List<PreocupacionEntity> p, DlgResult listener){
        preocupaciones = p;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.preocupacion_model,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PreocupacionEntity p = preocupaciones.get(position);
        holder.txtPreocupacion.setText(p.getPreocupacion());
        holder.txtPreocupacion.setOnClickListener(e->{
            listener.result("PreocupacionesAdapter",1);
        });
    }

    @Override
    public int getItemCount() {
        return preocupaciones.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtPreocupacion;
        public MyViewHolder(View view) {
            super(view);
            txtPreocupacion = view.findViewById(R.id.txtPreocupacion);
        }
    }
}
