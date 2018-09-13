package com.developer.kirill.myfinance;

import android.support.v4.app.Fragment;

public class StatisticsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new StatisticsFragment();
    }
}
