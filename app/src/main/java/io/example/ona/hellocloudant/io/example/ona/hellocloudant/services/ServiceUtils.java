package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//import com.cloudant.client.api.ClientBuilder;
//import com.cloudant.client.api.CloudantClient;

import java.net.URL;
import java.util.List;

/**
 * Created by onamacuser on 12/03/2016.
 */
public class ServiceUtils {
    public static SQLiteDatabase db;

    public static SQLiteDatabase getDB() {
        String dbpath = "/data/data/io.example.ona.hellocloudant/app_data/opensrp_devtest_filteredpull/db.sync";
        if (db == null) {
            db = SQLiteDatabase.openDatabase(dbpath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        return db;
    }

    public static void connect() throws Exception {

        new Thread(new Runnable() {
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
