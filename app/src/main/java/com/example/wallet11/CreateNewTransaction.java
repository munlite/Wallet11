package com.example.wallet11;


import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Integer.*;


public class CreateNewTransaction extends Activity {
    SQLiteDatabase db;
    public static final String EXTRA_NAME = "name";
    private final int euroRun = 64;
    private final int dollar = 54;
    Cursor cursor;
    Cursor cursorWalletFirst;


    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.transaction_main );

        SQLiteOpenHelper walletDatabaseHelper = new DataBase(this  );
        try {

            db = walletDatabaseHelper.getReadableDatabase();
            cursorWalletFirst = db.query( "WALLET", new String[]{"NAME", "AMOUNT"},
                    "NAME = ?", new String[]{getIntent().getExtras().get( (EXTRA_NAME) ).toString()},
                    null, null, null );
            cursorWalletFirst.moveToFirst();
            ((TextView) findViewById( R.id.tNameWallet )).setText( cursorWalletFirst.getString( 0 ) );
            ((TextView) findViewById( R.id.tAmountWallet )).setText( String.valueOf( cursorWalletFirst.getInt( 1 ) ) );
        }
        catch (SQLException e){
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        SQLiteOpenHelper sqLiteOpenHelper = new DataBase( this );
        try {
            db = sqLiteOpenHelper.getReadableDatabase();
            cursor = db.query( "WALLET", new String[]{"NAME"},
                    null,null,null,null, null );
            Spinner nameOfWallet = (Spinner) findViewById( R.id.nameOfWallet );
            ArrayList<String> names = new ArrayList<>(  );
            while (cursor.moveToNext()) {
                if ( !cursor.getString( 0 ).equals( getIntent().getExtras().get( (EXTRA_NAME) ).toString() ))
                names.add(cursor.getString( 0 ));
            }
            SpinnerAdapter adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, names);
            ((ArrayAdapter<String>) adapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nameOfWallet.setAdapter( adapter );
        }
        catch (SQLException e){
        Toast.makeText( this, "Database unavailable", Toast.LENGTH_SHORT ).show();
        }
    }


    public void transactionMoney(View view){
        SQLiteOpenHelper sqLiteOpenHelper = new DataBase( this );
        Spinner spinner = (Spinner) findViewById( R.id.nameOfWallet );
        String text = spinner.getSelectedItem().toString();

        if (((EditText) findViewById( R.id.Amount )).getEditableText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Введите данные",
                    Toast.LENGTH_LONG).show();
            return;

        }
        else {
            String transfer = ((EditText) findViewById( R.id.Amount )).getText().toString();
            int transferAmount = Integer.parseInt( transfer );

            try {
                db = sqLiteOpenHelper.getWritableDatabase();
                cursor = db.query( "WALLET", new String[]{"NAME", "AMOUNT"},
                        "NAME = ?", new String[]{text}, null, null, null );
                cursor.moveToFirst();

                cursorWalletFirst = db.query( "WALLET", new String[]{"NAME", "AMOUNT"},
                        "NAME = ?", new String[]{getIntent().getExtras().get( (EXTRA_NAME) ).toString()},
                        null, null, null );
                cursorWalletFirst.moveToFirst();

                Spinner spinner1 = (Spinner) findViewById( R.id.spinnerCurrency );
                String currency = spinner1.getSelectedItem().toString();
                switch (currency) {
                    case "euro": {
                        if ((transferAmount * euroRun <= cursorWalletFirst.getInt( 1 ))) {
                            int residual = (cursorWalletFirst.getInt( 1 ) - transferAmount * euroRun);
                            int refill = (cursor.getInt( 1 ) + transferAmount * euroRun);
                            DateFormat df = new SimpleDateFormat( "EEE, d MMM yyyy, HH:mm" );
                            String date = df.format( Calendar.getInstance().getTime() );
                            DataBase.residualWallet( db, cursorWalletFirst.getString( 0 ), residual, text, refill );
                            DataBase.historyTransaction( db, transferAmount, currency, ((EditText) findViewById( R.id.comment )).getText().toString(),
                                    date, cursorWalletFirst.getString( 0 ), cursor.getString( 0 ) );
                            ((TextView) findViewById( R.id.tAmountWallet )).setText( String.valueOf( residual ) );
                            Toast.makeText( this, "Перевод выполнен", Toast.LENGTH_SHORT ).show();
                            break;

                        } else {
                            Toast.makeText( this, "На кошельке недостаточно денег для перевода", Toast.LENGTH_SHORT ).show();
                            break;
                        }
                    }
                    case "dollar": {
                        if ((transferAmount * dollar <= cursorWalletFirst.getInt( 1 ))) {
                            int residual = (cursorWalletFirst.getInt( 1 ) - transferAmount * dollar);
                            int refill = (cursor.getInt( 1 ) + transferAmount * dollar);
                            DateFormat df = new SimpleDateFormat( "EEE, d MMM yyyy, HH:mm" );
                            String date = df.format( Calendar.getInstance().getTime() );
                            DataBase.residualWallet( db, cursorWalletFirst.getString( 0 ), residual, text, refill );
                            DataBase.historyTransaction( db, transferAmount, currency, ((EditText) findViewById( R.id.comment )).getText().toString(),
                                    date, cursorWalletFirst.getString( 0 ), cursor.getString( 0 ) );
                            ((TextView) findViewById( R.id.tAmountWallet )).setText( String.valueOf( residual ) );
                            Toast.makeText( this, "Перевод выполнен", Toast.LENGTH_SHORT ).show();
                            break;
                        } else {
                            Toast.makeText( this, "На кошельке недостаточно денег для перевода", Toast.LENGTH_SHORT ).show();
                            break;
                        }
                    }

                }


            } catch (SQLException e) {
                Toast.makeText( this, "Database unavailable", Toast.LENGTH_SHORT ).show();
            }


            }
        }


    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        cursorWalletFirst.close();
        db.close();
    }
}
