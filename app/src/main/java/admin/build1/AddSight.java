package admin.build1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import admin.build1.database.TraveliaDatabaseHelper;

public class AddSight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sight);
    }


    public void addSight(View view) {

        TraveliaDatabaseHelper databaseService = TraveliaDatabaseHelper.getInstance(this);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(TraveliaDatabaseHelper.DB_NAME, MODE_PRIVATE, null);
        int kittyImageId = R.drawable.kitty;

        TextView nameView = (TextView) findViewById(R.id.newSightName);
        String name = nameView.getText().toString();

        TextView historyShortView = (TextView) findViewById(R.id.newSightShort);
        String historyShort = historyShortView.getText().toString();

        TextView historyLongView = (TextView) findViewById(R.id.newSightLong);
        String historyLong = historyLongView.getText().toString();

        TextView contactView = (TextView) findViewById(R.id.newSightContact);
        String contact = contactView.getText().toString();

        TextView factsView = (TextView) findViewById(R.id.newSightFacts);
        String facts = factsView.getText().toString();

        databaseService.insertSight(db, name, historyShort, historyLong, contact, facts, 0, 0, kittyImageId);

        setResult(2, new Intent());
        finish();

    }
}