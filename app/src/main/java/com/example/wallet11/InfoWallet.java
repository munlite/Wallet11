package com.example.wallet11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;



public class InfoWallet extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public String wallet;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_info_wallet );
        SQLiteOpenHelper walletDatabaseHelper = new DataBase(this  );
        try {
            db = walletDatabaseHelper.getReadableDatabase();
            cursor = db.query( "WALLET", new String[]{"NAME", "AMOUNT"},
                    "NAME = ?", new String[]{getIntent().getExtras().get((EXTRA_NAME)).toString()},
                    null,null,null);
            cursor.moveToFirst();
            ((TextView)findViewById( R.id.name )).setText( cursor.getString( 0) );
            ((TextView)findViewById( R.id.money )).setText( String.valueOf(  cursor.getInt( 1 )) );

        }
        catch (SQLException e){
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    public void onDelete( View view ) {

        TextView name = (TextView) findViewById( R.id.name );
        String nameS = name.getText().toString();
        DataBase.deleteWallet( db, nameS );
        Intent intent = new Intent( this, MainActivity.class );
        startActivity( intent );

    }
    public void makeTransaction(View view){
        Intent intent = new Intent(InfoWallet.this, CreateNewTransaction.class);
        intent.putExtra(CreateNewTransaction.EXTRA_NAME, getIntent().getExtras().get((EXTRA_NAME)).toString());
         startActivity(intent);
    }
}
