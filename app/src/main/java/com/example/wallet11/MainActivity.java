package com.example.wallet11;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteOpenHelper walletDatabaseHelper = new DataBase(this  );
        ListView listView = (ListView) findViewById(R.id.list_item );
        try {
            db = walletDatabaseHelper.getReadableDatabase();
            cursor = db.query( "WALLET", new String[]{"_id", "NAME", "AMOUNT"},
                    null,null,null,null,null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter( this,
                    R.layout.item_view,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{R.id.textNWallet},
                    0);
            listView.setAdapter(listAdapter);
        }
        catch (SQLException e){
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

            listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick( AdapterView<?> listView, View itemView, int position, long id ) {

                    String name = ((TextView) itemView.findViewById( R.id.textNWallet )).getText().toString();
                    if (listView.getAdapter().getCount()>=2) {
                        Intent intent = new Intent( MainActivity.this, InfoWallet.class );
                        intent.putExtra( InfoWallet.EXTRA_NAME, name );
                        startActivity( intent );
                    }
                    else {
                        Toast.makeText( MainActivity.this, "Недостаточно счетов для совершения перевода", Toast.LENGTH_SHORT ).show();
                    }
                }
            } );
        }





    @Override
    protected void onResume() {
        super.onResume();
        Cursor newCursor = db.query("WALLET", new String[]{"_id", "NAME", "AMOUNT"},
                null, null, null, null, null);
        ListView listView = findViewById( R.id.list_item );
        SimpleCursorAdapter adapter = (SimpleCursorAdapter)listView.getAdapter();
        adapter.changeCursor(newCursor);
        cursor = newCursor;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрытие курсора и базы данных
        cursor.close();
        db.close();
    }

    public void onCreateNewWallet( View view ) {
        Intent intent = new Intent( this, CreateNewWallet.class );
        startActivity( intent );
    }

    public void onCreateTransaction( View view ) {
        Intent intent = new Intent( this, HistoryTransaction.class );
        startActivity( intent );
    }
}
