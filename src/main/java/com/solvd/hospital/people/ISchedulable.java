package com.solvd.hospital.people;

import java.time.LocalDateTime;

public interface ISchedulable<T> {
    void scheduleAppointment(LocalDateTime localDateTime, T o);
}
