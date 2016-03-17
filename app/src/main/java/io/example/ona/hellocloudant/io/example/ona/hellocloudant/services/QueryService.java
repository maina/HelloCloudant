package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

import android.content.Context;
import android.util.Log;

import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.datastore.DatastoreNotCreatedException;
import com.cloudant.sync.datastore.DocumentBody;
import com.cloudant.sync.datastore.DocumentRevision;
import com.cloudant.sync.query.IndexManager;
import com.cloudant.sync.query.QueryResult;

import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.datastore.DatastoreNotCreatedException;
import com.cloudant.sync.query.IndexManager;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by onamacuser on 11/03/2016.
 */

public class QueryService {
    private final Context mContext;
    private static final String DATASTORE_MANGER_DIR = "data";
    private static final String LOG_TAG = "PullPushService";
    DatastoreManager manager;
    private final String dbURL = "http://<YOUR_IP>:5984/opensrp_devtest", dataStore = "opensrp_devtest_filteredpull";

    public QueryService(Context context) {

        this.mContext = context;

        // Set up our tasks datastore within its own folder in the applications
        // data directory.
        File path = this.mContext.getApplicationContext().getDir(
                DATASTORE_MANGER_DIR,
                Context.MODE_PRIVATE
        );

        manager = new DatastoreManager(path.getAbsolutePath());


        Listener listener = new Listener(latch,this.mContext,path.getAbsolutePath()+File.separator+dataStore+File.separator+"db.sync" );
        Log.d(LOG_TAG, "Set up database at " + path.getAbsolutePath());
    }

    private CountDownLatch latch = null;


    public void getClients() throws Exception {
        Datastore ds = manager.openDatastore(dataStore);
        IndexManager im = new IndexManager(ds);


        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Alert");

        QueryResult result = im.find(query);

        for (DocumentRevision rev : result) {
            DocumentBody doc = rev.getBody();
            Map<String, Object> map = doc.asMap();
            if (map.containsKey("type") && map.get("type").equals("Alert")) {
                Log.d("ID", String.valueOf(map.get("type")));
                Log.d("TYPE", String.valueOf(map.get("entityId")));
            }

            // The returned revision object contains all fields for
            // the object. You cannot project certain fields in the
            // current implementation.
        }
    }

    public void getUpdatedDocs() throws Exception {
        Datastore ds = manager.openDatastore(dataStore);
        IndexManager im = new IndexManager(ds);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        Date lastSyncDate = df.parse("09-03-2016 12:00:00");
// query: { "timestamp": { "$gt": 12 } }
        Map<String, Object> query = new HashMap<String, Object>();
        Map<String, Object> gttimestamp = new HashMap<String, Object>();
        long timestamp = lastSyncDate.getTime();

        gttimestamp.put("$gt", "2016-03-16 09:37:46");
        query.put("providerId", "demotest");

        QueryResult result = im.find(query);
//        int size = result.size();
//        Log.d("TAG", "" + size);

        for (DocumentRevision rev : result) {
            DocumentBody doc = rev.getBody();
            Map<String, Object> map = doc.asMap();
            if (map.containsKey("providerId") && map.get("providerId").toString().equalsIgnoreCase("demoprovideridtimestamp")) {
                Log.d("TIMESTAMP", String.valueOf(map.get("timestamp")));
            }
        }

    }
}
