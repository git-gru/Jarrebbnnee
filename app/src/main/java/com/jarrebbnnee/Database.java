package com.jarrebbnnee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vi6 on 29-Mar-17.
 */

public class Database extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Jarrebbnnee";

    // Contacts table name
    private static final String TABLE_catIDpair = "catIDpair";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_catIDpair+"("+KEY_ID+" TEXT, "+KEY_NAME+" TEXT)";
        db.execSQL(query);
        Log.e("DATABASE ","TABLE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_catIDpair);
        // Create tables again
        onCreate(db);

    }

    void add_cat_id_pair(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,id);
        values.put(KEY_NAME,name);

        db.insert(TABLE_catIDpair,null,values);
        Log.e("Database", "added "+name+"   "+id);
        db.close();
    }
    String getCat(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_catIDpair, new String[] { KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            String name=cursor.getString(1);
            return name;
        }
        return "demo";
    }


    ArrayList<HashMap<String,String>> viewLastSyncCategories() {
        ArrayList<HashMap<String,String>> last = new ArrayList<HashMap<String,String>>();
        String selectQuery = "SELECT  * FROM " + TABLE_catIDpair;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            do {
                HashMap<String,String> map= new HashMap<String, String>();
                String pc_id = cursor.getString(0).toString();
                String pc_name = cursor.getString(1).toString();
                map.put("pc_id", pc_id);
                map.put("pc_name", pc_name);
                last.add(map);
                Log.e("database", "added "+pc_id+"\t"+pc_name);
            } while (cursor.moveToNext());


            // return contact list
        }
        db.close();
        return last;
    }

    public void clearTable()   {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_catIDpair, null,null);
    }
}
