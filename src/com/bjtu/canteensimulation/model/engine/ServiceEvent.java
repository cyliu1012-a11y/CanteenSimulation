package com.bjtu.canteensimulation.model.engine;

import com.bjtu.canteensimulation.model.entity.CanteenWindow;
import com.bjtu.canteensimulation.model.entity.Student;

public class ServiceEvent extends SimulationEvent {
    private final CanteenWindow window;

    public ServiceEvent(long time, CanteenWindow window) {
        super(time);
        this.window = window;
    }

    @Override
    public void process(SimulationEngine engine) {
        Student student = window.poll();

        if (student == null) {
            window.setIdle(true);
            return;
        }

        student.setState("ORDERING");

        // ✅ 在开始服务时分配座位
        boolean hasSeat = engine.assignSeat(student);
        if (!hasSeat) {
            // 如果没有座位，学生等待（可以放回队列，简化处理）
            student.setState("WAITING_SEAT");
            // 5秒后重试
            engine.scheduleEvent(new ServiceEvent(time + 5, window));
            window.addStudent(student);  // 放回队列
            return;
        }

        long serviceTime = engine.generateServiceTime();
        engine.scheduleEvent(new FinishEvent(time + serviceTime, student, window));
    }
}