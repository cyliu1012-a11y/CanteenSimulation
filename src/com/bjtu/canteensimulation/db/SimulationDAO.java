package com.bjtu.canteensimulation.db;

import com.bjtu.canteensimulation.model.entity.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationDAO {

    // -------------------------- 1. 自动插入接口 --------------------------
    public int insertSimulationInfo(int studentCount, int windowCount, int seatCount,
                                    String strategy, String startTime, String endTime) {
        String sql = "INSERT INTO simulation_info(student_count, window_count, seat_count, strategy_type, simulate_start_time, simulate_end_time) VALUES(?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int simId = -1;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, studentCount);
            pstmt.setInt(2, windowCount);
            pstmt.setInt(3, seatCount);
            pstmt.setString(4, strategy);
            pstmt.setString(5, startTime);
            pstmt.setString(6, endTime);
            pstmt.executeUpdate();
            // 获取自动生成的主键
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                simId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return simId;
    }

    public void insertStudentRecords(int simulationId, List<Student> studentList) {
        String sql = "INSERT INTO student_simulation_record(simulation_id, student_id, arrive_time, queue_start_time, service_start_time, dine_start_time, finish_time, wait_time) VALUES(?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            for (Student stu : studentList) {
                pstmt.setInt(1, simulationId);
                pstmt.setInt(2, stu.getId());
                pstmt.setDouble(3, stu.getArriveTime());
                pstmt.setDouble(4, stu.getQueueStartTime());
                pstmt.setDouble(5, stu.getServiceStartTime());
                pstmt.setDouble(6, stu.getDineStartTime());
                pstmt.setDouble(7, stu.getFinishTime());
                pstmt.setDouble(8, stu.getQueueStartTime() - stu.getArriveTime()); // 等待时间
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    // -------------------------- 2. 历史查询接口 --------------------------
    public List<SimulationInfo> listAllSimulations() {
        List<SimulationInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM simulation_info ORDER BY id DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SimulationInfo info = new SimulationInfo();
                info.setId(rs.getInt("id"));
                info.setStudentCount(rs.getInt("student_count"));
                info.setWindowCount(rs.getInt("window_count"));
                info.setSeatCount(rs.getInt("seat_count"));
                info.setStrategyType(rs.getString("strategy_type"));
                info.setStartTime(rs.getString("simulate_start_time"));
                info.setEndTime(rs.getString("simulate_end_time"));
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // -------------------------- 3. 统计接口（示例：平均等待时间） --------------------------
    public double getAverageWaitTime(int simulationId) {
        String sql = "SELECT AVG(wait_time) FROM student_simulation_record WHERE simulation_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double avg = 0;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, simulationId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                avg = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return avg;
    }
}
