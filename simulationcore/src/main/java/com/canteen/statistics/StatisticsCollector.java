package com.canteen.statistics;

import javax.swing.text.html.ObjectView;

/**
 * 统计数据收集器接口
 * 定义统计数据收集方法，供事件处理逻辑记录相关数据
 */
public interface StatisticsCollector {
    /**
     * 收集单条数据
     *
     * @param key   数据键
     * @param value 数据值
     */
    void collectData(String key, Object value);

    /**
     * 记录学生达到事件
     *
     * @param studentId    学生ID
     * @param arrivalTime  达到时间
     * @param chosenWindow 选择的窗口
     */
    void onStudentArrived(int studentId, double arrivalTime, int chosenWindow);

    /**
     * 记录服务开始事件
     *
     * @param studentId 学生ID
     * @param startTime 开始时间
     * @param windowId  窗口ID
     */
    void onServiceStarted(int studentId, double startTime, int windowId);
    /**
     * 记录服务结束事件
     * @param studentId  学生ID
     * @param endTime 结束时间
     */
    void onServiceFinished(int studentId,double endTime);
    /**
     * 记录队列长度变化
     * @param windowId 窗口ID
     * @param queueLength 当前队列长度
     * @param time 当前时间
     */
    void onQueueLengthChanged(int windowId,int queueLength,double time);
    /**
     * 记录学生离开事件
     * @param studentId 学生ID
     * @param departureTime 离开时间
     */
    void onStudentLeft(int studentId,double departureTime);
    /**
     * 获取统计数据
     * @param key 统计项键名
     * @return 统计值
     */
    Object getStatistic(String key);
    /**
     * 打印所有统计结果
     */
    void printStatistics();
    /**
     * 到处统计数据到文件
     * @param filePath 文件路径
     */
    void exportToFile(String filePath);
}
