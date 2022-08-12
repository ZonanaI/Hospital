package com.solvd.hospital.people;

import java.time.LocalTime;

public abstract class Employee extends Person{

    protected int workedHours;
    protected LocalTime entryTime;
    protected LocalTime leavingTime;
    protected int [] workingDays;
    protected double payRate;


    public Employee (int age, String gender, String fullName, String ID) {
        super(age, gender, fullName, ID);
        this.workedHours = 0;
    }

    public int getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(int workedHours) {
        this.workedHours = workedHours;
    }

    public double getPayRate() {
        return payRate;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public abstract double getPayCheck();

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalTime getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(LocalTime leavingTime) {
        this.leavingTime = leavingTime;
    }

    public int[] getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int[] workingDays) {
        this.workingDays = workingDays;
    }
}
