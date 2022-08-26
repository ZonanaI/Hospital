package com.solvd.hospital.rooms;

public enum DefaultRooms {
    CONSULTING_1A("c", "1-a"),
    DELIVERY_2A("d", "2-a"),
    INTENSIVE_CARE_2C("i", "2-c"),
    MATERNITY_2B("m", "2-b"),
    NURSERY_2B("n", "2-b"),
    RECOVERY_2B("r", "2-b"),
    SURGERY_2A("s", "2-a"),
    ;
    private String type;
    private String location;

    DefaultRooms(String type, String location) {
        this.type = type;
        this.location = location;
    }

    //Getters
    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }
}
