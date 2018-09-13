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

public class IncomeListFragment extends Fragment {
    private RecyclerView mIncomeRecyclerView;
    private IncomeAdapter mAdapter;
    private Paint p = new Paint();
    private static String sortOrder = "Date";
    private static Date period;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public static void setSortParameter(String parameter) {
        sortOrder = parameter;
    }
    public static void setPeriod(Date date){
        period = date;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_income:
                Income income = new Income();
                income.setDate(period);
                IncomeLab.get(getActivity()).addIncome(income);
                Intent intent = IncomePagerActivity.newIntent(getActivity(), income.getID());
                startActivity(intent);
                return true;
            case R.id.addOdi:
                sortOrder = "Standard";
                updateUI();
                return true;
            case R.id.dateOdi:
                sortOrder = "Date";
                updateUI();
                return true;
            case R.id.smtoBi:
                sortOrder = "Ascending";
                updateUI();
                return true;
            case R.id.btoSmi:
                sortOrder = "Descending";
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inc_list, container, false);
        mIncomeRecyclerView = view.findViewById(R.id.inc_recycler_view);
        mIncomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mIncomeRecyclerView);
        return view;
    }

    private void updateUI() {
        IncomeLab incomeLab = IncomeLab.get(getActivity());
        List<Income> incomes = incomeLab.getIncomes(sortOrder);
        if (mAdapter == null) {
            mAdapter = new IncomeAdapter(incomes);
            mIncomeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setIncomes(incomes);
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
        inflater.inflate(R.menu.fragment_income_list, menu);

    }

    private class IncomeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mTitleIncomeNameView;
        private TextView mDateIncomeView;
        private TextView mIncomeValue;
        private Income mIncome;
        private TextView mCategory;
        private TextView mNote;

        public IncomeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_income, parent, false));
            mTitleIncomeNameView = itemView.findViewById(R.id.title_inc);
            mDateIncomeView = itemView.findViewById(R.id.date_inc);
            mIncomeValue = itemView.findViewById(R.id.price_inc);
            mCategory = itemView.findViewById(R.id.categoryTitleinc);
            mNote = itemView.findViewById(R.id.categoryTitleinc2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = IncomePagerActivity.newIntent(getActivity(), mIncome.getID());
            startActivity(intent);
        }

        public void bind(Income income) {
            mIncome = income;
            mTitleIncomeNameView.setText(mIncome.getIncomeName());
            String dateFormat = "EEE dd, MMM";
            String dateString = DateFormat.format(dateFormat, mIncome.getDate()).toString();
            mDateIncomeView.setText(dateString);
            mIncomeValue.setText(String.valueOf(mIncome.getIncome()));
            mCategory.setText(mIncome.getCategory());
            String noteText;
            try {
                if (mIncome.getNote().length() > 5) {
                    noteText = mIncome.getNote().substring(0, 5) + " ...";
                } else {
                    noteText = mIncome.getNote();
                }
                mNote.setText(noteText);
            } catch (Exception e) {
                e.printStackTrace();
                noteText = "";
                mNote.setText(noteText);
            }
        }
    }

    private class IncomeAdapter extends RecyclerView.Adapter<IncomeListFragment.IncomeHolder> {
        private List<Income> mIncomes;

        public IncomeAdapter(List<Income> incomes) {
            mIncomes = incomes;
        }

        @NonNull
        @Override
        public IncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new IncomeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull IncomeHolder incomeHolder, int position) {
            Income income = mIncomes.get(position);
            incomeHolder.bind(income);
        }

        @Override
        public int getItemCount() {
            return mIncomes.size();
        }

        public void setIncomes(List<Income> incomes) {
            mIncomes = incomes;
        }

        public void removeElement(int position) {
            IncomeLab incomeLab = IncomeLab.get(getActivity());
            incomeLab.deleteIncome(mIncomes.get(position).getID());
            mIncomes.remove(position);
            notifyItemRemoved(position);
        }
    }
}
