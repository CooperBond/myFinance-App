package com.developer.kirill.myfinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.kirill.myfinance.database.IncCategoryBaseHelper;
import com.developer.kirill.myfinance.database.IncCategoryCursorWrapper;
import com.developer.kirill.myfinance.database.IncCategoryDbSchema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class IncomeCategoryLab {
    private static IncomeCategoryLab sCategory;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static IncomeCategoryLab get(Context context) {
        if (sCategory == null) {
            sCategory = new IncomeCategoryLab(context);
        }
        return sCategory;
    }

    public void addCategory(Category category) {
        ContentValues values = getContentValues(category);
        mDatabase.insert(IncCategoryDbSchema.IncCategoriesTable.iNAME, null, values);
    }

    public void updateCategory(Category category) {
        String uuidString = category.getCategoryId().toString();
        ContentValues values = getContentValues(category);
        mDatabase.update(IncCategoryDbSchema.IncCategoriesTable.iNAME
                , values
                , IncCategoryDbSchema.IncCategoriesTable.iCategCols.iCATEGID + " = ?"
                , new String[]{uuidString});
    }

    public void delete(UUID id) {
        mDatabase.delete(IncCategoryDbSchema.IncCategoriesTable.iNAME, IncCategoryDbSchema.IncCategoriesTable.iCategCols.iCATEGID
                + " = ?", new String[]{id.toString()});
    }

    public Category getCategory(UUID id) {
        IncCategoryCursorWrapper cursor = queryCategories(
                IncCategoryDbSchema.IncCategoriesTable.iCategCols.iCATEGID + " = ?",
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

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        IncCategoryCursorWrapper cursor = queryCategories(null, null);
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

    private IncomeCategoryLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new IncCategoryBaseHelper(mContext).getWritableDatabase();
    }

    private IncCategoryCursorWrapper queryCategories(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                IncCategoryDbSchema.IncCategoriesTable.iNAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new IncCategoryCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Category category) {
        ContentValues values = new ContentValues();
        values.put(IncCategoryDbSchema.IncCategoriesTable.iCategCols.iCATEGORY, category.getCategory());
        values.put(IncCategoryDbSchema.IncCategoriesTable.iCategCols.iCATEGID, category.getCategoryId().toString());
        return values;
    }

}
