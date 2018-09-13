package com.developer.kirill.myfinance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;


public class IncomePagerActivity extends AppCompatActivity {
    public static final String EXTRA_INCOME_ID = "income_id";
    private ViewPager mViewPager;
    private List<Income> mIncomes;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, IncomePagerActivity.class);
        intent.putExtra(EXTRA_INCOME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_pager);

        UUID incomeId = (UUID) getIntent().getSerializableExtra(EXTRA_INCOME_ID);

        mViewPager = findViewById(R.id.income_view_pager);
        mIncomes = IncomeLab.get(this).getIncomes("Date");
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Income income = mIncomes.get(position);
                return IncomeFragment.newInstance(income.getID());
            }

            @Override
            public int getCount() {
                return mIncomes.size();
            }
        });
        for (int i = 0; i < mIncomes.size(); i++) {
            if (mIncomes.get(i).getID().equals(incomeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


}
