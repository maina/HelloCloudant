package io.example.ona.hellocloudant;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.cloudant.sync.replication.ErrorInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.Constants;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.ReplicationListenerCallback;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.PullPushService;

public class MainActivity extends BaseActivity  {
    private static final String TAG=MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });



//CLOUDANT SQLITE DB ACCESS

            String selectQuery = "SELECT  current,deleted,json,updated_at FROM revs where sequence=1'";

//            SQLiteDatabase db = ((HelloCloudantApplication) this.getApplication()).getCloudantDB();
//            if(db==null){
//                loadDatabase();
//            }
//             db = ((HelloCloudantApplication) this.getApplication()).getCloudantDB();
//            Cursor cursor = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//
//                    Integer current = (Integer.parseInt(cursor.getString(0)));
//                    Log.d(TAG,"CURRENT "+current);
//                    Integer deleted = (Integer.parseInt(cursor.getString(1)));
//                    Log.d(TAG,"DELETED "+deleted);
//                    byte[] json = (cursor.getBlob(2));
//                    String jsonString = new String(json, "UTF-8");
//                    Log.d(TAG,"JSON "+jsonString);
//                    String updatedat = (cursor.getString(3));
//                    Log.d(TAG,"UPDATED AT "+updatedat);
//
//                } while (cursor.moveToNext());
//            }
//            QueryService queryService = new QueryService(this);
//
//            queryService.getUpdatedDocs();
        } catch (Exception e) {
            Log.e("MaainActivity", e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
