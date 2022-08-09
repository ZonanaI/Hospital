package people;

import Procedures.Procedures;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Physician extends Employee implements IDiagnostics {

    protected Set<Procedures> doneProcedures;
    protected String medicalSpeciality;
    public Physician (int age, String gender, String fullName, String ID) {
        super(age, gender, fullName, ID);
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

        Physician physician = (Physician) obj;
        return (physician.ID.equals(this.ID));
    }

    @Override
    public double getPayCheck (){
        double proceduresPay = 0;
        Iterator <Procedures> proceduresIterator = doneProcedures.iterator();
        while (proceduresIterator.hasNext()){
            Procedures currentProcedure = proceduresIterator.next();
            if(!currentProcedure.isPaidToPhysician()){
                double currentProcedurePayRate = proceduresIterator.next().getPhysicianPayRate();
                proceduresPay += currentProcedurePayRate;
                currentProcedure.setPaidToPhysician(true);
            }
        }
        return workedHours * payRate + proceduresPay;
    }

    @Override
    public void measureVitalSigns(Patient patient){
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
        doneProcedures.add(Procedures.addDiagnosticProcedure(patient.ID, this.ID, now));
    }
}
