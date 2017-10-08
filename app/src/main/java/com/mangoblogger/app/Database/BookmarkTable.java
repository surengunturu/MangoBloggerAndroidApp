package com.mangoblogger.app.Database;

/**
 * Created by ujjawal on 7/10/17.
 *
 */

public interface BookmarkTable {
    String NAME_ANALYTICS_TERMS = "AnalyticsTable";
    String NAME_UX_TERMS = "UxTermsTable";

    String COLUMN_ID = "_id";
    String POSITION = "position";
    String TITLE = "title";
    String DESCRIPTION = "description";

    String[] PROJECTION = new String[]{COLUMN_ID, POSITION, TITLE, DESCRIPTION};
    String CREATE_BODY = " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + POSITION + " INTEGER, "
            + TITLE + " TEXT, "
            + DESCRIPTION + " TEXT);";

    String CREATE_ANALYTICS_TERMS_TABLE = "CREATE TABLE " + NAME_ANALYTICS_TERMS + CREATE_BODY;
    String CREATE_UX_TERMS_TABLE = "CREATE TABLE " + NAME_UX_TERMS + CREATE_BODY;
}
