package com.inspection.dao;

import com.inspection.config.DBConfig;
import com.inspection.vo.InspectionVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InspectionDAO {
    private static volatile InspectionDAO instance;

    private InspectionDAO() {
        try {
            Class.forName(DBConfig.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBC Driver not found: " + DBConfig.JDBC_DRIVER, e);
        }
    }

    public static InspectionDAO getInstance() {
        if (instance == null) {
            synchronized (InspectionDAO.class) {
                if (instance == null) {
                    instance = new InspectionDAO();
                }
            }
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.DB_URL, DBConfig.DB_USER, DBConfig.DB_PASSWORD);
    }

    public int saveInspection(InspectionVO vo) throws SQLException {
        String sql = "INSERT INTO Product_Mi_Inspection (template_name, category, worker_name, raw_data, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, vo.getTemplateName());
            ps.setString(2, vo.getCategory());
            ps.setString(3, vo.getWorkerName());
            ps.setString(4, vo.getRawData());
            ps.setString(5, vo.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean updateEditingStatus(int inspectionId, boolean isEditing) throws SQLException {
        String sql = "UPDATE Product_Mi_Inspection SET status = ? WHERE id = ?";
        String status = isEditing ? "In-Progress" : "Pending";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, inspectionId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean isTemplateLocked(String templateName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Product_Mi_Inspection WHERE template_name = ? AND status = 'In-Progress'";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, templateName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
