package com.example.electricitybillcalculator;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailViewActivity extends AppCompatActivity {
    private TextView monthTextView, unitsTextView, totalChargesTextView,
            rebateTextView, finalCostTextView;
    private BillDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        monthTextView = findViewById(R.id.monthTextView);
        unitsTextView = findViewById(R.id.unitsTextView);
        totalChargesTextView = findViewById(R.id.totalChargesTextView);
        rebateTextView = findViewById(R.id.rebateTextView);
        finalCostTextView = findViewById(R.id.finalCostTextView);

        dbHelper = new BillDatabaseHelper(this);

        int billId = getIntent().getIntExtra("BILL_ID", -1);
        if (billId != -1) {
            Bill bill = dbHelper.getBill(billId);
            displayBillDetails(bill);
        }
    }
    private void displayBillDetails(Bill bill) {
        monthTextView.setText("Month: " + bill.getMonth());
        unitsTextView.setText("Units: " + bill.getUnits() + " kWh");
        totalChargesTextView.setText("Total Charges: RM" + String.format("%.2f", bill.getTotalCharges()));

        double rebateAmount = bill.getTotalCharges() * (bill.getRebate() / 100);
        rebateTextView.setText("Rebate: " + String.format("%.0f", bill.getRebate()) + "% = RM" + String.format("%.2f", rebateAmount));
        finalCostTextView.setText("Final Cost: RM" + String.format("%.2f", bill.getFinalCost()));
    }
}