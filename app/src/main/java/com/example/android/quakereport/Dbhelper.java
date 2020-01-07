package com.example.android.quakereport;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hp on 30-09-2018.
 */

public class Dbhelper extends SQLiteOpenHelper{
    private static final int DB_VER = 1;
    private static final String DB_NAME = "QuakeOffline.db";
    public static final String TB_NAME = "RecentQuakes";
    public static final String MAG = "MAGNITUDE";
    public static final String LOC = "LOCATION";
    public static final String uRL = "URL";
    public static final String tIME = "TIME";
    public static final String Create = "CREATE TABLE IF NOT EXISTS RecentQuakes(MAGNITUDE FLOAT,LOCATION TEXT,URL TEXT,TIME INTEGER)";
    public Dbhelper(Context context){
        super(context,DB_NAME,null,DB_VER);
        //SQLiteDatabase d = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("On","ONCRREATE!!!!!!!!!!!");
        String CREATE_TABLE = "CREATE TABLE " + TB_NAME + "("+MAG + " FLOAT,"+LOC+" TEXT," + uRL + " TEXT," + tIME + " INTEGER)";
        //String Create = "CREATE TABLE RecentQuakes(MAGNITUDE FLOAT,LOCATION TEXT,URL TEXT,TIME INTEGER)";

        //sqLiteDatabase.execSQL(Create);
        Log.v("TaG",Create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(sqLiteDatabase);
    }
    public Cursor RetrieveData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM "+TB_NAME,null);
        return result;
    }
}
