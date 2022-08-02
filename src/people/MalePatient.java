package people;

public class MalePatient extends Patient {
    public MalePatient (int age, String fullName, String complexity,
                        String socialSecurityNumber) {
        super(age, "male",fullName, complexity, socialSecurityNumber);
    }
}
