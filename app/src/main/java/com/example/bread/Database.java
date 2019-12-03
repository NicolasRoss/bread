package com.example.bread;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

//    TABLES ARE: USERS, BANK_CARDS, STOCKS, TRANSACTIONS,
    public static final String DB_NAME = "data.db";
//    USERS
    public static final int VERSION_NUM = 1;
    public static final String USERS = "USERS";
    public static final String USER_ID = "USER_ID";
    public static final String FNAME = "FIRST_NAME";
    public static final String LNAME = "LAST_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String PW = "PASSWORD";
//    BANK_CARDS
    public static final String BANK_CARDS = "BANK_CARDS";
    public static final String CARD_NUM = "CARD_NUMBER";
//    STOCKS
    public static final String STOCKS = "STOCKS";
    public static final String STOCK_NAME = "STOCK_NAME";
    public static final String STOCK_VALUE = "STOCK_VALUE";


//    CREATE QUERIES
//    USERS
    public static final String USER_CREATE = "create table "
            + USERS + "(" + USER_ID + " integer primary key autoincrement, "+ FNAME
            + " text not null, " + LNAME + " text not null, " + EMAIL + " text not null, "
            + PW + " text not null);";
//    BANK_CARDS
    public static final String BANK_CARD_CREATE = "create table "
            + BANK_CARDS + "(" + USER_ID + "integer primary key, " + CARD_NUM + " integer not null, " + FNAME
            + " text not null, " + LNAME + " text not null);";
//    STOCKS
    public static final String STOCK_CREATE = "create table "
            + STOCKS + "(" + STOCK_NAME + " text primary key, " + STOCK_VALUE
            + " Float not null);";



    public Database(Context ctx){
        super(ctx, DB_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db){
        Log.i("Database", "Calling onCreate");
        Log.i("Database", "creating USERS");
        db.execSQL(USER_CREATE);
        Log.i("Database", "creating BANK_CARD");
        db.execSQL(BANK_CARD_CREATE);
        Log.i("Database", "creating STOCKS");
        db.execSQL(STOCK_CREATE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i("Database", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        final String userQuery = "DROP TABLE IF EXISTS " + USERS;
        final String bcQuery = "DROP TABLE IF EXISTS " + BANK_CARDS;
        final String stockQuery = "DROP TABLE IF EXISTS " + STOCKS;
//        final String query = "DROP TABLE IF EXISTS " + USERS;
        db.execSQL(userQuery);
        db.execSQL(bcQuery);
        db.execSQL(stockQuery);
        onCreate(db);
    }




}