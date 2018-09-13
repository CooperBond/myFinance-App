package com.developer.kirill.myfinance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.developer.kirill.myfinance.database.IncomeDbSchema.IncomeTable;

import com.developer.kirill.myfinance.database.IncomeDbSchema.Icols;


public class IncomeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "incomeBase.db";

    public IncomeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + IncomeTable.iNAME + "(" +
                "_id integer primary key autoincrement, " +
                Icols.iUUID + ", " +
                Icols.INCOME_NAME + ", " +
                Icols.INCOME_VALUE + ", " +
                Icols.iDATE + ", " +
                Icols.iCATEGORY + ", " +
                Icols.iNOTE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }


}
