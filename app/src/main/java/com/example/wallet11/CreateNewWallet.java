package com.example.wallet11;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNewWallet extends Activity {
    private SQLiteDatabase db;
    private Cursor cursor;
    protected String[] list;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.new_wallet );

    }


    public void addNewWallet( View view ) {
        SQLiteOpenHelper sqLiteOpenHelper = new DataBase(this);
        try {

            db = sqLiteOpenHelper.getWritableDatabase();

            EditText name = (EditText) findViewById( R.id.tNameNewWallet );

            EditText amount = (EditText) findViewById( R.id.tNewAmount );


            if (name.getEditableText().toString().length() == 0 || amount.getEditableText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Введите данные",
                        Toast.LENGTH_LONG).show();
                return;

            }
            else {
                String nameS = name.getText().toString();
                int amountI = Integer.parseInt( String.valueOf(amount.getText()) );
                DataBase.insertWallet( db, nameS, amountI );

                Intent intent = new Intent( this, MainActivity.class );
                startActivity( intent );
            }
        }
        catch (SQLException e) {
            Toast.makeText( this, "Database unavailable", Toast.LENGTH_SHORT ).show();
        }

    }



    public void onDestroy() {
        super.onDestroy();
        cursor.close();
         db.close();
    }



}
