package com.bjtu.canteensimulation.view.component;

import com.bjtu.canteensimulation.controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlPanel {
    private HBox panel;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private MainController controller;

    public ControlPanel(MainController controller) {
        this.controller = controller;
        initializePanel();
    }

    private void initializePanel() {
        panel = new HBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5;");
        panel.setAlignment(Pos.CENTER);

        startButton = new Button("开始");
        stopButton = new Button("暂停");
        resetButton = new Button("重置");

        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        stopButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        resetButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        // 绑定按钮事件
        startButton.setOnAction(e -> controller.startSimulation());
        stopButton.setOnAction(e -> controller.stopSimulation());
        resetButton.setOnAction(e -> controller.resetSimulation());

        panel.getChildren().addAll(startButton, stopButton, resetButton);
    }

    public HBox getPanel() {
        return panel;
    }
}
