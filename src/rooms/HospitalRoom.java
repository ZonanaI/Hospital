package rooms;

import people.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class HospitalRoom {

    protected String location;  /* Floor-sector e.g., first floor sector A: 1-A */
    protected int patientsCount;
    protected int deceasesCount;
    protected int recoveriesCount;
    protected Set <Patient> patientsSet = new LinkedHashSet<>();
    protected Set <Patient> deceasesSet = new LinkedHashSet<>();
    protected Set <Employee> employeeSet = new HashSet<>();

    public HospitalRoom (int patientCount){
        this.patientsCount = patientCount;
    }
    public HospitalRoom() {}
    public HospitalRoom (String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPatientsCount(int patientsCount) {
        this.patientsCount = patientsCount;
    }

    public void admitNewPatients (int toAddPatients){
        patientsCount += toAddPatients;
    }

    public void confirmDeceases(int toAddDeceases) {
        deceasesCount += toAddDeceases;
    }

    public void confirmRecoveries(int toAddRecoveries) {
        recoveriesCount += toAddRecoveries;
    }

    public void addPatient(Patient patient) {
        patientsSet.add(patient);
    }

    public void removePatient(Patient patient) {
        patientsSet.remove(patient);
    }

    public int getPatientsCount (){
        return patientsSet.size();
    }
    public int getPatientsCount (String complexity){
        int patientsCount = 0;
        for (Patient patient : patientsSet) {
            if (patient.getComplexity().equals(complexity)) {
                patientsCount++;
            }
        }
        return  patientsCount;
    }

    public void addPatientToSet(int age, String gender, String fullName, String complexity,
                                String socialSecurityNumber) {
        if (gender.equals("male")){
            patientsSet.add(new MalePatient(age, fullName, complexity, socialSecurityNumber));
        } else {
            patientsSet.add(new FemalePatient(age, fullName, complexity, socialSecurityNumber));
        }

    }

    public void addEmployeeToSet(int age, String gender, String fullName, String profession,
                                 String socialSecurityNumber) {
        switch (profession){
            case "physician":
                employeeSet.add(new Physician(age, gender, fullName, socialSecurityNumber));
                break;
            case "nurse":
                employeeSet.add(new Nurse(age, gender, fullName, socialSecurityNumber));
                break;
            default:
                break;
        }
    }
    public void addDecease(Patient patient) {
        deceasesSet.add(patient);
    }

    public int getDeceasesCount (){
        return  deceasesSet.size();
    }
}