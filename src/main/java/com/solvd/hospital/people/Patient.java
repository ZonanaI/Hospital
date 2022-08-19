package com.solvd.hospital.people;

import com.solvd.hospital.Procedures.Procedures;
import com.solvd.hospital.exceptions.InvalidAgeException;
import com.solvd.hospital.exceptions.InvalidBloodTypeException;
import com.solvd.hospital.exceptions.InvalidOxygenLevelException;
import com.solvd.hospital.rooms.HospitalRoom;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;

public abstract class Patient extends Person implements ISchedulable<Physician>, ICallable, IChargeable {
    private static final Logger log = LogManager.getLogger(Patient.class);
    protected String bloodType;
    protected String complexity;
    protected int systolicPressure;
    protected int diastolicPressure;
    protected int oxygenPressure;
    protected int heartRate;
    protected String diagnostic;
    protected String drugPrescription;
    protected String vitalSignsHistory;
    protected double amountCharged;
    protected LinkedMap<LocalDateTime, Physician> scheduledAppointments = new LinkedMap<>();
    protected ArrayList<Procedures> receivedProcedures = new ArrayList<>();
    public static final int SYSTOLIC_PRESSURE_REFERENCE = 120;
    public static final int DIASTOLIC_PRESSURE_REFERENCE = 80;
    public static final int LOW_OXYGEN_PRESSURE = 60;
    public static final int LOW_HEART_RATE = 60;
    public static final int HIGH_HEART_RATE = 100;
    public static final String[] BLOOD_TYPES = new String[]{"O-", "O+", "B-", "B+", "A-", "A+", "AB-", "AB+"};

    @Override
    public String toString() {
        return "Patient: " + fullName + ", SSN: " + ID;
    }

    public Patient(int age, String gender, String fullName, String ID, String complexity, String bloodType)
            throws InvalidAgeException, InvalidBloodTypeException {
        super(age, gender, fullName, ID);
        this.complexity = complexity;
        if (!Arrays.asList(BLOOD_TYPES).contains(bloodType)) {
            throw new InvalidBloodTypeException("Invalid blood type, must be any of:" +
                    " O-, O+, B-, B+, A-, A+, AB-, AB+");
        }
        this.bloodType = bloodType;
        this.amountCharged = 0;
    }

    @Override
    public void scheduleAppointment(LocalDateTime askedDateTime, Physician physician) {
        scheduledAppointments.put(askedDateTime, physician);
    }

    @Override
    public void callPerson(HospitalRoom hospitalRoom) {
        log.info("Please patient:" + this.toString() + "report to " + hospitalRoom.toString());
    }

    @Override
    public void requestCharges() {
        Iterator<Procedures> proceduresIterator = receivedProcedures.iterator();
        double totalRemaining = 0;
        Scanner scanner = new Scanner(System.in);
        while (proceduresIterator.hasNext()) {
            Procedures currentProcedure = proceduresIterator.next();
            if (!currentProcedure.isPaidByPatient()) {
                log.info("You have a: " + currentProcedure.getType() + " procedure to pay which costs: " +
                        currentProcedure.getCost() + ". Press 1 to pay it know, any other to pay later");
                String command = scanner.nextLine();
                if (command.equals("1")) {
                    currentProcedure.setPaidByPatient(true);
                    continue;
                }
                totalRemaining += currentProcedure.getCost();
            }
        }
        log.info("You have a: $" + totalRemaining + " remaining to pay");
        scanner.close();
    }


    //Setters and getters
    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getOxygenPressure() {
        return oxygenPressure;
    }

    public void setOxygenPressure(int oxygenPressure) throws InvalidOxygenLevelException {
        if (oxygenPressure < 50) {
            throw new InvalidOxygenLevelException("Oxygen pressure too low check your oximeter");
        }
        this.oxygenPressure = oxygenPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getDrugPrescription() {
        return drugPrescription;
    }

    public void setDrugPrescription(String drugPrescription) {
        this.drugPrescription = drugPrescription;
    }

    public String getVitalSignsHistory() {
        return vitalSignsHistory;
    }

    public void setVitalSignsHistory(String vitalSignsHistory) {
        this.vitalSignsHistory = vitalSignsHistory;
    }

}
