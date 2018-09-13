package com.developer.kirill.myfinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import com.developer.kirill.myfinance.database.IncomeBaseHelper;
import com.developer.kirill.myfinance.database.IncomeCursorWrapper;
import com.developer.kirill.myfinance.database.IncomeDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class IncomeLab {
    private static IncomeLab sIncomeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static String smartParam;

    public static void setPeriodParam(String param) {
        smartParam = param;
    }

    public static IncomeLab get(Context context) {
        if (sIncomeLab == null) {
            sIncomeLab = new IncomeLab(context);
        }
        return sIncomeLab;
    }

    private IncomeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new IncomeBaseHelper(mContext).getWritableDatabase();

    }

    public void addIncome(Income income) {
        ContentValues values = getContentValues(income);
        mDatabase.insert(IncomeDbSchema.IncomeTable.iNAME, null, values);
    }

    public List<Income> getIncomes(String parameter) {
        List<Income> incomes = new ArrayList<>();
        IncomeCursorWrapper cursor = queryExpenses(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                incomes.add(cursor.getIncome());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        incomes = sortByPeriod(incomes, smartParam);
        if (parameter.equals("Standard")) {
            incomes = noSort(incomes);
        } else if (parameter.equals("Date")) {
            incomes = sortByDate(incomes);
        } else if (parameter.equals("Ascending")) {
            incomes = sortAscending(incomes);
        } else if (parameter.equals("Descending")) {
            incomes = sortDescending(incomes);
        }
        return incomes;
    }

    private List<Income> sortByDate(List<Income> list) {
        Collections.sort(list, new Comparator<Income>() {
            @Override
            public int compare(Income income, Income t1) {
                return income.getDate().compareTo(t1.getDate());
            }
        });
        return list;
    }

    private List<Income> sortAscending(List<Income> list) {
        Collections.sort(list, new Comparator<Income>() {
            @Override
            public int compare(Income income, Income t1) {
                Integer a = income.getIncome();
                Integer b = t1.getIncome();
                return a.compareTo(b);
            }
        });
        return list;
    }

    private List<Income> sortDescending(List<Income> list) {
        Collections.sort(list, new Comparator<Income>() {
            @Override
            public int compare(Income income, Income t1) {
                Integer a = income.getIncome();
                Integer b = t1.getIncome();
                return b.compareTo(a);
            }
        });
        return list;
    }

    private List<Income> sortByPeriod(List<Income> list, String date) {
        List<Income> newList = new ArrayList<>();
        String dateFormat = "MMM";
        for (int i = 0; i < list.size(); i++) {
            String mDate = DateFormat.format(dateFormat, list.get(i).getDate()).toString();
            if (mDate.equals(date)) {
                newList.add(list.get(i));
            }
        }
        return newList;
    }

    private List<Income> noSort(List<Income> list) {
        return list;
    }

    public Income getIncome(UUID id) {
        IncomeCursorWrapper cursor = queryExpenses(
                IncomeDbSchema.Icols.iUUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getIncome();
        } finally {
            cursor.close();
        }
    }

    public void deleteIncome(UUID incomeId) {
        String uuidString = incomeId.toString();
        mDatabase.delete(IncomeDbSchema.IncomeTable.iNAME, IncomeDbSchema.Icols.iUUID
                + " = ?", new String[]{uuidString});
    }

    public void updateIncome(Income income) {
        String uuidString = income.getID().toString();
        ContentValues values = getContentValues(income);
        mDatabase.update(IncomeDbSchema.IncomeTable.iNAME
                , values
                , IncomeDbSchema.Icols.iUUID + " = ?"
                , new String[]{uuidString});
    }


    private IncomeCursorWrapper queryExpenses(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                IncomeDbSchema.IncomeTable.iNAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new IncomeCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Income income) {
        ContentValues values = new ContentValues();
        values.put(IncomeDbSchema.Icols.iUUID, income.getID().toString());
        values.put(IncomeDbSchema.Icols.INCOME_NAME, income.getIncomeName());
        values.put(IncomeDbSchema.Icols.INCOME_VALUE, String.valueOf(income.getIncome()));
        values.put(IncomeDbSchema.Icols.iDATE, income.getDate().getTime());
        values.put(IncomeDbSchema.Icols.iCATEGORY, income.getCategory());
        values.put(IncomeDbSchema.Icols.iNOTE, income.getNote());
        return values;
    }

    public File getPhotoFile(Income income) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, income.getPhotoFilename());
    }
}
