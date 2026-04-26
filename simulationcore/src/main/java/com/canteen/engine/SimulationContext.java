package com.canteen.engine;
import com.canteen.statistics.StatisticsCollector;

/**
 * 仿真上下文接口
 * 提供仿真运行的上下文信息，供事件处理逻辑使用
 */
public class SimulationContext {
    /** 仿真引擎引用 */
    private final SimulationEngine engine;
    /** 仿真时钟引用 */
    private final SimulationClock clock;
    /** 调度器引用 */
    private final Scheduler scheduler;
    /** 统计收集器引用 */
    private StatisticsCollector statisticsCollector;
    /** 自定义数据存储 */
    private final java.util.Map<String,Object> attributes;
    /**
     * 构造函数
     * @param engine 仿真引擎
     * @param clock 仿真时钟
     * @param scheduler 调度器
     * @param statisticsCollector 统计收集器
     */
    public SimulationContext(SimulationEngine engine,SimulationClock clock,Scheduler scheduler,StatisticsCollector statisticsCollector){
        this.engine=engine;
        this.clock=clock;
        this.scheduler=scheduler;
        this.statisticsCollector=statisticsCollector;
        this.attributes=new java.util.HashMap<>();
    }

    /**
     * 获取仿真引擎
     * @return 仿真引擎
     */
    public SimulationEngine getEngine(){
        return engine;
    }
    /**
     * 获取当前仿真时间
     * @return 当前时间
     */
    public double getCurrentTime(){
        return clock.getCurrentTime();
    }
    /**
     * 获取仿真时钟
     * @return 仿真时钟
     */
    public SimulationClock getClock(){
        return clock;
    }
    /**
     * 获取调度器
     * @return 调度器
     */
    public Scheduler getScheduler(){
        return scheduler;
    }
    /**
     * 获取统计收集器
     * @return 统计收集器
     */
    public StatisticsCollector getStatisticsCollector(){
        return statisticsCollector;
    }
    /**
     * 设置统计收集器
     * @param collector 统计收集器
     */
    public void setStatisticsCollector(StatisticsCollector collector){
        this.statisticsCollector=collector;
    }
    /**调度事件
     * @param event 要调度的事件
     */
    public void scheduleEvent(Event event){
        scheduler.schedule(event);
    }
    /**
     * 存储自定义属性
     * @param key 键
     * @param value 值
     */
    public void setAttribute(String key,Object value){
        attributes.put(key,value);
    }
    /**
     * 获取自定义属性
     * @param key 键
     * @return value 值
     */
    public Object getAttribute(String key){
        return attributes.get(key);
    }
    /**
     * 获取自定义属性（带类型转换）
     * @param key 键
     * @param clazz 目标类型
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key,Class<T> clazz){
        Object value=attributes.get(key);
        if (value==null){
            return null;
        }
        if (clazz.isInstance(value)){
            return (T)value;
        }
        throw new ClassCastException("属性 ‘"+key+"’ 的类型不是 "+clazz.getName());
    }
    /**
     * 移除自定义属性
     * @param key 键
     * @return 被移除的值
     */
    public Object removeAttribute(String key){
        return attributes.remove(key);
    }
    /**
     * 清空所有自定义属性
     */
    public void clearAttributes(){
        attributes.clear();
    }
}
