package Procedures;

import people.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    }

    public static Procedures addDiagnosticProcedure (String patientID, String physicianID, LocalDateTime localDateTime){
        String type = "diagnostic";
        String ID = "001";
        double cost = 20;
        double physicianPayRate = 5;
        return new Procedures(type, ID, cost, physicianPayRate, patientID, physicianID, localDateTime);
    }

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
