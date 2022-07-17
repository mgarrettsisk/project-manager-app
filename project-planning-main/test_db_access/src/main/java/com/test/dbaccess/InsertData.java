package com.test.dbaccess;

import com.projman.dbaccess.*;

import java.sql.*;

public class InsertData {

    public static String url;
    public static String user;
    public static String password;

    public void addProject(Project project) {

        String sql = Query.insertProject;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getProjectDesc());
            pstmt.setString(3, project.getOwner());
            int id = db.insertSQL(pstmt);
            System.out.println("\n Project insert:");
            System.out.println("Project added. project_id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMember(Member member) {

        String sql = Query.insertMember;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, member.getMemberName());
            pstmt.setInt(2, member.getProjectId());
            int id = db.insertSQL(pstmt);
            System.out.println("\n Member insert:");
            System.out.println("Member added. member_id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRisk(Risk risk) {

        String sql = Query.insertRisk;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, risk.getRiskDesc());
            pstmt.setInt(2, risk.getRiskStatusId());
            pstmt.setInt(3, risk.getProjectId());
            int id = db.insertSQL(pstmt);
            System.out.println("\n Risk insert:");
            System.out.println("Risk added. risk_id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProjectTime(ProjectTime projectTime) {

        String sql = Query.insertProjectTime;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setInt(1, projectTime.getHours());
            pstmt.setInt(2, projectTime.getMemberId());
            pstmt.setInt(3, projectTime.getPhaseId());
            java.util.Date utilDate = projectTime.getWorkDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(4, sqlDate);
            pstmt.setInt(5, projectTime.getProjectId());
            int id = db.insertSQL(pstmt);
            System.out.println("\n Time insert:");
            System.out.println("Time added. pt_id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
