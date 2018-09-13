package com.developer.kirill.myfinance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpNamesBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "expenseNamesBase.db";

    public ExpNamesBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ExpenseAddedNamesDbSchema.ExpenseNameTable.NAMEN + "(" +
                "_id integer primary key autoincrement, " +
                ExpenseAddedNamesDbSchema.ENcols.expName + ", "+
                ExpenseAddedNamesDbSchema.ENcols.expInd + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
