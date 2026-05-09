package com.bjtu.canteensimulation.model.engine;

import com.bjtu.canteensimulation.config.SimulationConfig;
import com.bjtu.canteensimulation.model.entity.CanteenWindow;
import com.bjtu.canteensimulation.model.entity.Seat;
import com.bjtu.canteensimulation.model.entity.Student;

import java.util.*;
import java.util.concurrent.*;

public class SimulationEngine {

    // 事件队列
    private final PriorityQueue<SimulationEvent> eventQueue = new PriorityQueue<>();

    // 窗口列表
    private final List<CanteenWindow> windows = new ArrayList<>();

    // 座位列表 - 只定义一次
    private final List<Seat> seats = new ArrayList<>();

    // 当前时间
    private long currentTime = 0;

    // 是否运行
    private volatile boolean running = false;

    // 线程池
    private ScheduledExecutorService executor;

    private final Random random = new Random();

    // 配置
    private SimulationConfig config;

    // 统计数据
    private int studentsServed = 0;
    private int studentsDining = 0;

    // 构造函数
    public SimulationEngine(SimulationConfig config) {
        this.config = config;

        int windowCount = config.getWindowCount();
        for (int i = 0; i < windowCount; i++) {
            windows.add(new CanteenWindow(i));
        }

        int seatCount = config.getSeatCount();
        for (int i = 0; i < seatCount; i++) {
            seats.add(new Seat(i));
        }
    }

    // 启动
    public void start() {
        if (running) return;
        running = true;
        executor = Executors.newSingleThreadScheduledExecutor();
        scheduleEvent(new ArrivalEvent(0));
        executor.scheduleAtFixedRate(this::step, 0, 50, TimeUnit.MILLISECONDS);
    }

    // 停止
    public void stop() {
        running = false;
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

    // 重置
    public void reset() {
        stop();
        eventQueue.clear();
        currentTime = 0;
        studentsServed = 0;
        studentsDining = 0;

        for (CanteenWindow window : windows) {
            window.getQueue().clear();
            window.setIdle(true);
        }

        for (Seat seat : seats) {
            seat.setOccupied(false);
        }
    }

    // 单步推进
    private void step() {
        if (!running || eventQueue.isEmpty()) return;
        SimulationEvent event = eventQueue.poll();
        currentTime = event.getTime();
        event.process(this);
        assignStudentsToWindows();
    }

    // 分配学生到窗口
    private void assignStudentsToWindows() {
        for (CanteenWindow window : windows) {
            if (window.isIdle() && !window.getQueue().isEmpty()) {
                window.setIdle(false);
                scheduleEvent(new ServiceEvent(currentTime, window));
            }
        }
    }

    // 新学生进入系统
    public void addStudent(Student student) {
        student.setState("QUEUING");
        CanteenWindow best = windows.stream()
                .min(Comparator.comparingInt(w -> w.getQueue().size()))
                .orElseThrow();
        best.addStudent(student);
    }

    // 分配座位
    public boolean assignSeat(Student student) {
        for (Seat seat : seats) {
            if (!seat.isOccupied()) {
                seat.setOccupied(true);
                studentsDining++;
                System.out.println("学生" + student.getId() + " 占用座位");
                return true;
            }
        }
        return false;
    }

    // 释放座位
    public void releaseSeat(Student student) {
        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                seat.setOccupied(false);
                studentsDining--;
                System.out.println("学生" + student.getId() + " 释放座位");
                break;
            }
        }
    }

    // 添加事件
    public void scheduleEvent(SimulationEvent event) {
        eventQueue.add(event);
    }

    // UI接口
    public int getSimulationTime() {
        return (int) currentTime;
    }

    public int getStudentsInQueue() {
        return windows.stream()
                .mapToInt(w -> w.getQueue().size())
                .sum();
    }

    public int getStudentsDining() {
        return studentsDining;
    }

    public int getStudentsServed() {
        return studentsServed;
    }

    public List<CanteenWindow> getWindows() {
        return windows;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public boolean isRunning() {
        return running;
    }

    // 随机分布
    public long generateArrivalInterval() {
        double lambda = config.getArrivalRate();
        if (lambda <= 0) lambda = 0.1;
        return (long) (-Math.log(1 - random.nextDouble()) / lambda);
    }

    public long generateServiceTime() {
        double lambda = config.getServiceRate();
        if (lambda <= 0) lambda = 0.2;
        return (long) (-Math.log(1 - random.nextDouble()) / lambda);
    }

    // 学生完成就餐
    public void studentFinished() {
        studentsServed++;
    }
}