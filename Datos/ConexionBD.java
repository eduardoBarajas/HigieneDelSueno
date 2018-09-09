package com.barajasoft.higienedelsueo.Datos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.barajasoft.higienedelsueo.Entidades.Paciente;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConexionBD {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Pacientes");
    ArrayList<Paciente> allPacientes = new ArrayList<>();

    private ConexionBD instance = null;

    public ConexionBD getInstance(){
        if(instance==null)
            instance = new ConexionBD();
        return instance;
    }

    private ConexionBD(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                allPacientes.add(dataSnapshot.getValue(Paciente.class));
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    public Paciente getPaciente(String nombre){
        for(Paciente p : allPacientes){
            if(p.getNombre().equals(nombre))
                return p;
        }
        return null;
    }
}
