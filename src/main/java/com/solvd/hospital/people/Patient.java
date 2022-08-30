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

public abstract class Patient extends Person implements ISchedulable<Physician>, ICallable, IChargeable, IEvacuable {
    private static final Logger log = LogManager.getLogger(Patient.class);
    protected BloodType bloodType;
    protected String complexity;
    protected int systolicPressure;
    protected int diastolicPressure;
    protected int oxygenLevel;
    protected int heartRate;
    protected String diagnostic;
    protected String drugPrescription;
    protected String vitalSignsHistory;
    protected double amountCharged;
    protected LinkedMap<LocalDateTime, Physician> scheduledAppointments = new LinkedMap<>();
    protected ArrayList<Procedures> receivedProcedures = new ArrayList<>();
    public static final int SYSTOLIC_PRESSURE_REFERENCE = 120;
    public static final int DIASTOLIC_PRESSURE_REFERENCE = 80;
    public static final int LOW_OXYGEN_LEVEL = 60;
    public static final int LOW_HEART_RATE = 60;
    public static final int HIGH_HEART_RATE = 100;
    public static final String[] BLOOD_TYPES = new String[]{"O-", "O+", "B-", "B+", "A-", "A+", "AB-", "AB+"};

    @Override
    public String toString() {
        return "Patient: " + fullName + ", SSN: " + ID;
    }

    public Patient(int age, String gender, String fullName, String ID, String complexity,
                   String bloodType) throws InvalidAgeException, InvalidBloodTypeException {
        super(age, gender, fullName, ID);
        this.complexity = complexity;
        if (Arrays.stream(BloodType.values()).noneMatch(e -> e.name().equals(bloodType))) {
            throw new InvalidBloodTypeException("Invalid blood type, must be any of:\n" +
                    "O_NEGATIVE, O_POSITIVE, A_NEGATIVE, A_POSITIVE, B_NEGATIVE, B_POSITIVE, AB_NEGATIVE," +
                    "AB_POSITIVE");
        }
        this.bloodType = BloodType.valueOf(bloodType);
        this.amountCharged = 0;
    }

    @Override
    public void scheduleAppointment(LocalDateTime askedDateTime, Physician physician) {
        scheduledAppointments.put(askedDateTime, physician);
    }

    @Override
    public void callPerson(HospitalRoom hospitalRoom) {
        log.info("Please patient:" + this + "report to " + hospitalRoom.toString());
    }

    @Override
    public void requestCharges() {
        Iterator<Procedures> proceduresIterator = receivedProcedures.iterator();
        double totalRemaining = 0;
        Scanner scanner = new Scanner(System.in);
        while (proceduresIterator.hasNext()) {
            Procedures currentProcedure = proceduresIterator.next();
            if (!currentProcedure.isPaidByPatient()) {
                log.info("You have a: " + currentProcedure.getType() + " procedure to pay " + " made by " +
                        currentProcedure.getPhysicianName() + " on " + currentProcedure.getLocalDateTime() +
                        ",\n which costs: " + currentProcedure.getCost() +
                        ".\nPress 1 to pay it know, any other to pay later");
                String command = scanner.nextLine();
                if (command.equals("1")) {
                    IThankable <Patient> thanks = t -> log.info("Thanks: " + t.fullName + " for paying");
                    thanks.thank(this);
                    currentProcedure.setPaidByPatient(true);
                    double totalOwedByPatients = Procedures.getTotalOwedByPatients() - currentProcedure.getCost();
                    Procedures.setTotalOwedByPatients(totalOwedByPatients);
                    continue;
                }
                totalRemaining += currentProcedure.getCost();
            }
        }
        log.info("You have a: $" + totalRemaining + " remaining to pay");
    }

    @Override
    public void evacuateTheRoom(String cause) {
        log.info("Please: " + this.fullName + " evacuate the room, there´s been a " + cause);
    }

    public void canReceiveFrom(Patient patient) {
        if (this.bloodType.getCanReceiveFrom().contains(patient.bloodType.toString())) {
            log.info(this.getFullName() + " can receive blood from: " + patient.getFullName());
        } else {
            log.info(this.getFullName() + " can't receive blood from: " + patient.getFullName());
        }
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

    public int getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(int oxygenLevel) throws InvalidOxygenLevelException {
        if (oxygenLevel < 50) {
            throw new InvalidOxygenLevelException("Oxygen pressure too low check your oximeter");
        }
        this.oxygenLevel = oxygenLevel;
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

    public void addProcedure(String type, Physician physician, LocalDateTime dateTime) {
        switch (type) {
            case "diagnostic":
                receivedProcedures.add(Procedures.addDiagnosticProcedure(this, physician, dateTime));
                break;

            case "birth":
                receivedProcedures.add(Procedures.addBirthProcedure(this, physician, dateTime));
                break;

            case "anesthesia":
                receivedProcedures.add(Procedures.addAnesthesiaProcedure(this, physician, dateTime));
                break;

            case "surgery":
                receivedProcedures.add(Procedures.addSurgeryProcedure(this, physician, dateTime));
                break;
        }

    }
}
