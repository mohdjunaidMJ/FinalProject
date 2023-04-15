package algonquin.cst2335.finalproject;

import android.provider.BaseColumns;

public class NewsArticleContract {
    private NewsArticleContract() {}

    public static class NewsArticleEntry implements BaseColumns {
        public static final String TABLE_NAME = "news_articles";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_IMAGE_URL = "image_url";
    }
}

