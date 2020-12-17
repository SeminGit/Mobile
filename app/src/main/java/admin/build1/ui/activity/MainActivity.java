package admin.build1.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.support.v7.app.AlertDialog;

import admin.build1.R;
import admin.build1.Services.BroadCastService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewSwitcher.ViewFactory {

    private ImageSwitcher mImageSwitcher;
    int position = 0;
    private int[] mImageIds = { R.drawable.img, R.drawable.img1,
            R.drawable.img2, R.drawable.coloja,R.drawable.far, R.drawable.starzam };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(this);
        mImageSwitcher.setImageResource(mImageIds[0]);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new Thread(new Runnable() {
            public void run() {
                do{

                    long endTime = System.currentTimeMillis()
                            + 10 * 1000;

                    while (System.currentTimeMillis() < endTime) {
                        synchronized (this) {
                            try {
                                wait(endTime -
                                        System.currentTimeMillis());
                            } catch (Exception e) {
                            }
                        }
                        mImageSwitcher.post(new Runnable() {
                            public void run() {
                                setPositionNext();
                                mImageSwitcher.setImageResource(mImageIds[position]);
                            }
                        });
                    }

                }while (true);
            }
        }).start();

    }

    public void onClickIS(View v) {
        switch (v.getId()) {
            case R.id.imageViewNext:
                setPositionNext();
                mImageSwitcher.setImageResource(mImageIds[position]);
                break;
            case R.id.imageViewPrev:
                setPositionPrev();
                mImageSwitcher.setImageResource(mImageIds[position]);
                break;

            default:
                break;
        }
    }

    public void setPositionNext() {
        position++;
        if (position > mImageIds.length - 1) {
            position = 0;
        }
    }

    public void setPositionPrev() {
        position--;
        if (position < 0) {
            position = mImageIds.length - 1;
        }
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new
                ImageSwitcher.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(0xFF000000);
        return imageView;
    }

    public void onImageSwitcherClick(View view) {
        Intent intentToBroadcast = new Intent("this.broadcast.action.taxi");
            intentToBroadcast.setAction("this.broadcast.action.taxi");
        intentToBroadcast.putExtra("message", "DONT TOUCH IMAGE");
        this.sendBroadcast(intentToBroadcast);
        /*Toast toast = Toast.makeText(getApplicationContext(),
                "onImageSwitcherClick!", Toast.LENGTH_SHORT);
        toast.show();*/
    }

     @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            openQuitDialog();
        }
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setTitle("Выход: Вы уверены?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });

        quitDialog.show();
    }
	
    public void onClick(View view) {
        Toast toast;
        switch (view.getId()) {
            case R.id.sights:
                Intent intent = new Intent(this,AttractionsActivity.class);
                startActivity(intent);
                break;
            case R.id.hotels:
                Intent intent1 = new Intent(this,HotelsActivity.class);
                startActivity(intent1);
                break;
            case R.id.sundry:
                Intent intent2 = new Intent(this,SundryActivity.class);
                startActivity(intent2);
                break;
            case R.id.cafe:
                Intent intent3 = new Intent(this,CafeActivity.class);
                startActivity(intent3);
                break;
            case R.id.map:
               // String uri = String.format("geo:%s,%s?z=18", Double.toString(53.678486), Double.toString(23.818513));
                Intent intent4 = new Intent(Intent.ACTION_VIEW);
                startActivity(intent4);
                break;
            /*case R.id.id6:
                toast = Toast.makeText(getApplicationContext(),
                        "Click №6!", Toast.LENGTH_SHORT);
                toast.show();
                break;*/

            default:
                break;
        }
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


}
