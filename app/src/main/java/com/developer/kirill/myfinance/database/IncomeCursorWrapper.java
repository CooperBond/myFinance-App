package com.developer.kirill.myfinance.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.developer.kirill.myfinance.Income;

import java.util.Date;
import java.util.UUID;

public class IncomeCursorWrapper extends CursorWrapper {
    public IncomeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Income getIncome() {
        String uuidString = getString(getColumnIndex(IncomeDbSchema.Icols.iUUID));
        String incomeName = getString(getColumnIndex(IncomeDbSchema.Icols.INCOME_NAME));
        int incomeValue = getInt(getColumnIndex(IncomeDbSchema.Icols.INCOME_VALUE));
        long incDate = getLong(getColumnIndex(IncomeDbSchema.Icols.iDATE));
        String category = getString(getColumnIndex(IncomeDbSchema.Icols.iCATEGORY));
        String note = getString(getColumnIndex(IncomeDbSchema.Icols.iNOTE));
        Income income = new Income(UUID.fromString(uuidString));
        income.setIncome(incomeValue);
        income.setDate(new Date(incDate));
        income.setIncomeName(incomeName);
        income.setCategory(category);
        income.setNote(note);
        return income;
    }
}