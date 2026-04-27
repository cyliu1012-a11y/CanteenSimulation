package com.canteen.engine;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Event implements Comparable<Event> {

    protected double time;

    protected long sequence;

    private static final AtomicLong SEQUENCE_GENERATOR=new AtomicLong(0);

    public Event(double time){
        this.time=time;
        this.sequence=SEQUENCE_GENERATOR.getAndIncrement();
    }

    public double getTime(){
        return time;
    }

    public long getSequence(){
        return sequence;
    }
    /**
     * 新增：设置事件时间（用于 scheduleDelayed）
     * @param time 新时间
     */
    public void setTime(double time) {
        if (time < 0) {
            throw new IllegalArgumentException("事件时间不能为负数");
        }
        this.time = time;
    }

    public abstract void process(SimulationContext context);

    @Override
    public int compareTo(Event other){
        int timeCompare=Double.compare(this.time,other.time);
        if(timeCompare!=0){
            return timeCompare;
        }
        return Long.compare(this.sequence, other.sequence);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName()+"{time="+time+",seq="+sequence+"}";
    }

}
