package com.bjtu.canteensimulation.view;

import com.bjtu.canteensimulation.controller.MainController;
import com.bjtu.canteensimulation.view.component.ControlPanel;
import com.bjtu.canteensimulation.view.component.StatusPanel;
import com.bjtu.canteensimulation.view.component.VisualizationCanvas;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainView {
    private BorderPane root;
    private MainController controller;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    private VisualizationCanvas visualizationCanvas;

    public MainView(MainController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");

        // 创建顶部容器
        VBox topContainer = new VBox(10);
        topContainer.setPadding(new Insets(10));
        topContainer.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 0 0 1 0;");

        // 标题
        Label titleLabel = new Label("北京交通大学食堂就餐仿真系统");
        titleLabel.setFont(Font.font("Microsoft YaHei", 18));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        // 创建组件
        controlPanel = new ControlPanel(controller);
        statusPanel = new StatusPanel(controller);

        topContainer.getChildren().addAll(titleLabel, controlPanel.getPanel(), statusPanel.getPanel());
        root.setTop(topContainer);

        // 创建中间区域
        visualizationCanvas = new VisualizationCanvas(controller);
        VBox centerContainer = new VBox();
        centerContainer.setPadding(new Insets(20));
        centerContainer.getChildren().add(visualizationCanvas.getCanvas());
        root.setCenter(centerContainer);

        // 添加底部状态栏
        Label statusBar = new Label("系统就绪");
        statusBar.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-padding: 5px;");
        root.setBottom(statusBar);
    }

    public BorderPane getRoot() {
        return root;
    }

    public void update() {
        if (visualizationCanvas != null) {
            visualizationCanvas.update();
        }
    }
}