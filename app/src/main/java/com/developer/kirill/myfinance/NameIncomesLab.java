package com.developer.kirill.myfinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.kirill.myfinance.database.IncNamesBaseHelper;
import com.developer.kirill.myfinance.database.IncNamesCursorWrapper;
import com.developer.kirill.myfinance.database.IncNamesDbSchema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class NameIncomesLab {

    private static NameIncomesLab sIconIncomesLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static NameIncomesLab get(Context context) {
        if (sIconIncomesLab == null) {
            sIconIncomesLab = new NameIncomesLab(context);
        }
        return sIconIncomesLab;
    }

    public List<Name> getNames() {
        List<Name> incomes = new ArrayList<>();
        IncNamesCursorWrapper cursor = queryExpenses(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                incomes.add(cursor.getName());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        incomes = sortByAlphabet(incomes);
        return incomes;
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
        String uuidString = name.getNameId().toString();
        ContentValues values = getContentValues(name);
        mDatabase.update(IncNamesDbSchema.IncomesNameTable.NAMEi
                , values
                , IncNamesDbSchema.IncCols.nameID + " = ?"
                , new String[]{uuidString});
    }

    public Name getName(UUID id) {
        IncNamesCursorWrapper cursor = queryExpenses(
                IncNamesDbSchema.IncCols.nameID + " = ?",
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

    public void delete(UUID id) {
        mDatabase.delete(IncNamesDbSchema.IncomesNameTable.NAMEi, IncNamesDbSchema.IncCols.nameID
                + " = ?", new String[]{id.toString()});
    }

    private NameIncomesLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new IncNamesBaseHelper(mContext).getWritableDatabase();
    }

    public void addName(Name name) {
        ContentValues values = getContentValues(name);
        mDatabase.insert(IncNamesDbSchema.IncomesNameTable.NAMEi, null, values);
    }

    private IncNamesCursorWrapper queryExpenses(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                IncNamesDbSchema.IncomesNameTable.NAMEi,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new IncNamesCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Name name) {
        ContentValues values = new ContentValues();
        values.put(IncNamesDbSchema.IncCols.incName, name.getDescription());
        values.put(IncNamesDbSchema.IncCols.nameID, name.getNameId().toString());
        return values;
    }
}

