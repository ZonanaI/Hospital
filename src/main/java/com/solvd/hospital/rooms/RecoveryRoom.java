package com.solvd.hospital.rooms;

public class RecoveryRoom extends HospitalRoom {
    public RecoveryRoom(String location) {
        super(location);
    }

    @Override
    public String toString() {
        return "Recovery room " + location;
    }
}
