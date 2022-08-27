package com.solvd.hospital.people;

import com.solvd.hospital.exceptions.InvalidAgeException;
import com.solvd.hospital.exceptions.InvalidBloodTypeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Scanner;

public final class FemalePatient extends Patient {
    private static final Logger log = LogManager.getLogger(FemalePatient.class);

    public FemalePatient(int age, String fullName, String ID, String complexity, String bloodType)
            throws InvalidAgeException, InvalidBloodTypeException {
        super(age, "female", fullName, ID, complexity, bloodType);
    }

    @Override
    public int hashCode() {
        int socialSecurityNumber = -1;
        try {
            socialSecurityNumber = Integer.parseInt(this.ID);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return socialSecurityNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FemalePatient femalePatient = (FemalePatient) obj;
        return (femalePatient.ID.equals(this.ID));
    }

    @Override
    public String toString() {
        return " Female patient: " + fullName + ", ID: " + ID;
    }

//    public Patient giveBirth() throws InvalidAgeException, InvalidBloodTypeException {
//        Scanner input = new Scanner(System.in);
//        int age = 0;
//        log.info("Please enter the baby weight:");
//        double weight = input.nextDouble();
//        log.info("Please enter the baby blood type:");
//        String bloodType = input.next();
//        String gender;
//        do {
//            log.info("Please enter the baby gender:");
//            gender = input.next().toLowerCase(Locale.ROOT);
//        } while (!gender.equals("male") && !gender.equals("female"));
//        log.info("Please enter the baby full name:");
//        String fullName = input.next();
//        log.info("Please enter the baby complexity status:");
//        String complexity = input.next();
//        log.info("Please enter the baby ID number:");
//        String ID = input.next();
//        input.close();
//        if (gender.equals("male")) {
//            return new MalePatient(age, fullName, ID, complexity, bloodType);
//        } else {
//            return new FemalePatient(age, fullName, ID, complexity, bloodType);
//        }
//    }
}
