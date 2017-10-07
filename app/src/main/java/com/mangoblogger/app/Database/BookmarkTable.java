package com.mangoblogger.app.Database;

/**
 * Created by ujjawal on 7/10/17.
 *
 */

public interface BookmarkTable {
    String NAME = "BookmarkTable";

    String COLUMN_ID = "_id";
    String POSITION = "position";
    String TITLE = "title";
    String DESCRIPTION = "description";

    String[] PROJECTION = new String[]{COLUMN_ID, POSITION, TITLE, DESCRIPTION};

    String CREATE = "CREATE TABLE " + NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + POSITION + " INTEGER, "
            + TITLE + " TEXT, "
            + DESCRIPTION + " TEXT);";

}
