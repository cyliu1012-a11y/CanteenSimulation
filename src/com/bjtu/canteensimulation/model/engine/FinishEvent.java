package com.bjtu.canteensimulation.model.engine;

import com.bjtu.canteensimulation.model.entity.CanteenWindow;
import com.bjtu.canteensimulation.model.entity.Student;

public class FinishEvent extends SimulationEvent {

    private final Student student;
    private final CanteenWindow window;

    public FinishEvent(long time, Student student, CanteenWindow window) {
        super(time);
        this.student = student;
        this.window = window;
    }

    @Override
    public void process(SimulationEngine engine) {
        student.setState("EATING");

        // ✅ 释放座位
        engine.releaseSeat(student);

        // 通知引擎学生完成就餐
        engine.studentFinished();

        window.setIdle(true);

        // 尝试为窗口分配下一个学生
        engine.scheduleEvent(new ServiceEvent(time, window));
    }
}