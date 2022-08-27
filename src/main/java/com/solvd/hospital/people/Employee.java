package com.solvd.hospital.people;

import com.solvd.hospital.exceptions.InvalidAgeException;
import com.solvd.hospital.exceptions.InvalidPayRateException;
import com.solvd.hospital.exceptions.InvalidWorkingDayException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeSet;

public abstract class Employee extends Person implements IEvacuable, ICallable, IDiagnosable {

    protected int workedHours;
    protected LocalTime entryTime;
    protected LocalTime leavingTime;
    protected ArrayList<Integer> workingDays;
    protected double payRate;
    protected TreeSet<LocalDate> vacationDays = new TreeSet<>();


    public Employee(int age, String gender, String fullName, String ID, double payRate, ArrayList<Integer> workingDays,
                    LocalTime entryTime, LocalTime leavingTime) throws InvalidAgeException, InvalidPayRateException,
            InvalidWorkingDayException {
        super(age, gender, fullName, ID);
        if (payRate <= 0) {
            throw new InvalidPayRateException("Pay rate must be greater than zero");
        }
        this.payRate = payRate;
        this.workedHours = 0;
        if (workingDays.isEmpty()) {
            throw new InvalidWorkingDayException("Employee must have at least one working day");
        }
        this.workingDays = workingDays;
        this.entryTime = entryTime;
        this.leavingTime = leavingTime;
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

    public ArrayList<Integer> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(ArrayList<Integer> workingDays) {
        this.workingDays = workingDays;
    }

    public void setVacationDays(LocalDate firstVacationDay, LocalDate lastVacationDay) {
        LocalDate date = firstVacationDay;
        while (date.isBefore(lastVacationDay)) {
            vacationDays.add(date);
            date.plusDays(1);
        }
    }
}
