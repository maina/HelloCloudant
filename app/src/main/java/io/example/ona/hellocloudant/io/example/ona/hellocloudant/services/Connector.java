package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

/**
 * Created by onamacuser on 11/03/2016.
 */

import android.util.Log;

//import com.cloudant.client.api.ClientBuilder;
//import com.cloudant.client.api.CloudantClient;

import java.net.URL;
import java.util.List;

public class Connector implements Runnable {

    ConnectorCallback c;

    public Connector(ConnectorCallback c) {
        this.c = c;
    }

    public void run() {
//        try {
//            CloudantClient client = ClientBuilder.url(new URL("http://46.101.51.199:5984"))
//                    .username("").password("")
//                    .build();
//            List<String> databases = client.getAllDbs();
//            System.out.println("All my databases : ");
//            this.c.listdbs(databases);
//            for (String db : databases) {
//                Log.e("CLOUDANT", db);
//                this.c.callback(db); // callback
//            }
//
//        } catch (Exception e) {
//            this.c.callback(e.getMessage()+"Exception");
//            Log.e("CLOUDANT", e.getMessage());
//        }
    }
}