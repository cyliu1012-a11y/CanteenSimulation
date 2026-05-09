package com.bjtu.canteensimulation;

import com.bjtu.canteensimulation.engine.core.EventQueue;
import com.bjtu.canteensimulation.engine.core.SimulationEngine;
import com.bjtu.canteensimulation.model.behaviour.ShortestQueueStrategy;
import com.bjtu.canteensimulation.model.behaviour.StudentChoiceStrategy;
import com.bjtu.canteensimulation.model.behaviour.UtilityBasedStrategy;
import com.bjtu.canteensimulation.model.entity.Cafeteria;
import com.bjtu.canteensimulation.model.entity.SeatPool;
import com.bjtu.canteensimulation.model.entity.ServiceWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("    食堂仿真系统 - 交互式调试版");

        // 1. 输入座位数
        System.out.print("请输入食堂总座位数：");
        int seatCount = sc.nextInt();
        SeatPool seatPool = new SeatPool(seatCount);

        // 2. 输入窗口数量
        System.out.print("请输入服务窗口数量：");
        int windowCount = sc.nextInt();
        List<ServiceWindow> windows = new ArrayList<>();

        // 3. 逐个配置窗口
        for (int i = 0; i < windowCount; i++) {
            System.out.println("\n--- 第 " + (i + 1) + " 个窗口 ---");
            System.out.print("窗口名称（如：米饭窗口）：");
            sc.nextLine(); // 清空换行
            String name = sc.nextLine();

            System.out.print("平均服务时间（秒，越小越快）：");
            double mean = sc.nextDouble();

            System.out.print("服务时间波动值（建议0.2~0.8）：");
            double std = sc.nextDouble();

            windows.add(new ServiceWindow(i + 1, name, mean, std));
        }

        // 4. 输入学生数量
        System.out.print("\n请输入仿真学生总数：");
        int studentCount = sc.nextInt();

        // 5. 选择策略
        System.out.println("\n请选择学生选择策略：");
        System.out.println("1. 最短队列优先");
        System.out.println("2. 喜好效用优先");
        System.out.print("请输入数字：");
        int strategyChoice = sc.nextInt();

        StudentChoiceStrategy strategy;
        if (strategyChoice == 1) {
            strategy = new ShortestQueueStrategy();
            System.out.println("已启用：最短队列优先策略");
        } else {
            strategy = new UtilityBasedStrategy();
            System.out.println("已启用：喜好效用优先策略");
        }

        // 6. 开始仿真
        System.out.println("\n==================================");
        System.out.println("         开始仿真...");
        System.out.println("==================================\n");

        Cafeteria cafeteria = new Cafeteria("交互式食堂", windows, seatPool);
        EventQueue queue = new EventQueue();
        SimulationEngine engine = new SimulationEngine(queue, cafeteria, strategy);

        engine.startSimulation(studentCount);

    }
}
