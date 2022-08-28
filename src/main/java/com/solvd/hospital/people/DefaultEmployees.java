package com.solvd.hospital.people;

import java.time.LocalTime;

public enum DefaultEmployees {

    JOHN_SMITH(33, "male", "John Smith", "physician", "33581569",
            40.0, "MON, TUE, WED, THU, FRI", LocalTime.of(8, 0),
            LocalTime.of(17, 0, 0), "c", "1-a"),
    ANDIE_BLANCHARD(66, "male", "Andie Blanchard", "physician", "10225739",
            75.0, "MON, TUE, THU", LocalTime.of(8, 0),
            LocalTime.of(17, 0, 0), "s", "2-a"),
    MARY_JO_ROBBINS(52, "female", "Mary Jo Robbins", "physician", "19226739",
            75.0, "MON, TUE, THU, FRI", LocalTime.of(8, 0),
            LocalTime.of(17, 0, 0), "c", "1-a"),
    MALCOM_LYON(45, "male", "Malcom Lyon", "physician", "24647160",
            50.0, "MON, TUE, THU, FRI", LocalTime.of(8, 0),
            LocalTime.of(17, 0, 0), "r", "2-b"),
    ASTON_JERVIS(46, "male", "Aston Jervis", "nurse", "24607187",
            25.0, "MON, TUE, WED, THU, FRI", LocalTime.of(8, 0),
            LocalTime.of(17, 0, 0), "r", "2-b"),
    DOREEN_MERRITT(38, "female", "Aston Jervis", "nurse", "30581569",
            25.0, "MON, TUE, WED, THU, FRI", LocalTime.of(8, 0),
            LocalTime.of(17, 0, 0), "r", "2-b"),
    ;
    private final int age;
    private final String gender;
    private final String fullName;
    private final String profession;
    private final String ID;
    private final double payRate;
    private final String workingDays;
    private final LocalTime entryTime;
    private final LocalTime leavingTime;
    private final String roomInitial;
    private final String sector;

    DefaultEmployees(int age, String gender, String fullName, String profession, String ID,
                     double payRate, String workingDays, LocalTime entryTime, LocalTime leavingTime,
                     String roomInitial, String sector) {
        this.age = age;
        this.gender = gender;
        this.fullName = fullName;
        this.profession = profession;
        this.ID = ID;
        this.payRate = payRate;
        this.workingDays = workingDays;
        this.entryTime = entryTime;
        this.leavingTime = leavingTime;
        this.roomInitial = roomInitial;
        this.sector = sector;
    }

    //Getters
    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProfession() {
        return profession;
    }

    public String getID() {
        return ID;
    }

    public double getPayRate() {
        return payRate;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public LocalTime getLeavingTime() {
        return leavingTime;
    }

    public String getRoomInitial() {
        return roomInitial;
    }

    public String getSector() {
        return sector;
    }
}
