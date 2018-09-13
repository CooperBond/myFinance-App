package com.developer.kirill.myfinance.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.developer.kirill.myfinance.Category;

import java.util.UUID;

public class IncCategoryCursorWrapper extends CursorWrapper {
    public IncCategoryCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Category getCategory() {
        String categoryName = getString(getColumnIndex(IncCategoryDbSchema.IncCategoriesTable.iCategCols.iCATEGORY));
        String categoryId = getString(getColumnIndex(IncCategoryDbSchema.IncCategoriesTable.iCategCols.iCATEGID));
        Category category = new Category(UUID.fromString(categoryId));
        category.setName(categoryName);
        return category;
    }
}
