package com.bjtu.canteensimulation.view.component;

import com.bjtu.canteensimulation.controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatusPanel {
    private HBox panel;
    private Label timeLabel;
    private Label servedLabel;
    private Label queueLabel;
    private Label diningLabel;
    private MainController controller;

    public StatusPanel(MainController controller) {
        this.controller = controller;
        initializePanel();
        bindProperties();
    }

    private void initializePanel() {
        panel = new HBox(20);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5;");
        panel.setAlignment(Pos.CENTER);

        // 创建带标签的显示项
        VBox timeBox = createInfoBox("仿真时间", "0 秒");
        VBox servedBox = createInfoBox("已服务人数", "0 人");
        VBox queueBox = createInfoBox("排队人数", "0 人");
        VBox diningBox = createInfoBox("就餐人数", "0 人");

        timeLabel = (Label) timeBox.getChildren().get(1);
        servedLabel = (Label) servedBox.getChildren().get(1);
        queueLabel = (Label) queueBox.getChildren().get(1);
        diningLabel = (Label) diningBox.getChildren().get(1);

        panel.getChildren().addAll(timeBox, servedBox, queueBox, diningBox);
    }

    private VBox createInfoBox(String title, String initialValue) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 8px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        Label valueLabel = new Label(initialValue);
        valueLabel.setStyle("-fx-text-fill: #333; -fx-font-size: 16px; -fx-font-weight: bold;");

        box.getChildren().addAll(titleLabel, valueLabel);
        return box;
    }

    private void bindProperties() {
        // 确保属性不为null
        if (controller.simulationTimeProperty() != null) {
            timeLabel.textProperty().bind(controller.simulationTimeProperty().asString().concat(" 秒"));
        }
        if (controller.studentsServedProperty() != null) {
            servedLabel.textProperty().bind(controller.studentsServedProperty().asString().concat(" 人"));
        }
        if (controller.studentsInQueueProperty() != null) {
            queueLabel.textProperty().bind(controller.studentsInQueueProperty().asString().concat(" 人"));
        }
        if (controller.studentsDiningProperty() != null) {
            diningLabel.textProperty().bind(controller.studentsDiningProperty().asString().concat(" 人"));
        }
    }

    public HBox getPanel() {
        return panel;
    }
}