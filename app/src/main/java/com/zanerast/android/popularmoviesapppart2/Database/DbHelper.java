package com.zanerast.android.popularmoviesapppart2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.zanerast.android.popularmoviesapppart2.Database.MovieContract.*;

/**
 * Created by Zane on 17/05/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movieDb.db";

    private static final int DATABASE_VERSION = 3;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +

                        MovieEntry._ID                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieEntry.COLUMN_TITLE           + " REAL NOT NULL, " +
                        MovieEntry.COLUMN_PLOT            + " REAL NOT NULL, " +
                        MovieEntry.COLUMN_USER_RATING     + " REAL NOT NULL, " +
                        MovieEntry.COLUMN_RELEASE_DATE    + " REAL NOT NULL, " +
                        MovieEntry.COLUMN_MOVIE_ID        + " INTEGER NOT NULL, " +
                        MovieEntry.COLUMN_POSTER_IMAGE    + " BLOB, " +
                        MovieEntry.COLUMN_BACKDROP_IMAGE  + " BLOB, " +
                        " UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
