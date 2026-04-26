package com.bjtu.canteensimulation.model.entity;

public class Student {

    private static int counter = 0;

    private final int id;
    private final long arrivalTime;

    private String state; // ⚠️ 用字符串兼容旧UI

    public Student(long arrivalTime) {
        this.id = counter++;
        this.arrivalTime = arrivalTime;
        this.state = "ARRIVING";
    }

    public int getId() {
        return id;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}