package com.bjtu.canteensimulation.controller;

import com.bjtu.canteensimulation.model.engine.SimulationEngine;
import com.bjtu.canteensimulation.config.SimulationConfig;
import com.bjtu.canteensimulation.view.MainView;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainController {

    private SimulationEngine engine;
    private SimulationConfig config;
    private ScheduledExecutorService uiUpdater;
    private ScheduledExecutorService simulationTimer;
    private MainView mainView;  // 添加这个字段

    // 可观察属性（绑定UI）
    private final IntegerProperty simulationTime = new SimpleIntegerProperty(0);
    private final IntegerProperty studentsServed = new SimpleIntegerProperty(0);
    private final IntegerProperty studentsInQueue = new SimpleIntegerProperty(0);
    private final IntegerProperty studentsDining = new SimpleIntegerProperty(0);
    private final BooleanProperty isRunning = new SimpleBooleanProperty(false);

    public MainController() {
        this.config = new SimulationConfig();
        this.engine = new SimulationEngine(config);
    }

    // 添加 setMainView 方法
    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    // =============================
    // 🎮 控制方法
    // =============================

    public void startSimulation() {
        if (isRunning.get()) return;

        engine.start();
        isRunning.set(true);

        startUIUpdater();
        startVisualizationUpdater();  // 添加可视化刷新
    }

    public void stopSimulation() {
        engine.stop();
        isRunning.set(false);
        stopUIUpdater();
        stopSimulationTimer();
    }

    public void resetSimulation() {
        engine.reset();
        updateFromEngine();
        isRunning.set(false);
        stopUIUpdater();
        stopSimulationTimer();
    }

    // =============================
    // 🔄 UI刷新
    // =============================

    private void startUIUpdater() {
        uiUpdater = Executors.newSingleThreadScheduledExecutor();
        uiUpdater.scheduleAtFixedRate(() -> {
            Platform.runLater(this::updateFromEngine);
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void startVisualizationUpdater() {
        if (uiUpdater != null && !uiUpdater.isShutdown()) {
            uiUpdater.shutdownNow();
        }
        uiUpdater = Executors.newSingleThreadScheduledExecutor();
        uiUpdater.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                updateFromEngine();
                // 触发视图刷新
                if (mainView != null) {
                    mainView.update();
                }
            });
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void startSimulationTimer(int speed) {
        long interval = Math.max(10, 100 - speed * 10);
        simulationTimer = Executors.newSingleThreadScheduledExecutor();
        // 这里可以根据需要添加定时任务
    }

    private void stopSimulationTimer() {
        if (simulationTimer != null && !simulationTimer.isShutdown()) {
            simulationTimer.shutdownNow();
        }
    }

    private void stopUIUpdater() {
        if (uiUpdater != null && !uiUpdater.isShutdown()) {
            uiUpdater.shutdownNow();
        }
    }

    public void updateFromEngine() {
        simulationTime.set(engine.getSimulationTime());
        studentsServed.set(engine.getStudentsServed());
        studentsInQueue.set(engine.getStudentsInQueue());
        studentsDining.set(engine.getStudentsDining());
    }

    // =============================
    // 📦 Getter（给UI绑定用）
    // =============================

    public IntegerProperty simulationTimeProperty() {
        return simulationTime;
    }

    public IntegerProperty studentsServedProperty() {
        return studentsServed;
    }

    public IntegerProperty studentsInQueueProperty() {
        return studentsInQueue;
    }

    public IntegerProperty studentsDiningProperty() {
        return studentsDining;
    }

    public BooleanProperty isRunningProperty() {
        return isRunning;
    }

    public SimulationEngine getEngine() {
        return engine;
    }

    public SimulationConfig getConfig() {
        return config;
    }
}