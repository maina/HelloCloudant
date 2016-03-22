package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.example.ona.hellocloudant.BaseActivity;

/**
 * Created by onamacuser on 16/03/2016.
 */
public class HelloCloudantBroadcastReceiver extends BroadcastReceiver {
    Activity activity;

    public HelloCloudantBroadcastReceiver(Activity _activity) {
        activity = _activity;

    }

    // Called when the BroadcastReceiver gets an Intent it's registered to receive
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();

            if (action.equals(Intent.ACTION_TIME_CHANGED)) {
                ((BaseActivity) activity).showToast("TIME CHANGED");
                Log.d("TimeChange","timechanged");
            } else if (action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                ((BaseActivity) activity).showToast("TIMEZONE CHANGED");
                Log.d("TimeChange", "timezonechanged");
            } else if (action.equals(Constants.Replication.ACTION_DATABASE_CREATED)) {
                ((BaseActivity) activity).loadDatabase();
            } else if (action.equals(Constants.Replication.ACTION_REPLICATION_COMPLETED)) {
                Integer docsReplicated=intent.getIntExtra(Constants.Replication.DOCUMENTS_REPLICATED, 0);
                ((BaseActivity) activity).showToast("Replication completed. Documents replicated: " + docsReplicated);
            } else if (action.equals(Constants.Replication.ACTION_REPLICATION_ERROR)) {
                ((BaseActivity) activity).showToast("Replicaton error occurred");
            }
        } catch (Exception e) {
            Log.e("TimeChange", e.getMessage());
        }

    }
}
