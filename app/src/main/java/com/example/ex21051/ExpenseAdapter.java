package com.example.ex21051;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * This adapter displays a list of expenses in a RecyclerView.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Expense expense);
    }

    /**
     * Sets the listener for clicking on an expense.
     *
     * @param listener the listener to set
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Creates an adapter with a list of expenses.
     *
     * @param expenseList the list of expenses to display
     */
    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    /**
     * Creates a new ViewHolder for an expense item.
     *
     * @param parent the parent ViewGroup
     * @param viewType the type of the view
     * @return a new ExpenseViewHolder
     */
    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expanse, parent, false);
        return new ExpenseViewHolder(view);
    }

    /**
     * Displays the expense data in the ViewHolder.
     *
     * @param holder the ViewHolder that displays the expense
     * @param position the position of the expense in the list
     */
    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.tvDescription.setText(expense.getDescription());
        holder.tvExpanseAmount.setText(expense.getAmount());
        holder.tvCategory.setText(expense.getCategory());
        holder.tvDate.setText(expense.getDate());

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(expense);
            }
            return true;
        });
    }


    /**
     * Returns the number of expenses in the list.
     *
     * @return the number of expenses
     */
    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    /**
     * Holds the views used to display an expense item.
     */
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvExpanseAmount, tvCategory, tvDate;

        /**
         * Creates a ViewHolder for an expense item.
         *
         * @param itemView the view of the expense item
         */
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvExpanseAmount = itemView.findViewById(R.id.tvExpanseAmount);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
