package admin.build1.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class imageTable {
    private static final String TABLE_NAME = "IMAGES";
    private static final String OWNER_ID = "owner_Id";
    private static final String FILE_NAME = "file_name";
    private static final String FILE_DIR = "file_dir";

    private static SQLiteDatabase mDatabase;

    public imageTable(SQLiteDatabase database) {
        mDatabase = database;
        initTable();
    }

    private void initTable() {
        if (mDatabase == null) return;

        mDatabase.execSQL("CREATE TABLE " + TABLE_NAME
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OWNER_ID + "INTEGER,"
                + FILE_NAME + "TEXT,"
                + FILE_DIR + "TEXT);"
        );
    }

    public void insertImage(ImageTableObject imageObject) {
        if (imageObject == null) return;

        ContentValues record = new ContentValues();
        record.put(OWNER_ID, imageObject.ownerId);
        record.put(FILE_NAME, imageObject.fileName);
        record.put(FILE_DIR, imageObject.fileDir);

        mDatabase.insertOrThrow(TABLE_NAME, null, record);
    }

    public ImageTableObject getImageByOwner(Integer ownerId) {
        if (ownerId == null) return null;

        String queryString =
                "SELECT " + OWNER_ID + ", " + FILE_NAME + ", " + FILE_DIR + " "
                + "WHERE " + OWNER_ID + " = ?" + " LIMIT 1";

        String[] whereArgs = new String[] {String.valueOf(ownerId)};
        Cursor searchResult = mDatabase.rawQuery(queryString, whereArgs);

        ImageTableObject imageInfo = new ImageTableObject();
        if (searchResult.getCount() == 1) {
            imageInfo.ownerId = searchResult.getString(0);
            imageInfo.fileName = searchResult.getString(1);
            imageInfo.fileDir = searchResult.getString(2);
        }

        return imageInfo;
    }

    public static class ImageTableObject {
        public String fileName;
        public String fileDir;
        public String ownerId;
    }
}
