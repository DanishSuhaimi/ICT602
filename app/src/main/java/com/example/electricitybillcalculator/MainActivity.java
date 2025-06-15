package com.example.electricitybillcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Spinner monthSpinner;
    private EditText unitsEditText, rebateEditText;
    private TextView totalChargesTextView, finalCostTextView;
    private Button saveButton;
    private BillDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        monthSpinner = findViewById(R.id.monthSpinner);
        unitsEditText = findViewById(R.id.unitsEditText);
        rebateEditText = findViewById(R.id.rebateEditText);
        totalChargesTextView = findViewById(R.id.totalChargesTextView);
        finalCostTextView = findViewById(R.id.finalCostTextView);
        saveButton = findViewById(R.id.saveButton);
        dbHelper = new BillDatabaseHelper(this);

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> calculateBill());

        Button viewBillsButton = findViewById(R.id.viewBillsButton);
        viewBillsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
            startActivity(intent);
        });
        Button instructionButton = findViewById(R.id.instructionButton);
        instructionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Instruction.class);
            startActivity(intent);
        });
        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
        saveButton.setOnClickListener(v -> saveBill());
    }
    private void calculateBill() {
        String unitsString = unitsEditText.getText().toString().trim();
        String rebateText = rebateEditText.getText().toString().trim();

        if (unitsString.isEmpty()) {
            Toast.makeText(this, "Please enter electricity units", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rebateText.isEmpty()) {
            Toast.makeText(this, "Please enter rebate percentage", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double units = Double.parseDouble(unitsString);
            double rebatePercentage = Double.parseDouble(rebateText);

            if (units <= 0) {
                Toast.makeText(this, "Units must be greater than 0", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rebatePercentage < 0 || rebatePercentage > 5) {
                Toast.makeText(this, "Rebate must be between 0% - 5%", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalCharges = calculateTotalCharges(units);
            double finalCost = totalCharges - (totalCharges * rebatePercentage / 100);

            totalChargesTextView.setText(String.format("Total Charges: RM%.2f", totalCharges));
            finalCostTextView.setText(String.format("Final Cost: RM%.2f", finalCost));

            totalChargesTextView.setVisibility(View.VISIBLE);
            finalCostTextView.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }
    private double calculateTotalCharges(double units) {
        double total = 0;

        if (units <= 200) {
            total = units * 0.218;
        } else if (units <= 300) {
            total = 200 * 0.218 + (units - 200) * 0.334;
        } else if (units <= 600) {
            total = 200 * 0.218 + 100 * 0.334 + (units - 300) * 0.516;
        } else {
            total = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (units - 600) * 0.546;
        }
        return total;
    }

    private void saveBill() {
        String month = monthSpinner.getSelectedItem().toString();
        String unitsString = unitsEditText.getText().toString().trim();
        String rebateText = rebateEditText.getText().toString().trim();

        if (unitsString.isEmpty() || rebateText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double units = Double.parseDouble(unitsString);
            double rebatePercentage = Double.parseDouble(rebateText);

            if (rebatePercentage < 0 || rebatePercentage > 5) {
                Toast.makeText(this, "Rebate must be between 0% and 5%", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalCharges = calculateTotalCharges(units);
            double finalCost = totalCharges - (totalCharges * rebatePercentage / 100);

            Bill bill = new Bill();
            bill.setMonth(month);
            bill.setUnits(units);
            bill.setTotalCharges(totalCharges);
            bill.setRebate(rebatePercentage);
            bill.setFinalCost(finalCost);

            dbHelper.addBill(bill);
            Toast.makeText(this, "Bill saved successfully", Toast.LENGTH_SHORT).show();

            // Reset form
            unitsEditText.setText("");
            rebateEditText.setText("");
            totalChargesTextView.setVisibility(View.GONE);
            finalCostTextView.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error saving bill", Toast.LENGTH_SHORT).show();
        }
    }
}