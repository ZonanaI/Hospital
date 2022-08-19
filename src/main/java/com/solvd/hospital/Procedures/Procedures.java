package com.solvd.hospital.Procedures;

import java.time.LocalDateTime;

public class Procedures {

    private String type;
    private String ID;
    private double cost;
    private double physicianPayRate;
    private String patientID;
    private String physicianID;
    private LocalDateTime localDateTime;
    private boolean paidToPhysician;
    private boolean paidByPatient;
    private static double totalOwedByPatients;
    private static double totalOwedToPhysician;

    static {
        totalOwedByPatients = 0;
        totalOwedToPhysician = 0;
    }

    public Procedures(String type, String ID, double cost, String patientID, String physicianID,
                      LocalDateTime localDateTime) {
        this.type = type;
        this.ID = ID;
        this.cost = cost;
        this.patientID = patientID;
        this.physicianID = physicianID;
        this.localDateTime = localDateTime;
        this.paidByPatient = false;
    }

    public Procedures(String type, String ID, double cost, double physicianPayRate, String patientID,
                      String physicianID, LocalDateTime localDateTime) {
        this.type = type;
        this.ID = ID;
        this.cost = cost;
        this.physicianPayRate = physicianPayRate;
        this.patientID = patientID;
        this.physicianID = physicianID;
        this.localDateTime = localDateTime;
        this.paidToPhysician = false;
        this.paidByPatient = false;
    }

    public static Procedures addDiagnosticProcedure(String patientID, String physicianID, LocalDateTime localDateTime) {
        String type = "diagnostic";
        String ID = "001";
        double cost = 120;
        double physicianPayRate = 30;
        totalOwedByPatients += cost;
        totalOwedToPhysician += physicianPayRate;
        return new Procedures(type, ID, cost, physicianPayRate, patientID, physicianID, localDateTime);
    }

    public static Procedures addBirthProcedure(String patientID, String physicianID, LocalDateTime localDateTime) {
        String type = "birth";
        String ID = "014";
        double cost = 2000;
        double physicianPayRate = 500;
        totalOwedByPatients += cost;
        totalOwedToPhysician += physicianPayRate;
        return new Procedures(type, ID, cost, physicianPayRate, patientID, physicianID, localDateTime);
    }

    public static Procedures addAnesthesiaProcedure(String patientID, String physicianID, LocalDateTime localDateTime) {
        String type = "anesthesia";
        String ID = "017";
        double cost = 1000;
        double physicianPayRate = 400;
        totalOwedByPatients += cost;
        totalOwedToPhysician += physicianPayRate;
        return new Procedures(type, ID, cost, physicianPayRate, patientID, physicianID, localDateTime);
    }

    public static Procedures addMinorSurgeryProcedure(String patientID, String physicianID, LocalDateTime localDateTime) {
        String type = "minor surgery";
        String ID = "022";
        double cost = 1500;
        double physicianPayRate = 375;
        totalOwedByPatients += cost;
        totalOwedToPhysician += physicianPayRate;
        return new Procedures(type, ID, cost, physicianPayRate, patientID, physicianID, localDateTime);
    }


    //Setters and getters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPhysicianPayRate() {
        return physicianPayRate;
    }

    public void setPhysicianPayRate(double physicianPayRate) {
        this.physicianPayRate = physicianPayRate;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPhysicianID() {
        return physicianID;
    }

    public void setPhysicianID(String physicianID) {
        this.physicianID = physicianID;
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

}
