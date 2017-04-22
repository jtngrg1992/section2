package com.example.jatingarg.section2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jatingarg on 22/04/17.
 */

public class DBhelper extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    public String dbPath;
    public static String dbName = "number.db";
    public static final int version = 1;
    public static Context currentContext;
    public static String tableName = "numbers";
    public static final String col_1 = "_id";
    public static final String col_2 = "number";


    private static final String TAG = "DBhelper";

    public DBhelper(Context context){
        super(context,dbName,null,version);
        currentContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: creating table");
        db.execSQL("create table if not exists " + tableName +
                    " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " number integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(int number){
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,number);
        long result = db.insert(tableName,null,contentValues);
        Log.d(TAG, "insertData: inserting " + Integer.toString(number ));
        if(result == -1){
            Log.e(TAG, "insertData: failed");
            return false;
        }
        Log.d(TAG, "insertData: success");
        return true;

    }

    public Cursor getAllData(){
        db = getWritableDatabase();
        return db.rawQuery("Select * from " + tableName,null);

    }

}
