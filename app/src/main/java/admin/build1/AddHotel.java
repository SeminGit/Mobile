package admin.build1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import admin.build1.database.TraveliaDatabaseHelper;

public class AddHotel extends AppCompatActivity {
    static final int GALLERY_REQUEST = 1;
    private Bitmap selectedImgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_gallery);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);
    }

    public void addItem(View view) {

        TextView nameView = (TextView)findViewById(R.id.hotel_name);
        String name = nameView.getText().toString();

        TextView contactView = (TextView) findViewById(R.id.hotel_contacts);
        String contact = contactView.getText().toString();

        TraveliaDatabaseHelper databaseService = TraveliaDatabaseHelper.getInstance(this);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(TraveliaDatabaseHelper.DB_NAME, MODE_PRIVATE, null);

        int kittyImageId = R.drawable.kitty;

        databaseService.insertHotel(db, name, contact,kittyImageId,kittyImageId);

        setResult(1, new Intent());
        finish();

    }

    public void addImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        ImageView imageView = (ImageView) findViewById(R.id.selected_image);

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        selectedImgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(selectedImgBitmap);
                }
        }
    }
}