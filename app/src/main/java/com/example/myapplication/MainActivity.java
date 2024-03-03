package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.os.BatteryManager;


public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register BroadcastReceiver to receive system broadcasts
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case ConnectivityManager.CONNECTIVITY_ACTION:
                        showToast("Broadcast System");
                        break;
                    case Intent.ACTION_BATTERY_CHANGED:
                        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                            showToast("Battery Charging");
                        } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                            showToast("Battery Discharging");
                        }
                        break;
                    case Intent.ACTION_TIMEZONE_CHANGED:
                        showToast("Timezone Changed");
                        break;
                }
            }
        };

        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
