package com.solvd.hospital.rooms;

public class SurgeryRoom extends HospitalRoom {
    public SurgeryRoom(String location) {
        super(location);
    }

    @Override
    public String toString() {
        return "Surgery room " + location;
    }
}
