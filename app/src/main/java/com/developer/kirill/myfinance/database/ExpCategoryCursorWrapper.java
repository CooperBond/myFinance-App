package com.developer.kirill.myfinance.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.developer.kirill.myfinance.Category;

import java.util.UUID;

public class ExpCategoryCursorWrapper extends CursorWrapper {
    public ExpCategoryCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Category getCategory() {
        String categoryName = getString(getColumnIndex(ExpCategoryDbSchema.ExpCategoriesTable.eCategCols.eCATEGORY));
        String categoryId = getString(getColumnIndex(ExpCategoryDbSchema.ExpCategoriesTable.eCategCols.eCATEGID));
        Category category = new Category(UUID.fromString(categoryId));
        category.setName(categoryName);
        return category;
    }
}
