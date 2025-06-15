package com.example.electricitybillcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class BillDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "electricity_bills.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BILLS = "bills";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_UNITS = "units";
    private static final String COLUMN_TOTAL_CHARGES = "total_charges";
    private static final String COLUMN_REBATE = "rebate";
    private static final String COLUMN_FINAL_COST = "final_cost";

    public BillDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BILLS_TABLE = "CREATE TABLE " + TABLE_BILLS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MONTH + " TEXT,"
                + COLUMN_UNITS + " REAL,"
                + COLUMN_TOTAL_CHARGES + " REAL,"
                + COLUMN_REBATE + " REAL,"
                + COLUMN_FINAL_COST + " REAL" + ")";
        db.execSQL(CREATE_BILLS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        onCreate(db);
    }
    public void addBill(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONTH, bill.getMonth());
        values.put(COLUMN_UNITS, bill.getUnits());
        values.put(COLUMN_TOTAL_CHARGES, bill.getTotalCharges());
        values.put(COLUMN_REBATE, bill.getRebate());
        values.put(COLUMN_FINAL_COST, bill.getFinalCost());
        db.insert(TABLE_BILLS, null, values);
        db.close();
    }
    public List<Bill> getAllBills() {
        List<Bill> billList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BILLS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(0));
                bill.setMonth(cursor.getString(1));
                bill.setUnits(cursor.getDouble(2));
                bill.setTotalCharges(cursor.getDouble(3));
                bill.setRebate(cursor.getDouble(4));
                bill.setFinalCost(cursor.getDouble(5));
                billList.add(bill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return billList;
    }
    public Bill getBill(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BILLS, new String[] {COLUMN_ID, COLUMN_MONTH, COLUMN_UNITS,
                        COLUMN_TOTAL_CHARGES, COLUMN_REBATE, COLUMN_FINAL_COST},
                COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Bill bill = new Bill(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getDouble(3),
                cursor.getDouble(4),
                cursor.getDouble(5));
        cursor.close();
        return bill;
    }
    public void deleteBill(int billId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("bills", "id = ?", new String[]{String.valueOf(billId)});
        db.close();
    }
}