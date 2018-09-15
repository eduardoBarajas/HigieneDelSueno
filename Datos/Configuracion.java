package com.barajasoft.higienedelsueo.Datos;

import android.os.Environment;
import android.util.Log;

import com.barajasoft.higienedelsueo.Entidades.Paciente;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuracion {
    private StringBuilder stringBuilder;
    private File file,folder;
    private Paciente conf;
    public Configuracion(){
        stringBuilder = new StringBuilder();
        folder = new File(Environment.getExternalStorageDirectory()+File.separator+"Higiene del sueño"+File.separator+"Configuracion");
        file = new File(folder.getPath()+File.separator+"conf.json");
    }

    public boolean checkIfExists(){
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }

    public boolean createFile(){
        try {
            if(!folder.exists()){
                folder.mkdirs();
                file.createNewFile();
            }else{
                file.createNewFile();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadJson(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
        }catch (Exception ex){
            Log.e("Error de lectura", ex.getMessage());
        }
    }

    public void writeJsonFile(String body){
        try{
            FileWriter writer = new FileWriter(file);
            writer.append(body);
            writer.flush();
            writer.close();
        }catch (Exception ex){
            Log.e("Error de escritura",ex.getMessage());
        }
    }

    public void parseJson(){
        try{
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            conf = new Paciente();
            conf.setNombre(jsonObject.getString("Nombre"));
            conf.setSexo(jsonObject.getString("Sexo"));
            conf.setHora_despertar(jsonObject.getString("Hora_Despertar"));
            conf.setHora_dormir(jsonObject.getString("Hora_Dormir"));
            conf.setEdad(jsonObject.getInt("Edad"));
        }catch (Exception ex){
            Log.e("Error en el parsing",ex.getMessage());
        }
    }

    public void deleteConfiguration(){
        if(file.exists()){
            file.delete();
            stringBuilder = new StringBuilder();
            conf = null;
        }
    }

    public Paciente getConf(){
        return conf;
    }
}
