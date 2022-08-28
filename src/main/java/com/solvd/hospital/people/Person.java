package com.solvd.hospital.people;

import com.solvd.hospital.exceptions.InvalidAgeException;

public abstract class Person {
    protected int age;
    protected double weight;
    protected double height;
    protected String gender;
    protected String fullName;
    protected String ID;
    protected boolean isAlive;

    public Person(int age, double weight, double height, String gender, String fullName,
                  String ID) {
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.fullName = fullName;
        this.ID = ID;
        this.isAlive = true;
    }

    public Person(int age, String gender, String fullName, String ID) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Age must be greater than zero");
        }
        this.age = age;
        this.gender = gender;
        this.fullName = fullName;
        this.ID = ID;
        this.isAlive = true;
    }


    //Setters and getters
    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public void setIsAlive(boolean alive) {
        this.isAlive = alive;
    }

    public String getID() {
        return ID;
    }
}

