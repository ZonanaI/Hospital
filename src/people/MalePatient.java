package people;

public final class MalePatient extends Patient {


    public MalePatient (int age, String fullName, String ID, String complexity, String bloodType) {
        super(age, "male",fullName, ID, complexity, bloodType);
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

        MalePatient malePatient = (MalePatient) obj;
        return (malePatient.ID.equals(this.ID));
    }

    @Override
    public String toString() {
        return "Male patient: " + fullName + ", ID: " + ID;
    }
}
