package com.bjtu.canteensimulation.model.entity;

import java.util.LinkedList;
import java.util.Queue;

public class ServiceWindow
{
    private final long id;
    private final String type;
    private boolean busy;
    private final Queue<Student> queue;
    private final double serviceTimeMean;
    private final double serviceTimeStd;
    private int servedCount;
    private double totalBusyTime;

    public ServiceWindow(long id, String type, double serviceTimeMean, double serviceTimeStd) {
        this.id = id;
        this.type = type;
        this.serviceTimeMean = serviceTimeMean;
        this.serviceTimeStd = serviceTimeStd;
        this.queue = new LinkedList<>();
        this.busy = false;
        this.servedCount = 0;
        this.totalBusyTime = 0;
    }

    public void addToQueue(Student student) {
        queue.offer(student);
    }

    public Student pollNextStudent() {
        return queue.poll();
    }

    public void startService() {
        this.busy = true;
    }

    public void finishService(double duration) {
        this.busy = false;
        this.servedCount++;
        this.totalBusyTime += duration;
    }

    // Getter
    public long getId() { return id; }
    public String getType() { return type; }
    public boolean isBusy() { return busy; }
    public int getQueueSize() { return queue.size(); }
    public double getServiceTimeMean() { return serviceTimeMean; }
    public double getServiceTimeStd() { return serviceTimeStd; }
    public int getServedCount() { return servedCount; }
    public double getTotalBusyTime() { return totalBusyTime; }

}
