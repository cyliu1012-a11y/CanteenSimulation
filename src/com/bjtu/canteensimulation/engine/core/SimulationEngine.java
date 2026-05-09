package com.bjtu.canteensimulation.engine.core;

import com.bjtu.canteensimulation.engine.event.*;
import com.bjtu.canteensimulation.model.behaviour.StudentChoiceStrategy;
import com.bjtu.canteensimulation.model.entity.Cafeteria;
import com.bjtu.canteensimulation.model.entity.SeatPool;
import com.bjtu.canteensimulation.model.entity.ServiceWindow;
import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.enums.StudentState;
import com.bjtu.canteensimulation.model.Preference;
import com.bjtu.canteensimulation.util.DecisionUtil;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimulationEngine {
    private final EventQueue eventQueue;
    private final Cafeteria cafeteria;
    private final StudentChoiceStrategy choiceStrategy;
    private final Random random = new Random();
    private double currentTime = 0;

    public SimulationEngine(EventQueue eventQueue, Cafeteria cafeteria, StudentChoiceStrategy choiceStrategy) {
        this.eventQueue = eventQueue;
        this.cafeteria = cafeteria;
        this.choiceStrategy = choiceStrategy;
    }

    public void startSimulation(int studentCount) {
        // 初始化：生成学生到达事件
        for (int i = 0; i < studentCount; i++) {
            double arriveTime = random.nextDouble() * 100;
            Preference preference = new Preference(
                    Map.of("米饭套餐", 0.8, "面食", 0.7),
                    0.5, 0.3
            );
            Student student = new Student(i, arriveTime, preference);
            eventQueue.addEvent(new StudentArrivalEvent(arriveTime, student, cafeteria.getWindows(), choiceStrategy));
        }

        System.out.println("=== 食堂仿真开始 ===");
        System.out.println("仿真总人数：" + studentCount);

        // 主事件循环
        while (!eventQueue.isEmpty()) {
            SimulationEvent event = eventQueue.getNextEvent();
            currentTime = event.getTime();

            if (event instanceof StudentArrivalEvent arrivalEvent) {
                arrivalEvent.process();
                Student student = arrivalEvent.getStudent();
                ServiceWindow selected = student.getSelectedWindow();
                System.out.printf("[%.2f 分钟] 学生 %d 到达，选择了 %s 窗口，排队人数：%d\n",
                        currentTime, student.getId(), selected.getType(), selected.getQueueSize());
                scheduleNextService(student);
            } else if (event instanceof StartServiceEvent serviceEvent) {
                serviceEvent.process();
                Student student = serviceEvent.getStudent();
                ServiceWindow window = serviceEvent.getWindow();
                System.out.printf("[%.2f 分钟] 学生 %d 开始在 %s 窗口就餐\n",
                        currentTime, student.getId(), window.getType());
                double serviceTime = DecisionUtil.generateServiceTime(3, 0.5);
                double finishTime = currentTime + serviceTime;
                eventQueue.addEvent(new FinishServiceEvent(finishTime, window, student));
            } else if (event instanceof FinishServiceEvent finishEvent) {
                finishEvent.process();
                Student student = finishEvent.getStudent();
                ServiceWindow window = finishEvent.getWindow();
                System.out.printf("[%.2f 分钟] 学生 %d 结束打餐，前往找座位\n",
                        currentTime, student.getId());
                eventQueue.addEvent(new FindSeatEvent(currentTime, student, cafeteria.getSeatPool(), this::findSeat));
                scheduleNextService(window);
            } else if (event instanceof FindSeatEvent seatEvent) {
                seatEvent.process();
                Student student = seatEvent.getStudent();
                SeatPool seatPool = cafeteria.getSeatPool();
                if (student.getState() == StudentState.DINING) {
                    System.out.printf("[%.2f 分钟] 学生 %d 找到了座位，开始就餐\n",
                            currentTime, student.getId());
                    double diningTime = DecisionUtil.generateDiningTime();
                    eventQueue.addEvent(new FinishDiningEvent(currentTime + diningTime, student, seatPool));
                } else {
                    System.out.printf("[%.2f 分钟] 学生 %d 没找到座位，离开食堂\n",
                            currentTime, student.getId());
                }
            } else if (event instanceof FinishDiningEvent diningEvent) {
                diningEvent.process();
                Student student = diningEvent.getStudent();
                System.out.printf("[%.2f 分钟] 学生 %d 就餐结束，离开食堂\n",
                        currentTime, student.getId());
            }
        }

        System.out.println("=== 仿真结束 ===");
        int totalServed = cafeteria.getWindows().stream().mapToInt(ServiceWindow::getServedCount).sum();
        System.out.println("总服务人数：" + totalServed);
        System.out.println("窗口利用率：");
        for (ServiceWindow w : cafeteria.getWindows()) {
            double usageRate = w.getTotalBusyTime() / currentTime * 100;
            System.out.printf("  %s 窗口：%.2f%%\n", w.getType(), usageRate);
        }
    }

    private void scheduleNextService(ServiceWindow window) {
        Student next = window.pollNextStudent();
        if (next != null) {
            eventQueue.addEvent(new StartServiceEvent(currentTime, window, next));
        }
    }

    private void scheduleNextService(Student student) {
        ServiceWindow window = student.getSelectedWindow();
        if (!window.isBusy()) {
            eventQueue.addEvent(new StartServiceEvent(currentTime, window, student));
        }
    }

    private boolean findSeat(Student student, SeatPool seatPool) {
        return seatPool.takeSeat(student);
    }

}