package com.example.electricitybillcalculator;

public class Bill {
    private int id;
    private String month;
    private double units;
    private double totalCharges;
    private double rebate;
    private double finalCost;

    public Bill() {}

    public Bill(int id, String month, double units, double totalCharges, double rebate, double finalCost) {
        this.id = id;
        this.month = month;
        this.units = units;
        this.totalCharges = totalCharges;
        this.rebate = rebate;
        this.finalCost = finalCost;
    }

    // Getters and setters for all fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public double getUnits() { return units; }
    public void setUnits(double units) { this.units = units; }
    public double getTotalCharges() { return totalCharges; }
    public void setTotalCharges(double totalCharges) { this.totalCharges = totalCharges; }
    public double getRebate() { return rebate; }
    public void setRebate(double rebate) { this.rebate = rebate; }
    public double getFinalCost() { return finalCost; }
    public void setFinalCost(double finalCost) { this.finalCost = finalCost; }

    // Override toString() to display in ListView
    @Override
    public String toString() {
        return month + " - RM " + String.format("%.2f", finalCost);
    }
}