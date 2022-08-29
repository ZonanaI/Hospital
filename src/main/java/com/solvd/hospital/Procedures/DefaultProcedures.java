package com.solvd.hospital.Procedures;

import java.time.LocalDateTime;

public enum DefaultProcedures {

    DIAGNOSTIC_33581170("diagnostic", "33581170", "33581569",
            LocalDateTime.parse("2022-08-24T12:20:00")),
    SURGERY_10181213("surgery", "10181213", "10181213",
            LocalDateTime.parse("2022-08-25T10:11:30")),
    ANESTHESIA_10181213("anesthesia", "10181213", "19226739",
            LocalDateTime.parse("2022-08-25T10:11:00")),
    SURGERY_09184353("surgery", "09184353", "10181213",
            LocalDateTime.parse("2022-08-25T10:11:30")),
    ANESTHESIA_09184353("anesthesia", "09184353", "19226739",
            LocalDateTime.parse("2022-08-25T10:11:00")),
    DIAGNOSTIC_09184353("diagnostic", "09184353", "24647160",
            LocalDateTime.parse("2022-08-25T10:11:00")),
    DIAGNOSTIC_14981432("diagnostic", "14981432", "24647160",
            LocalDateTime.parse("2022-08-19T10:11:00")),
    DIAGNOSTIC_12312654("diagnostic", "12312654", "24647160",
            LocalDateTime.parse("2022-08-19T10:11:00")),
    BIRTH_39341594("birth", "39341594", "10225739",
            LocalDateTime.parse("2022-08-22T13:15:00")),
    DIAGNOSTIC_39341594("diagnostic", "39341594", "24647160",
            LocalDateTime.parse("2022-08-19T10:11:00")),

    ;
    private final String type;
    private final String patientID;
    private final String physicianID;
    private final LocalDateTime localDateTime;

    DefaultProcedures(String type, String patientID, String physicianID, LocalDateTime localDateTime) {
        this.type = type;
        this.patientID = patientID;
        this.physicianID = physicianID;
        this.localDateTime = localDateTime;
    }

    //Getters

    public String getType() {
        return type;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getPhysicianID() {
        return physicianID;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
