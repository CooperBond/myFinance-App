package com.developer.kirill.myfinance.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.developer.kirill.myfinance.Expense;

import java.util.Date;
import java.util.UUID;

public class ExpenseCursorWrapper extends CursorWrapper {
    public ExpenseCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Expense getExpense() {
        String uuidString = getString(getColumnIndex(ExpenseDbSchema.Ecols.eUUID));
        String expenseName = getString(getColumnIndex(ExpenseDbSchema.Ecols.EXPENSE_NAME));
        int expenseValue = getInt(getColumnIndex(ExpenseDbSchema.Ecols.EXPENSE_VALUE));
        long expDate = getLong(getColumnIndex(ExpenseDbSchema.Ecols.eDATE));
        String category = getString(getColumnIndex(ExpenseDbSchema.Ecols.eCATEGORY));
        String note = getString(getColumnIndex(ExpenseDbSchema.Ecols.eNOTE));
        Expense expense = new Expense(UUID.fromString(uuidString));
        expense.setExpense(expenseValue);
        expense.setDate(new Date(expDate));
        expense.setExpenseName(expenseName);
        expense.setCategory(category);
        expense.setNote(note);
        return expense;
    }
}
