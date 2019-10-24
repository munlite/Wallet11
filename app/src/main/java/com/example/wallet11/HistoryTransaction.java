package com.example.wallet11;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class HistoryTransaction extends AppCompatActivity {
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_history_transaction );
        SQLiteOpenHelper walletDatabaseHelper = new DataBase( this );
        ListView listView = (ListView) findViewById( R.id.list_history );
        try {
            db = walletDatabaseHelper.getReadableDatabase();
            cursor = db.query( "TRANSACTIONS", new String[]{"_id", "AMOUNT", "CURRENCY", "WALLET_FIRST", "WALLET_SECOND", "DATE", "COMMENT"},
                    null, null, null, null, null );
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter( this,
                    R.layout.item_history,
                    cursor,
                    new String[]{"AMOUNT", "CURRENCY", "WALLET_FIRST", "WALLET_SECOND", "DATE", "COMMENT"},
                    new int[]{R.id.textViewAmount, R.id.textViewCurrency, R.id.TextViewWalletFirst, R.id.TextViewWalletSecond, R.id.textViewDate,
                            R.id.editTextComment},
                    0 );
            listView.setAdapter( listAdapter );
        } catch (SQLException e) {
            Toast.makeText( this, "Database unavailable", Toast.LENGTH_SHORT ).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
