package com.developer.kirill.myfinance.database;

public class ExpenseDbSchema {
    public static final class ExpenseTable {
        public static final String NAME = "expenses";

    }
    public static final class Ecols {
        public static final String eUUID = "euuid";
        public static final String EXPENSE_NAME = "expname";
        public static final String EXPENSE_VALUE = "expValue";
        public static final String eDATE = "edate";
        public static final String eCATEGORY ="ecategory";
        public static final String eNOTE = "enote";
    }
}
