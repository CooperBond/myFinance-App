package com.developer.kirill.myfinance;

import android.support.v4.app.Fragment;

public class IncomeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new IncomeListFragment();
    }
}
