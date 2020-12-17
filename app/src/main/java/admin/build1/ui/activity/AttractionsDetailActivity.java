package admin.build1.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


import admin.build1.R;
import admin.build1.database.TraveliaDatabaseHelper;

public class AttractionsDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions_detail);

        try {

            Intent intent = getIntent();
            int id = intent.getIntExtra("id", 1);
            SQLiteOpenHelper sightsDatabaseHelper = new TraveliaDatabaseHelper(this);
            SQLiteDatabase db = sightsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("SIGHTS",
                    new String[]{"NAME", "SHORT", "IMAGE_RESOURCE_ID"}, "_id = ?",
                    new String[]{Integer.toString(id)}, null, null, null, null);
            //Move to the first record in the Cursor
            if (cursor.moveToFirst()) {
                String name = cursor.getString(0);

                String text = cursor.getString(1);
                int photoId = cursor.getInt(2);

                TextView namesight = (TextView) findViewById(R.id.name);
                namesight.setText(name);
                TextView textsight = (TextView) findViewById(R.id.textsight);
                textsight.setText(text);
                //фото
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onText(View view) {

        try {
            Intent intent = getIntent();
            int id = intent.getIntExtra("id", 1);
            SQLiteOpenHelper sightsDatabaseHelper = new TraveliaDatabaseHelper(this);
            SQLiteDatabase db = sightsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("SIGHTS",
                    new String[]{"SHORT", "LONG", "CONTACTS", "FACTS","LATITUDE","LONGITUDE"}, "_id = ?",
                    new String[]{Integer.toString(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                String shorttext = cursor.getString(0);
                String longtext = cursor.getString(1);
                String contact = cursor.getString(2);
                String fact = cursor.getString(3);
                double lat = cursor.getDouble(4);
                double lng = cursor.getDouble(5);
                TextView textsight1 = (TextView) findViewById(R.id.textsight);
                switch (view.getId()) {
                    case R.id.button3:
                        textsight1.setText(shorttext);
                        break;
                    case R.id.button4:
                        textsight1.setText(longtext);
                        break;
                    case R.id.contact:
                        textsight1.setText(contact);
                        break;
                    case R.id.fact:
                        textsight1.setText(fact);
                        break;
                    case R.id.map1:
                        String uri = String.format("geo:%s,%s?z=18", Double.toString(lat), Double.toString(lng));
                        Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent4);
                        break;
                    default:
                        break;
                }
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
