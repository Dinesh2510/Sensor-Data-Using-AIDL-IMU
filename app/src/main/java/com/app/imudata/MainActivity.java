package com.app.imudata;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.imudata.Service.GetDeviceInfoService;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "android-aidl-example";
    public GetDeviceInfoInterface service;
    TextView txt_imu;
    GetDeviceInfoService getDeviceInfoService_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();


        txt_imu = findViewById(R.id.txt_imu);
        initService();

    }


    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder boundService) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    service = GetDeviceInfoInterface.Stub.asInterface(boundService);
                                    try {

                                        txt_imu.setText(service.getIMU_Data());

                                        // Toast.makeText(MainActivity.this, "AIDL service connected"+service.getIMU_Data(), Toast.LENGTH_LONG).show();
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 1);

                        }
                    });
                }
            }, 0, 1);


        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(MainActivity.this, "AIDL service disconnected", Toast.LENGTH_LONG).show();
        }
    };

    private void initService() {
        Intent i = new Intent();
        i.setClassName(this.getPackageName(), GetDeviceInfoService.class.getName());
        boolean bindResult = bindService(i, mConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "initService() bindResult: " + bindResult);

        if(bindResult) {

        }
    }

    private void releaseService() {
        unbindService(mConnection);
        mConnection = null;
        Log.d(TAG, "releaseService() trigger");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }
}
