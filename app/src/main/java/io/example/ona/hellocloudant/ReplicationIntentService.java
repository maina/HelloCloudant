package io.example.ona.hellocloudant;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.cloudant.sync.replication.Replication;

import java.util.HashMap;
import java.util.Map;

import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.Constants;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.PullPushService;
import io.example.ona.hellocloudant.io.example.ona.hellocloudant.services.ReplicationService;

/**
 * Created by onamacuser on 18/03/2016.
 */
public class ReplicationIntentService extends IntentService {
    private static final String TAG = ReplicationIntentService.class.getCanonicalName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ReplicationIntentService(String name) {
        super(name);
    }

    public ReplicationIntentService() {
        super("ReplicationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            PullPushService replicationService = new PullPushService(getApplicationContext(),null);
//            Map<String,String> params= new HashMap<String,String>();
//            params.put("locationId","korangi");
           replicationService.filteredPull(Constants.SyncFilters.FILTER_TIMESTAMP_NOT_EMPTY,null);
         //   replicationService.pull();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }
}
