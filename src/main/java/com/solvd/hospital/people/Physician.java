package com.solvd.hospital.people;

import com.solvd.hospital.Procedures.Procedures;
import com.solvd.hospital.rooms.HospitalRoom;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Physician extends Employee implements IDiagnosable, ISchedulable<Patient>, ICallable {

    private static final Logger log = LogManager.getLogger(Physician.class);
    protected Set<Procedures> doneProcedures;
    protected String medicalSpeciality;
    protected LinkedMap<LocalDateTime, Patient> scheduledAppointments;
    protected int appointmentDuration;

    public Physician(int age, String gender, String fullName, String ID) {
        super(age, gender, fullName, ID);
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
                double currentProcedurePayRate = proceduresIterator.next().getPhysicianPayRate();
                proceduresPay += currentProcedurePayRate;
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
        log.info("Please enter the measured oxygen pressure:");
        patient.setOxygenPressure(scanner.nextInt());
        stringBuilder.append("\nOxygen Pressure: " + patient.getOxygenPressure());
        log.info("Please enter the measured heart rate:");
        patient.setHeartRate(scanner.nextInt());
        stringBuilder.append("\nHeart rate: " + patient.getHeartRate());

        patient.setVitalSignsHistory(stringBuilder.toString());
        scanner.close();
        doneProcedures.add(Procedures.addDiagnosticProcedure(patient.ID, this.ID, now));
    }

    @Override
    public void callPerson(HospitalRoom hospitalRoom) {
        log.info("Please Dr.:" + this.toString() + "report to " + hospitalRoom.toString());
    }

    public String getMedicalSpeciality() {
        return medicalSpeciality;
    }

    public void setMedicalSpeciality(String medicalSpeciality) {
        this.medicalSpeciality = medicalSpeciality;
    }

    public void initializeSchedule() {
        if (scheduledAppointments.isEmpty()) {
            LocalDateTime currentLocalDateTime = LocalDateTime.now();
            for (long i = 0; i < 365; i++) {
                LocalDate nextScheduledDate = currentLocalDateTime.plusDays(i).toLocalDate();
                if (Arrays.binarySearch(workingDays, currentLocalDateTime.getDayOfWeek().getValue()) >= 0) {
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
                if (Arrays.binarySearch(workingDays, lastScheduledRegister.getDayOfWeek().getValue()) >= 0) {
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

    public void scheduleAppointment(LocalDateTime askedDateTime, Patient patient) {
        if (isScheduleFree(askedDateTime)) {
            scheduledAppointments.put(askedDateTime, patient);
            patient.scheduleAppointment(askedDateTime, this);
        } else {
            log.error("The asked time: " + askedDateTime.toString() + " is already taken," +
                    "please choose another");
        }
    }
}
