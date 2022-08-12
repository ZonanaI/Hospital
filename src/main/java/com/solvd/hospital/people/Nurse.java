package com.solvd.hospital.people;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public final class Nurse extends Employee implements IDiagnostics {

    public Nurse(int age, String gender, String fullName, String ID){
        super(age, gender, fullName, ID);
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
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        return socialSecurityNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(obj == null || obj.getClass()!= this.getClass()){
            return false;
        }

        Nurse nurse = (Nurse) obj;
        return (nurse.ID.equals(this.ID));
    }

    @Override
    public double getPayCheck(){
        return workedHours * payRate;
    }

    @Override
    public void measureVitalSigns(Patient patient) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder(patient.getVitalSignsHistory());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        stringBuilder.append("\n" + dtf.format(now));
        System.out.println("Please enter the measured systolic pressure:");
        patient.setSystolicPressure(scanner.nextInt());
        stringBuilder.append("\nSystolic Pressure: " + patient.getSystolicPressure());
        System.out.println("Please enter the measured diastolic pressure:");
        patient.setDiastolicPressure(scanner.nextInt());
        stringBuilder.append("\nDiastolic Pressure: " + patient.getDiastolicPressure());
        System.out.println("Please enter the measured oxygen pressure:");
        patient.setOxygenPressure(scanner.nextInt());
        stringBuilder.append("\nOxygen Pressure: " + patient.getOxygenPressure());
        System.out.println("Please enter the measured heart rate:");
        patient.setHeartRate(scanner.nextInt());
        stringBuilder.append("\nHeart rate: " + patient.getHeartRate());

        patient.setVitalSignsHistory(stringBuilder.toString());
        scanner.close();
    }
}
