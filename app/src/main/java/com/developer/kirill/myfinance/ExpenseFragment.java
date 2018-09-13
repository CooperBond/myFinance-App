package com.developer.kirill.myfinance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class ExpenseFragment extends Fragment {
    private static final int REQUEST_DATE = 0;
    private static final String ARG_EXPENSE_ID = "expense_id";
    private static final String DIALOG_DATE = "DialogDateInc";
    private static final String EXPENSE_VAL = "ExpVal";
    private static final String CATEG_VAL= "Categ";
    private static final int REQUEST_EXPENSE = 1;
    private static final int REQUEST_ICON = 2;
    private static final int REQUEST_CATEG = 3;
    private static final String ICON_VAL = "ICon";
    private Expense mExp;
    private Button expNameField;
    private Button expPriceField;
    private Button dateButton;
    private Button categButton;
    private Button dateYesterday;
    private ImageButton makeNote;
    private ImageButton makePhoto;

    public static ExpenseFragment newInstance(UUID incomeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXPENSE_ID, incomeId);
        ExpenseFragment fragment = new ExpenseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID expenseId = (UUID) getArguments().getSerializable(ARG_EXPENSE_ID);
        mExp = ExpenseLab.get(getActivity()).getExpense(expenseId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        ExpenseLab.get(getActivity()).updateExpense(mExp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        expNameField = view.findViewById(R.id.expense_title);
        expNameField.setText(mExp.getExpenseName());
        expNameField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                NameExpensesListFragment dialog = NameExpensesListFragment.newInstance(expNameField.getText().toString());
                dialog.setTargetFragment(ExpenseFragment.this, REQUEST_ICON);
                dialog.show(fragmentManager, ICON_VAL);
            }
        });
        expPriceField = view.findViewById(R.id.price_title);
        expPriceField.setText(String.valueOf(mExp.getExpense()));
        expPriceField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                ValueInputFragment dialog = ValueInputFragment.newInstance(mExp.getExpense());
                dialog.setTargetFragment(ExpenseFragment.this, REQUEST_EXPENSE);
                dialog.show(fragmentManager, EXPENSE_VAL);
            }
        });
        dateButton = view.findViewById(R.id.dateButton);
        updateDate();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mExp.getDate());
                dialog.setTargetFragment(ExpenseFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager, DIALOG_DATE);
            }
        });
        categButton = view.findViewById(R.id.categoryButton);
        categButton.setText(mExp.getCategory());
        categButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                CategoryExpensesListFragment dialog = CategoryExpensesListFragment.newInstance(mExp.getCategory());
                dialog.setTargetFragment(ExpenseFragment.this,REQUEST_CATEG);
                dialog.show(fragmentManager,CATEG_VAL);
            }
        });
        dateYesterday = view.findViewById(R.id.buttonYesterdayExp);
        dateYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                mExp.setDate(calendar.getTime());
                updateDate();
            }
        });
        makeNote= view.findViewById(R.id.buttonNoteExp);
        makeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                NoteFragmentExpense dialog = NoteFragmentExpense.newInstance(mExp);
                dialog.setTargetFragment(ExpenseFragment.this, 0);
                dialog.show(fragmentManager, "test");
            }
        });
        makePhoto = view.findViewById(R.id.buttonCameraExp);
        makePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                PhotoFragment dialog = PhotoFragment.newInstance(mExp);
                dialog.setTargetFragment(ExpenseFragment.this,0);
                dialog.show(fragmentManager,"one");
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mExp.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_EXPENSE) {
            int income = (int) data.getSerializableExtra(ValueInputFragment.EXTRA_EXPENSE);
            mExp.setExpense(income);
            expPriceField.setText(String.valueOf(mExp.getExpense()));
        } else if (requestCode == REQUEST_ICON) {
            String name = (String) data.getSerializableExtra(NameExpensesListFragment.EXTRA_DESCR);
            expNameField.setText(name);
            mExp.setExpenseName(name);
        }else if (requestCode == REQUEST_CATEG){
            String category = (String) data.getSerializableExtra(CategoryExpensesListFragment.EXTRA_CATEG);
            categButton.setText(category);
            mExp.setCategory(category);
        }
    }

    private void updateDate() {
        String dateFormat = "EEE dd, MMM";
        String dateString = DateFormat.format(dateFormat, mExp.getDate()).toString();
        dateButton.setText(dateString);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_delete, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                UUID expenseId = mExp.getId();
                ExpenseLab.get(getActivity()).deleteExpense(expenseId);
                getActivity().finish();
                Toast.makeText(getActivity(), "Expense " + mExp.getExpenseName() + " deleted!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.save:
                Objects.requireNonNull(getActivity()).finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
