package com.solvd.hospital.Procedures;

import com.solvd.hospital.people.Patient;
import com.solvd.hospital.people.Physician;

import java.time.LocalDateTime;

public class Procedures {

    private String type;
    private String ID;
    private double cost;
    private double physicianPayRate;
    private Patient patient;
    private Physician physician;
    private LocalDateTime localDateTime;
    private boolean paidToPhysician;
    private boolean paidByPatient;
    private static double totalOwedByPatients;
    private static double totalOwedToPhysician;

    static {
        totalOwedByPatients = 0;
        totalOwedToPhysician = 0;
    }

    public Procedures(String type, String ID, double cost, double physicianPayRate, Patient patient,
                      Physician physician, LocalDateTime localDateTime) {
        this.type = type;
        this.ID = ID;
        this.cost = cost;
        this.physicianPayRate = physicianPayRate;
        this.patient = patient;
        this.physician = physician;
        this.localDateTime = localDateTime;
        this.paidToPhysician = false;
        this.paidByPatient = false;
    }

    public static Procedures addDiagnosticProcedure(Patient patient, Physician physician, LocalDateTime localDateTime) {
        String type = "diagnostic";
        String ID = "001";
        double cost = 120;
        double physicianPayRate = 30;
        totalOwedByPatients += cost;
        totalOwedToPhysician += physicianPayRate;
        return new Procedures(type, ID, cost, physicianPayRate, patient, physician, localDateTime);
    }

    public static Procedures addBirthProcedure(Patient patient, Physician physician, LocalDateTime localDateTime) {
        String type = "birth";
        String ID = "014";
        double cost = 2000;
        double physicianPayRate = 500;
        totalOwedByPatients += cost;
        totalOwedToPhysician += physicianPayRate;
        return new Procedures(type, ID, cost, physicianPayRate, patient, physician, localDateTime);
    }

    public static Procedures addAnesthesiaProcedure(Patient patient, Physician physician, LocalDateTime localDateTime) {
        String type = "anesthesia";
        String ID = "017";
        double cost = 1000;
        double physicianPayRate = 400;
        totalOwedByPatients += cost;
        totalOwedToPhysician += physicianPayRate;
        return new Procedures(type, ID, cost, physicianPayRate, patient, physician, localDateTime);
    }

    public static Procedures addSurgeryProcedure(Patient patient, Physician physician, LocalDateTime localDateTime) {
        String type = "surgery";
        String ID = "022";
        double cost = 1500;
        double physicianPayRate = 375;
        totalOwedByPatients += cost;
        totalOwedToPhysician += physicianPayRate;
        return new Procedures(type, ID, cost, physicianPayRate, patient, physician, localDateTime);
    }


    //Setters and getters
    public String getType() {
        return type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getCost() {
        return cost;
    }

    public double getPhysicianPayRate() {
        return physicianPayRate;
    }

    public boolean isPaidToPhysician() {
        return paidToPhysician;
    }

    public void setPaidToPhysician(boolean paidToPhysician) {
        this.paidToPhysician = paidToPhysician;
    }

    public boolean isPaidByPatient() {
        return paidByPatient;
    }

    public void setPaidByPatient(boolean paidByPatient) {
        this.paidByPatient = paidByPatient;
    }

    public String getPhysicianName() {
        return this.physician.getFullName();
    }

    public String getPatientName() {
        return this.patient.getFullName();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public static double getTotalOwedByPatients() {
        return totalOwedByPatients;
    }

    public static double getTotalOwedToPhysician() {
        return totalOwedToPhysician;
    }

    public static void setTotalOwedByPatients(double totalOwedByPatients) {
        Procedures.totalOwedByPatients = totalOwedByPatients;
    }

    public static void setTotalOwedToPhysician(double totalOwedToPhysician) {
        Procedures.totalOwedToPhysician = totalOwedToPhysician;
    }
}
