package com.developer.kirill.myfinance;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.developer.kirill.myfinance.Balance.balanceObject;

public class SettingsFragment extends Fragment {
    private ImageButton expensesList;
    private ImageButton report;
    private ImageButton incomeList;
    private ImageButton settings;
    private TextView balanceView;
    private TextView totalIncome;
    private TextView totalExpense;
    public static Button period;
    private static final int REQUESTED_PERIOD = 2;
    private static final int NOTIFY_ID = 101;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_main_act, container, false);
        period = view.findViewById(R.id.buttonPeriod);
        String dateFormat = "MMM";
        Date date = new Date();
        String dateString1 = DateFormat.format(dateFormat, date).toString();
        period.setText(dateString1);
        period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                PeriodCheckFragment dialog = PeriodCheckFragment.newInstance(period.getText().toString());
                dialog.setTargetFragment(SettingsFragment.this, REQUESTED_PERIOD);
                dialog.show(fragmentManager, "periodVal");
            }
        });
        expensesList = view.findViewById(R.id.expenseButton);
        expensesList.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExpenseListActivity.class);
                ExpenseLab.setPeriodParam(period.getText().toString());
                ExpenseListFragment.setSortParameter("Date");
                try {
                    ExpenseListFragment.setPeriod(dateSet());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
        incomeList = view.findViewById(R.id.incomeButton);
        incomeList.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IncomeListActivity.class);
                IncomeLab.setPeriodParam(period.getText().toString());
                IncomeListFragment.setSortParameter("Date");
                try {
                    IncomeListFragment.setPeriod(dateSet());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
        report = view.findViewById(R.id.reportButton);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                startActivity(intent);
            }
        });
        settings = view.findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GeneralSettingsActivity.class);
                startActivity(intent);
            }
        });
        totalIncome = view.findViewById(R.id.totalIncome);
        balanceView = view.findViewById(R.id.balanceView);
        totalExpense = view.findViewById(R.id.totalExpenses);
        updateUI();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Date dateSet() throws ParseException {
        Date newDate = new Date();
        String dateFormat = "MMM";
        String mDate = DateFormat.format(dateFormat, newDate).toString();
        String s=period.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MMM");
        Date docDate= format.parse(s);
        SimpleDateFormat formatCurrent = new SimpleDateFormat();
        formatCurrent.applyPattern("MMM");
        Date curDate = formatCurrent.parse(mDate);
        if (!docDate.equals(curDate)){
            newDate = docDate;
        }
        return newDate;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUESTED_PERIOD) {
            String newPeriod;
            newPeriod = (String) data.getSerializableExtra(PeriodCheckFragment.EXTRA_PERIOD);
            period.setText(newPeriod);
            updateUI();
        }
    }

    private void updateUI() {
        ExpenseLab.setPeriodParam(period.getText().toString());
        IncomeLab.setPeriodParam(period.getText().toString());
        setTotals();
        int exp = balanceObject.getTotalE();
        int inc = balanceObject.getTotalI();
        balanceObject.setBalance(inc, exp);
        balanceView.setText("Balance is: " + String.valueOf(balanceObject.getBalance()));
        totalIncome.setText("+ " + String.valueOf(inc));
        totalExpense.setText("- " + String.valueOf(exp));
    }

    public void setTotals() {
        int totalIncome = readDBincomes();
        int totalExpense = readDBExpenses();
        balanceObject.setTotalI(totalIncome);
        balanceObject.setTotalE(totalExpense);
    }


    public int readDBExpenses() {
        ExpenseLab expenseLab = ExpenseLab.get(getActivity());
        List<Expense> expenses = expenseLab.getExpenses("Standard");
        int totalExp = 0;
        Expense[] data = new Expense[expenses.size()];
        for (int i = 0; i < expenses.size(); i++) {
            data[i] = expenses.get(i);
        }
        for (int i = 0; i < data.length; i++) {
            totalExp += data[i].getExpense();
        }
        return totalExp;
    }

    public int readDBincomes() {
        IncomeLab incomeLab = IncomeLab.get(getActivity());
        List<Income> incomes = incomeLab.getIncomes("Date");
        int totalInc = 0;
        Income[] data = new Income[incomes.size()];
        for (int i = 0; i < incomes.size(); i++) {
            data[i] = incomes.get(i);
        }
        for (int i = 0; i < data.length; i++) {
            totalInc += data[i].getIncome();
        }
        return totalInc;
    }
}
