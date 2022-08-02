package people;

import java.util.Locale;
import java.util.Scanner;

public class FemalePatient extends Patient {

    public FemalePatient(int age, String fullName, String complexity,
                         String socialSecurityNumber) {
        super(age, "female", fullName, complexity, socialSecurityNumber);
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
        System.out.println("Please enter the baby social security number:");
        String socialSecurityNumber = input.next();
        input.close();
        if (gender.equals("male")){
            return new MalePatient(age, fullName, complexity, socialSecurityNumber);
        } else {
            return new FemalePatient(age, fullName, complexity, socialSecurityNumber);
        }
    }
}
