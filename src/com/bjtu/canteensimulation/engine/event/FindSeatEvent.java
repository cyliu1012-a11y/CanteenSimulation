package com.bjtu.canteensimulation.engine.event;

import com.bjtu.canteensimulation.engine.core.SimulationEvent;
import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.entity.SeatPool;
import com.bjtu.canteensimulation.model.behaviour.SeatFindingStrategy;
import com.bjtu.canteensimulation.model.enums.StudentState;

public class FindSeatEvent extends SimulationEvent {
    private final Student student;
    private final SeatPool seatPool;
    private final SeatFindingStrategy strategy;

    public FindSeatEvent(double time, Student student, SeatPool seatPool, SeatFindingStrategy strategy) {
        super(time);
        this.student = student;
        this.seatPool = seatPool;
        this.strategy = strategy;
    }

    @Override
    public void process() {
        boolean success = strategy.findSeat(student, seatPool);
        if (success) {
            student.setState(StudentState.DINING);
        }
    }

    @Override
    public double getTime() {
        return super.getTime();
    }

    public Student getStudent() {
        return student;
    }

    public SeatPool getSeatPool() {
        return seatPool;
    }

}
