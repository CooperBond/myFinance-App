package com.developer.kirill.myfinance;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.List;

public class StatiscicsLab extends Activity {
    private static StatiscicsLab statiscicsLab;
    private List<StatisticElement> list;
    private Context mContext;
    private StatisticElement s4;
    private StatisticElement s6;

    public static StatiscicsLab get(Context context) {
        if (statiscicsLab == null) {
            statiscicsLab = new StatiscicsLab(context);
        }
        return statiscicsLab;
    }

    private StatiscicsLab(Context context) {

    }

    public List<StatisticElement> getStatiscics() {
        List<StatisticElement> listS = new ArrayList<>();
        StatisticElement s1 = new StatisticElement();
        s1.setDescription("Period");
        s1.setElement("Current");
        StatisticElement s2 = new StatisticElement();
        s2.setDescription("Total Income");
        s2.setElement(String.valueOf(Balance.balanceObject.totalI));
        StatisticElement s3 = new StatisticElement();
        s3.setDescription("Total Expenses");
        s3.setElement(String.valueOf(Balance.balanceObject.totalE));
        s4 = new StatisticElement();
        s4.setDescription("Maximum expense");
        setMAXexpense();
        StatisticElement s5 = new StatisticElement();
        s5.setDescription("Maximum by category");
        s5.setElement("Max by category");
        s6 = new StatisticElement();
        s6.setDescription("Minimum expense");
        setMINexpense();
        StatisticElement s7 = new StatisticElement();
        s7.setDescription("Minimum by category");
        s7.setElement(getMINexpenseByCategory());
        StatisticElement s8 = new StatisticElement();
        s8.setDescription("Average expenditure per day");
        s8.setElement(getAVGexpensePerDay());
        StatisticElement s9 = new StatisticElement();
        s9.setDescription("Average expense arithmetically");
        s9.setElement(getAVGexpenseARTH());
        listS.add(s1);
        listS.add(s2);
        listS.add(s3);
        listS.add(s4);
        listS.add(s5);
        listS.add(s6);
        listS.add(s7);
        listS.add(s8);
        listS.add(s9);
        return listS;
    }

    private void setMAXexpense() {
        ExpenseLab expenseLab = ExpenseLab.get(this);
        List<Expense> expenses = expenseLab.getExpenses("Standard");
        int index = 0;
        int max = expenses.get(index).getExpense();
        for (int i = 1; i < expenses.size(); i++) {
            if (expenses.get(i).getExpense() > max) {
                max = expenses.get(i).getExpense();
                index = i;
            }
        }
        s4.setElementId(expenses.get(index).getId());
        s4.setElement(expenses.get(index).getExpenseName());
    }

    private void setMINexpense() {
        ExpenseLab expenseLab = ExpenseLab.get(this);
        List<Expense> expenses = expenseLab.getExpenses("Standard");
        int index = 0;
        int min = expenses.get(index).getExpense();
        for (int i = 1; i < expenses.size(); i++) {
            if (expenses.get(i).getExpense() < min) {
                min = expenses.get(i).getExpense();
                index = i;
            }
        }
        s6.setElementId(expenses.get(index).getId());
        s6.setElement(expenses.get(index).getExpenseName());
    }

    private String getMINexpenseByCategory() {
        String result = "";
        ExpenseLab expenseLab = ExpenseLab.get(this);
        List<Expense> expenses = expenseLab.getExpenses("Standard");
        //TODO minimum search by category
        return result;
    }

    private String getAVGexpensePerDay() {
        String result = "";
        ExpenseLab expenseLab = ExpenseLab.get(this);
        List<Expense> expenses = expenseLab.getExpenses("Standard");
        return result;
    }

    private String getAVGexpenseARTH() {
        String result = "";
        ExpenseLab expenseLab = ExpenseLab.get(this);
        List<Expense> expenses = expenseLab.getExpenses("Standard");
        int daysCount = 1;
        int totalExpense = 0;
        for (int i = 0; i < expenses.size(); i++) {
            totalExpense += expenses.get(i).getExpense();
        }
        for (int i = 0; i < expenses.size() - 1; i++) {
            String dateFormat = "EEE dd, MMM";
            String dateString1 = DateFormat.format(dateFormat, expenses.get(i).getDate()).toString();
            String dateString2 = DateFormat.format(dateFormat, expenses.get(i + 1).getDate()).toString();
            if (!dateString1.equals(dateString2)) {
                daysCount++;
            }
        }
        result = String.valueOf(totalExpense / daysCount);
        return result;
    }
}
