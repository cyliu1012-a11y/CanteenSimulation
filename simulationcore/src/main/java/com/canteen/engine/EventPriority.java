package com.canteen.engine;
/**
 * 事件优先级枚举
 *定义不同事件类型的优先级，用于处理时间相同时的执行顺序
 */
public enum EventPriority {
    /**最高优先级：系统事件*/
    SYSTEM(0),
    /**高优先级：学生到达事件*/
    ARRIVAL(1),
    /**中高优先级：服务开始事件*/
    SERVICE_START(2),
    /**中优先级：服务结束事件*/
    SERVICE_FINISH(3),
    /**中低优先级：找座位事件*/
    FIND_SEAT(4),
    /**低优先级：就餐结束事件*/
    DINING_FINISH(5),
    /**最低优先级：其他事件*/
    OTHER(6);

    private final int value;
    EventPriority(int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }

    /**
     * 根据事件类名获取对应的优先级
     * @param eventClass 事件类
     * @return 对应优先级
     */
    public static EventPriority fromEventClass(Class<?extends Event> eventClass){
        String className=eventClass.getSimpleName();
        return switch (className) {
            case "StudentArrivalEvent" -> ARRIVAL;
            case "StartServiceEvent" -> SERVICE_START;
            case "FinishServiceEvent" -> SERVICE_FINISH;
            case "FindSeatEvent" -> FIND_SEAT;
            case "FinishDiningEvent" -> DINING_FINISH;
            default -> OTHER;
        };
    }
}
