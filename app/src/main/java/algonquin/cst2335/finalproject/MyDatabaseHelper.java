package algonquin.cst2335.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MyDatabaseSchema.MyTable.NAME + "(" +
                MyDatabaseSchema.MyTable.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MyDatabaseSchema.MyTable.Columns.TITLE + " TEXT, " +
                MyDatabaseSchema.MyTable.Columns.CONTENT + " TEXT)"



        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyDatabaseSchema.MyTable.NAME);
        onCreate(db);
    }
}
