package admin.build1;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import admin.build1.Services.ToastService;
import admin.build1.database.TraveliaDatabaseHelper;

public class AddNewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        TextView name = (TextView)findViewById(R.id.newItemName);
        name.setText(getIntent().getStringExtra("testValue"));
    }


    public void addItem(View view) {

        TextView name = (TextView)findViewById(R.id.newItemName);

        switch (getIntent().getStringExtra("itemType")){
            case "hotel":
            {
                TraveliaDatabaseHelper databaseService = TraveliaDatabaseHelper.getInstance(this);
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase(TraveliaDatabaseHelper.DB_NAME, MODE_PRIVATE, null);
                databaseService.insertHotel(db, name.getText().toString(), "",0,0);
                ToastService.showToast(this, "Record was created?");

                break;
            }
        }


    }
}