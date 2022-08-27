package com.solvd.hospital.people;

public enum BloodType {
    O_NEGATIVE("O_NEGATIVE"),
    O_POSITIVE("O_NEGATIVE, O_POSITIVE"),
    A_NEGATIVE("O_NEGATIVE, A_NEGATIVE"),
    A_POSITIVE("O_NEGATIVE, O_POSITIVE, A_NEGATIVE, A_POSITIVE"),
    B_NEGATIVE("O_NEGATIVE, B_NEGATIVE"),
    B_POSITIVE("O_NEGATIVE ,O_POSITIVE, B_NEGATIVE, B_POSITIVE"),
    AB_NEGATIVE("O_NEGATIVE, A_NEGATIVE, B_NEGATIVE, AB_NEGATIVE"),
    AB_POSITIVE("O_NEGATIVE, O_POSITIVE, A_NEGATIVE, A_POSITIVE, B_NEGATIVE, B_POSITIVE, AB_NEGATIVE," +
            "AB_POSITIVE");
    private String canReceiveFrom;

    BloodType(String canReceiveFrom) {
        this.canReceiveFrom = canReceiveFrom;
    }

    //Getters
    public String getCanReceiveFrom() {
        return canReceiveFrom;
    }
}
