package com.bjtu.canteensimulation.model.entity;

import java.util.List;

public class Cafeteria {
    private final String name;
    private final List<ServiceWindow> windows;
    private final SeatPool seatPool;

    public Cafeteria(String name, List<ServiceWindow> windows, SeatPool seatPool) {
        this.name = name;
        this.windows = windows;
        this.seatPool = seatPool;
    }

    // Getter
    public String getName() {
        return name;
    }

    public List<ServiceWindow> getWindows() {
        return windows;
    }

    public SeatPool getSeatPool() {
        return seatPool;
    }
}


