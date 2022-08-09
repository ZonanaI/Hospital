package people;

import java.util.Locale;
import java.util.Scanner;

public final class FemalePatient extends Patient {

    public FemalePatient(int age, String fullName, String ID, String complexity, String bloodType) {
        super(age, "female", fullName, ID, complexity, bloodType);
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

        FemalePatient femalePatient = (FemalePatient) obj;
        return (femalePatient.ID.equals(this.ID));
    }

    @Override
    public String toString() {
        return " Female patient: " + fullName + ", ID: " + ID;
    }

    public Patient giveBirth() {
        Scanner input = new Scanner(System.in);
        int age = 0;
        System.out.println("Please enter the baby weight:");
        double weight = input.nextDouble();
        System.out.println("Please enter the baby blood type:");
        String bloodType = input.next();
        String gender;
        do {
            System.out.println("Please enter the baby gender:");
            gender = input.next().toLowerCase(Locale.ROOT);
        } while (!gender.equals("male") && !gender.equals("female"));
        System.out.println("Please enter the baby full name:");
        String fullName = input.next();
        System.out.println("Please enter the baby complexity status:");
        String complexity = input.next();
        System.out.println("Please enter the baby ID number:");
        String ID = input.next();
        input.close();
        if (gender.equals("male")){
            return new MalePatient(age, fullName, ID, complexity, bloodType);
        } else {
            return new FemalePatient(age, fullName, ID, complexity, bloodType);
        }
    }
}
