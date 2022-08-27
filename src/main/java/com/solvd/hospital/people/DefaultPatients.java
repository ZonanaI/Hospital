package com.solvd.hospital.people;

public enum DefaultPatients {

    LEONARD_CHAPMAN(33, "male", "Leonard Chapman", "33581170",
            "low", "AB_POSITIVE", "c", "1-a"),
    JOE_ROGERS(70, "male", "Joe Rogers", "10181213",
            "high", "O_NEGATIVE", "s", "2-a"),
    MIKE_ABRAHAM(82, "male", "Mike Abraham", "09184353",
            "high", "A_NEGATIVE", "r", "2-b"),
    PHIL_TRENGOVE(55, "male", "Phil Trengove", "14981432",
            "medium", "B_POSITIVE", "r", "2-b"),
    DAFNE_GODDARD(66, "female", "Dafne Goddard", "12312654",
            "high", "O_NEGATIVE", "s", "2-a"),
    BRIANA_GARCIA(25, "female", "Briana Garcia", "39341594",
            "low", "A_POSITIVE", "c", "1-a");

    final private int age;
    final private String gender;
    final private String fullName;
    final private String ID;
    final private String complexity;
    final private String bloodType;
    final private String roomInitial;
    final private String sector;

    DefaultPatients(int age, String gender, String fullName, String ID, String complexity, String bloodType,
                    String roomInitial, String sector) {
        this.age = age;
        this.gender = gender;
        this.fullName = fullName;
        this.ID = ID;
        this.complexity = complexity;
        this.bloodType = bloodType;
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

    public String getID() {
        return ID;
    }

    public String getComplexity() {
        return complexity;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getRoomInitial() {
        return roomInitial;
    }

    public String getSector() {
        return sector;
    }

}
