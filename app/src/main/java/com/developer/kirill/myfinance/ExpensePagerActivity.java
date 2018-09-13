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


public class ExpensePagerActivity extends AppCompatActivity {
    public static final String EXTRA_EXPENSE_ID = "expense_id";
    private ViewPager mViewPager;
    private List<Expense> mExpenses;

    public static Intent newIntent(Context packageContext, UUID expenseId) {
        Intent intent = new Intent(packageContext, ExpensePagerActivity.class);
        intent.putExtra(EXTRA_EXPENSE_ID, expenseId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_pager);

        UUID expenseId = (UUID) getIntent().getSerializableExtra(EXTRA_EXPENSE_ID);

        mViewPager = findViewById(R.id.expense_view_pager);
        mExpenses = ExpenseLab.get(this).getExpenses("Date");
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Expense expense = mExpenses.get(position);
                return ExpenseFragment.newInstance(expense.getId());
            }

            @Override
            public int getCount() {
                return mExpenses.size();
            }
        });
        for (int i = 0; i < mExpenses.size(); i++) {
            if (mExpenses.get(i).getId().equals(expenseId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


}
