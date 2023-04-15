package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseOperations {
    private SQLiteDatabase mDatabase;

    public MyDatabaseOperations(SQLiteDatabase database) {
        mDatabase = database;
    }

    public void insert(String title, String content) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseSchema.MyTable.Columns.TITLE, title);
        values.put(MyDatabaseSchema.MyTable.Columns.CONTENT, content);
        mDatabase.insert(MyDatabaseSchema.MyTable.NAME, null, values);
    }

    public void update(long id, String title, String content) {
        ContentValues values = new ContentValues();
        values.put(MyDatabaseSchema.MyTable.Columns.TITLE, title);
        values.put(MyDatabaseSchema.MyTable.Columns.CONTENT, content);
        mDatabase.update(MyDatabaseSchema.MyTable.NAME, values, MyDatabaseSchema.MyTable.Columns.ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void delete(long id) {
        mDatabase.delete(MyDatabaseSchema.MyTable.NAME, MyDatabaseSchema.MyTable.Columns.ID + " = ?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public <MyDataModel> List<MyDataModel> getAll() {
        List<MyDataModel> items = new ArrayList<>();
        Cursor cursor = mDatabase.query(MyDatabaseSchema.MyTable.NAME, null, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    List<MyDataModel>  item = new ArrayList<>();
//                    item.setID(cursor.getLong(cursor.getColumnIndex(MyDatabaseSchema.MyTable.Columns.ID)));
//                    item.setTitle(cursor.getString(cursor.getColumnIndex(MyDatabaseSchema.MyTable.Columns.TITLE)));
//                    items.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return items;
    }
}
