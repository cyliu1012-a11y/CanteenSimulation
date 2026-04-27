package com.canteen.infrastructure;

public final class TimeUtil {
    private TimeUtil(){
        //私有构造函数，防止实例化
    }
    /**秒转毫秒的倍数*/
    public static final double SECONDS_TO_MILLIS=1000.0;
    /**分钟转秒的倍数*/
    public static final double MINUTES_TO_SECONDS=60.0;
    /**小时转秒的倍数*/
    public static final double HOURS_TO_SECONDS=3600.0;
    /**秒转毫秒*/
    public static double secondsToMillis(double seconds){
        return seconds*SECONDS_TO_MILLIS;
    }
    /**毫秒转秒*/
    public static double millisToSeconds(double millis){
        return millis/SECONDS_TO_MILLIS;
    }
    /**分钟转秒*/
    public static double minutesToSeconds(double minutes){
        return minutes*MINUTES_TO_SECONDS;
    }
    /**小时转秒*/
    public static double hoursToSeconds(double hours){
        return hours*HOURS_TO_SECONDS;
    }
    /**
     * 格式化时间（秒转为 HH：MM：SS 格式）
     * @param seconds 秒数
     * @return 格式化后的时间字符串
     */
    public static String formatTime(double seconds){
        int totalSeconds=(int)Math.round(seconds);
        int hours=totalSeconds/3600;
        int minutes=(totalSeconds%3600)/60;
        int secs=totalSeconds%60;
        return String.format("%02d:%02d:%02d",hours,minutes,secs);
    }
    /**
     * 格式化时间（秒转为带单位的字符串）
     * @param seconds 秒数
     * @return 带单位的时间字符串
     */
    public static String formatTimeWithUnit(double seconds){
        if(seconds<60){
            return String.format("%.2f秒",seconds);
        } else if (seconds<3600) {
            return String.format("%.2f 分钟",seconds/MINUTES_TO_SECONDS);
        }else {
            return String.format("%.2f 小时",seconds/HOURS_TO_SECONDS);
        }
    }
    /**
     * 获取当前系统时间戳（秒）
     * @return 时间戳
     */
    public static double getCurrentTimestamp(){
        return System.currentTimeMillis()/SECONDS_TO_MILLIS;
    }
    /**
     * 计算两个时间点的差值
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 时间差 （绝对值）
     */
    public static double timeDifference(double startTime,double endTime){
        return Math.abs(endTime-startTime);
    }
    /**
     * 检查时间是否在有效范围内
     * @param time 待检查的时间
     * @param min 最小值
     * @param max 最大值
     * @return 是否有效
     */
    public static boolean isValidTime(double time,double min,double max){
        return time>=min&&time<=max;
    }
    /**
     * 限制时间在指定范围内
     * @param time 原时间
     * @param min 最小值
     * @param max 最大值
     * @return 限制后的时间
     */
    public static double clamp(double time,double min,double max){
        return Math.max(min,Math.min(max,time));
    }
    /**
     * 判断两个时间是否近似相等（误差范围内）
     * @param t1 时间1
     * @param t2 时间2
     * @param epsilon 允许误差
     * @return 是否近似相等
     */
    public static boolean approximatelyEqual(double t1,double t2,double epsilon){
        return Math.abs(t1-t2)<epsilon;
    }
    /**
     * 判断两个时间是否近似相等（默认误差1e-6）
     * @param t1 时间1
     * @param t2 时间2
     * @return 是否近似相等
     */
    public static boolean approximatelyEqual(double t1,double t2){
        return approximatelyEqual(t1,t2,1e-6);
    }


}
