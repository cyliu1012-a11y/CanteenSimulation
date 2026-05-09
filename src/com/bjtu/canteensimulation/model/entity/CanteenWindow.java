package com.bjtu.canteensimulation.model.entity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CanteenWindow {

    private final int id;

    private final BlockingQueue<Student> queue = new LinkedBlockingQueue<>();

    private volatile boolean idle = true;

    public CanteenWindow(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addStudent(Student student) {
        queue.offer(student);
    }

    public Student poll() {
        return queue.poll();
    }

    public BlockingQueue<Student> getQueue() {
        return queue;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;   // ✅ 修复
    }
}