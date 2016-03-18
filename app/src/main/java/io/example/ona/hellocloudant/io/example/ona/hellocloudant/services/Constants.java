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
public class Constants {

    public static final String DATASTORE_MANGER_DIR = "data";

    public static class Replication {

        public static final String ACTION_DATABASE_CREATED = "io.example.ona.hellocloudant.DATABASE_CREATED_ACTION";
        public static final String ACTION_REPLICATION_ERROR = "io.example.ona.hellocloudant.REPLICATION_ERROR_ACTION";
        public static final String ACTION_REPLICATION_COMPLETED = "io.example.ona.hellocloudant.REPLICATION_COMPLETED_ACTION";
        public static final String REPLICATION_ERROR = "REPLICATION_ERROR";
        public static final String DOCUMENTS_REPLICATED = "DOCUMENTS_REPLICATED";
        public static final String BATCHES_REPLICATED = "BATCHES_REPLICATED";

    }
    public static class SyncFilters {
        //these pull filters must be defined in your couchDB server for them to work
        public static final String FILTER_TIMESTAMP_NOT_EMPTY = "syncfilters/timestampnotemptyfilter";
        public static final String FILTER_LOCATION_ID = "syncfilters/locationidfilter";
        public static final String FILTER_TYPE_AND_LOCATION_ID="syncfilters/typeandlocationidfilter";
    }
}
