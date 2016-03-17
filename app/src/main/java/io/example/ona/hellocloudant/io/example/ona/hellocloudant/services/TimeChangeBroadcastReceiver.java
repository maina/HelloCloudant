package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by onamacuser on 16/03/2016.
 */
public class TimeChangeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();

            if (action.equals(Intent.ACTION_TIME_CHANGED)) {
                Log.d("TAG", "TimeChange");
            } else if (action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                Log.d("TAG", "TimeZoneChanged");
            }
        } catch (Exception e) {
            Log.e("TimeChange", e.getMessage());
        }

    }
}
