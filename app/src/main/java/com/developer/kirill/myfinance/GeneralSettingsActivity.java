package com.developer.kirill.myfinance;

import android.support.v4.app.Fragment;

public class GeneralSettingsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new GeneralSettingsFragment();
    }
}
