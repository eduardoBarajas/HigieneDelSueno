package com.barajasoft.higienedelsueo.Actividades;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.barajasoft.higienedelsueo.Adaptadores.RegistrosTableViewAdapter;
import com.barajasoft.higienedelsueo.Datos.ConexionBDRegistrosDelSueno;
import com.barajasoft.higienedelsueo.Datos.RegistroEntity;
import com.barajasoft.higienedelsueo.Models.CellModel;
import com.barajasoft.higienedelsueo.Models.ColumHeaderModel;
import com.barajasoft.higienedelsueo.Models.RowHeaderModel;
import com.barajasoft.higienedelsueo.R;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RegistrosViewer extends AppCompatActivity {
    private List<RowHeaderModel> rowHeaderList = new LinkedList<>();;
    private List<ColumHeaderModel> columHeaderList = new LinkedList<>();;
    private List<List<CellModel>> cellList = new LinkedList<>();;
    private String[] col = {"fecha","id","nombre","sexo","edad","horaDormir","horaDespertar",
            "sustanciasDañinas","ritmoCardico1","ritmoCardico2","ritmoCardico3","ritmoCardico4",
            "sensacionEstres1","sensacionEstres2","sensacionEstres3","sensacionEstres4",
            "preocupaciones","promedioRuido","promedioIluminacion","horaEnQueDesperto","horaEnQueDurmio"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for(String str : col){
            columHeaderList.add(new ColumHeaderModel(str));
        }
        setContentView(R.layout.registros_viewer_layout);
        com.evrencoskun.tableview.TableView tb = findViewById(R.id.content);
        RegistrosTableViewAdapter adapter = new RegistrosTableViewAdapter(getApplicationContext());
        tb.setAdapter(adapter);
        TextView txt = findViewById(R.id.textView16);
        try {
            ArrayList<RegistroEntity> registros = (ArrayList<RegistroEntity>) new ConexionBDRegistrosDelSueno(getApplicationContext()).execute("getAll").get();
            for(RegistroEntity r: registros)
                setData(r);
            adapter.setAllItems(columHeaderList,rowHeaderList,cellList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void setData(RegistroEntity r){
        rowHeaderList.add(new RowHeaderModel(String.valueOf(rowHeaderList.size())));
        List<CellModel> l = new LinkedList<>();
        l.add(new CellModel(col[0],r.getFecha()));
        l.add(new CellModel(col[1],String.valueOf(r.getId_registro())));
        l.add(new CellModel(col[2],r.getNombre()));
        l.add(new CellModel(col[3],r.getSexo()));
        l.add(new CellModel(col[4],String.valueOf(r.getEdad())));
        l.add(new CellModel(col[5],r.getDormir()));
        l.add(new CellModel(col[6],r.getDespertar()));
        l.add(new CellModel(col[7],String.valueOf(r.getIngerio_sustancias())));
        l.add(new CellModel(col[8],String.valueOf(r.getRitmo_cardiaco1())));
        l.add(new CellModel(col[9],String.valueOf(r.getRitmo_cardiaco2())));
        l.add(new CellModel(col[10],String.valueOf(r.getRitmo_cardiaco3())));
        l.add(new CellModel(col[11],String.valueOf(r.getRitmo_cardiaco4())));
        l.add(new CellModel(col[12],String.valueOf(r.getSensacion_estres1())));
        l.add(new CellModel(col[13],String.valueOf(r.getSensacion_estres2())));
        l.add(new CellModel(col[14],String.valueOf(r.getSensacion_estres3())));
        l.add(new CellModel(col[15],String.valueOf(r.getSensacion_estres4())));
        l.add(new CellModel(col[16],String.valueOf(r.getPreocupaciones())));
        l.add(new CellModel(col[17],String.valueOf(r.getPromedio_ruido())));
        l.add(new CellModel(col[18],String.valueOf(r.getPromedio_iluminacion())));
        l.add(new CellModel(col[19],String.valueOf(r.getHora_en_que_desperto())));
        l.add(new CellModel(col[20],String.valueOf(r.getHora_en_que_durmio())));
        cellList.add(l);
    }
}
