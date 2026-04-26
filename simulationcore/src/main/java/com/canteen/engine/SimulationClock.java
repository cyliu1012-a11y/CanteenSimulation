package com.canteen.engine;

import com.canteen.infrastructure.SimulationException;

/**
 * 仿真时钟
 * 管理仿真时间，提供时间推进和记录功能
 */
public class SimulationClock {
    /** 当前仿真时间 */
    private double currentTime;
    /** 仿真开始时间 */
    private double startTime;
    /** 仿真结束时间 */
    private double endTime;
    /** 时间缩放因子（用于加速仿真，默认为1） */
    private double timeScale;
    /**
     * 构造函数
     */
    public SimulationClock(){
        this.currentTime=0.0;
        this.startTime=0.0;
        this.endTime=Double.MAX_VALUE;
        this.timeScale=1.0;
    }
    /**
     * 带参数的构造函数
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public SimulationClock(double startTime,double endTime){
        this.currentTime=startTime;
        this.startTime=startTime;
        this.endTime=endTime;
        this.timeScale=1.0;
    }
    /**
     * 获取当前仿真时间
     * @return 当前时间
     */
    public double getCurrentTime(){
        return currentTime;
    }
    /**
     * 设置当前仿真时间
     * @param time 新时间
     * @throws SimulationException 如果时间倒退
     */
    public void setCurrentTime(double time){
        if (time<currentTime){
            throw new SimulationException("仿真时间不能倒退："+time+"<"+currentTime);
        }
        this.currentTime=time;
    }
    /**
     * 推进仿真时间
     * @param delta 时间增量
     * @throws SimulationException 如果增量无效
     */
    public void advance(double delta){
        if(delta<0){
            throw new SimulationException("时间增量不能为负数："+delta);
        }
        this.currentTime+=delta;
    }
    /**
     * 获取仿真结束时间
     * @return 结束时间
     */
    public double getEndTime(){
        return endTime;
    }
    /**
     * 设置仿真结束时间
     * @param endTime 结束时间
     */
    public void setEndTime(double endTime){
        this.endTime=endTime;
    }
    /**
     * 获取时间缩放因子
     * @return 缩放因子
     */
    public double getTimeScale(){
        return timeScale;
    }
    /**
     * 设置时间缩放因子
     * @param timeScale 缩放因子（必须大于0）
     */
    public void setTimeScale(double timeScale){
        if (timeScale<=0){
            throw new IllegalArgumentException("时间缩放因子必须大于0");
        }
        this.timeScale=timeScale;
    }
    /**
     * 检查仿真是否结束
     * @return true表示已结束
     */
    public boolean isFinished(){
        return currentTime>=endTime;
    }
    /**
     * 重置仿真时钟
     */
    public void reset(){
        this.currentTime=startTime;
    }
    /**
     * 完全重置（包括开始和结束时间）
     * @param newStartTime 新开始时间
     * @param newEndTime 新结束时间
     */
    public void reset(double newStartTime,double newEndTime){
        this.startTime=newStartTime;
        this.endTime=newEndTime;
        this.currentTime=newStartTime;
    }
    @Override
    public String toString(){
        return String.format("SimulationClock{current=%.3f,start=%.3f,end=%.3f}",currentTime,startTime,endTime);
    }
}
