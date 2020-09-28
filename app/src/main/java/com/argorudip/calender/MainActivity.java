package com.argorudip.calender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("data1", "onCreate");
        Toast.makeText(MainActivity.this, Config.getDeviceName(),
                Toast.LENGTH_SHORT).show();
        startService();

        final Handler handler = new Handler();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(1000);
                        Log.d("data1", "datalopp");
                        handler.post(this);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        findViewById(R.id.btnEnable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0377778888"));

                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("data1", "onRestart");
        status = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("data1", "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("data1", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService();
        Log.d("data1", "onstop");


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("data1", "onDestroy");
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("status", "anggota");
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("data1", "onResume");
    }


    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Anda sedang menjalan aplikasi ini");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }
}