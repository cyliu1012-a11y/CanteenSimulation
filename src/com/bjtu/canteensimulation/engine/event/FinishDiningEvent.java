package com.bjtu.canteensimulation.engine.event;

import com.bjtu.canteensimulation.engine.core.SimulationEvent;
import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.entity.SeatPool;
import com.bjtu.canteensimulation.model.enums.StudentState;

public class FinishDiningEvent extends SimulationEvent {
    private final Student student;
    private final SeatPool seatPool;

    public FinishDiningEvent(double time, Student student, SeatPool seatPool) {
        super(time);
        this.student = student;
        this.seatPool = seatPool;
    }

    @Override
    public void process() {
        student.setState(StudentState.LEFT);
        seatPool.releaseSeat();
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
