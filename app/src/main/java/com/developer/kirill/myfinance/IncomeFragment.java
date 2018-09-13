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

public class IncomeFragment extends Fragment {
    private static final int REQUEST_DATE = 0;
    private static final String ARG_INCOME_ID = "income_id";
    private static final String DIALOG_DATE = "DialogDateInc";
    private static final String INCOME_VAL = "IncVal";
    private static final int REQUEST_INCOME = 1;
    private static final int REQUEST_ICON = 2;
    private static final String ICON_VAL = "ICon";
    private static final String CATEG_VAL = "Categ";
    private static final int REQUEST_CATEG = 3;
    private Income mInc;
    private Button incNameField;
    private Button incPriceField;
    private Button dateButton;
    private Button categoryButton;
    private Button yesterdayButton;
    private ImageButton note;
    private ImageButton photo;

    public static IncomeFragment newInstance(UUID incomeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_INCOME_ID, incomeId);
        IncomeFragment fragment = new IncomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID incomeId = (UUID) getArguments().getSerializable(ARG_INCOME_ID);
        mInc = IncomeLab.get(getActivity()).getIncome(incomeId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        IncomeLab.get(getActivity()).updateIncome(mInc);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        incNameField = view.findViewById(R.id.income_title);
        incNameField.setText(mInc.getIncomeName());
        incNameField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                NameIncomesListFragment dialog = NameIncomesListFragment.newInstance(incNameField.getText().toString());
                dialog.setTargetFragment(IncomeFragment.this, REQUEST_ICON);
                dialog.show(fragmentManager, ICON_VAL);
            }
        });
        incPriceField = view.findViewById(R.id.price_title_i);
        incPriceField.setText(String.valueOf(mInc.getIncome()));
        incPriceField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                ValueInputFragment dialog = ValueInputFragment.newInstance(mInc.getIncome());
                dialog.setTargetFragment(IncomeFragment.this, REQUEST_INCOME);
                dialog.show(fragmentManager, INCOME_VAL);
            }
        });
        dateButton = view.findViewById(R.id.buttonDateInc);
        updateDate();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mInc.getDate());
                dialog.setTargetFragment(IncomeFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager, DIALOG_DATE);
            }
        });
        categoryButton = view.findViewById(R.id.categoryButtonIncome);
        categoryButton.setText(mInc.getCategory());
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                CategoryIncomesListFragment dialog = CategoryIncomesListFragment.newInstance(mInc.getCategory());
                dialog.setTargetFragment(IncomeFragment.this, REQUEST_CATEG);
                dialog.show(fragmentManager, CATEG_VAL);
            }
        });
        yesterdayButton = view.findViewById(R.id.yesterdayDateButton);
        yesterdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                mInc.setDate(calendar.getTime());
                updateDate();
            }
        });
        note = view.findViewById(R.id.note_button_inc);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                NoteFragmentIncome dialog = NoteFragmentIncome.newInstance(mInc);
                dialog.setTargetFragment(IncomeFragment.this, 0);
                dialog.show(fragmentManager, "test");
            }
        });
        photo = view.findViewById(R.id.photo_button_inc);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                IncomePhotoFragment dialog = IncomePhotoFragment.newInstance(mInc);
                dialog.setTargetFragment(IncomeFragment.this,1);
                dialog.show(fragmentManager,"two");
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
            mInc.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_INCOME) {
            int income = (int) data.getSerializableExtra(ValueInputFragment.EXTRA_EXPENSE);
            mInc.setIncome(income);
            incPriceField.setText(String.valueOf(mInc.getIncome()));
        } else if (requestCode == REQUEST_ICON) {
            String name = (String) data.getSerializableExtra(NameExpensesListFragment.EXTRA_DESCR);
            incNameField.setText(name);
            mInc.setIncomeName(name);
        } else if (requestCode == REQUEST_CATEG) {
            String name = (String) data.getSerializableExtra(CategoryIncomesListFragment.EXTRA_CATEGI);
            categoryButton.setText(name);
            mInc.setCategory(name);
        }
    }

    private void updateDate() {
        String dateFormat = "EEE dd, MMM";
        String dateString = DateFormat.format(dateFormat, mInc.getDate()).toString();
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
                UUID incomeId = mInc.getID();
                IncomeLab.get(getActivity()).deleteIncome(incomeId);
                getActivity().finish();
                Toast.makeText(getActivity(), "Income " + mInc.getIncomeName() + " deleted!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.save:
                Objects.requireNonNull(getActivity()).finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
