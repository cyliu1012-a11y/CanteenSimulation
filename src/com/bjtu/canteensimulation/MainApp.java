package com.bjtu.canteensimulation;

import com.bjtu.canteensimulation.controller.MainController;
import com.bjtu.canteensimulation.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // 创建控制器
        MainController controller = new MainController();

        // 创建视图
        MainView mainView = new MainView(controller);

        // 设置控制器中的视图引用（关键！）
        controller.setMainView(mainView);

        // 创建场景
        Scene scene = new Scene(mainView.getRoot(), 850, 600);

        // 配置舞台
        primaryStage.setTitle("北京交通大学食堂就餐仿真系统");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 可选：自动启动仿真
        // controller.startSimulation();
    }
}