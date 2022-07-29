package people;

public class MalePatient extends Patient {
    public MalePatient (int age, double weight, String bloodType, String fullName, String complexity,
                        String socialSecurityNumber) {
        super(age, weight, bloodType, "male",fullName, complexity, socialSecurityNumber);
    }
}
