package com.developer.kirill.myfinance.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.developer.kirill.myfinance.Name;

import java.util.UUID;

public class IncNamesCursorWrapper extends CursorWrapper {
    public IncNamesCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Name getName() {
        String incomeName = getString(getColumnIndex(IncNamesDbSchema.IncCols.incName));
        String incomeId = getString(getColumnIndex(IncNamesDbSchema.IncCols.nameID));
        Name name = new Name(UUID.fromString(incomeId));
        name.setDescription(incomeName);
        return name;
    }
}
