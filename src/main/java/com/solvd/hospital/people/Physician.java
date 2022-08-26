package com.solvd.hospital.people;

import com.solvd.hospital.Procedures.Procedures;
import com.solvd.hospital.exceptions.InvalidAgeException;
import com.solvd.hospital.exceptions.InvalidOxygenLevelException;
import com.solvd.hospital.exceptions.InvalidPayRateException;
import com.solvd.hospital.exceptions.InvalidWorkingDayException;
import com.solvd.hospital.rooms.HospitalRoom;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Physician extends Employee implements IDiagnosable, ISchedulable<Patient>, ICallable, IEvacuable {

    private static final Logger log = LogManager.getLogger(Physician.class);
    protected ArrayList<Procedures> doneProcedures = new ArrayList<>();
    protected String medicalSpeciality;
    protected LinkedMap<LocalDateTime, Patient> scheduledAppointments = new LinkedMap<>();
    protected int appointmentDuration;

    public Physician(int age, String gender, String fullName, String ID, double payRate,
                     ArrayList<Integer> workingDays, LocalTime entryTime, LocalTime leavingTime)
            throws InvalidAgeException, InvalidPayRateException, InvalidWorkingDayException {
        super(age, gender, fullName, ID, payRate, workingDays, entryTime, leavingTime);
        this.appointmentDuration = 30;
    }

    @Override
    public String toString() {
        return "Physician: " + fullName;
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

        Physician physician = (Physician) obj;
        return (physician.ID.equals(this.ID));
    }

    @Override
    public double getPayCheck() {
        double proceduresPay = 0;
        Iterator<Procedures> proceduresIterator = doneProcedures.iterator();
        while (proceduresIterator.hasNext()) {
            Procedures currentProcedure = proceduresIterator.next();
            if (!currentProcedure.isPaidToPhysician()) {
                proceduresPay += currentProcedure.getPhysicianPayRate();
                currentProcedure.setPaidToPhysician(true);
            }
        }
        return workedHours * payRate + proceduresPay;
    }

    @Override
    public void measureVitalSigns(Patient patient) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder(patient.getVitalSignsHistory());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        stringBuilder.append("\n" + dtf.format(now));
        log.info("Please enter the measured systolic pressure:");
        patient.setSystolicPressure(scanner.nextInt());
        stringBuilder.append("\nSystolic Pressure: " + patient.getSystolicPressure());
        log.info("Please enter the measured diastolic pressure:");
        patient.setDiastolicPressure(scanner.nextInt());
        stringBuilder.append("\nDiastolic Pressure: " + patient.getDiastolicPressure());
        log.info("Please enter the measured oxygen level:");
        try {
            patient.setOxygenLevel(scanner.nextInt());
        } catch (InvalidOxygenLevelException e) {
            log.error(e.getMessage());
        }
        stringBuilder.append("\nOxygen level: " + patient.getOxygenLevel());
        log.info("Please enter the measured heart rate:");
        patient.setHeartRate(scanner.nextInt());
        stringBuilder.append("\nHeart rate: " + patient.getHeartRate());

        patient.setVitalSignsHistory(stringBuilder.toString());
        scanner.close();
        patient.receivedProcedures.add(Procedures.addDiagnosticProcedure(patient.ID, this.ID, now));
        doneProcedures.add(Procedures.addDiagnosticProcedure(patient.ID, this.ID, now));
    }

    @Override
    public void callPerson(HospitalRoom hospitalRoom) {
        if (!this.vacationDays.contains(LocalDate.now())) {
            log.info("Please Dr.:" + this.toString() + " report to " + hospitalRoom.toString());
        }
        log.info("Sorry, Dr:" + this.toString() + " is on vacation");
    }

    @Override
    public void evacuateTheRoom(String cause) {
        log.info("Please Dr.: " + this.fullName + " evacuate the room, there´s been a " + cause);
    }

    public void initializeSchedule() {
        if (scheduledAppointments.isEmpty()) {
            LocalDateTime currentLocalDateTime = LocalDateTime.now();
            for (long i = 0; i < 365; i++) {
                LocalDate nextScheduledDate = currentLocalDateTime.plusDays(i).toLocalDate();
                if (workingDays.contains(currentLocalDateTime.getDayOfWeek().getValue())) {
                    for (LocalTime currentTime = entryTime; currentTime.
                            isAfter(leavingTime.minusMinutes(appointmentDuration));
                         currentTime.plusMinutes(appointmentDuration)) {
                        LocalDateTime fullScheduleDateTime = LocalDateTime.of(nextScheduledDate, currentTime);
                        scheduledAppointments.put(fullScheduleDateTime, null);
                    }
                }
            }
        } else {
            LocalDateTime lastScheduledRegister = scheduledAppointments.lastKey();
            for (long i = 1; i < 365; i++) {
                LocalDate nextScheduledDate = lastScheduledRegister.plusDays(i).toLocalDate();
                if (workingDays.contains(lastScheduledRegister.getDayOfWeek().getValue())) {
                    for (LocalTime currentTime = entryTime; currentTime.
                            isAfter(leavingTime.minusMinutes(appointmentDuration));
                         currentTime.plusMinutes(appointmentDuration)) {
                        LocalDateTime fullScheduleDateTime = LocalDateTime.of(nextScheduledDate, currentTime);
                        scheduledAppointments.put(fullScheduleDateTime, null);
                    }
                }
            }
        }
    }

    public boolean isScheduleFree(LocalDateTime askedDateTime) {
        if (scheduledAppointments.containsKey(askedDateTime) &&
                scheduledAppointments.get(askedDateTime) == null) {
            return true;
        }
        return false;
    }

    @Override
    public void scheduleAppointment(LocalDateTime askedDateTime, Patient patient) {
        if (isScheduleFree(askedDateTime)) {
            scheduledAppointments.put(askedDateTime, patient);
            patient.scheduleAppointment(askedDateTime, this);
        } else {
            log.error("The asked time: " + askedDateTime.toString() + " is already taken or the Doctor won´t be" +
                    " available, please choose another");
        }
    }

    //Set vacation, remove date keys, if it has an appointment prints a warning to re-schedule.
    @Override
    public void setVacationDays(LocalDate firstVacationDay, LocalDate lastVacationDay) {
        for (LocalDate date = firstVacationDay; date.isBefore(lastVacationDay); date.plusDays(1)) {
            vacationDays.add(date);
        }
        for (LocalDate date = firstVacationDay; date.isBefore(lastVacationDay); date.plusDays(1)) {
            for (LocalTime time = entryTime; time.isAfter(leavingTime.minusMinutes(appointmentDuration));
                 time.plusMinutes(appointmentDuration)) {
                LocalDateTime currentLocalDateTime = date.atTime(time);
                if (scheduledAppointments.containsKey(currentLocalDateTime)) {
                    if (scheduledAppointments.get(currentLocalDateTime) != null) {
                        String patientName = scheduledAppointments.get(currentLocalDateTime).getFullName();
                        log.info("Please re schedule appointment on " + currentLocalDateTime + " with patient: "
                                + patientName);
                        scheduledAppointments.remove(currentLocalDateTime);
                    }
                    scheduledAppointments.remove(currentLocalDateTime);
                }
            }
        }
    }

    //Setters getters
    public String getMedicalSpeciality() {
        return medicalSpeciality;
    }

    public void setMedicalSpeciality(String medicalSpeciality) {
        this.medicalSpeciality = medicalSpeciality;
    }
}
