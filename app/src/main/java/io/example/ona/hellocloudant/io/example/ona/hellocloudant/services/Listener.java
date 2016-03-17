package io.example.ona.hellocloudant.io.example.ona.hellocloudant.services;

/**
 * Created by onamacuser on 11/03/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cloudant.sync.notifications.DatabaseCreated;
import com.cloudant.sync.notifications.ReplicationCompleted;
import com.cloudant.sync.notifications.ReplicationErrored;
import com.cloudant.sync.replication.ErrorInfo;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.CountDownLatch;

/**
 * A {@code ReplicationListener} that sets a latch when it's told the
 * replication has finished.
 */
public class Listener {

    private final CountDownLatch latch;
    public ErrorInfo error = null;
    public int documentsReplicated;
    public int batchesReplicated;
    Context context;
String dbpath=null;
    Listener(CountDownLatch latch,Context _context,String _dbpath) {
        this.latch = latch;
        context=_context;
        dbpath=_dbpath;
    }
    Listener(CountDownLatch latch,Context _context) {
        this.latch = latch;
        context=_context;
    }

    @Subscribe
    public void complete(ReplicationCompleted event) {
        this.documentsReplicated = event.documentsReplicated;
        this.batchesReplicated = event.batchesReplicated;
        latch.countDown();
    }

    @Subscribe
    public void error(ReplicationErrored event) {
        this.error = event.errorInfo;
        latch.countDown();
    }
    @Subscribe
    public void onDatabaseCreated(DatabaseCreated event) {

        SQLiteDatabase db=getDatabase();
        String CREATE_CONTACTS_TABLE = "CREATE TABLE COMPANY(\n" +
                "   ID INT PRIMARY KEY     NOT NULL,\n" +
                "   NAME           TEXT    NOT NULL)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    SQLiteDatabase getDatabase() {

        try {
//            DatabaseHandler db = new DatabaseHandler(context);
//
//            return db.getWritableDatabase();
            SQLiteDatabase db = SQLiteDatabase.openDatabase(dbpath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            ServiceUtils.db=db;
            return db;
        } catch (Exception e) {
            Log.e("ERROR",e.getMessage());
            return null;
        }
    }
}
