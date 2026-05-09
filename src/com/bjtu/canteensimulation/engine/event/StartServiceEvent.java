package com.bjtu.canteensimulation.engine.event;

import com.bjtu.canteensimulation.engine.core.SimulationEvent;
import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.entity.ServiceWindow;
import com.bjtu.canteensimulation.model.enums.StudentState;

public class StartServiceEvent extends SimulationEvent {
    private final ServiceWindow window;
    private final Student student;

    public StartServiceEvent(double time, ServiceWindow window, Student student) {
        super(time); // 调用父类构造函数初始化time
        this.window = window;
        this.student = student;
    }

    @Override
    public void process() {
        student.setState(StudentState.SERVING);
        window.startService();
        student.setServiceStartTime(super.getTime());
    }

    @Override
    public double getTime() {
        return super.getTime();
    }
    public Student getStudent() {
        return student;
    }

    public ServiceWindow getWindow() {
        return window;
    }

}