package com.bjtu.canteensimulation.engine.core;

import java.util.PriorityQueue;

public class EventQueue {
    // 改成存 SimulationEvent
    private final PriorityQueue<SimulationEvent> queue;

    public EventQueue() {
        queue = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getTime(), e2.getTime()));
    }

    public void addEvent(SimulationEvent event) {
        queue.offer(event);
    }

    public SimulationEvent getNextEvent() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

}