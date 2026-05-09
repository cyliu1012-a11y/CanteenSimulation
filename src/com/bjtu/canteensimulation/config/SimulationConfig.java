package com.bjtu.canteensimulation.config;

public class SimulationConfig {
    private int windowCount = 5;
    private int seatCount = 30;
    private int simulationSpeed = 3;
    private double arrivalRate = 0.1;  // 到达率（每单位时间到达人数）
    private double serviceRate = 0.2;   // 服务率（窗口处理速度）

    public SimulationConfig() {}

    public int getWindowCount() { return windowCount; }
    public void setWindowCount(int windowCount) { this.windowCount = windowCount; }

    public int getSeatCount() { return seatCount; }
    public void setSeatCount(int seatCount) { this.seatCount = seatCount; }

    public int getSimulationSpeed() { return simulationSpeed; }
    public void setSimulationSpeed(int simulationSpeed) { this.simulationSpeed = simulationSpeed; }

    public double getArrivalRate() { return arrivalRate; }
    public void setArrivalRate(double arrivalRate) { this.arrivalRate = arrivalRate; }

    public double getServiceRate() { return serviceRate; }
    public void setServiceRate(double serviceRate) { this.serviceRate = serviceRate; }
}
