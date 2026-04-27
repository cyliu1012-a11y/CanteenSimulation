package com.canteen.engine;

import com.canteen.infrastructure.SimulationException;

import java.util.PriorityQueue;

public class EventQueue {

    private final PriorityQueue<Event> queue;

    private static final int MAX_CAPACITY=100000;

    public EventQueue(){
        this.queue=new PriorityQueue<>();
    }

    public void addEvent(Event event){
        if (event==null){
            throw new IllegalArgumentException("事件不能为null");
        }
        if (event.getTime()<0){
            throw new SimulationException("事件不能为负数："+event.getTime());
        }
        if (queue.size()>=MAX_CAPACITY) {
            throw new SimulationException("事件队列已满，最大容量：" + MAX_CAPACITY);
        }
        queue.offer(event);
    }

    public Event getNextEvent(){
        return queue.poll();
    }

    public Event peekNextEvent(){
        return queue.peek();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public int size(){
        return queue.size();
    }

    public void clear(){
        queue.clear();
    }

    public int getMaxCapacity(){
        return MAX_CAPACITY;
    }
}
