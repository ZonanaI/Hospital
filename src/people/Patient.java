package people;

public abstract class Patient extends Person{
    protected String complexity;
    public Patient (int age, double weight, String bloodType, String gender, String fullName, String complexity,
                    String socialSecurityNumber) {
        super(age, weight, bloodType, gender, fullName, socialSecurityNumber);
        this.complexity = complexity;
    }
}
