package com.mangoblogger.app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mangoblogger.app.BlogModel;

import java.util.List;

/**
 * Created by ujjawal on 7/10/17.
 *
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabasHelper";
    private static final String DB_NAME = "MobileInsight";
    private static final String DB_SUFFIX = ".db";
    private static final int DB_VERSION = 1;

    private static DatabaseHelper mInstance;


    private DatabaseHelper(Context context) {
        super(context, DB_NAME + DB_SUFFIX, null, DB_VERSION);
    }

    /**
     * Database access point
     * Singleton instance
     *
     * @param context context of the activity
     * @return database instance
     */
    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private static SQLiteDatabase getReadableDatabase(Context context) {
        return getInstance(context).getReadableDatabase();
    }

    private static SQLiteDatabase getWritableDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the state table first followed by TownTable and others
        // because of the Foreign key dependency
        db.execSQL(BookmarkTable.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // leaving it blank for now
    }

    public static void addBookmark(Context context, BlogModel blogModel, int position) {
        SQLiteDatabase writableDatabase = getWritableDatabase(context);
        ContentValues values = createContentValuesForBookmark(blogModel, position);
        writableDatabase.insert(BookmarkTable.NAME, null, values);
    }

    public static

    private static  ContentValues createContentValuesForBookmark(BlogModel blogModel, int position) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(BookmarkTable.POSITION, position);
        values.put(BookmarkTable.TITLE, blogModel.getTitle());
        values.put(BookmarkTable.DESCRIPTION, blogModel.getDescription());
        return values;
    }

}
