package com.canteen.infrastructure;

public class SimulationException extends RuntimeException{

    public SimulationException(){
        super();
    }

    public SimulationException(String message){
        super(message);
    }

    public SimulationException(String message,Throwable cause){
        super(message, cause);
    }

    public SimulationException(Throwable cause){
        super(cause);
    }

    public static SimulationException eventSchedulingFailed(String eventType,double time,double currentTime){
        return new SimulationException(String.format("事件调度失败：%s事件时间=%.3f,当前时间=%.3f",eventType,time,currentTime));
    }

    public static SimulationException entityNotFound(String entityType,Object id){
        return new SimulationException(String.format("实体未找到：%s[id=%s]",entityType,id));
    }

    public static SimulationException invalidConfiguration(String configKey,Object value,String reason){
        return new SimulationException(String.format("无效配置：%s=%s,%s",configKey,value,reason));
    }
}
