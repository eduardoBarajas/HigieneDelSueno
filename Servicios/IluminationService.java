package com.barajasoft.higienedelsueo.Servicios;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;
import android.widget.Toast;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.CameraError;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraFocus;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.barajasoft.higienedelsueo.BroadcastReceivers.Alarm;

import java.io.File;
import java.util.ArrayList;

public class IluminationService extends com.androidhiddencamera.HiddenCameraService {
    Alarm alarm = new Alarm();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //alarm.setAlarm(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (HiddenCameraUtils.canOverDrawOtherApps(this)) {
                CameraConfig cameraConfig = new CameraConfig()
                        .getBuilder(this)
                        .setCameraFacing(CameraFacing.REAR_FACING_CAMERA)
                        .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
                        .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                        .setCameraFocus(CameraFocus.AUTO)
                        .build();

                startCamera(cameraConfig);

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(IluminationService.this,"Capturando imagen",Toast.LENGTH_SHORT).show();
                        takePicture();
                    }
                }, 2000L);
            } else {
                //Abrir opciones para dar permisos
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
            }
        }else
        {
            Toast.makeText(this, "Permiso para la camara no disponible", Toast.LENGTH_SHORT).show();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onImageCapture(@NonNull File imageFile) {
        Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        Bitmap mutableBitmap = bmp.copy(Bitmap.Config.ARGB_8888,true);
        bmp = null;
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(mutableBitmap, 320, 240, false);
        ArrayList<Float> luminicencia = new ArrayList<>();
        float[] hsl = new float[3];
        for(int x=0;x<resizedBitmap.getWidth();x++){
            for(int y=0;y<resizedBitmap.getHeight();y++){
                int c = resizedBitmap.getPixel(x,y);
                ColorUtils.colorToHSL(c,hsl);
                luminicencia.add(hsl[2]);
            }
        }
        float luz = 0;
        for(Float v: luminicencia){
            luz += v;
        }
        Toast.makeText(this,"Promedio de luz: "+String.valueOf(luz/luminicencia.size()*100)+"%",Toast.LENGTH_LONG).show();
        Log.e("Promedio de luz",String.valueOf(luz/luminicencia.size()*100));
        stopSelf();
    }

    @Override
    public void onCameraError(int errorCode) {
        switch (errorCode) {
            case CameraError.ERROR_CAMERA_OPEN_FAILED:
                //Camera open failed. Probably because another application
                //is using the camera
                Toast.makeText(this, "error_cannot_open", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_IMAGE_WRITE_FAILED:
                //Image write failed. Please check if you have provided WRITE_EXTERNAL_STORAGE permission
                Toast.makeText(this, "error_cannot_write", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE:
                //camera permission is not available
                //Ask for the camera permission before initializing it.
                Toast.makeText(this, "error_cannot_get_permission", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION:
                //Display information dialog to the user with steps to grant "Draw over other app"
                //permission for the app.
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_FRONT_CAMERA:
                Toast.makeText(this, "error_not_having_camera", Toast.LENGTH_LONG).show();
                break;
        }
        stopSelf();
    }
}
