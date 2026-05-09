package com.bjtu.canteensimulation.engine.event;

import com.bjtu.canteensimulation.engine.core.SimulationEvent;
import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.entity.ServiceWindow;
import com.bjtu.canteensimulation.model.behaviour.StudentChoiceStrategy;
import com.bjtu.canteensimulation.model.enums.StudentState;
import java.util.List;

public class StudentArrivalEvent extends SimulationEvent {
    // 1. 声明所有成员变量
    private final Student student;
    private final List<ServiceWindow> windows;
    private final StudentChoiceStrategy strategy;

    // 2. 构造函数：给变量赋值，并调用父类构造
    public StudentArrivalEvent(double time, Student student, List<ServiceWindow> windows, StudentChoiceStrategy strategy) {
        super(time);
        this.student = student;
        this.windows = windows;
        this.strategy = strategy;
    }

    // 3. 实现process()方法
    @Override
    public void process() {
        student.setState(StudentState.QUEUE_WAITING);
        ServiceWindow selected = strategy.chooseWindow(student, windows);
        student.setSelectedWindow(selected);
        selected.addToQueue(student);
    }

    // 4. 实现getTime()方法（可选，父类已有，但加上更保险）
    @Override
    public double getTime() {
        return super.getTime();
    }

    // 5. 给每个变量写public的getter方法（这是你现在最缺的！）
    public Student getStudent() {
        return student;
    }

    public List<ServiceWindow> getWindows() {
        return windows;
    }

    public StudentChoiceStrategy getStrategy() {
        return strategy;
    }

}