package admin.build1.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import admin.build1.R;
import admin.build1.database.TraveliaCursorLoader1;
import admin.build1.database.TraveliaCursorLoader3;
import admin.build1.database.TraveliaDatabaseHelper;
import admin.build1.ui.adapter.CafeAdapter;
import admin.build1.ui.adapter.HotelsAdapter;

public class CafeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<Cursor>,
        CafeAdapter.CafeOnClickListener {

    private static final int CAFE_LOADER_ID = 1;

    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        //initDrawer();

        mRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(CAFE_LOADER_ID, null, this);

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
    @Override
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


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new TraveliaCursorLoader3(this, TraveliaDatabaseHelper.getInstance(this));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRecycler.setAdapter(new CafeAdapter(data, this));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    public void onClick(View view) {
    }

    @Override
    public void onCafeClick(int id) {

        try {
            SQLiteOpenHelper sightsDatabaseHelper = new TraveliaDatabaseHelper(this);
            SQLiteDatabase db = sightsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("CAFE",
                    new String[]{"NAME", "CONTACTS", "IMAGE_RESOURCE_ID"}, "_id = ?",
                    new String[]{Integer.toString(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                String name = cursor.getString(0);
                String text = cursor.getString(1);
                int photoId = cursor.getInt(2);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.card_hotels,
                        (ViewGroup)findViewById(R.id.layout));
                TextView namehotels = (TextView)layout.findViewById(R.id.text_card1);
                namehotels.setText(name);
                TextView texthotels = (TextView)layout.findViewById(R.id.text_card);
                texthotels.setText(text);
                ImageView imagehotels = (ImageView)layout.findViewById(R.id.image_card);
                imagehotels.setImageResource(photoId);

                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder. setView(layout);;
                builder.show();
            }
            cursor.close();
            db.close();

        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
