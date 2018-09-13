package com.developer.kirill.myfinance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IncNamesBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "incomesNamesBase.db";
    public IncNamesBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + IncNamesDbSchema.IncomesNameTable.NAMEi + "(" +
                "_id integer primary key autoincrement, " +
                IncNamesDbSchema.IncCols.incName + ", "+
                IncNamesDbSchema.IncCols.nameID+ ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
