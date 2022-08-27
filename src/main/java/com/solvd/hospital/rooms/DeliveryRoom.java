package com.solvd.hospital.rooms;

public class DeliveryRoom extends HospitalRoom {
    public DeliveryRoom(String location) {
        super(location);
    }

    @Override
    public String toString() {
        return "Delivery room " + location;
    }
}
