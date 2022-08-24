package com.solvd.hospital.rooms;

public class NurseryRoom extends HospitalRoom {

    public NurseryRoom(String location) {
        super(location);
    }

    @Override
    public String toString() {
        return "Nursery room " + location;
    }
}
