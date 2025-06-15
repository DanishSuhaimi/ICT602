package com.example.electricitybillcalculator;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private android.widget.ListView billsListView;
    private BillDatabaseHelper dbHelper;
    private List<Bill> bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        billsListView = findViewById(R.id.billsListView);
        dbHelper = new BillDatabaseHelper(this);

        displayBills();
    }
    private void displayBills() {
        bills = dbHelper.getAllBills();

        if (bills.isEmpty()) {
            Toast.makeText(this, "No bills saved yet", Toast.LENGTH_SHORT).show();
        }
        BillListAdapter adapter = new BillListAdapter(this, bills, dbHelper);
        billsListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayBills();
    }
}