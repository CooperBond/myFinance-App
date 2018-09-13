package com.developer.kirill.myfinance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PeriodCheckFragment extends DialogFragment {
    private static String mPeriod;
    private PeriodAdapter mAdapter;
    private RecyclerView mRecView;
    private static String dialogTitle = "";
    public static final String EXTRA_PERIOD = "extra_period";

    public static PeriodCheckFragment newInstance(String title) {
        mPeriod = title;
        return new PeriodCheckFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.recycle, null);
        mRecView = view.findViewById(R.id.recycler_view);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(mPeriod)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK, mPeriod);
                    }
                })
                .create();

    }

    private void updateUI() {
        List<String> dates = new ArrayList<>();
        String dateFormat = "MMM";
        Calendar calendarNEXT = Calendar.getInstance();
        calendarNEXT.add(Calendar.DATE, +30);
        Calendar calendarPREV = Calendar.getInstance();
        calendarPREV.add(Calendar.DATE, -30);
        String periodNEXT = DateFormat.format(dateFormat, calendarNEXT).toString();
        String periodCURR = DateFormat.format(dateFormat, new Date()).toString();
        String periodPREV = DateFormat.format(dateFormat, calendarPREV).toString();
        dates.add(periodNEXT);
        dates.add(periodCURR);
        dates.add(periodPREV);
        if (mAdapter == null) {
            mAdapter = new PeriodAdapter(dates);
            mRecView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class PeriodHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mDesrc;


        PeriodHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.text_fragment, parent, false));
            mDesrc = itemView.findViewById(R.id.description_target);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mPeriod = mDesrc.getText().toString();
            Dialog dialog = getDialog();
            dialog.setTitle(mPeriod);
        }

        public void bind(String date) {
            mPeriod = date;
            mDesrc.setText(date);
        }
    }

    private class PeriodAdapter extends RecyclerView.Adapter<PeriodHolder> {
        private List<String> mDates;

        public PeriodAdapter(List<String> dates) {
            mDates = dates;
        }


        @NonNull
        @Override
        public PeriodHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PeriodHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull PeriodHolder periodHolder, int i) {
            String date = mDates.get(i);
            periodHolder.bind(date);
        }

        @Override
        public int getItemCount() {
            return mDates.size();
        }
    }

    private void sendResult(int resultCode, String period) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PERIOD, period);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
