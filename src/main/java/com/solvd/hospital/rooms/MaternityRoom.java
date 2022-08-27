package com.solvd.hospital.rooms;

public class MaternityRoom extends HospitalRoom {
    public MaternityRoom(String location) {
        super(location);
    }

    @Override
    public String toString() {
        return "Maternity room " + location;
    }
}
