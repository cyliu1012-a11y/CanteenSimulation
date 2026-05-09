package com.bjtu.canteensimulation.model.entity;

import java.util.concurrent.LinkedBlockingQueue;

public class SeatPool
{
    private final int totalSeats;
    private int usedSeats;
    private final LinkedBlockingQueue<Student> waitingSeatQueue;

    public SeatPool(int totalSeats) {
        this.totalSeats = totalSeats;
        this.usedSeats = 0;
        this.waitingSeatQueue = new LinkedBlockingQueue<>();
    }

    public boolean takeSeat(Student student) {
        if (usedSeats < totalSeats) {
            usedSeats++;
            return true;
        }
        waitingSeatQueue.offer(student);
        return false;
    }

    public void releaseSeat() {
        usedSeats--;
        if (usedSeats < 0) usedSeats = 0;
    }

    public Student pollWaitingStudent() {
        return waitingSeatQueue.poll();
    }

    // Getter
    public int getTotalSeats() { return totalSeats; }
    public int getUsedSeats() { return usedSeats; }
    public int getWaitingSeatCount() { return waitingSeatQueue.size(); }

}
