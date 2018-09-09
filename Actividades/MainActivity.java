/*package com.barajasoft.higienedelsueo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.CameraError;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends com.androidhiddencamera.HiddenCameraActivity {
    //implements SensorEventListener
    private SensorManager sensorManager;
    private Sensor iluminacion;
    private TextureView textureView;
    private boolean clicked = false;

    private CameraConfig cameraConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = findViewById(R.id.button);
        boton.setOnClickListener(e->{
            takePicture();

        });
        cameraConfig = new CameraConfig()
                .getBuilder(this)
                .setCameraFacing(CameraFacing.REAR_FACING_CAMERA)
                .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
                .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                .setImageRotation(CameraRotation.ROTATION_270)
                .build();
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            startCamera(cameraConfig);
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},101);
        }
        TextView txt = findViewById(R.id.hello);
        textureView = findViewById(R.id.textureView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public void onSensorChanged(SensorEvent event){
//        float luminicense = event.values[0];
//        Toast.makeText(getApplicationContext(),String.valueOf(luminicense),Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        //sensorManager.registerListener(this,iluminacion,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        //sensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onImageCapture(@NonNull File imageFile) {
        Log.e("Seguun","SE tomo la foto");
    }

    @Override
    public void onCameraError(int errorCode) {
        switch (errorCode) {
            case CameraError.ERROR_CAMERA_OPEN_FAILED:
                //Camera open failed. Probably because another application
                //is using the camera
                break;
            case CameraError.ERROR_IMAGE_WRITE_FAILED:
                //Image write failed. Please check if you have provided WRITE_EXTERNAL_STORAGE permission
                break;
            case CameraError.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE:
                //camera permission is not available
                //Ask for the camra permission before initializing it.
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION:
                //Display information dialog to the user with steps to grant "Draw over other app"
                //permission for the app.
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_FRONT_CAMERA:
                Toast.makeText(this, "Your device does not have front camera.", Toast.LENGTH_LONG).show();
                break;
        }
    }
}*/
package com.barajasoft.higienedelsueo.Actividades;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidhiddencamera.CameraConfig;
import com.barajasoft.higienedelsueo.BroadcastReceivers.Alarm;
import com.barajasoft.higienedelsueo.Datos.ConexionBDRegistrosDelSueno;
import com.barajasoft.higienedelsueo.Datos.Configuracion;
import com.barajasoft.higienedelsueo.Datos.RegistroEntity;
import com.barajasoft.higienedelsueo.Datos.SensoresVal;
import com.barajasoft.higienedelsueo.Dialogos.NivelEstresDlg;
import com.barajasoft.higienedelsueo.Dialogos.PreocupacionesDlg;
import com.barajasoft.higienedelsueo.Entidades.CurrentTime;
import com.barajasoft.higienedelsueo.Entidades.Paciente;
import com.barajasoft.higienedelsueo.HeartRateMonitor;
import com.barajasoft.higienedelsueo.R;
import com.barajasoft.higienedelsueo.Servicios.IluminationService;
import com.barajasoft.higienedelsueo.Servicios.NoiseService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements SensorEventListener, Observer {
    public static final int CONF_CHANGE = 777;
    private SensorManager sensorManager;
    private Sensor iluminacion;
    private Sensor accelerometro;
    private TextureView textureView;
    private float previousValue = 0 ;
    private float[] previousPosition = {0,0};
    private Paciente pacienteActual;
    private CameraConfig cameraConfig;
    private TextView txtNombre,txtSexo,txtHoraDespertar,txtHoraDormir;
    private Configuracion configuracion;
    private SensoresVal valores_sensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        valores_sensores = SensoresVal.getInstance();
        //Obtener configuracion inicial
        configuracion = new Configuracion();
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button2);
        Button btn2 = findViewById(R.id.button3);
        TextView textView = findViewById(R.id.textView2);

        CurrentTime time = CurrentTime.getInstance(getApplicationContext());
        time.addObserver(this);

        btn1.setOnClickListener(e->{
//            RegistroEntity nuevo = new RegistroEntity();
////            nuevo.setSexo("Hombre");
////            nuevo.setRitmo_cardiaco(121);
////            nuevo.setPreocupaciones(0);
////            nuevo.setNombre("Eduardo");
////            nuevo.setIngerio_sustancias(0);
////            nuevo.setDespertar("07:00");
////            nuevo.setDormir("23:30");
////            nuevo.setHora_en_que_desperto("08:00");
////            nuevo.setHora_en_que_durmio("01:30");
////            new ConexionBDRegistrosDelSueno(getApplicationContext()).execute("addRegistro",nuevo);
////            Log.e("Entro","SEGUN SE AGREGA");
        });
        btn2.setOnClickListener(e->{
            try {
                List<RegistroEntity> registros = (List<RegistroEntity>) new ConexionBDRegistrosDelSueno(getApplicationContext()).execute("getAllByName","Eduardo").get();
                Log.e("Entro","AQUIIIIIiiXXXXXXXX2");
                for(RegistroEntity r: registros){
                    Log.e("Entro","AQUIIIIIii");
                    textView.setText(textView.getText()+" "+String.valueOf(r.getId_registro())+" "+r.getNombre()+" "+r.getDespertar()+" "+r.getHora_en_que_desperto());
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                e1.printStackTrace();
            }
        });

        txtNombre = findViewById(R.id.txtNombreMain);
        txtSexo = findViewById(R.id.txtSexoMain);
        txtHoraDespertar = findViewById(R.id.txtHoraDespertarMain);
        txtHoraDormir = findViewById(R.id.txtHoraDormirMain);
        loadPaciente();

        //crear pila con los horarios en que se debe checar el paciente
        Queue<String> hora_mediciones = new LinkedList<>();
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



        for(String hora: hora_mediciones){
            Log.e("HoraMedicion",hora);
        }

        Log.e("tope",hora_mediciones.peek());


        Button btnPreocupaciones = findViewById(R.id.button4);
        btnPreocupaciones.setOnClickListener(e->{
            PreocupacionesDlg dlg = new PreocupacionesDlg(MainActivity.this);
            dlg.show();
        });

        Button btnNivelEstres = findViewById(R.id.button5);
        btnNivelEstres.setOnClickListener(e->{
            NivelEstresDlg dlg = new NivelEstresDlg(MainActivity.this);
            dlg.show();
        });

        Button btnHeart = findViewById(R.id.heart);
        btnHeart.setOnClickListener(e->{
            Intent intent = new Intent(this,HeartRateMonitor.class);
            startActivity(intent);
        });
        Button boton = findViewById(R.id.button);
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, 201);
        boton.setOnClickListener(e->{
            Intent intent = new Intent(this,IluminationService.class);
            startService(intent);
        });
        Button sonido = findViewById(R.id.audio);
        sonido.setOnClickListener(e->{
            Intent intent = new Intent(this,NoiseService.class);
            startService(intent);
        });
        TextView txt = findViewById(R.id.hello);
        textureView = findViewById(R.id.textureView);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        List<Sensor> sensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        for(Sensor s : sensores){
//            txt.setText(txt.getText() +"\n"+ s.getName());
//        }
        iluminacion = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(iluminacion != null){
            sensorManager.registerListener(this,iluminacion,SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        }else{
            Toast.makeText(getApplicationContext(),"No tiene sensor de luz!!",Toast.LENGTH_SHORT).show();
        }
        accelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometro != null){
            sensorManager.registerListener(this,accelerometro,SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        }else{
            Toast.makeText(getApplicationContext(),"No tiene acelerometro!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPaciente(){
        configuracion.loadJson();
        configuracion.parseJson();
        pacienteActual = configuracion.getConf();
        txtHoraDormir.setText(pacienteActual.getHora_dormir());
        txtHoraDespertar.setText(pacienteActual.getHora_despertar());
        txtSexo.setText(pacienteActual.getSexo());
        txtNombre.setText(pacienteActual.getNombre());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONF_CHANGE && resultCode == RESULT_OK){
            Toast.makeText(getApplicationContext(),"Hiciste un cambio en la conf",Toast.LENGTH_SHORT).show();
            String[] conf = data.getStringArrayExtra("NuevaConf");
            txtNombre.setText(conf[0]);
            txtSexo.setText(conf[1]);
            txtHoraDespertar.setText(conf[3]);
            txtHoraDormir.setText(conf[2]);
        }else{
            Toast.makeText(getApplicationContext(),"Cancelaste el cambio en la conf",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            if(previousValue > event.values[0] || previousValue < event.values[0])
                valores_sensores.setLux(event.values[0]);
            previousValue = event.values[0];
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if(previousPosition[0] > event.values[1] + 1 || previousPosition[0] < event.values[1] - 1 || previousPosition[1] > event.values[0] + 1 || previousPosition[1] < event.values[0] - 1  ){
                Log.e("ValueX", String.valueOf(event.values[1]));
                Log.e("ValueY",String.valueOf(event.values[0]));
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
            Log.i("Sensor Changed", "Accuracy :" + i);
        }
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.i("Sensor Changed", "Accuracy :" + i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,iluminacion,SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this,accelerometro,SensorManager.SENSOR_DELAY_FASTEST);
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
        sensorManager.unregisterListener(this);
        Alarm alarm = Alarm.getInstance();
        alarm.cancelAlarm(getApplicationContext());
        Log.e("ServicoAudio","SE cancelo");
        CurrentTime timer = CurrentTime.getInstance(getApplicationContext());
        timer.stop();
        Log.e("Temporizador","SE cancelo");
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
        switch (item.getItemId()){
            case R.id.action_configuracion:
                Toast.makeText(this,"Configuracion seleccionada",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,ConfiguracionInicial.class);
                intent.putExtra("Conf",new String[]{pacienteActual.getNombre(),pacienteActual.getSexo(),pacienteActual.getHora_dormir()
                ,pacienteActual.getHora_despertar()});
                startActivityForResult(intent,CONF_CHANGE);
                break;
            default: break;
        }
        return true;
    }


    @Override
    public void update(Observable observable, Object o) {
        String[] hora = (String[])o;
        Log.e("Hora Actual",hora[0]+":"+hora[1]);
        int horaDormir = Integer.parseInt(pacienteActual.getHora_dormir().split(":")[0])*60;
        horaDormir += Integer.parseInt(pacienteActual.getHora_dormir().split(":")[1]);
        int horaActual = (Integer.parseInt(hora[0])*60)+Integer.parseInt(hora[1]);

        Log.e("SONIDO ACTUAL",String.valueOf(valores_sensores.getDb()));
        Log.e("ILUMINACION ACTUAL",String.valueOf(valores_sensores.getLux()));
        Log.e("POSICION ACTUAL",String.valueOf(valores_sensores.getPosicionX())+" "+String.valueOf(valores_sensores.getPosicionY()));
    }

}