package com.developer.kirill.myfinance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.developer.kirill.myfinance.database.ExpenseDbSchema.ExpenseTable;

import com.developer.kirill.myfinance.database.ExpenseDbSchema.Ecols;


public class ExpenseBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "expenseBase.db";

    public ExpenseBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ExpenseTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                Ecols.eUUID + ", " +
                Ecols.EXPENSE_NAME + ", " +
                Ecols.EXPENSE_VALUE + ", " +
                Ecols.eDATE + ", " +
                Ecols.eCATEGORY +", "+
                Ecols.eNOTE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }


}

