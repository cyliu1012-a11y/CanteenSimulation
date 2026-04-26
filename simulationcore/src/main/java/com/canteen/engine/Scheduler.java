package com.canteen.engine;

import com.canteen.infrastructure.SimulationException;

/**
 * 事件调度器
 * 负责事件的调度、添加和移除
 */
public class Scheduler {
    /** 事件队列 */
    private final EventQueue eventQueue;
    /** 仿真时钟引用 */
    private final SimulationClock clock;
    /**
     * 构造函数
     * @param clock 仿真时钟
     */
    public Scheduler(SimulationClock clock){
        this.eventQueue=new EventQueue();
        this.clock=clock;
    }
    /**
     * 调度事件
     * @param event 要调度的事件
     * @throws SimulationException 如果事件时间小于当前时间
     */
    public void schedule(Event event){
        if(event.getTime()<clock.getCurrentTime()){
            throw new SimulationException(String.format("无法调度过去的事件：事件时间=%.3f,当前时间=%.3f",event.getTime(),clock.getCurrentTime()));
        }
        eventQueue.addEvent(event);
    }
    /**
     * 在指定延迟后调度事件
     * @param event 事件对象
     * @param delay 延迟时间
     */
    public void scheduleDelayed(Event event,double delay){
        if (delay<0){
            throw new IllegalArgumentException("延迟时间不能为负数："+delay);
        }
        double scheduledTime=clock.getCurrentTime()+delay;
        //修改原事件时间
        event.setTime(scheduledTime);
        schedule(event);
    }
    /**
     * 获取下一个待处理事件
     * @return 下一个事件
     */
    public Event getNextEvent(){
        return eventQueue.getNextEvent();
    }
    /**
     *查看下一个待处理事件（不移除）
     * @return 下一个事件
     */
    public Event peekNextEvent(){
        return eventQueue.peekNextEvent();
    }
    /**
     * 检查是否有待处理事件
     * @return true表示有事件
     */
    public boolean hasNextEvent(){
        return !eventQueue.isEmpty();
    }
    /**
     * 获取待处理事件数量
     * @return 事件数量
     */
    public int getPendingEventCount(){
        return eventQueue.size();
    }
    /**
     * 清空所有待处理事件
     */
    public void clear(){
        eventQueue.clear();
    }
    /**
     * 获取事件队列（用于调试）
     * @return 事件队列
     */
    public EventQueue getEventQueue(){
        return eventQueue;
    }

}
