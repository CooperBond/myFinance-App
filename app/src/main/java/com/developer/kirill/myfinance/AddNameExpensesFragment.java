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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddNameExpensesFragment extends DialogFragment {
    private EditText nameInput;

    public static final String EXTRA_DESCR = "extra_desc";


    public static AddNameExpensesFragment newInstance() {
        AddNameExpensesFragment fragment = new AddNameExpensesFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.name_add_layout, null);
        nameInput = view.findViewById(R.id.inpuName);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Name name = new Name();
                        String newData = nameInput.getText().toString();
                        name.setDescription(newData);
                        NameExpenseLab.get(getActivity()).addName(name);
                        sendResult(Activity.RESULT_OK, newData);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, String descr) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DESCR, descr);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}

