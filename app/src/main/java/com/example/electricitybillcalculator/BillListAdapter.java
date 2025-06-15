package com.example.electricitybillcalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;
public class BillListAdapter extends BaseAdapter {
    private Context context;
    private List<Bill> bills;
    private BillDatabaseHelper dbHelper;
    public BillListAdapter(Context context, List<Bill> bills, BillDatabaseHelper dbHelper) {
        this.context = context;
        this.bills = bills;
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return bills.size();
    }

    @Override
    public Object getItem(int position) {
        return bills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bills.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bill bill = bills.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        }

        TextView billInfo = convertView.findViewById(R.id.billInfoTextView);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        billInfo.setText(bill.getMonth() + " - RM" + String.format("%.2f", bill.getFinalCost()));

        // Open detail when item clicked
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailViewActivity.class);
            intent.putExtra("BILL_ID", bill.getId());
            context.startActivity(intent);
        });

        // Delete button logic
        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Bill")
                    .setMessage("Are you sure you want to delete this bill?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        dbHelper.deleteBill(bill.getId());
                        bills.remove(position);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
        return convertView;
    }
}