package admin.build1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import admin.build1.Services.ToastService;
import admin.build1.database.TraveliaDatabaseHelper;

public class UpdateHotel extends AppCompatActivity {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hotel);

        String name = getIntent().getStringExtra("name");
        String contacts = getIntent().getStringExtra("contacts");
        id = getIntent().getIntExtra("id", 0);

        TextView nameView = (TextView) findViewById(R.id.updateItemName);
        nameView.setText(name);
        TextView contactView = (TextView) findViewById(R.id.updateItemContacts);
        contactView.setText(contacts);
    }


    public void updateHotel12(View view) {

        TextView nameView = (TextView) findViewById(R.id.updateItemName);
        String name = nameView.getText().toString();

        TextView contactView = (TextView) findViewById(R.id.updateItemContacts);
        String contact = contactView.getText().toString();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(TraveliaDatabaseHelper.DB_NAME, MODE_PRIVATE, null);

        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("CONTACTS", contact);

        db.update("HOTELS", cv, "_ID = ?", new String[]{String.valueOf(id)});

        setResult(5, new Intent());
        finish();

    }
}