package admin.build1.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import admin.build1.R;
import admin.build1.Services.BroadCastService;
import admin.build1.Services.ToastService;
import admin.build1.database.TraveliaCursorLoader2;
import admin.build1.database.TraveliaDatabaseHelper;

import admin.build1.ui.adapter.ParkAdapter;
import admin.build1.ui.adapter.TaxiAdapter;


public class TaxiActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<Cursor>,
        TaxiAdapter.TaxiOnClickListener {
    private static final int TAXI_LOADER_ID = 1;

    private RecyclerView mRecycler;
    private String Contacts_Phone;
    private int selectedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi);

        initDrawer();

        mRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);;
        mRecycler.setLayoutManager(layoutManager);

        getSupportLoaderManager().initLoader(TAXI_LOADER_ID, null, this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
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
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new TraveliaCursorLoader2(this, TraveliaDatabaseHelper.getInstance(this));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRecycler.setAdapter(new TaxiAdapter(data, this));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onTaxiClick(int id) {
        selectedId = id;
        try {
            SQLiteOpenHelper sightsDatabaseHelper = new TraveliaDatabaseHelper(this);
            SQLiteDatabase db = sightsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("TAXI",
                    new String[]{"NAME", "CONTACTS", "IMAGE_RESOURCE_ID"}, "_id = ?",
                    new String[]{Integer.toString(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                String name = cursor.getString(0);
                String text = cursor.getString(1);
                int photoId = cursor.getInt(2);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.card_taxi,
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

    public void onClick11(int id) {
        try {
            SQLiteOpenHelper sightsDatabaseHelper = new TraveliaDatabaseHelper(this);
            SQLiteDatabase db = sightsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("TAXI",
                    new String[]{"NUMBER"}, "_id = ?",
                    new String[]{Integer.toString(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                String number = cursor.getString(0);


                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
                startActivity(intent);
            }
            cursor.close();
            db.close();
        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void onClick(View view){

        BroadCastService.fireShowToastBroadCast(this, "Taxi ");
    }

    public void deleteTaxi(View view) {

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(TraveliaDatabaseHelper.DB_NAME, MODE_PRIVATE, null);
        TraveliaDatabaseHelper dbHelper = new TraveliaDatabaseHelper(this);

        dbHelper.deleteById(db, "TAXI", selectedId);
        startActivity(new Intent(this, TaxiActivity.class));

    }

    public void searchClick2(View view) {

        SQLiteOpenHelper sightsDatabaseHelper = new TraveliaDatabaseHelper(this);
        SQLiteDatabase db = sightsDatabaseHelper.getReadableDatabase();

        TextView searchText = (TextView) findViewById(R.id.searchHotelText);
        String search = searchText.getText().toString();

        TraveliaDatabaseHelper.insertTaxi(db,"test","num","num","cont", 0);

        try{
            Cursor cursor = db.rawQuery("SELECT * FROM TAXI WHERE Name LIKE ?",new String[]{"%" + search + "%"});
            ToastService.showToast(this, String.valueOf(cursor.getCount()));
            mRecycler.setAdapter(new TaxiAdapter(cursor, this));
        }catch (Exception e){
            ToastService.showToast(this, e.getMessage());
        }
        //getSupportLoaderManager().initLoader(HOTELS_LOADER_ID, null, null);

    }

}
