package io.example.ona.hellocloudant;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cloudant.sync.replication.ErrorInfo;

import java.io.File;

import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.Constants;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.HelloCloudantBroadcastReceiver;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.ReplicationListenerCallback;

public class BaseActivity extends AppCompatActivity implements ReplicationListenerCallback{
    private static final String TAG=BaseActivity.class.getCanonicalName();

    HelloCloudantBroadcastReceiver helloCloudantBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Intent replicationServiceIntent = new Intent(this, ReplicationIntentService.class);
        startService(replicationServiceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupReplicationBroadcastReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(helloCloudantBroadcastReceiver);
    }

    private void setupReplicationBroadcastReceiver() {
        // The filter's action is BROADCAST_ACTION
        IntentFilter helloCloudantIntentFilter = new IntentFilter(Constants.Replication.ACTION_DATABASE_CREATED);
        helloCloudantIntentFilter.addAction(Constants.Replication.ACTION_REPLICATION_COMPLETED);
        helloCloudantIntentFilter.addAction(Constants.Replication.ACTION_REPLICATION_ERROR);
        helloCloudantIntentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        helloCloudantIntentFilter.addAction("android.intent.action.TIME_SET");
        // Instantiates a new DownloadStateReceiver
        helloCloudantBroadcastReceiver = new HelloCloudantBroadcastReceiver(this);
        // Registers the DownloadStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(helloCloudantBroadcastReceiver, helloCloudantIntentFilter);
    }
    @Override
    public void replicationCompleted(int documentsReplicated, int batchesReplicated) {
        showToast("Replication complete. "+documentsReplicated +" documents replicated");

    }

    @Override
    public void replicationFailed(ErrorInfo error) {
        showToast(error.getException().getMessage());

    }
    //load cloudant db
    public void loadDatabase() {

        try {
          String  dataStoreName = this.getString(R.string.datastore_name);
            // Set up our tasks datastore within its own folder in the applications
            // data directory.
            File path = this.getApplicationContext().getDir(
                    Constants.DATASTORE_MANGER_DIR,
                    Context.MODE_PRIVATE
            );
            String dbpath=path.getAbsolutePath().concat(File.separator).concat(dataStoreName).concat(File.separator).concat("db.sync");
            SQLiteDatabase db = SQLiteDatabase.openDatabase(dbpath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            ((HelloCloudantApplication) this.getApplication()).setCloudantDB(db);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

}
