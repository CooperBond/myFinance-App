package com.developer.kirill.myfinance.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.developer.kirill.myfinance.Name;

import java.util.UUID;

public class ExpNamesCursorWrapper extends CursorWrapper {
    public ExpNamesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Name getName() {
        String expenseId = getString(getColumnIndex(ExpenseAddedNamesDbSchema.ENcols.expInd));
        String expenseName = getString(getColumnIndex(ExpenseAddedNamesDbSchema.ENcols.expName));
        Name name = new Name(UUID.fromString(expenseId));
        name.setDescription(expenseName);
        return name;
    }
}
