package com.solvd.hospital.people;

import com.solvd.hospital.rooms.HospitalRoom;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public abstract class Patient extends Person implements ISchedulable<Physician>, ICallable {
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

    protected LinkedMap<LocalDateTime, Physician> scheduledAppointments;
    public static final int SYSTOLIC_PRESSURE_REFERENCE = 120;
    public static final int DIASTOLIC_PRESSURE_REFERENCE = 80;
    public static final int LOW_OXYGEN_PRESSURE = 60;
    public static final int LOW_HEART_RATE = 60;
    public static final int HIGH_HEART_RATE = 100;


    @Override
    public String toString() {
        return "Patient: " + fullName + ", SSN: " + ID;
    }

    public Patient(int age, String gender, String fullName,
                   String ID, String complexity, String bloodType) {
        super(age, gender, fullName, ID);
        this.complexity = complexity;
        this.bloodType = bloodType;
    }

    @Override
    public void scheduleAppointment(LocalDateTime askedDateTime, Physician physician) {
        scheduledAppointments.put(askedDateTime, physician);
    }

    @Override
    public void callPerson(HospitalRoom hospitalRoom) {
        log.info("Please patient:" + this.toString() + "report to " + hospitalRoom.toString());
    }

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

    public void setOxygenPressure(int oxygenPressure) {
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
