package admin.build1.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import admin.build1.R;
import admin.build1.database.TraveliaDatabaseHelper;

public class ParkDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            Intent intent = getIntent();
            int id = intent.getIntExtra("id", 1);
            this.id = id;
            SQLiteOpenHelper sightsDatabaseHelper = new TraveliaDatabaseHelper(this);
            SQLiteDatabase db = sightsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("PARKS",
                    new String[]{"NAME", "TEXT", "IMAGE_RESOURCE_ID"}, "_id = ?",
                    new String[]{Integer.toString(id)}, null, null, null, null);
            //Move to the first record in the Cursor
            if (cursor.moveToFirst()) {
                String name = cursor.getString(0);

                String text = cursor.getString(1);
                int photoId = cursor.getInt(2);

                TextView namesight = (TextView) findViewById(R.id.namepark);
                namesight.setText(name);
                TextView textsight = (TextView) findViewById(R.id.textpark);
                textsight.setText(text);
                //фото
                ImageView photo = (ImageView) findViewById(R.id.photopark);
                photo.setImageResource(photoId);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onMap (View view) {

        try {
            Intent intent = getIntent();
            int id = intent.getIntExtra("id", 1);
            SQLiteOpenHelper sightsDatabaseHelper = new TraveliaDatabaseHelper(this);
            SQLiteDatabase db = sightsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("PARKS",
                    new String[]{"LATITUDE", "LONGITUDE"}, "_id = ?",
                    new String[]{Integer.toString(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                String lat = cursor.getString(0);
                String lng = cursor.getString(1);
                String uri = String.format("geo:%s,%s?z=16", lat, lng);
                Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent4);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            event.startTracking();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_attractions) {
            Intent intent = new Intent(this,AttractionsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_hotels) {
            Intent intent1 = new Intent(this,HotelsActivity.class);
            startActivity(intent1);
        } else if (id == R.id.nav_cafe) {
            Intent intent3 = new Intent(this,CafeActivity.class);
            startActivity(intent3);
        } else if (id == R.id.nav_sundry) {
            Intent intent2 = new Intent(this,SundryActivity.class);
            startActivity(intent2);
        } else if (id == R.id.nav_map) {
            Intent intent4 = new Intent(Intent.ACTION_VIEW);
            startActivity(intent4);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void deletePark(View view) {

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(TraveliaDatabaseHelper.DB_NAME, MODE_PRIVATE, null);
        TraveliaDatabaseHelper dbHelper = new TraveliaDatabaseHelper(this);

        dbHelper.deleteById(db, "PARKS", this.id);

        setResult(4, new Intent());
        finish();

    }
}
