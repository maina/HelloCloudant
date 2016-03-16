package io.example.ona.hellocloudant;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.Connector;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.ConnectorCallback;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.PullPushService;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.QueryService;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.ServiceUtils;

public class MainActivity extends AppCompatActivity implements ConnectorCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
//        PullPushService pullPushService= new PullPushService(this);
//        try {
//            pullPushService.pull();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//
//        try {
//            new Connector(this).run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            Map<String, String> params = new HashMap();


           PullPushService pullPushService= new PullPushService(this);
           pullPushService.filteredPull(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        QueryService queryService= new QueryService(this);
        try {
            queryService.getUpdatedDocs();
        } catch (Exception e) {
            e.printStackTrace();
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
    public void callback(String dname) {
        Log.d("",dname);
    }

    @Override
    public void listdbs(List<String> dbs) {
        Log.d("",dbs.get(0));

    }
}
