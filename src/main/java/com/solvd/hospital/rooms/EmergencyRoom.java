package com.solvd.hospital.rooms;

public class EmergencyRoom extends HospitalRoom {
    public EmergencyRoom(String location) {
        super(location);
    }

    @Override
    public String toString() {
        return "Emergency room " + location;
    }
}
