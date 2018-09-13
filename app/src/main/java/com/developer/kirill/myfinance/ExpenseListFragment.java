package com.developer.kirill.myfinance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class ExpenseListFragment extends Fragment {
    private RecyclerView mExpenseRecyclerView;
    private ExpenseAdapter mAdapter;
    private Paint p = new Paint();
    private static String sortParameter = "Date";
    private static Date period;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static void setSortParameter(String parameter) {
        sortParameter = parameter;
    }
    public static void setPeriod(Date date){
        period = date;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_expense:
                Expense expense = new Expense();
                expense.setDate(period);
                ExpenseLab.get(getActivity()).addExpense(expense);
                Intent intent = ExpensePagerActivity.newIntent(getActivity(), expense.getId());
                startActivity(intent);
                return true;
            case R.id.addOd:
                sortParameter = "Standard";
                updateUI();
                return true;
            case R.id.dateOd:
                sortParameter = "Date";
                updateUI();
                return true;
            case R.id.smtoB:
                sortParameter = "Ascending";
                updateUI();
                return true;
            case R.id.btoSm:
                sortParameter = "Descending";
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exp_list, container, false);
        mExpenseRecyclerView = view.findViewById(R.id.exp_recycler_view);
        mExpenseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    mAdapter.removeElement(viewHolder.getAdapterPosition());
                    updateUI();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX < 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mExpenseRecyclerView);
        return view;
    }

    private void updateUI() {
        ExpenseLab expenseLab = ExpenseLab.get(getActivity());
        List<Expense> expenses = expenseLab.getExpenses(sortParameter);
        //TODO empty list background
        if (mAdapter == null) {
            mAdapter = new ExpenseAdapter(expenses);
            mExpenseRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setmExpenses(expenses);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_expense_list, menu);
    }

    private class ExpenseHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mTitleExpenseNameView;
        private TextView mDateExpenseView;
        private TextView mExpenseValue;
        private Expense mExpense;
        private TextView mCategory;
        private TextView mNote;

        public ExpenseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_expense, parent, false));
            mTitleExpenseNameView = itemView.findViewById(R.id.title_exp);
            mDateExpenseView = itemView.findViewById(R.id.date_exp);
            mExpenseValue = itemView.findViewById(R.id.price_exp);
            mCategory = itemView.findViewById(R.id.categoryTitle);
            mNote = itemView.findViewById(R.id.noteExp);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = ExpensePagerActivity.newIntent(getActivity(), mExpense.getId());
            startActivity(intent);
        }

        public void bind(Expense expense) {
            mExpense = expense;
            mTitleExpenseNameView.setText(mExpense.getExpenseName());
            String dateFormat = "EEE dd, MMM";
            String dateString = DateFormat.format(dateFormat, mExpense.getDate()).toString();
            mDateExpenseView.setText(dateString);
            mExpenseValue.setText(String.valueOf(mExpense.getExpense()));
            mCategory.setText(mExpense.getCategory());
            String noteText;
            try {
                if (mExpense.getNote().length() > 5) {
                    noteText = mExpense.getNote().substring(0, 5) + " ...";
                } else {
                    noteText = mExpense.getNote();
                }
                mNote.setText(noteText);
            } catch (Exception e) {
                e.printStackTrace();
                noteText = "";
                mNote.setText(noteText);
            }
        }
    }

    private class ExpenseAdapter extends RecyclerView.Adapter<ExpenseListFragment.ExpenseHolder> {
        private List<Expense> mExpenses;

        public ExpenseAdapter(List<Expense> expenses) {
            mExpenses = expenses;
        }

        @NonNull
        @Override
        public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ExpenseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ExpenseHolder expenseHolder, int position) {
            Expense expense = mExpenses.get(position);
            expenseHolder.bind(expense);
        }

        @Override
        public int getItemCount() {
            return mExpenses.size();
        }

        public void setmExpenses(List<Expense> expenses) {
            mExpenses = expenses;
        }

        public void removeElement(int position) {
            ExpenseLab expenseLab = ExpenseLab.get(getActivity());
            expenseLab.deleteExpense(mExpenses.get(position).getId());
            mExpenses.remove(position);
            notifyItemRemoved(position);
        }
    }
}
