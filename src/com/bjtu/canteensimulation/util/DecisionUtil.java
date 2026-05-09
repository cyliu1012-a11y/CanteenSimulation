package com.bjtu.canteensimulation.util;

import com.bjtu.canteensimulation.model.Preference;
import com.bjtu.canteensimulation.model.entity.ServiceWindow;
import java.util.*;

public class DecisionUtil
{
    private static final Random random = new Random();

    // 计算窗口效用
    public static double calculateUtility(Preference p, ServiceWindow w) {
        double typeScore = p.getTypeScore(w.getType());
        double queueFactor = 1.0 / (1 + w.getQueueSize() * p.getQueueSensitivity());
        return typeScore * queueFactor;
    }

    // 生成正态分布的服务时间
    public static double generateServiceTime(double mean, double std) {
        double value = random.nextGaussian() * std + mean;
        return Math.max(value, 0.5); // 最小0.5秒，避免负数
    }

    // 生成就餐时间
    public static double generateDiningTime() {
        return 15 + random.nextDouble() * 10; // 15~25分钟
    }
}
