package com.developer.kirill.myfinance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GeneralSettingsFragment extends Fragment {
    private Button setBase;
    private Button eraseData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_sets, container, false);
        setBase = view.findViewById(R.id.addBase);
        setBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name name = new Name();
                name.setDescription("Food");
                Name name1 = new Name();
                name1.setDescription("Fuel");
                Name name2 = new Name();
                name2.setDescription("Rent payment");
                Name name3 = new Name();
                name3.setDescription("Cinema");
                Name name4 = new Name();
                name4.setDescription("Restaurants");
                Name name5 = new Name();
                name5.setDescription("Drinks");
                Name name6 = new Name();
                name6.setDescription("Public transport");
                Name name7 = new Name();
                name7.setDescription("Tickets");
                Name name8 = new Name();
                name8.setDescription("Earnings");
                Name name9 = new Name();
                name9.setDescription("Loan");
                Name name10 = new Name();
                name10.setDescription("Electronics");
                Name name11 = new Name();
                name11.setDescription("Repair");
                Name name12 = new Name();
                name12.setDescription("Gifts");
                Name name13 = new Name();
                name13.setDescription("Loan returning");
                Name name14 = new Name();
                name14.setDescription("Sport");
                Name name15 = new Name();
                name15.setDescription("Contract payment");
                Name name16 = new Name();
                name16.setDescription("Other");

                NameExpenseLab nameExpenseLab = NameExpenseLab.get(getActivity());
                nameExpenseLab.addName(name);
                nameExpenseLab.addName(name1);
                nameExpenseLab.addName(name2);
                nameExpenseLab.addName(name3);
                nameExpenseLab.addName(name4);
                nameExpenseLab.addName(name5);
                nameExpenseLab.addName(name6);
                nameExpenseLab.addName(name7);
                nameExpenseLab.addName(name8);
                nameExpenseLab.addName(name9);
                nameExpenseLab.addName(name10);
                nameExpenseLab.addName(name11);
                nameExpenseLab.addName(name12);
                nameExpenseLab.addName(name13);
                nameExpenseLab.addName(name14);
                nameExpenseLab.addName(name15);
                nameExpenseLab.addName(name16);

                Name name17 = new Name();
                name17.setDescription("Salary");
                Name name18 = new Name();
                name18.setDescription("Business");
                Name name19 = new Name();
                name19.setDescription("Card transaction");
                Name name20 = new Name();
                name20.setDescription("Cash");
                Name name21 = new Name();
                name21.setDescription("Other");

                NameIncomesLab nameIncomesLab = NameIncomesLab.get(getActivity());
                nameIncomesLab.addName(name17);
                nameIncomesLab.addName(name18);
                nameIncomesLab.addName(name19);
                nameIncomesLab.addName(name20);
                nameIncomesLab.addName(name21);

                Category category = new Category();
                category.setName("Main activity");
                Category category1 = new Category();
                category1.setName("Support activity");
                Category category2 = new Category();
                category2.setName("Unexpected income");

                IncomeCategoryLab incomeCategoryLab = IncomeCategoryLab.get(getActivity());
                incomeCategoryLab.addCategory(category);
                incomeCategoryLab.addCategory(category1);
                incomeCategoryLab.addCategory(category2);

                Category category3 = new Category();
                category3.setName("Unexpected expense");
                Category category4 = new Category();
                category4.setName("Planned expense");
                Category category5 = new Category();
                category5.setName("Food and drinks");
                Category category6 = new Category();
                category6.setName("Education");
                Category category7 = new Category();
                category7.setName("Transport");
                Category category8 = new Category();
                category8.setName("Entertainment");
                Category category9 = new Category();
                category9.setName("Trips");
                Category category10 = new Category();
                category10.setName("Health");

                ExpenseCategoryLab expenseCategoryLab = ExpenseCategoryLab.get(getActivity());
                expenseCategoryLab.addCategory(category3);
                expenseCategoryLab.addCategory(category4);
                expenseCategoryLab.addCategory(category5);
                expenseCategoryLab.addCategory(category6);
                expenseCategoryLab.addCategory(category7);
                expenseCategoryLab.addCategory(category8);
                expenseCategoryLab.addCategory(category9);
                expenseCategoryLab.addCategory(category10);

            }
        });
        eraseData = view.findViewById(R.id.eraseData);
        eraseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
