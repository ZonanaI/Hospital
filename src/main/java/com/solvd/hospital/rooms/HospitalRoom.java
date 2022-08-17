package com.solvd.hospital.rooms;

import com.solvd.hospital.people.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class HospitalRoom {

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

    public void addPatientToSet(int age, String gender, String fullName,
                                String ID, String complexity, String bloodType) {
        if (gender.equals("male")) {
            patientsSet.add(new MalePatient(age, fullName, ID, complexity, bloodType));
        } else {
            patientsSet.add(new FemalePatient(age, fullName, ID, complexity, bloodType));
        }

    }

    public void addEmployeeToSet(int age, String gender, String fullName, String profession,
                                 String ID) {
        switch (profession) {
            case "physician":
                employeeSet.add(new Physician(age, gender, fullName, ID));
                break;
            case "nurse":
                employeeSet.add(new Nurse(age, gender, fullName, ID));
                break;
            default:
                break;
        }
    }
}
