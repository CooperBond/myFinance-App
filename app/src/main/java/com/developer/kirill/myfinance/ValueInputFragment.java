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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ValueInputFragment extends DialogFragment {
    private static final String ARG_EXPENSE = "expensex";
    public static final String EXTRA_EXPENSE = "extra_expense";
    private ImageButton buttonOne;
    private ImageButton buttonTwo;
    private ImageButton buttonThree;
    private ImageButton buttonFour;
    private ImageButton buttonFive;
    private ImageButton buttonSix;
    private ImageButton buttonSeven;
    private ImageButton buttonEight;
    private ImageButton buttonNine;
    private ImageButton buttonZero;
    private Button confirm;
    private TextView resultField;
    private ImageButton deleteButton;
    private int count = 0;

    public static ValueInputFragment newInstance(int expense) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXPENSE, expense);
        ValueInputFragment fragment = new ValueInputFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        int expense = (int) getArguments().getSerializable(ARG_EXPENSE);
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.value_input_layout, null);
        resultField = view.findViewById(R.id.result);
        resultField.setText(String.valueOf(expense));
        deleteButton = view.findViewById(R.id.deleteButton1);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = resultField.getText().toString();
                if ( result.equals("")) {
                    resultField.setText(0);
                }else if(result.length()==1) {
                    resultField.setText("");
                }else if (result.length()==2){
                    String cut = String.valueOf(result.charAt(0));
                    resultField.setText(cut);
                } else {
                    String cut = result.substring(0, result.length() - 1);
                    resultField.setText(cut);
                }
            }
        });

        buttonOne = view.findViewById(R.id.imageButton1);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(1));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 1);
                }
            }
        });

        buttonTwo = view.findViewById(R.id.imageButton2);
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(2));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 2);
                }
            }
        });
        buttonThree = view.findViewById(R.id.imageButton3);
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(3));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 3);
                }
            }
        });
        buttonFour = view.findViewById(R.id.imageButton4);
        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(4));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 4);
                }
            }
        });
        buttonFive = view.findViewById(R.id.imageButton5);
        buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(5));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 5);
                }
            }
        });
        buttonSix = view.findViewById(R.id.imageButton6);
        buttonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(6));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 6);
                }
            }
        });
        buttonSeven = view.findViewById(R.id.imageButton7);
        buttonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(7));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 7);
                }
            }
        });
        buttonEight = view.findViewById(R.id.imageButton8);
        buttonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(8));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 8);
                }
            }
        });
        buttonNine = view.findViewById(R.id.imageButton9);
        buttonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(9));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 9);
                }
            }
        });
        buttonZero = view.findViewById(R.id.imageButton0);
        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    resultField.setText(String.valueOf(0));
                    count++;
                } else {
                    resultField.setText(resultField.getText().toString() + 0);
                }
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (resultField.getText().toString().equals("")) {
                            Toast f = Toast.makeText(getActivity(),"Enter the value!",Toast.LENGTH_SHORT);
                            f.show();
                        } else {
                            int expenseD = Integer.parseInt(resultField.getText().toString());
                            sendResult(Activity.RESULT_OK, expenseD);
                        }
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, int expense) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_EXPENSE, expense);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
