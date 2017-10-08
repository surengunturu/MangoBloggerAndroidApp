package com.mangoblogger.app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mangoblogger.app.model.BlogModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ujjawal on 7/10/17.
 *
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabasHelper";
    private static final String DB_NAME = "MangoBlogger";
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

    public static void addBookmark(Context context, BlogModel blogModel, int position, boolean isAnalyticsTerm) {
        SQLiteDatabase writableDatabase = getWritableDatabase(context);
        ContentValues values = createContentValuesForBookmark(blogModel, position);
        if(isAnalyticsTerm) {
            writableDatabase.insert(BookmarkTable.CREATE_ANALYTICS_TERMS_TABLE, null, values);
        } else {
            writableDatabase.insert(BookmarkTable.CREATE_UX_TERMS_TABLE, null, values);
        }
    }

    public static List<BlogModel> getTermsList(Context context, boolean isAnalyticTerm) {
        if(isAnalyticTerm) {
            return loadTerms(context, BookmarkTable.NAME_ANALYTICS_TERMS);
        } else {
            return loadTerms(context, BookmarkTable.NAME_UX_TERMS);
        }
    }

    public static int[] getBookmarkPositionList(Context context, boolean isAnalyticTerm) {
        if(isAnalyticTerm) {
            return loadBookmarkPositionList(context, BookmarkTable.NAME_ANALYTICS_TERMS);
        } else {
            return loadBookmarkPositionList(context, BookmarkTable.NAME_UX_TERMS);
        }
    }

    private static int[] loadBookmarkPositionList(Context context, final String TABLE_NAME) {
        Cursor cursor = getCursor(context, TABLE_NAME, BookmarkTable.PROJECTION);
        int[] tmpPoisitionList = new int[cursor.getCount()];
        int i = 0;
        if(cursor.getCount()>0) {
            do {
                tmpPoisitionList[i] = cursor.getInt(1);
            } while (cursor.moveToNext());
        }
        return tmpPoisitionList;
    }

    private static SQLiteDatabase getReadableDatabase(Context context) {
        return getInstance(context).getReadableDatabase();
    }

    private static SQLiteDatabase getWritableDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }

    private static Cursor getCursor(Context context, final String TABLE_NAME, final String[] PROJECTION) {
        SQLiteDatabase database = getReadableDatabase(context);
        Cursor cursor = database.query(TABLE_NAME, PROJECTION, null,
                null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the state table first followed by TownTable and others
        // because of the Foreign key dependency
        db.execSQL(BookmarkTable.CREATE_ANALYTICS_TERMS_TABLE);
        db.execSQL(BookmarkTable.CREATE_UX_TERMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // leaving it blank for now
    }


    private static List<BlogModel> loadTerms(Context context, final String TABLE_NAME) {
        Cursor cursor = getCursor(context, TABLE_NAME, BookmarkTable.PROJECTION);
        List<BlogModel> tmpList = new ArrayList<>(cursor.getCount());
        do {
            BlogModel blogModel = getTermsData(cursor);
            tmpList.add(blogModel);
        } while (cursor.moveToNext());
        return tmpList;
    }



    private static BlogModel getTermsData(Cursor cursor) {
        final String title = cursor.getString(2); // magic number based on projection
        final String description = cursor.getString(3);
        final String image_url = "null";

        return new BlogModel(title, description, image_url);
    }
    

    private static  ContentValues createContentValuesForBookmark(BlogModel blogModel, int position) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(BookmarkTable.POSITION, position);
        values.put(BookmarkTable.TITLE, blogModel.getTitle());
        values.put(BookmarkTable.DESCRIPTION, blogModel.getDescription());
        return values;
    }

}
