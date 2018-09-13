package com.developer.kirill.myfinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.kirill.myfinance.database.ExpCategoryBaseHelper;
import com.developer.kirill.myfinance.database.ExpCategoryCursorWrapper;
import com.developer.kirill.myfinance.database.ExpCategoryDbSchema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ExpenseCategoryLab {
    private static ExpenseCategoryLab sCategory;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static ExpenseCategoryLab get(Context context) {
        if (sCategory == null) {
            sCategory = new ExpenseCategoryLab(context);
        }
        return sCategory;
    }

    public Category getCategory(UUID id) {
        ExpCategoryCursorWrapper cursor = queryCategories(
                ExpCategoryDbSchema.ExpCategoriesTable.eCategCols.eCATEGID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCategory();
        } finally {
            cursor.close();
        }
    }

    public void delete(UUID id) {
        mDatabase.delete(ExpCategoryDbSchema.ExpCategoriesTable.eNAME, ExpCategoryDbSchema.ExpCategoriesTable.eCategCols.eCATEGID
                + " = ?", new String[]{id.toString()});
    }

    public void updateCategory(Category category) {
        String uuidString = category.getCategoryId().toString();
        ContentValues values = getContentValues(category);
        mDatabase.update(ExpCategoryDbSchema.ExpCategoriesTable.eNAME
                , values
                , ExpCategoryDbSchema.ExpCategoriesTable.eCategCols.eCATEGID + " = ?"
                , new String[]{uuidString});
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        ExpCategoryCursorWrapper cursor = queryCategories(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                categories.add(cursor.getCategory());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        categories = sortByAlphabet(categories);
        return categories;
    }

    private List<Category> sortByAlphabet(final List<Category> list) {
        Collections.sort(list, new Comparator<Category>() {
            @Override
            public int compare(Category category, Category t1) {
                return category.getCategory().compareTo(t1.getCategory());
            }
        });
        return list;
    }

    private ExpCategoryCursorWrapper queryCategories(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ExpCategoryDbSchema.ExpCategoriesTable.eNAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ExpCategoryCursorWrapper(cursor);
    }

    public void addCategory(Category category) {
        ContentValues values = getContentValues(category);
        mDatabase.insert(ExpCategoryDbSchema.ExpCategoriesTable.eNAME, null, values);
    }

    private static ContentValues getContentValues(Category category) {
        ContentValues values = new ContentValues();
        values.put(ExpCategoryDbSchema.ExpCategoriesTable.eCategCols.eCATEGID, category.getCategoryId().toString());
        values.put(ExpCategoryDbSchema.ExpCategoriesTable.eCategCols.eCATEGORY, category.getCategory());
        return values;
    }

    private ExpenseCategoryLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ExpCategoryBaseHelper(mContext).getWritableDatabase();
    }

}

