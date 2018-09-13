package com.developer.kirill.myfinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.kirill.myfinance.database.ExpenseBaseHelper;
import com.developer.kirill.myfinance.database.ExpenseCursorWrapper;
import com.developer.kirill.myfinance.database.ExpenseDbSchema;
import com.developer.kirill.myfinance.database.ExpenseDbSchema.Ecols;

import java.io.File;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


public class ExpenseLab {

    private static ExpenseLab sExpenseLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static String smartParam;

    public static void setPeriodParam(String param) {
        smartParam = param;
    }

    public static ExpenseLab get(Context context) {
        if (sExpenseLab == null) {
            sExpenseLab = new ExpenseLab(context);
        }
        return sExpenseLab;
    }

    private ExpenseLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ExpenseBaseHelper(mContext).getWritableDatabase();
    }

    public void addExpense(Expense expense) {
        ContentValues values = getContentValues(expense);
        mDatabase.insert(ExpenseDbSchema.ExpenseTable.NAME, null, values);
    }

    public List<Expense> getExpenses(String parameter) {
        List<Expense> expenses = new ArrayList<>();
        ExpenseCursorWrapper cursor = queryExpenses(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                expenses.add(cursor.getExpense());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        expenses = sortByPeriod(expenses, smartParam);
        if (parameter.equals("Standard")) {
            expenses = noSort(expenses);
        } else if (parameter.equals("Date")) {
            expenses = sortByDate(expenses);
        } else if (parameter.equals("Ascending")) {
            expenses = sortAscending(expenses);
        } else if (parameter.equals("Descending")) {
            expenses = sortDescending(expenses);
        } else {
            expenses = sortSmart(expenses, parameter);
        }
        return expenses;
    }

    private List<Expense> sortByDate(List<Expense> list) {
        Collections.sort(list, new Comparator<Expense>() {
            @Override
            public int compare(Expense expense, Expense t1) {
                return expense.getDate().compareTo(t1.getDate());
            }
        });
        return list;
    }

    private List<Expense> sortAscending(List<Expense> list) {
        Collections.sort(list, new Comparator<Expense>() {
            @Override
            public int compare(Expense expense, Expense t1) {
                Integer a = expense.getExpense();
                Integer b = t1.getExpense();
                return a.compareTo(b);
            }
        });
        return list;
    }

    private List<Expense> sortDescending(List<Expense> list) {
        Collections.sort(list, new Comparator<Expense>() {
            @Override
            public int compare(Expense expense, Expense t1) {
                Integer a = expense.getExpense();
                Integer b = t1.getExpense();
                return b.compareTo(a);
            }
        });
        return list;
    }

    private List<Expense> sortSmart(List<Expense> list, String category) {
        List<Expense> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCategory().equals(category)) {
                newList.add(list.get(i));
            }
        }
        return newList;
    }

    private List<Expense> sortByPeriod(List<Expense> list, String date) {
        List<Expense> newList = new ArrayList<>();
        String dateFormat = "MMM";
        for (int i = 0; i < list.size(); i++) {
            String mDate = DateFormat.format(dateFormat, list.get(i).getDate()).toString();
            if (mDate.equals(date)) {
                newList.add(list.get(i));
            }
        }
        return newList;
    }


    private List<Expense> noSort(List<Expense> list) {
        return list;
    }

    public Expense getExpense(UUID id) {
        ExpenseCursorWrapper cursor = queryExpenses(
                Ecols.eUUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getExpense();
        } finally {
            cursor.close();
        }
    }

    public void deleteExpense(UUID expenseId) {
        String uuidString = expenseId.toString();
        mDatabase.delete(ExpenseDbSchema.ExpenseTable.NAME, Ecols.eUUID
                + " = ?", new String[]{uuidString});
    }

    public void updateExpense(Expense expense) {
        String uuidString = expense.getId().toString();
        ContentValues values = getContentValues(expense);
        mDatabase.update(ExpenseDbSchema.ExpenseTable.NAME
                , values
                , Ecols.eUUID + " = ?"
                , new String[]{uuidString});
    }

    private ExpenseCursorWrapper queryExpenses(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ExpenseDbSchema.ExpenseTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ExpenseCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Expense expense) {
        ContentValues values = new ContentValues();
        values.put(Ecols.eUUID, expense.getId().toString());
        values.put(Ecols.EXPENSE_NAME, expense.getExpenseName());
        values.put(Ecols.EXPENSE_VALUE, String.valueOf(expense.getExpense()));
        values.put(Ecols.eDATE, expense.getDate().getTime());
        values.put(Ecols.eCATEGORY, expense.getCategory());
        values.put(Ecols.eNOTE, expense.getNote());
        return values;
    }

    public File getPhotoFile(Expense expense) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, expense.getPhotoFilename());
    }

}
