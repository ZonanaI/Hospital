package rooms;

import people.Patient;

import java.util.Set;

public abstract class HospitalRoom {
    protected int patientsCount;
    protected int deceasesCount;
    protected int recoveriesCount;
    protected Set <Patient> patientsSet;
    public HospitalRoom (int patientCount){
        this.patientsCount = patientCount;
    }

    public int getPatientsCount() {
        return patientsCount;
    }

    public void setPatientsCount(int patientsCount) {
        this.patientsCount = patientsCount;
    }

    public void admitNewPatients (int toAddPatients){
        patientsCount += toAddPatients;
    }

    public int getDeceasesCount(){
        return deceasesCount;
    }


    public void confirmDeceases(int toAddDeceases) {
        deceasesCount += toAddDeceases;
        patientsCount -= toAddDeceases;
    }

    public void confirmRecoveries(int toAddRecoveries) {
        recoveriesCount += toAddRecoveries;
        patientsCount -= toAddRecoveries;
    }
}
