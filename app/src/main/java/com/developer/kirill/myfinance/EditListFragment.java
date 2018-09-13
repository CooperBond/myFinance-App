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

public class EditListFragment extends DialogFragment {
    private EditText nameInput;
    private static String name;
    public static final String EXTRA_STRING = "extra_string";


    public static EditListFragment newInstance(String text) {
        EditListFragment fragment = new EditListFragment();
        name = text;
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
                .inflate(R.layout.editlist_layout, null);
        nameInput = view.findViewById(R.id.nameEdited);
        nameInput.setText(name);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newName = nameInput.getText().toString();
                        sendResult(Activity.RESULT_OK, newName);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, String descr) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STRING, descr);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
