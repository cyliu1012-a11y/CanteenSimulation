package com.bjtu.canteensimulation.view.component;

import com.bjtu.canteensimulation.controller.MainController;
import com.bjtu.canteensimulation.model.entity.CanteenWindow;
import com.bjtu.canteensimulation.model.entity.Seat;
import com.bjtu.canteensimulation.model.entity.Student;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class VisualizationCanvas {

    private Canvas canvas;
    private GraphicsContext gc;
    private MainController controller;

    public VisualizationCanvas(MainController controller) {
        this.controller = controller;
        initializeCanvas();
    }

    private void initializeCanvas() {
        canvas = new Canvas(850, 400);
        gc = canvas.getGraphicsContext2D();

        // 添加调试输出
        System.out.println("Canvas initialized: " + canvas.getWidth() + "x" + canvas.getHeight());
    }

    public void update() {
        drawSimulation();
    }

    private void drawSimulation() {
        if (gc == null || controller == null) return;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 背景
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 标题
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(14));
        gc.fillText("食堂布局示意图", 380, 25);

        // 检查引擎是否存在
        if (controller.getEngine() == null) {
            gc.fillText("引擎未初始化", 380, 200);
            return;
        }

        drawWindows();
        drawSeats();
        drawLegend();
    }

    private void drawWindows() {
        var windows = controller.getEngine().getWindows();
        if (windows == null || windows.isEmpty()) {
            gc.fillText("没有窗口数据", 50, 100);
            return;
        }

        double windowY = 50;
        double windowWidth = 90;
        double windowHeight = 60;
        double spacing = 20;

        // 计算起始X，居中显示
        double totalWidth = windows.size() * windowWidth + (windows.size() - 1) * spacing;
        double startX = (canvas.getWidth() - totalWidth) / 2;

        for (int i = 0; i < windows.size(); i++) {
            CanteenWindow window = windows.get(i);
            double x = startX + i * (windowWidth + spacing);

            // 窗口背景
            if (window.isIdle()) {
                gc.setFill(Color.LIGHTGREEN);
            } else {
                gc.setFill(Color.ORANGE);
            }
            gc.fillRect(x, windowY, windowWidth, windowHeight);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, windowY, windowWidth, windowHeight);

            // 窗口文字
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font(12));
            gc.fillText("窗口 " + window.getId(), x + 25, windowY + 25);

            // 状态文字
            gc.setFont(Font.font(10));
            String status = window.isIdle() ? "空闲" : "忙碌";
            gc.fillText(status, x + 35, windowY + 45);

            // 绘制队列（在窗口下方）
            int queueSize = window.getQueue().size();
            if (queueSize > 0) {
                gc.setFill(Color.BLUE);
                for (int j = 0; j < Math.min(queueSize, 5); j++) {
                    double sx = x + 5 + j * 12;
                    double sy = windowY + windowHeight + 8;
                    gc.fillOval(sx, sy, 8, 8);
                }

                gc.setFill(Color.DARKBLUE);
                gc.setFont(Font.font(9));
                gc.fillText("队列:" + queueSize, x + 5, windowY + windowHeight - 5);
            }
        }
    }

    private void drawSeats() {
        var seats = controller.getEngine().getSeats();
        double startX = 50;
        double startY = 160;
        double seatWidth = 28;
        double seatHeight = 28;
        int seatsPerRow = 12;
        if (seats == null || seats.isEmpty()) {
            System.out.println("没有座位数据！");
            gc.fillText("没有座位数据", startX, startY);
            return;
        }

        int occupiedCount = 0;
        for (Seat seat : seats) {
            if (seat.isOccupied()) occupiedCount++;
        }
        System.out.println("座位占用: " + occupiedCount + "/" + seats.size());

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(12));
        gc.fillText("就餐区", startX, startY - 5);

        // 绘制边框
        int rows = (int) Math.ceil(seats.size() / (double) seatsPerRow);
        gc.setStroke(Color.GRAY);
        gc.strokeRect(startX - 5, startY - 5,
                seatsPerRow * (seatWidth + 3) + 5,
                rows * (seatHeight + 3) + 5);

        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            int row = i / seatsPerRow;
            int col = i % seatsPerRow;
            double x = startX + col * (seatWidth + 3);
            double y = startY + row * (seatHeight + 3);

            if (seat.isOccupied()) {
                gc.setFill(Color.ORANGE);
            } else {
                gc.setFill(Color.LIGHTBLUE);
            }
            gc.fillRect(x, y, seatWidth, seatHeight);
            gc.setStroke(Color.GRAY);
            gc.strokeRect(x, y, seatWidth, seatHeight);
        }
    }

    private void drawLegend() {
        double legendY = 320;

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(11));
        gc.fillText("图例:", 50, legendY);

        // 空闲窗口
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(90, legendY - 12, 15, 12);
        gc.setFill(Color.BLACK);
        gc.fillText("空闲窗口", 110, legendY);

        // 忙碌窗口
        gc.setFill(Color.ORANGE);
        gc.fillRect(180, legendY - 12, 15, 12);
        gc.setFill(Color.BLACK);
        gc.fillText("忙碌窗口", 200, legendY);

        // 排队学生
        gc.setFill(Color.BLUE);
        gc.fillOval(280, legendY - 10, 10, 10);
        gc.setFill(Color.BLACK);
        gc.fillText("排队学生", 295, legendY);

        // 就餐座位
        gc.setFill(Color.ORANGE);
        gc.fillRect(370, legendY - 12, 15, 12);
        gc.setFill(Color.BLACK);
        gc.fillText("就餐桌位", 390, legendY);

        // 空闲座位
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(460, legendY - 12, 15, 12);
        gc.setFill(Color.BLACK);
        gc.fillText("空闲桌位", 480, legendY);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void refresh() {
        update();
    }
}