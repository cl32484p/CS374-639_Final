package com.example.ourproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<transactionClass> transactions;

    public TransactionAdapter(List<transactionClass> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        transactionClass transaction = transactions.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView balanceView;
        private TextView dpdView;
        private TextView differenceView;
        private TextView differenceDPDView;
        private TextView dateView;
        private TextView transactionNumberView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleView);
            balanceView = itemView.findViewById(R.id.balanceView);
            dpdView = itemView.findViewById(R.id.dpdView);
            differenceView = itemView.findViewById(R.id.differenceView);
            differenceDPDView = itemView.findViewById(R.id.differenceDPDView);
            dateView = itemView.findViewById(R.id.dateView);
            transactionNumberView = itemView.findViewById(R.id.transactionNumberView);
        }

        public void bind(transactionClass transaction) {
            titleView.setText(transaction.getTitle());
            balanceView.setText(String.format(Locale.getDefault(), "%.2f", transaction.getBalance()));
            dpdView.setText(String.format(Locale.getDefault(), "%.2f", transaction.getDollersPer()));
            differenceView.setText(String.format(Locale.getDefault(), "%.2f", transaction.getDifference()));
            differenceDPDView.setText(String.format(Locale.getDefault(), "%.2f", transaction.getDollarsPerDifference()));
            dateView.setText(transaction.getDate());
            transactionNumberView.setText(String.valueOf(transaction.getHisTitle()));
        }




    }







}