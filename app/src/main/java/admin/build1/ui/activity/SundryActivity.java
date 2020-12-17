package admin.build1.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import admin.build1.R;

public class SundryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sundry);
    }
    public void onClick(View view) {
        Toast toast;
        switch (view.getId()) {
            case R.id.taxi:
                Intent intent = new Intent(this,TaxiActivity.class);
                startActivity(intent);
                break;
            case R.id.parks:
                Intent intent4 = new Intent(this,ParkActivity.class);
                startActivity(intent4);
                break;

            default:
                break;
        }
    }
}
