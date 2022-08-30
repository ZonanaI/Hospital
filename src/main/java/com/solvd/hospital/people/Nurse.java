package com.solvd.hospital.people;

import com.solvd.hospital.exceptions.InvalidAgeException;
import com.solvd.hospital.exceptions.InvalidOxygenLevelException;
import com.solvd.hospital.exceptions.InvalidPayRateException;
import com.solvd.hospital.exceptions.InvalidWorkingDayException;
import com.solvd.hospital.rooms.HospitalRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public final class Nurse extends Employee implements IDiagnosable, ICallable, IEvacuable {
    private static final Logger log = LogManager.getLogger(Nurse.class);

    public Nurse(int age, String gender, String fullName, String ID, double payRate, ArrayList<Integer> workingDays,
                 LocalTime entryTime, LocalTime leavingTime) throws InvalidAgeException, InvalidPayRateException,
            InvalidWorkingDayException {
        super(age, gender, fullName, ID, payRate, workingDays, entryTime, leavingTime);
    }

    @Override
    public String toString() {
        return "Nurse: " + fullName;
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

        Nurse nurse = (Nurse) obj;
        return (nurse.ID.equals(this.ID));
    }

    @Override
    public double getPayCheck() {
        IThankable<Nurse> thanks = t -> log.info("Thanks: " + t.fullName + " for your non stop work!");
        thanks.thank(this);
        return workedHours * payRate;
    }

    @Override
    public void measureVitalSigns(Patient patient) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder(patient.getVitalSignsHistory());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        stringBuilder.append("\n").append(dtf.format(now));
        log.info("Please enter the measured systolic pressure:");
        patient.setSystolicPressure(scanner.nextInt());
        stringBuilder.append("\nSystolic Pressure: ").append(patient.getSystolicPressure());
        log.info("Please enter the measured diastolic pressure:");
        patient.setDiastolicPressure(scanner.nextInt());
        stringBuilder.append("\nDiastolic Pressure: ").append(patient.getDiastolicPressure());
        log.info("Please enter the measured oxygen level:");
        try {
            patient.setOxygenLevel(scanner.nextInt());
        } catch (InvalidOxygenLevelException e) {
            log.error(e.getMessage());
        }
        stringBuilder.append("\nOxygen Level: ").append(patient.getOxygenLevel());
        log.info("Please enter the measured heart rate:");
        patient.setHeartRate(scanner.nextInt());
        stringBuilder.append("\nHeart rate: ").append(patient.getHeartRate());

        patient.setVitalSignsHistory(stringBuilder.toString());
    }

    @Override
    public void callPerson(HospitalRoom hospitalRoom) {
        if (!this.vacationDays.contains(LocalDate.now())) {
            log.info("Please Nurse:" + this + " report to " + hospitalRoom.toString());
        }
        log.info("Sorry, Nurse:" + this + " is on vacation");
    }

    @Override
    public void evacuateTheRoom(String cause) {
        log.info("Please Nurse: " + this.fullName + " evacuate the room, there´s been a " + cause);
    }
}
