package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

/**
 * Created by onamacuser on 11/03/2016.
 */

import android.content.Context;
import android.util.Log;

import com.cloudant.sync.replication.ReplicatorBuilder;
import com.cloudant.sync.replication.Replicator;
import com.cloudant.sync.datastore.BasicDocumentRevision;
import com.cloudant.sync.datastore.ConflictException;
import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.datastore.DatastoreNotCreatedException;
import com.cloudant.sync.datastore.DocumentBodyFactory;
import com.cloudant.sync.datastore.DocumentException;
import com.cloudant.sync.datastore.MutableDocumentRevision;
import com.cloudant.sync.notifications.ReplicationCompleted;
import com.cloudant.sync.notifications.ReplicationErrored;
import com.cloudant.sync.replication.Replicator;
import com.cloudant.sync.replication.ReplicatorBuilder;
import com.google.common.eventbus.Subscribe;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

public class PullPushService {
    private final Context mContext;
    private static final String DATASTORE_MANGER_DIR = "data";
    private static final String TASKS_DATASTORE_NAME = "tasks";
    private Datastore mDatastore;
    private static final String LOG_TAG = "PullPushService";
    DatastoreManager manager;
    private final String dbURL="http://27.147.129.61:5984/opensrp",dataStore="opensrp";

    public PullPushService(Context context) {

        this.mContext = context;

        // Set up our tasks datastore within its own folder in the applications
        // data directory.
        File path = this.mContext.getApplicationContext().getDir(
                DATASTORE_MANGER_DIR,
                Context.MODE_PRIVATE
        );
        manager = new DatastoreManager(path.getAbsolutePath());
        try {
            this.mDatastore = manager.openDatastore(TASKS_DATASTORE_NAME);
        } catch (DatastoreNotCreatedException dnce) {
            Log.e(LOG_TAG, "Unable to open Datastore", dnce);
        }

        Log.d(LOG_TAG, "Set up database at " + path.getAbsolutePath());
    }

    private CountDownLatch latch = null;

    public void push() throws Exception {
        // Username/password are supplied in the URL and can be Cloudant API keys
        URI uri = new URI(dbURL);

        //http://27.147.129.61:5984/_utils/database.html?opensrp/_design/Event/_view/all

        Datastore ds = manager.openDatastore(dataStore);

// Create a replicator that replicates changes from the local
// datastore to the remote database.
        Replicator replicator = ReplicatorBuilder.push().to(uri).from(ds).build();

// Use a CountDownLatch to provide a lightweight way to wait for completion
        CountDownLatch latch = new CountDownLatch(1);
        Listener listener = new Listener(latch);
        replicator.getEventBus().register(listener);
        replicator.start();
        latch.await();
        replicator.getEventBus().unregister(listener);
        if (replicator.getState() != Replicator.State.COMPLETE) {
            System.out.println("Error replicating TO remote");
            System.out.println(listener.error);
        } else {
            System.out.println(String.format("Replicated %d documents in %d batches",
                    listener.documentsReplicated, listener.batchesReplicated));
        }

    }

    public void pull() throws Exception {
// Username/password are supplied in the URL and can be Cloudant API keys
        URI uri = new URI(dbURL);

        Datastore ds = manager.openDatastore(dataStore);

// Create a replicator that replicates changes from the remote
// database to the local datastore.
        Replicator replicator = ReplicatorBuilder.pull().from(uri).to(ds).build();

// Use a CountDownLatch to provide a lightweight way to wait for completion
        CountDownLatch latch = new CountDownLatch(1);
        Listener listener = new Listener(latch);
        replicator.getEventBus().register(listener);
        replicator.start();
        latch.await();
        replicator.getEventBus().unregister(listener);
        if (replicator.getState() != Replicator.State.COMPLETE) {
            System.out.println("Error replicating FROM remote");
            System.out.println(listener.error);
        }
    }

    public void pushpull() throws Exception {
        // Username/password are supplied in the URL and can be Cloudant API keys
        URI uri = new URI(dbURL);

        Datastore ds = manager.openDatastore(dataStore);

// Create the pull replicator
        Replicator pullReplicator = ReplicatorBuilder.pull().from(uri).to(ds).build();

// Create the push replicator
        Replicator pushReplicator = ReplicatorBuilder.push().to(uri).from(ds).build();

// Use a latch starting at 2 as we're waiting for two replications to finish
        latch = new CountDownLatch(2);
        Listener listener = new Listener(latch);

// Set the listener and start for both pull and push replications
        pullReplicator.getEventBus().register(listener);
        pullReplicator.start();
        pushReplicator.getEventBus().register(listener);
        pushReplicator.start();

// Wait for both replications to complete, decreasing the latch via listeners
        latch.await();

// Unsubscribe the listeners
        pullReplicator.getEventBus().unregister(listener);
        pushReplicator.getEventBus().unregister(listener);

// Unfortunately in this implementation we'll only record the last error
// the listener saw
        if (pullReplicator.getState() != Replicator.State.COMPLETE) {
            System.out.println("Error replicating FROM remote");
            System.out.println(listener.error);
        }
        if (pushReplicator.getState() != Replicator.State.COMPLETE) {
            System.out.println("Error replicating TO remote");
            System.out.println(listener.error);
        }
    }
}