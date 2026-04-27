package com.canteen.engine;
import com.canteen.statistics.StatisticsCollector;

/**
 * 仿真引擎主类
 * 负责驱动整个仿真过程
 */
public class SimulationEngine {
    /** 仿真时钟 */
    private final SimulationClock clock;
    /** 事件调度器 */
    private final Scheduler scheduler;
    /** 统计收集器 */
    private StatisticsCollector statisticsCollector;
    /** 仿真上下文 */
    private SimulationContext context;
    /** 引擎是否正在运行 */
    private boolean running;
    /** 引擎是否已暂停 */
    private  boolean paused;
    /** 最大仿真时间（秒） */
    private double maxSimulationTime;
    /**
     * 构造函数
     */
    public SimulationEngine(){
        this.clock=new SimulationClock();
        this.scheduler=new Scheduler(clock);
        this.running=false;
        this.paused=false;
        this.maxSimulationTime=Double.MAX_VALUE;
    }
    /**
     * 带参数的构造函数
     * @param maxSimulationTime 最大仿真时间
     */
    public SimulationEngine(double maxSimulationTime){
        this();
        this.maxSimulationTime=maxSimulationTime;
        this.clock.setEndTime(maxSimulationTime);
    }
    /**
     * 初始化仿真引擎
     * @param config 仿真配置（可选）
     */
    public void initialize(Object config){
        //重置状态
        this.running=false;
        this.paused=false;
        this.scheduler.clear();
        this.clock.reset();
        //创造仿真上下文
        this.context=new SimulationContext(this,clock,scheduler,statisticsCollector);
        //子类或调用方需要在此之后添加初始事件
        //例如：SimulationEngine engine = new SimulationEngine();
        //engine.registerCollector(stats);
        //engine.initialize(null);  // 初始化
        //engine.scheduleEvent(new StudentArrivalEvent(0, firstStudent));//调用方手动添加初始事件
        //engine.run(3600);
    }
    /**
     * 注册统计收集器
     * @param collector 统计收集器
     */
    public void registerCollector(StatisticsCollector collector){
        this.statisticsCollector=collector;
        //如果上下文已创建，更新上下文中的统计收集器
        if (this.context!=null) {
            this.context.setStatisticsCollector(collector);
        }
    }
    /**
     * 调度事件
     * @param event 要调度的事件
     */
    public void scheduleEvent(Event event){
        scheduler.schedule(event);
    }
    /**
     * 运行仿真到指定时间
     * @param endTime 结束时间
     */
    public void runUntil(double endTime){
        if (running){
            throw new IllegalStateException("仿真引擎已在运行中");
        }
        running=true;
        paused=false;
        try {
            //主循环
            while(running&&!paused&&clock.getCurrentTime()<endTime){
                //检查是否有待处理事件
                if (!scheduler.hasNextEvent()){
                    System.out.println("事件队列为空，仿真结束");
                    break;
                }
                //获取下一个事件
                Event nextEvent= scheduler.getNextEvent();
                if (nextEvent==null){
                    break;
                }
                //如果事件时间超过结束时间，停止仿真
                if (nextEvent.getTime()>endTime){
                    break;
                }
                //推进仿真时钟
                clock.setCurrentTime(nextEvent.getTime());
                //执行事件
                nextEvent.process(context);
            }
        } catch (Exception e) {
            System.err.println("仿真运行出错："+e.getMessage());
            e.printStackTrace();
        }finally {
            running=false;
        }
    }
    /**
     * 运行仿真
     * @param duration 运行时长
     */
    public void run(double duration){
        if (duration<=0){
            throw new IllegalArgumentException("运行时长必须大于0："+duration);
        }
        double endTime=clock.getCurrentTime()+duration;
        runUntil(endTime);
    }
    /**
     * 运行完整仿真（直到事件队列为空或达到最大时间）
     */
    public void runFull(){
        runUntil(maxSimulationTime);
    }
    /**
     * 停止仿真
     */
    public void stop(){
        this.running=false;
    }
    /**
     * 暂停仿真
     */
    public void pause(){
        this.paused=true;
    }
    /**
     * 恢复仿真
     */
    public void resume(){
        if (!running){
            throw new IllegalStateException("仿真引擎未运行，无法恢复");
        }
        this.paused=false;
        //继续运行
        runUntil(maxSimulationTime);
    }
    /**
     * 重置仿真引擎
     */
    public void reset(){
        stop();
        scheduler.clear();
        clock.reset();
        running=false;
        paused=false;
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
     * 获取仿真上下文
     * @return 仿真上下文
     */
    public SimulationContext getContext(){
        return context;
    }
    /**获取统计收集器
     * @return 统计收集器
     */
    public StatisticsCollector getStatisticsCollector(){
        return statisticsCollector;
    }
    /**
     * 检查引擎是否正在运行
     * @return true表示正在运行
     */
    public boolean isRunning(){
        return running;
    }
    /**
     * 检查引擎是否已暂停
     * @return true表示已暂停
     */
    public boolean isPaused(){
        return paused;
    }
}
