package com.app.imudata.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.app.imudata.GetDeviceInfoInterface;

public class GetDeviceInfoService extends Service implements SensorEventListener {
    public String data_send = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SHARED_PREFERENCES_NAME = "_portal";
    public static final String SOME_DATA = "data_";
    private static final String TAG = "GetDeviceInfoService";
    private SensorManager sensorManager;
    private Sensor sensor;
    GetDeviceInfoInterface.Stub myBinder = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {

        return (new GetDeviceInfoInterface.Stub() {
            @Override
            public String getIMU_Data() throws RemoteException {
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
                register_sensor();
                sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                data_send = sharedPreferences.getString(SOME_DATA, "");
                Log.d("sensor", "onCreate: " + sensor);
                Log.d(TAG, "getIMU_Data: " + data_send);
                return (data_send);
            }
        });
    }

    private void register_sensor() {
        sensorManager.registerListener(this, sensor, 80000);
    }

    ;


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            data_send = "w = " + sensorEvent.values[0] + "\nx = " + sensorEvent.values[1] + "\ny = " + sensorEvent.values[2] + "\nz = " + sensorEvent.values[3];
            sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(SOME_DATA, data_send);
            editor.apply();

            Log.d(TAG, "onSensorChanged: " + data_send);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
