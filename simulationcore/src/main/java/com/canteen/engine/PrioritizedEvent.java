package com.canteen.engine;

/**
 * 带优先级的事件抽象类
 * 扩展Event，增强优先级支持，用于更精细的事件排序
 */
public abstract class PrioritizedEvent extends Event{
    /**事件优先级*/
    protected final EventPriority priority;
    /**
     * 构造函数
     * @param time 事件发生时间
     * @param priority 事件优先级
     */
    public PrioritizedEvent(double time,EventPriority priority){
        super(time);
        this.priority=priority;
    }
    /**
     * 获取事件优先级
     * @return 优先级
     */
    public EventPriority getPriority(){
        return priority;
    }
    /**
     * 重写比较方法，增加优先级比较
     * 排序规则：先按时间，再按优先级，最后按序号
     */
    @Override
    public int compareTo(Event other){
        //如果other不是PrioritizedEvent，回到父类比较
        if(!(other instanceof PrioritizedEvent)){
            return super.compareTo(other);
        }
        PrioritizedEvent that=(PrioritizedEvent) other;
        //1. 按时间
        int timeCompare=Double.compare(this.time,that.time);
        if (timeCompare!=0){
            return timeCompare;
        }
        //2. 按优先级
        int priorityCompare=Integer.compare(this.priority.getValue(),that.priority.getValue());
        if (priorityCompare!=0){
            return priorityCompare;
        }
        //3. 按序号
        return Long.compare(this.sequence,that.sequence);
    }
}
