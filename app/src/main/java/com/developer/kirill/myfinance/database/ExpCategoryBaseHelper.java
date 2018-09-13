package com.developer.kirill.myfinance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.developer.kirill.myfinance.database.ExpCategoryDbSchema.ExpCategoriesTable.eCategCols.eCATEGID;
import static com.developer.kirill.myfinance.database.ExpCategoryDbSchema.ExpCategoriesTable.eCategCols.eCATEGORY;

public class ExpCategoryBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "categnames.db";
    public ExpCategoryBaseHelper(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ExpCategoryDbSchema.ExpCategoriesTable.eNAME + "(" +
                "_id integer primary key autoincrement, "
                +eCATEGID + ", "+
                eCATEGORY + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
