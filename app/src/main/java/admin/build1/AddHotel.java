package admin.build1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import admin.build1.database.TraveliaDatabaseHelper;

public class AddHotel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

    }


    public void addItem(View view) {

        TextView nameView = (TextView)findViewById(R.id.newItemName);
        String name = nameView.getText().toString();

        TextView contactView = (TextView) findViewById(R.id.newItemContacts);
        String contact = contactView.getText().toString();

        TraveliaDatabaseHelper databaseService = TraveliaDatabaseHelper.getInstance(this);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(TraveliaDatabaseHelper.DB_NAME, MODE_PRIVATE, null);

        int kittyImageId = R.drawable.kitty;

        databaseService.insertHotel(db, name, contact,kittyImageId,kittyImageId);

        setResult(1, new Intent());
        finish();

    }
}