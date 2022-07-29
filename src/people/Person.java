package people;

public abstract class Person {
    protected int age;
    protected double weight;
    protected String bloodType;
    protected String gender;
    protected  String fullName;
    protected String socialSecurityNumber;
    protected boolean isAlive;

    protected double[] bloodPressure;

    public Person (int age, double weight, String bloodType, String gender, String fullName,
                   String socialSecurityNumber) {
        this.age = age;
        this.weight = weight;
        this.bloodType = bloodType;
        this.gender = gender;
        this.isAlive = true;
        this.fullName = fullName;
        this.socialSecurityNumber = socialSecurityNumber;
    }
    public Person (int age, String gender, String fullName, String socialSecurityNumber) {
        this.age = age;
        this.gender = gender;
        this.fullName = fullName;
        this.socialSecurityNumber = socialSecurityNumber;
    }
    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public void setIsAlive(boolean alive) {
        this.isAlive = alive;
    }
}

