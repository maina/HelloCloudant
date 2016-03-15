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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by onamacuser on 11/03/2016.
 */

public class QueryService {
    private final Context mContext;
    private static final String DATASTORE_MANGER_DIR = "data";
   private Datastore mDatastore;
    private static final String LOG_TAG = "PullPushService";
    DatastoreManager manager;
    private final String dbURL = "http://46.101.51.199:5984/opensrp_devtest", dataStore = "opensrp_devtest2";

    public QueryService(Context context) {

        this.mContext = context;

        // Set up our tasks datastore within its own folder in the applications
        // data directory.
        File path = this.mContext.getApplicationContext().getDir(
                DATASTORE_MANGER_DIR,
                Context.MODE_PRIVATE
        );
        manager = new DatastoreManager(path.getAbsolutePath());
        try {
            this.mDatastore = manager.openDatastore(dataStore);
        } catch (DatastoreNotCreatedException dnce) {
            Log.e(LOG_TAG, "Unable to open Datastore", dnce);
        }

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
            Map<String,Object> map = doc.asMap();
            if(map.containsKey("type") && map.get("type").equals("Alert")) {
                Log.d("ID", String.valueOf(map.get("type")));
                Log.d("TYPE", String.valueOf(map.get("entityId")));
            }

            // The returned revision object contains all fields for
            // the object. You cannot project certain fields in the
            // current implementation.
        }
    }
}
