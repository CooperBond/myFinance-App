package com.developer.kirill.myfinance;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class StatisticsFragment extends Fragment {
    private RecyclerView mStatRecyclerView;
    private StatiscicsAdapter mAdapter;
    private String periodButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        periodButton = SettingsFragment.period.getText().toString();
        View view = inflater.inflate(R.layout.fragment_stats_list, container,
                false);
        mStatRecyclerView = view
                .findViewById(R.id.stat_recycler_view);
        mStatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        StatiscicsLab statiscicsLab = StatiscicsLab.get(getActivity());
        List<StatisticElement> elements = statiscicsLab.getStatiscics();
        mAdapter = new StatiscicsAdapter(elements);
        mStatRecyclerView.setAdapter(mAdapter);

    }

    private class StatsHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private Button mButtonTextView;
        private StatisticElement mElement;

        public StatsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_stat, parent, false));
            mTitleTextView = itemView.findViewById(R.id.textDescr);
            mButtonTextView = itemView.findViewById(R.id.statButton);
        }

        public void bind(final StatisticElement element) {
            List<Category> categories = ExpenseCategoryLab.get(getActivity()).getCategories();
            List<Expense> expenses = ExpenseLab.get(getActivity()).getExpenses("Standard");
            mElement = element;
            mTitleTextView.setText(mElement.getDescription());
            mButtonTextView.setText(mElement.getElement());
            Drawable drawable = getResources().getDrawable(R.drawable.grad_pink);
            itemView.setBackground(drawable);
            if (mTitleTextView.getText().toString().equals("Maximum by category")) {
                mButtonTextView.setText(categories.get(getMaxExpenseCategory(categories, expenses)).getCategory());
            } else if (mTitleTextView.getText().toString().equals("Minimum by category")) {
                mButtonTextView.setText(categories.get(getMinExpenseCategory(categories, expenses)).getCategory());
            } else if (mTitleTextView.getText().toString().equals("Maximum expense")) {
                mButtonTextView.setText(mElement.getElement());
            } else if (mTitleTextView.getText().toString().equals("Minimum expense")) {
                mButtonTextView.setText(mElement.getElement());
            } else if (mTitleTextView.getText().toString().equals("Period")) {
                mButtonTextView.setText(periodButton);
            }
            mButtonTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTitleTextView.getText().toString().equals("Period")) {

                    } else if (mTitleTextView.getText().toString().equals("Total Income")) {
                        Intent intent = new Intent(getActivity(), IncomeListActivity.class);
                        IncomeListFragment.setSortParameter(SettingsFragment.period.getText().toString());
                        startActivity(intent);

                    } else if (mTitleTextView.getText().toString().equals("Total Expenses")) {
                        Intent intent = new Intent(getActivity(), ExpenseListActivity.class);
                        ExpenseListFragment.setSortParameter(SettingsFragment.period.getText().toString());
                        startActivity(intent);
                    } else if (mTitleTextView.getText().toString().equals("Maximum expense")) {
                        Intent intent = ExpensePagerActivity.newIntent(getActivity(), mElement.getElementId());
                        startActivity(intent);
                    } else if (mTitleTextView.getText().toString().equals("Maximum by category")) {
                        Intent intent = new Intent(getActivity(), ExpenseListActivity.class);
                        ExpenseListFragment.setSortParameter(mButtonTextView.getText().toString());
                        startActivity(intent);
                    } else if (mTitleTextView.getText().toString().equals("Minimum expense")) {
                        Intent intent = ExpensePagerActivity.newIntent(getActivity(), mElement.getElementId());
                        startActivity(intent);
                    } else if (mTitleTextView.getText().toString().equals("Minimum by category")) {
                        Intent intent = new Intent(getActivity(), ExpenseListActivity.class);
                        ExpenseListFragment.setSortParameter(mButtonTextView.getText().toString());
                        startActivity(intent);
                    } else if (mTitleTextView.getText().toString().equals("Average expenditure per day")) {

                    } else if (mTitleTextView.getText().toString().equals("Average expense arithmetically")) {

                    }

                }
            });
        }

        private int getMaxExpenseCategory(List<Category> categories, List<Expense> expenses) {
            int[] totals = new int[categories.size()];
            for (int i = 0; i < totals.length; i++) {
                for (int j = 0; j < expenses.size(); j++) {
                    if (expenses.get(j).getCategory().equals(categories.get(i).getCategory())) {
                        totals[i] += expenses.get(j).getExpense();
                    }
                }
            }
            int max = totals[0];
            for (int i = 1; i < totals.length; i++) {
                if (totals[i] > max) {
                    max = totals[i];
                }
            }
            int index = 0;
            for (int i = 0; i < totals.length; i++) {
                if (totals[i] == max) {
                    index = i;
                }
            }
            return index;
        }

        private int getMinExpenseCategory(List<Category> categories, List<Expense> expenses) {
            int[] totals = new int[categories.size()];
            for (int i = 0; i < totals.length; i++) {
                for (int j = 0; j < expenses.size(); j++) {
                    if (expenses.get(j).getCategory().equals(categories.get(i).getCategory())) {
                        totals[i] += expenses.get(j).getExpense();
                    }
                }
            }
            int min = 0;
            if (totals[0] == 0) {
                for (int i = 0; i < totals.length; i++) {
                    if (totals[i] != 0) {
                        min = totals[i];
                        break;
                    }
                }
            } else {
                min = totals[0];
            }
            for (int i = 1; i < totals.length; i++) {
                if (totals[i] < min && totals[i] != 0) {
                    min = totals[i];
                }
            }
            int index = 0;
            for (int i = 0; i < totals.length; i++) {
                if (totals[i] == min) {
                    index = i;
                }
            }
            return index;
        }
    }

    private class StatiscicsAdapter extends RecyclerView.Adapter<StatsHolder> {
        private List<StatisticElement> mElements;

        public StatiscicsAdapter(List<StatisticElement> elements) {
            mElements = elements;
        }


        @NonNull
        @Override
        public StatsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new StatsHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull StatsHolder statsHolder, int i) {
            StatisticElement statisticElement = mElements.get(i);
            statsHolder.bind(statisticElement);
        }

        @Override
        public int getItemCount() {
            return mElements.size();
        }
    }
}
