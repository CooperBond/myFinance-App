package com.developer.kirill.myfinance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NoteFragmentIncome extends DialogFragment {
    private EditText noteView;
    private static String noteText;
    private static Income mIncome;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_switch_layout, null);
        noteView = view.findViewById(R.id.note_text_inc);
        noteView.setText(noteText);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mIncome.setNote(noteView.getText().toString());
                    }
                })
                .create();
    }

    public static NoteFragmentIncome newInstance(Income income) {
        NoteFragmentIncome fragment = new NoteFragmentIncome();
        mIncome = income;
        noteText = mIncome.getNote();
        return fragment;
    }
}
