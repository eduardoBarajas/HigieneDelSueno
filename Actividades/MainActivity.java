package com.barajasoft.higienedelsueo.Actividades;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.barajasoft.higienedelsueo.BroadcastReceivers.Alarm;
import com.barajasoft.higienedelsueo.Datos.Configuracion;
import com.barajasoft.higienedelsueo.Datos.SensoresVal;
import com.barajasoft.higienedelsueo.Datos.Sesion;
import com.barajasoft.higienedelsueo.Dialogos.DlgRitmoCardiaco;
import com.barajasoft.higienedelsueo.Dialogos.NivelEstresDlg;
import com.barajasoft.higienedelsueo.Dialogos.PreocupacionesDlg;
import com.barajasoft.higienedelsueo.Entidades.CurrentTime;
import com.barajasoft.higienedelsueo.Entidades.Paciente;
import com.barajasoft.higienedelsueo.Listeners.DlgResult;
import com.barajasoft.higienedelsueo.R;
import com.barajasoft.higienedelsueo.Servicios.NoiseService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements SensorEventListener, Observer {
    public static final int CONF_CHANGE = 777;
    private SensorManager sensorManager;
    private Sensor iluminacion;
    private Sensor accelerometro;
    private float previousValue = 0 ;
    private float[] previousPosition = {0,0};
    private Paciente pacienteActual;
    private TextView txtNombre,txtSexo,txtHoraDespertar,txtHoraDormir,txtEdad;
    private Configuracion configuracion;
    private SensoresVal valores_sensores;
    private Queue<String> hora_mediciones;
    private boolean despierto = false;
    private boolean dormido = false;
    private int numeroMedicionesRealizadas = 0;
    private ConstraintLayout root;
    private DlgResult listener;
    private Sesion currentSesion;
    private boolean noiseServiceStarted = false;
    private boolean sensorsStarted = false;
    private boolean fourHourMark = false;
    private boolean firstMedicion = false;
    private boolean secondMedicion = false;
    private boolean thirdMedicion = false;
    private boolean fourMedicion = false;
    private boolean halfHourToSleepMark = false;
    private boolean halfHourToWakeUpMark = false;
    private boolean dlgCanceled = false;
    private CurrentTime time;
    private TextView timerProximaMedicion, tiempoActual;
    private String[] hora = new String[]{"0","0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentSesion = Sesion.getInstance();
        valores_sensores = SensoresVal.getInstance();
        //Obtener configuracion inicial
        configuracion = new Configuracion();
        setContentView(R.layout.activity_main);
        timerProximaMedicion = findViewById(R.id.textView2);
        tiempoActual = findViewById(R.id.txtTiempo);
        root = findViewById(R.id.root);
        txtEdad = findViewById(R.id.txtEdadMain);
        Button btn1 = findViewById(R.id.button2);
        Button btn2 = findViewById(R.id.button3);
        time = CurrentTime.getInstance(getApplicationContext());
        time.addObserver(this);
        listener = new DlgResult() {
            @Override
            public void result(String dlgTag, Object res) {
                switch (dlgTag){
                    case "dlg_ritmo_cardiaco":{
                            if(!dlgCanceled){
                                currentSesion.setMeds_cardiacos(numeroMedicionesRealizadas,Integer.parseInt(String.valueOf(res)));
                                numeroMedicionesRealizadas++;
                            }
                            break;
                    }
                    case "PreocupacionesDlg":currentSesion.setTiene_preocupaciones(Integer.parseInt(String.valueOf(res)));break;
                    case "NivelEstresDlg":{
                            if(!dlgCanceled)
                                currentSesion.setNivel_estres(numeroMedicionesRealizadas,String.valueOf(res));
                        break;
                    }
                }
            }
        };
        btn1.setOnClickListener(e->{
            if(Integer.parseInt(hora[1])+10 >= 60){
                if(Integer.parseInt(hora[0])+1>23){
                    hora[0] = "0";
                    hora[1] = String.valueOf(Math.abs(Integer.parseInt(hora[1])+10 - 60));
                }else{
                    hora[0] = String.valueOf(Integer.parseInt(hora[0])+1);
                    hora[1] = "0";
                }
            }else{
                hora[1] = String.valueOf(Integer.parseInt(hora[1])+10);
            }
            update(null,null);
        });
        btn2.setOnClickListener(e->{
            if(Integer.parseInt(hora[1])+1 >= 60){
                if(Integer.parseInt(hora[0])+1>23){
                    hora[0] = "0";
                    hora[1] = String.valueOf(Math.abs(Integer.parseInt(hora[1])+1 - 60));
                }else{
                    hora[0] = String.valueOf(Integer.parseInt(hora[0])+1);
                    hora[1] = "0";
                }
            }else{
                hora[1] = String.valueOf(Integer.parseInt(hora[1])+1);
            }
            update(null,null);
        });
        txtNombre = findViewById(R.id.txtNombreMain);
        txtSexo = findViewById(R.id.txtSexoMain);
        txtHoraDespertar = findViewById(R.id.txtHoraDespertarMain);
        txtHoraDormir = findViewById(R.id.txtHoraDormirMain);
        loadPaciente();
        generarTiemposMediciones();
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, 201);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        iluminacion = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(iluminacion == null)
            Toast.makeText(getApplicationContext(),"No tiene sensor de luz!!",Toast.LENGTH_SHORT).show();
        accelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometro == null)
            Toast.makeText(getApplicationContext(),"No tiene acelerometro!!",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONF_CHANGE && resultCode == RESULT_OK){
            String[] conf = data.getStringArrayExtra("NuevaConf");
            txtNombre.setText(conf[0]);
            txtSexo.setText(conf[1]);
            txtHoraDespertar.setText(conf[3]);
            txtHoraDormir.setText(conf[2]);
            txtEdad.setText(conf[4]);
            pacienteActual = new Paciente(conf[0],conf[1],conf[2],conf[3],conf[4]);
            //reinicio las mediciones
            restartMediciones();
        }else{
            Toast.makeText(getApplicationContext(),"Cancelaste el cambio en la conf",Toast.LENGTH_SHORT).show();
        }
    }

    private void restartMediciones() {
        currentSesion.resetSesion();
        currentSesion.setPaciente(pacienteActual);
        numeroMedicionesRealizadas = 0;
        despierto = false;
        dormido = false;
        fourHourMark = false;
        firstMedicion = false;
        secondMedicion = false;
        thirdMedicion = false;
        fourMedicion = false;
        halfHourToSleepMark = false;
        halfHourToWakeUpMark = false;
        dlgCanceled = false;
        if(sensorsStarted)
            stopSensors();
        generarTiemposMediciones();
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            if(previousValue > event.values[0] || previousValue < event.values[0]){
                valores_sensores.setLux(event.values[0]);
                Log.e("LUX",String.valueOf(event.values[0]));
            }
            previousValue = event.values[0];
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if(previousPosition[0] > event.values[1] + 1 || previousPosition[0] < event.values[1] - 1 || previousPosition[1] > event.values[0] + 1 || previousPosition[1] < event.values[0] - 1  ){
                valores_sensores.setPosicionX(event.values[1]);
                valores_sensores.setPosicionY(event.values[0]);
            }
            previousPosition[0] = event.values[1];
            previousPosition[1] = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        if(sensor.getType() == Sensor.TYPE_LIGHT){
            Log.e("Sensor Changed", "Accuracy :" + i);
        }
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.e("Sensor Changed", "Accuracy :" + i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //si quiero que todo el tiempo esten funcionando los sensores activarlos aqui
        if(sensorsStarted)
            activateSensors();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        //aqui parar los sensores para que cuando se cierre la aplicacion no sigan corriendo
        stopSensors();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.action_configuracion:
                intent = new Intent(this,ConfiguracionInicial.class);
                intent.putExtra("Conf",new String[]{pacienteActual.getNombre(),pacienteActual.getSexo(),pacienteActual.getHora_dormir()
                ,pacienteActual.getHora_despertar(),String.valueOf(pacienteActual.getEdad())});
                startActivityForResult(intent,CONF_CHANGE);
                break;
            case R.id.action_registros:
                intent = new Intent(this,RegistrosViewer.class);
                startActivity(intent);
                break;
            default: break;
        }
        return true;
    }

    @Override
    public void update(Observable observable, Object o) {
        //String[] hora = (String[])o;
//        if(sensorsStarted){
//            Sesion.getInstance().addMediciondB(valores_sensores.getDb());
//            Sesion.getInstance().addMedicionLux(valores_sensores.getLux());
//        }
        Log.e("Mediciones en cola","en la cola");
        for(String h : hora_mediciones){
            Log.e("horaMedicion",h);
        }
        tiempoActual.setText(hora[0]+":"+hora[1]);
        Log.e("Hora Actual", hora[0] + ":" + hora[1]);
        int horaDormir = Integer.parseInt(pacienteActual.getHora_dormir().split(":")[0]) * 60 + Integer.parseInt(pacienteActual.getHora_dormir().split(":")[1]);
        int horaDespertar = Integer.parseInt(pacienteActual.getHora_despertar().split(":")[0]) * 60 + Integer.parseInt(pacienteActual.getHora_despertar().split(":")[1]);
        //int horaActualMedicion = (Integer.parseInt(hora_mediciones.peek().split(":")[0]) * 60) + Integer.parseInt(hora_mediciones.peek().split(":")[1]);
        int horaActual = Integer.parseInt(hora[0]) * 60 + Integer.parseInt(hora[1]);
        //int horaActualMedicionMasUnaHora = (Integer.parseInt(hora_mediciones.peek().split(":")[0]) * 60 + 60) + Integer.parseInt(hora_mediciones.peek().split(":")[1]);
        Log.e("Mediciones", "Numero de mediciones: " + String.valueOf(numeroMedicionesRealizadas));
        Log.e("Hora Actual", String.valueOf(horaActual));
        Log.e("Hora Actual", hora[0] + ":" + hora[1]);
        //Log.e("Hora Actual Medicion", String.valueOf(horaActualMedicion));
        //Log.e("Hora Actual Medicion", hora_mediciones.peek());
//        Log.e("Hora adelantada", String.valueOf(horaActualMedicionMasUnaHora / 60) + ":" + String.valueOf(horaActualMedicionMasUnaHora % 60));
//        Log.e("Hora Adelantada ", String.valueOf(horaActualMedicionMasUnaHora));
        if(horaActual == horaDormir-(4*60)){
            Toast.makeText(getApplicationContext(),"Faltan 4 horas para dormir",Toast.LENGTH_SHORT).show();
            fourHourMark = true;
        }
        if(sensorsStarted){
            Sesion.getInstance().addMediciondB(valores_sensores.getDb());
            Sesion.getInstance().addMedicionLux(valores_sensores.getLux());
            if(!dormido && halfHourToSleepMark){
                if(valores_sensores.getDb()<=40){
                    if(valores_sensores.getLux()<=10){
                        if(valores_sensores.getPosicionX() == previousPosition[0] && previousPosition[1] == valores_sensores.getPosicionY()){
                            dormido = true;
                            Snackbar.make(root, "Segun los sensores se durmio", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            if(!despierto && halfHourToWakeUpMark){
                if(valores_sensores.getDb()>=40){
                    if(valores_sensores.getLux()>=10){
                        if(valores_sensores.getPosicionX() != previousPosition[0] || previousPosition[1] != valores_sensores.getPosicionY()){
                            despierto = true;
                            Snackbar.make(root, "Segun los sensores se desperto", Snackbar.LENGTH_SHORT).show();
                            if(sensorsStarted)
                                stopSensors();
                        }
                    }
                }
            }
        }
        if(fourHourMark){
            if(!firstMedicion){
                if(horaActual == Integer.parseInt(hora_mediciones.peek().split(":")[0])*60+Integer.parseInt(hora_mediciones.peek().split(":")[1])){
                    firstMedicion = true;
                    showMedicionCardiaca();
                    hora_mediciones.remove(hora_mediciones.peek());
                }
            }
            if(!secondMedicion&&firstMedicion){
                if(horaActual == Integer.parseInt(hora_mediciones.peek().split(":")[0])*60+Integer.parseInt(hora_mediciones.peek().split(":")[1])){
                    secondMedicion = true;
                    showMedicionCardiaca();
                    hora_mediciones.remove(hora_mediciones.peek());
                }
            }
            if(!thirdMedicion&&secondMedicion){
                if(horaActual == Integer.parseInt(hora_mediciones.peek().split(":")[0])*60+Integer.parseInt(hora_mediciones.peek().split(":")[1])){
                    thirdMedicion = true;
                    showMedicionCardiaca();
                    hora_mediciones.remove(hora_mediciones.peek());
                }
            }
            if(!fourMedicion&&thirdMedicion){
                if(horaActual == Integer.parseInt(hora_mediciones.peek().split(":")[0])*60+Integer.parseInt(hora_mediciones.peek().split(":")[1])){
                    fourMedicion = true;
                    showMedicionCardiaca();
                    hora_mediciones.remove(hora_mediciones.peek());
                }
            }
            if(!halfHourToSleepMark&&fourMedicion){
                if(horaActual == Integer.parseInt(hora_mediciones.peek().split(":")[0])*60+Integer.parseInt(hora_mediciones.peek().split(":")[1])){
                    halfHourToSleepMark = true;
                    showPreocupacionesDlg();
                    if(!sensorsStarted)
                        activateSensors();
                    hora_mediciones.remove(hora_mediciones.peek());
                }
            }
            if(horaActual == horaDormir && !dormido){
                Toast.makeText(getApplicationContext(),"Se Durmio",Toast.LENGTH_SHORT).show();
                dormido = true;
            }
        }

        if(horaActual == (horaDespertar-30) && dormido){
            halfHourToWakeUpMark = true;
            Toast.makeText(getApplicationContext(),"Media Hora para que se despierte",Toast.LENGTH_SHORT).show();
            hora_mediciones.remove(hora_mediciones.peek());
        }
        if(halfHourToWakeUpMark){
            if(horaActual == horaDespertar){
                Toast.makeText(getApplicationContext(),"Se Desperto",Toast.LENGTH_SHORT).show();
                despierto = true;
                if(sensorsStarted)
                    stopSensors();
                for(Double v : Sesion.getInstance().getMedicionesLux()){
                    Log.e("Medicion ", String.valueOf(v));
                }
            }
        }
        setTimerProximaMedicion(horaActual);
    }
    private void showMedicionCardiaca() {
        broadcastIntent("Medicion de ritmo cardiaco");
        //pedir medicion cardiaca
        NivelEstresDlg dlg = new NivelEstresDlg(this, listener);
        dlg.setOnDismissListener(e -> {
            //Snackbar.make(root, "Se realiza medicion numero " + String.valueOf(numeroMedicionesRealizadas) + " del ritmo cardiaco", Snackbar.LENGTH_SHORT).show();
            DlgRitmoCardiaco dlgRitmoCardiaco = new DlgRitmoCardiaco(this, listener);
            dlgRitmoCardiaco.setCanceledOnTouchOutside(false);
            if (!dlgRitmoCardiaco.isShowing() && !dlgCanceled) {
                dlgRitmoCardiaco.show();
                dlgRitmoCardiaco.setOnCancelListener(ex -> {
                    currentSesion.setMeds_cardiacos(numeroMedicionesRealizadas, 0);
                    currentSesion.setNivel_estres(numeroMedicionesRealizadas, "NA");
                    numeroMedicionesRealizadas++;
                    Snackbar.make(root, "Se cancelo la medicion", Snackbar.LENGTH_SHORT).show();
                });
            }
        });
        dlg.setOnCancelListener(e->{
            currentSesion.setMeds_cardiacos(numeroMedicionesRealizadas, 0);
            currentSesion.setNivel_estres(numeroMedicionesRealizadas, "NA");
            numeroMedicionesRealizadas++;
            Snackbar.make(root, "Se cancelo la medicion", Snackbar.LENGTH_SHORT).show();
            dlgCanceled = true;
        });
        dlg.show();
    }

    private void showPreocupacionesDlg(){
        broadcastIntent("Tienes Alguna Preocupacion?");
        PreocupacionesDlg dlg = new PreocupacionesDlg(this, listener);
        dlg.setOnCancelListener(e -> {
            Toast.makeText(getApplicationContext(), "Cancelaste el dlg", Toast.LENGTH_SHORT).show();
            Sesion.getInstance().setTiene_preocupaciones(-1); //menos uno por que fue cancelado
        });
        dlg.setCanceledOnTouchOutside(false);
        if (!dlg.isShowing()) {
            dlg.show();
        }

    }

    private void setTimerProximaMedicion(int horaActual){
        int horaMedicion = 0;
        int next = 0;
        if(hora_mediciones.size()>0){
            horaMedicion = Integer.parseInt(hora_mediciones.peek().split(":")[0])*60+Integer.parseInt(hora_mediciones.peek().split(":")[1]);
            if(horaMedicion - horaActual < 0 ){
                next = horaMedicion - horaActual + 1440;
            }else{
                next = horaMedicion - horaActual;
            }
        }
        timerProximaMedicion.setText("Falta:\n"+String.valueOf((next)/60)+":"+String.valueOf((next)%60));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(getApplicationContext(),"Se entro con la notificacion",Toast.LENGTH_SHORT).show();
    }

    private void broadcastIntent(String type) {
        Intent intent = new Intent();
        intent.putExtra("tipo",type);
        intent.setAction("android.intent.action.NUEVA_MEDICION");
        sendBroadcast(intent);
    }

    private void generarTiemposMediciones(){
        //crear pila con los horarios en que se debe checar el paciente
        hora_mediciones = new LinkedList<>();
        for(int i=4;i>0;i--){
            if(Integer.parseInt(pacienteActual.getHora_dormir().split(":")[0])-i<0){
                hora_mediciones.add(String.valueOf(24+(Integer.parseInt(pacienteActual.getHora_dormir().split(":")[0])-i))+":"+pacienteActual.getHora_dormir().split(":")[1]);
            }else{
                hora_mediciones.add(String.valueOf(Integer.parseInt(pacienteActual.getHora_dormir().split(":")[0])-i)+":"+pacienteActual.getHora_dormir().split(":")[1]);
            }
        }
        int mediaHoraAntesDormir = 0;
        if(Integer.parseInt(pacienteActual.getHora_dormir().split(":")[0])==0 && Integer.parseInt(pacienteActual.getHora_dormir().split(":")[1])-30 < 0){
            int res = Integer.parseInt(pacienteActual.getHora_dormir().split(":")[1])-30;
            mediaHoraAntesDormir = 24*60+res;
        }else{
            mediaHoraAntesDormir = Integer.parseInt(pacienteActual.getHora_dormir().split(":")[0])*60+Integer.parseInt(pacienteActual.getHora_dormir().split(":")[1])-30;
        }
        hora_mediciones.add(String.valueOf(mediaHoraAntesDormir/60)+":"+String.valueOf(mediaHoraAntesDormir%60));

        int mediaHoraAntesDespertar = 0;
        if(Integer.parseInt(pacienteActual.getHora_despertar().split(":")[0])==0 && Integer.parseInt(pacienteActual.getHora_despertar().split(":")[1])-30 < 0){
            int res = Integer.parseInt(pacienteActual.getHora_despertar().split(":")[1])-30;
            mediaHoraAntesDespertar = 24*60+res;
        }else{
            mediaHoraAntesDespertar = Integer.parseInt(pacienteActual.getHora_despertar().split(":")[0])*60+Integer.parseInt(pacienteActual.getHora_despertar().split(":")[1])-30;
        }
        hora_mediciones.add(String.valueOf(mediaHoraAntesDespertar/60)+":"+String.valueOf(mediaHoraAntesDespertar%60));
    }

    private void activateSensors(){
        sensorManager.registerListener(this,iluminacion,SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this,accelerometro,SensorManager.SENSOR_DELAY_FASTEST);
        if(!noiseServiceStarted){
            Intent intent = new Intent(this,NoiseService.class);
            startService(intent);
            noiseServiceStarted = true;
        }
        sensorsStarted = true;
    }

    private void stopSensors(){
        sensorsStarted = false;
        sensorManager.unregisterListener(this);
        Alarm alarm = Alarm.getInstance();
        alarm.cancelAlarm(getApplicationContext());
        Log.e("ServicoAudio","SE cancelo");
        CurrentTime timer = CurrentTime.getInstance(getApplicationContext());
        timer.stop();
        Log.e("Temporizador","SE cancelo");
    }

    private void loadPaciente(){
        configuracion.loadJson();
        configuracion.parseJson();
        pacienteActual = configuracion.getConf();
        txtHoraDormir.setText(pacienteActual.getHora_dormir());
        txtHoraDespertar.setText(pacienteActual.getHora_despertar());
        txtSexo.setText(pacienteActual.getSexo());
        txtNombre.setText(pacienteActual.getNombre());
        txtEdad.setText(String.valueOf(pacienteActual.getEdad()));
        currentSesion.setPaciente(pacienteActual);
    }
}