package com.example.wallet11;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.StringWriter;


public class DataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "account"; // Имя базы данных
    private static final int DB_VERSION = 1;

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE WALLET ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "AMOUNT INTEGER);");
        db.execSQL("CREATE TABLE TRANSACTIONS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "AMOUNT INTEGER, "
                + "CURRENCY TEXT, "
                + "COMMENT TEXT, "
                + "DATE TEXT,"
                + "WALLET_FIRST TEXT,"
                + "WALLET_SECOND TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    protected static void insertWallet( SQLiteDatabase db, String name, int amount){
        ContentValues walletValues = new ContentValues(  );
        walletValues.put( "NAME", name );
        walletValues.put( "Amount", amount );
        db.insert("WALLET", null, walletValues);
    }

    protected static void deleteWallet(SQLiteDatabase db, String name){
        db.delete( "WALLET",
                "NAME = ?",
                new String[]{name});
    }
    protected static void residualWallet( SQLiteDatabase db, String walletFirst, int moneyFirst, String walletSecond, int moneySecond){
        ContentValues cv = new ContentValues();
        cv.put( "AMOUNT", moneyFirst );
        db.update("WALLET",
                cv,
                "NAME = ?", new
                        String[]{walletFirst});

        cv.put( "AMOUNT", moneySecond );
        db.update( "WALLET",
                cv,
                "NAME = ?",
                new String[]{walletSecond});
    }
    protected static void historyTransaction( SQLiteDatabase db, int amount, String corrency, String text,
                                              String date, String walletFirst, String walletSecond ){
        ContentValues transaction = new ContentValues(  );
        transaction.put( "Amount", amount );
        transaction.put( "CURRENCY", corrency);
        transaction.put( "CURRENCY", corrency);
        transaction.put( "COMMENT", text);
        transaction.put( "DATE", date);
        transaction.put( "WALLET_FIRST", walletFirst);
        transaction.put( "WALLET_SECOND", walletSecond);
        db.insert("TRANSACTIONS", null, transaction);

    }
}
