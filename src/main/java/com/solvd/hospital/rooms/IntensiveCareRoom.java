package com.solvd.hospital.rooms;

public class IntensiveCareRoom extends HospitalRoom {
    public IntensiveCareRoom(String location) {
        super(location);
    }

    @Override
    public String toString() {
        return "Intensive care room " + location;
    }
}
