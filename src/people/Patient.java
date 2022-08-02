package people;

public abstract class Patient extends Person{
    protected String complexity;

    public Patient (int age, String gender, String fullName, String complexity,
                    String socialSecurityNumber) {
        super(age, gender, fullName, socialSecurityNumber);
        this.complexity = complexity;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

}
