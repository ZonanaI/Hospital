package com.solvd.hospital.rooms;

public class ConsultingRoom extends HospitalRoom {
    public ConsultingRoom(String location) {
        super(location);
    }

    @Override
    public String toString() {
        return "Consulting room " + location;
    }
}
