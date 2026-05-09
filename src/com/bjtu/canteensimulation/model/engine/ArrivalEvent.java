package com.bjtu.canteensimulation.model.engine;

import com.bjtu.canteensimulation.model.entity.Student;

public class ArrivalEvent extends SimulationEvent {

    public ArrivalEvent(long time) {
        super(time);
    }

    @Override
    public void process(SimulationEngine engine) {

        Student student = new Student(time);
        engine.addStudent(student);

        long nextTime = time + engine.generateArrivalInterval();
        engine.scheduleEvent(new ArrivalEvent(nextTime));
    }
}
