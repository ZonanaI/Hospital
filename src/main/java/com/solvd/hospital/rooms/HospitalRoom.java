package com.solvd.hospital.rooms;

import com.solvd.hospital.CustomLinkedList;
import com.solvd.hospital.exceptions.InvalidAgeException;
import com.solvd.hospital.exceptions.InvalidBloodTypeException;
import com.solvd.hospital.exceptions.InvalidPayRateException;
import com.solvd.hospital.exceptions.InvalidWorkingDayException;
import com.solvd.hospital.people.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class HospitalRoom implements IEvacuable {
    private static final Logger log = LogManager.getLogger(HospitalRoom.class);
    protected String location;  /* Floor-sector e.g., first floor sector a: 1-a */
    protected Set<Patient> patientsSet = new LinkedHashSet<>();
    protected Set<Employee> employeeSet = new HashSet<>();

    public HospitalRoom(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public int getPatientsCount() {
        return patientsSet.size();
    }

    public int getPatientsCount(String complexity) {
        int patientsCount = 0;
        for (Patient patient : patientsSet) {
            if (patient.getComplexity().equals(complexity)) {
                patientsCount++;
            }
        }
        return patientsCount;
    }

    public void addPatientToSet(int age, String gender, String fullName, String ID, String complexity,
                                String bloodType) throws InvalidAgeException, InvalidBloodTypeException {
        if (gender.equals("male")) {
            patientsSet.add(new MalePatient(age, fullName, ID, complexity, bloodType));
        } else {
            patientsSet.add(new FemalePatient(age, fullName, ID, complexity, bloodType));
        }

    }

    public void addEmployeeToSet(int age, String gender, String fullName, String profession, String ID,
                                 double payRate, ArrayList<Integer> workingDays) throws InvalidAgeException,
            InvalidPayRateException, InvalidWorkingDayException {
        switch (profession) {
            case "physician":
                Physician physician = new Physician(age, gender, fullName, ID, payRate, workingDays);
                physician.initializeSchedule();
                employeeSet.add(physician);
                break;
            case "nurse":
                employeeSet.add(new Nurse(age, gender, fullName, ID, payRate, workingDays));
                break;
            default:
                break;
        }
    }

    public Patient searchPatient(String fullName) {
        for (Patient patient : patientsSet) {
            if (patient.getFullName().toLowerCase().equals(fullName)) {
                return patient;
            }
        }
        return null;
    }

    public Employee searchEmployee(String fullName) {
        for (Employee employee : employeeSet) {
            if (employee.getFullName().toLowerCase().equals(fullName)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public void evacuateTheRoom(String cause) {
        for (Patient patient : patientsSet) {
            patient.evacuateTheRoom(cause);
        }
        for (Employee employee : employeeSet) {
            employee.evacuateTheRoom("cause");
        }
    }

    //Getters
    public Set<Patient> getPatientsSet() {
        return patientsSet;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }
}
