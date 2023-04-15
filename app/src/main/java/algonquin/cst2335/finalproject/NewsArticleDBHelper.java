package algonquin.cst2335.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsArticleDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "news_articles.db";
    private static final int DATABASE_VERSION = 1;

    public NewsArticleDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_NEWS_ARTICLES_TABLE = "CREATE TABLE " + NewsArticleContract.NewsArticleEntry.TABLE_NAME + " ("
                + NewsArticleContract.NewsArticleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NewsArticleContract.NewsArticleEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + NewsArticleContract.NewsArticleEntry.COLUMN_DESCRIPTION + " TEXT, "
                + NewsArticleContract.NewsArticleEntry.COLUMN_URL + " TEXT NOT NULL, "
                + NewsArticleContract.NewsArticleEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_NEWS_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + NewsArticleContract.NewsArticleEntry.TABLE_NAME);
        onCreate(db);
    }
}
