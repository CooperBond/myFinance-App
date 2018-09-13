package com.developer.kirill.myfinance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.developer.kirill.myfinance.database.IncCategoryDbSchema.IncCategoriesTable.iCategCols.iCATEGID;
import static com.developer.kirill.myfinance.database.IncCategoryDbSchema.IncCategoriesTable.iCategCols.iCATEGORY;

public class IncCategoryBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "categnamesi.db";
    public IncCategoryBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + IncCategoryDbSchema.IncCategoriesTable.iNAME + "(" +
                "_id integer primary key autoincrement, " +
                iCATEGID + ", "+
                iCATEGORY + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
