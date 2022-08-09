package people;

public abstract class Employee extends Person{

    protected int workedHours;
    protected double payRate;


    public Employee (int age, String gender, String fullName, String ID) {
        super(age, gender, fullName, ID);
        this.workedHours = 0;
    }

    public int getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(int workedHours) {
        this.workedHours = workedHours;
    }

    public double getPayRate() {
        return payRate;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public abstract double getPayCheck();
}
