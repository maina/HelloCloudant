package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

import android.util.Log;

//import com.cloudant.client.api.ClientBuilder;
//import com.cloudant.client.api.CloudantClient;

import java.net.URL;
import java.util.List;

/**
 * Created by onamacuser on 12/03/2016.
 */
public class ServiceUtils {
    public static void connect () throws Exception{

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
//                    CloudantClient client = ClientBuilder.url(new URL("http://localhost"))
//                            .username("").password("")
//                            .build();
//                    List<String> databases = client.getAllDbs();
//                    System.out.println("All my databases : ");
//                    for ( String db : databases ) {
//                        Log.e("CLOUDANT",db);
//                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }
}
