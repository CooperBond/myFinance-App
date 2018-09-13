package com.developer.kirill.myfinance;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.kirill.myfinance.database.ExpNamesBaseHelper;
import com.developer.kirill.myfinance.database.ExpNamesCursorWrapper;
import com.developer.kirill.myfinance.database.ExpenseAddedNamesDbSchema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class NameExpenseLab {
    private static NameExpenseLab sIconExpenseLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static NameExpenseLab get(Context context) {
        if (sIconExpenseLab == null) {
            sIconExpenseLab = new NameExpenseLab(context);
        }
        return sIconExpenseLab;
    }

    public Name getName(UUID id) {
        ExpNamesCursorWrapper cursor = queryExpenses(
                ExpenseAddedNamesDbSchema.ENcols.expInd + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getName();
        } finally {
            cursor.close();
        }
    }

    public void delete(UUID uuid) {
        mDatabase.delete(ExpenseAddedNamesDbSchema.ExpenseNameTable.NAMEN, ExpenseAddedNamesDbSchema.ENcols.expInd
                + " = ?", new String[]{uuid.toString()});
    }

    public List<Name> getNames() {
        List<Name> expenses = new ArrayList<>();
        ExpNamesCursorWrapper cursor = queryExpenses(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                expenses.add(cursor.getName());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        expenses = sortByAlphabet(expenses);
        return expenses;
    }

    private List<Name> sortByAlphabet(final List<Name> list) {
        Collections.sort(list, new Comparator<Name>() {

            @Override
            public int compare(Name name, Name t1) {
                return name.getDescription().compareTo(t1.getDescription());
            }
        });
        return list;
    }

    public void updateName(Name name) {
        String text = name.getNameId().toString();
        ContentValues values = getContentValues(name);
        mDatabase.update(ExpenseAddedNamesDbSchema.ExpenseNameTable.NAMEN
                , values
                , ExpenseAddedNamesDbSchema.ENcols.expInd + " = ?"
                , new String[]{text});
    }

    private NameExpenseLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ExpNamesBaseHelper(mContext).getWritableDatabase();

    }

    public void addName(Name name) {
        ContentValues values = getContentValues(name);
        mDatabase.insert(ExpenseAddedNamesDbSchema.ExpenseNameTable.NAMEN, null, values);
    }

    private ExpNamesCursorWrapper queryExpenses(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ExpenseAddedNamesDbSchema.ExpenseNameTable.NAMEN,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ExpNamesCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Name name) {
        ContentValues values = new ContentValues();
        values.put(ExpenseAddedNamesDbSchema.ENcols.expInd, name.getNameId().toString());
        values.put(ExpenseAddedNamesDbSchema.ENcols.expName, name.getDescription());
        return values;
    }
}

