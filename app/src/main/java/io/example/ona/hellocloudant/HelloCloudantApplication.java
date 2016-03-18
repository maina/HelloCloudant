package io.example.ona.hellocloudant;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by onamacuser on 18/03/2016.
 */
public class HelloCloudantApplication extends Application {
    private SQLiteDatabase cloudantDB;


    public SQLiteDatabase getCloudantDB() {
        return cloudantDB;
    }

    public void setCloudantDB(SQLiteDatabase cloudantDB) {
        this.cloudantDB = cloudantDB;
    }


}
