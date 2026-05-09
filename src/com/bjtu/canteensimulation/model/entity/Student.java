package com.bjtu.canteensimulation.model.entity;

import com.bjtu.canteensimulation.model.Preference;
import com.bjtu.canteensimulation.model.enums.StudentState;

public class Student
{
    private final long id;
    private StudentState state;
    private final double arriveTime;
    private double queueStartTime;
    private double waitingTime;
    private double serviceStartTime;
    private double serviceDuration;
    private final Preference preference;
    private ServiceWindow selectedWindow;

    public Student(long id, double arriveTime, Preference preference) {
        this.id = id;
        this.arriveTime = arriveTime;
        this.preference = preference;
        this.state = StudentState.ARRIVED;}
    public long getId() { return id; }
    public StudentState getState() { return state; }
    public void setState(StudentState state) { this.state = state; }
    public double getArriveTime() { return arriveTime; }
    public double getQueueStartTime() { return queueStartTime; }
    public void setQueueStartTime(double queueStartTime) { this.queueStartTime = queueStartTime; }
    public double getWaitingTime() { return waitingTime; }
    public void setWaitingTime(double waitingTime) { this.waitingTime = waitingTime; }
    public double getServiceStartTime() { return serviceStartTime; }
    public void setServiceStartTime(double serviceStartTime) { this.serviceStartTime = serviceStartTime; }
    public double getServiceDuration() { return serviceDuration; }
    public void setServiceDuration(double serviceDuration) { this.serviceDuration = serviceDuration; }
    public Preference getPreference() { return preference; }
    public ServiceWindow getSelectedWindow() { return selectedWindow; }
    public void setSelectedWindow(ServiceWindow selectedWindow) { this.selectedWindow = selectedWindow; }

}
