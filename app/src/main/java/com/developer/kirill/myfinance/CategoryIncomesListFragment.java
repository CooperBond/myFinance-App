package com.developer.kirill.myfinance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class CategoryIncomesListFragment extends DialogFragment {


    private RecyclerView mRecView;
    private CategoryAdapter mAdapter;
    public static final String EXTRA_CATEGI = "extra_categi";
    private static final int REQUEST_CATEGI = 3;
    private static final int REQUEST_NAME = 2;
    private static final String CATEGI_VAL = "Categi";
    private static String mDes;
    private static String dialogTitle = "Category: ";
    private Paint p = new Paint();
    private UUID nameUUID;
    private int namePosition;
    private String currentName;
    private Category mCategory;

    public static CategoryIncomesListFragment newInstance(String title) {
        if (title == null) {
            mDes = "";
        } else {
            mDes = title;
        }
        CategoryIncomesListFragment fragment = new CategoryIncomesListFragment();
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_AppCompat_Dark);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.recycle, null);
        mRecView = view.findViewById(R.id.recycler_view);
        mRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    mAdapter.removeElement(viewHolder.getAdapterPosition());
                    updateUI();
                } else {
                    namePosition = viewHolder.getAdapterPosition();
                    FragmentManager fragmentManager = getFragmentManager();
                    currentName = mAdapter.mCategories.get(namePosition).getCategory();
                    mCategory = mAdapter.mCategories.get(namePosition);
                    EditListFragment dialog = EditListFragment.newInstance(currentName);
                    dialog.setTargetFragment(CategoryIncomesListFragment.this, REQUEST_NAME);
                    dialog.show(fragmentManager, CATEGI_VAL);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
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
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecView);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(dialogTitle + mDes)
                .setNeutralButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FragmentManager fragmentManager = getFragmentManager();
                        AddCategoryIncomeFragment dialog = AddCategoryIncomeFragment.newInstance();
                        dialog.setTargetFragment(CategoryIncomesListFragment.this, REQUEST_CATEGI);
                        dialog.show(fragmentManager, CATEGI_VAL);
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK, mDes);
                    }
                })
                .create();
    }


    private void updateUI() {
        IncomeCategoryLab incomeCategoryLab = IncomeCategoryLab.get(getActivity());
        List<Category> categories = incomeCategoryLab.getCategories();
        if (mAdapter == null) {
            mAdapter = new CategoryAdapter(categories);
            mRecView.setAdapter(mAdapter);
        } else {
            mAdapter.setCategories(categories);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CATEGI) {
            mDes = (String) data.getSerializableExtra(AddCategoryIncomeFragment.EXTRA_CATEGI);
            sendResult(Activity.RESULT_OK, mDes);
        }else if (requestCode == REQUEST_NAME){
            String newName = (String) data.getSerializableExtra(EditListFragment.EXTRA_STRING);
            IncomeCategoryLab expenseLab = IncomeCategoryLab.get(getActivity());
            Category category = expenseLab.getCategory(mCategory.getCategoryId());
            category.setName(newName);
            mAdapter.editElement(category);
            updateUI();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class CategoryHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mDesrc;

        public CategoryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.text_fragment, parent, false));
            mDesrc = itemView.findViewById(R.id.description_target);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Animation blinkanim = AnimationUtils.loadAnimation(getContext(),
                    R.anim.animc);
            view.startAnimation(blinkanim);
            mDes = mDesrc.getText().toString();
            Dialog dialog = getDialog();
            dialog.setTitle(dialogTitle + mDes);

        }

        public void bind(Category category) {
            mCategory = category;
            mDesrc.setText(mCategory.getCategory());
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
        private List<Category> mCategories;

        public CategoryAdapter(List<Category> categories) {
            mCategories = categories;
        }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CategoryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int position) {
            Category category = mCategories.get(position);
            categoryHolder.bind(category);
        }


        @Override
        public int getItemCount() {
            return mCategories.size();
        }


        public void setCategories(List<Category> categories) {
            mCategories = categories;
        }

        public void removeElement(int position) {
            IncomeCategoryLab incomeCategoryLab = IncomeCategoryLab.get(getActivity());
            incomeCategoryLab.delete(mCategories.get(position).getCategoryId());
            mCategories.remove(position);
            notifyItemRemoved(position);
        }

        public void editElement(Category category) {
            IncomeCategoryLab incomeCategoryLab = IncomeCategoryLab.get(getActivity());
            incomeCategoryLab.updateCategory(category);
            mCategories.set(namePosition, category);
            notifyDataSetChanged();
        }

    }

    private void sendResult(int resultCode, String descr) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGI, descr);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}


