package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

/**
 * Created by onamacuser on 11/03/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.cloudant.sync.notifications.DatabaseCreated;
import com.cloudant.sync.notifications.ReplicationCompleted;
import com.cloudant.sync.notifications.ReplicationErrored;
import com.cloudant.sync.replication.ErrorInfo;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.CountDownLatch;

import io.example.ona.hellocloudant.HelloCloudantApplication;

/**
 * A {@code ReplicationListener} that sets a latch when it's told the
 * replication has finished.
 */
public class ReplicationListener {

    private final CountDownLatch latch;
    public ErrorInfo error = null;
    Context context;
    ReplicationListenerCallback callback;


    ReplicationListener(CountDownLatch latch, Context _context, ReplicationListenerCallback _callback) {
        this.latch = latch;
        context = _context;
        callback = _callback;
    }


    ReplicationListener(CountDownLatch latch, Context _context) {
        this.latch = latch;
        context = _context;
    }

    @Subscribe
    public void complete(ReplicationCompleted event) {
        if (callback != null) {
            callback.replicationCompleted(event.documentsReplicated, event.batchesReplicated);
        }
        else {
            //Fire this incase the replication was lauched from an intent service
            Intent localIntent = new Intent(Constants.Replication.ACTION_REPLICATION_COMPLETED);
            // Puts the status into the Intent
            localIntent.putExtra(Constants.Replication.DOCUMENTS_REPLICATED, event.documentsReplicated);
            localIntent.putExtra(Constants.Replication.BATCHES_REPLICATED, event.batchesReplicated);
            // Broadcasts the Intent to receivers in this app.
            LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
        }
        latch.countDown();
    }

    @Subscribe
    public void error(ReplicationErrored event) {
        if (callback != null) {
            callback.replicationFailed(event.errorInfo);
        } else {
            //Fire this incase the replication was lauched from an intent service
            Intent localIntent = new Intent(Constants.Replication.ACTION_REPLICATION_ERROR);
            // Puts the status into the Intent
            localIntent.putExtra(Constants.Replication.REPLICATION_ERROR, event.errorInfo.getException().getMessage());
            // Broadcasts the Intent to receivers in this app.
            LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
        }
        latch.countDown();
    }

    @Subscribe
    public void onDatabaseCreated(DatabaseCreated event) {
//fire broadcast, this assumes the replication logic is fired through the intent service for the first time which is the only time a new db is created
        Intent localIntent = new Intent(Constants.Replication.ACTION_DATABASE_CREATED);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
    }


}
