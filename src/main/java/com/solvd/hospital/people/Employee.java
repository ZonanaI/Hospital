package com.solvd.hospital.people;

import com.solvd.hospital.exceptions.InvalidAgeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeSet;

public abstract class Employee extends Person {

    protected int workedHours;
    protected LocalTime entryTime;
    protected LocalTime leavingTime;
    protected int[] workingDays;
    protected double payRate;
    protected TreeSet<LocalDate> vacationDays = new TreeSet<>();


    public Employee(int age, String gender, String fullName, String ID) throws InvalidAgeException {
        super(age, gender, fullName, ID);
        this.workedHours = 0;
    }


    //Setters and getters
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

    public void setVacationDays(LocalDate firstVacationDay, LocalDate lastVacationDay) {
        for (LocalDate date = firstVacationDay; date.isBefore(lastVacationDay); date.plusDays(1)) {
            vacationDays.add(date);
        }
    }
}
