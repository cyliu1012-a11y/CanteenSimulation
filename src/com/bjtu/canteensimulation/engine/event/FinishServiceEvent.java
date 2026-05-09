package com.bjtu.canteensimulation.engine.event;

import com.bjtu.canteensimulation.engine.core.SimulationEvent;
import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.entity.ServiceWindow;
import com.bjtu.canteensimulation.model.enums.StudentState;

// 1. 必须写：extends SimulationEvent
public class FinishServiceEvent extends SimulationEvent {

    // 2. 必须声明这两个成员变量，不然student和window都不存在
    private final Student student;
    private final ServiceWindow window;

    // 3. 必须写构造方法，给变量赋值，同时调用super(time)
    public FinishServiceEvent(double time, ServiceWindow window, Student student) {
        super(time);
        this.window = window;
        this.student = student;
    }

    // 4. 必须实现process()方法
    @Override
    public void process() {
        student.setState(StudentState.FIND_SEAT);
        window.finishService(super.getTime() - student.getServiceStartTime());
    }

    // 5. 可以写getTime()（也可以不写，父类已经有了）
    @Override
    public double getTime() {
        return super.getTime();
    }

    // 6. 必须写getter方法，引擎里会调用
    public Student getStudent() {
        return student;
    }

    public ServiceWindow getWindow() {
        return window;
    }

}
