package com.example.bread;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Let's get this Bread
 * Creating and generating our database. It consists of 4 tables, BANK_CARDS, USERS, STOCKS, TRANSACTIONS.
 * The BANK_CARDS table's columns are as follows: BANK_CARDS, EMAIL, CARD_NUM, FNAME, LNAME
 * The USERS table's columns are as follows: EMAIL, FNAME, LNAME, PW (PASSWORD)
 * The STOCKS table's columns are as follows: STOCK_NAME, STOCK_VALUE
 * The TRANSACTION table's collumns are as follows: COST, EMAIL.
 * The Id for all the tables are EMAIL.
 * @author Noah Nichols
 * @version 2019.12
 */
public class Database extends SQLiteOpenHelper {

    //    TABLES ARE: USERS, BANK_CARDS, STOCKS, TRANSACTIONS,
    public static final String DB_NAME = "data.db";

//    USERS
    public static final int VERSION_NUM = 5;
    public static final String USERS = "USERS";
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
    public static final String COST = "COST";
    public static final String TRANSACTIONS = "TRANSACTIONS";


    //    CREATE QUERIES
//    USERS
    public static final String USER_CREATE = "create table "
            + USERS + "(" + EMAIL + " text not null, "+ FNAME
            + " text not null, " + LNAME + " text not null, "
            + PW + " text not null);";
    //    BANK_CARDS
    public static final String BANK_CARD_CREATE = "create table "
            + BANK_CARDS + "(" + EMAIL + "text not null, " + CARD_NUM + " text not null, " + FNAME
            + " text not null, " + LNAME + " text not null);";
    //    STOCKS
    public static final String STOCK_CREATE = "create table "
            + STOCKS + "(" + STOCK_NAME + " text primary key, " + STOCK_VALUE
            + " Float not null);";

    public static final String TRANS_CREATE = "create table "
        + TRANSACTIONS + "(" + COST + " text not null, " + EMAIL + " text not null);";


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
        Log.i("Database", "creating TRANSACTIONS");
        db.execSQL(TRANS_CREATE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i("Database", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        final String userQuery = "DROP TABLE IF EXISTS " + USERS;
        final String bcQuery = "DROP TABLE IF EXISTS " + BANK_CARDS;
        final String stockQuery = "DROP TABLE IF EXISTS " + STOCKS;
        final String transQuery = "DROP TABLE IF EXISTS " +TRANSACTIONS;
//        final String query = "DROP TABLE IF EXISTS " + USERS;
        db.execSQL(userQuery);
        db.execSQL(bcQuery);
        db.execSQL(stockQuery);
        db.execSQL(transQuery);
        onCreate(db);
    }




}