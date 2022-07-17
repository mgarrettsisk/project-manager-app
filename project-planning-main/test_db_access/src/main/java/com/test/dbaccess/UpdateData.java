package com.test.dbaccess;

import com.projman.dbaccess.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateData {
    public static String url;
    public static String user;
    public static String password;

    public void updateProject(Project project) {

        String sql = Query.updateProject;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getProjectDesc());
            pstmt.setString(3, project.getOwner());
            pstmt.setInt(4, project.getProjectId());
            int rowsUpdated = db.updateSQL(pstmt);
            System.out.println("\n Project update:");
            System.out.println("Project updated. Records Updated = " + rowsUpdated);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateMember(Member member) {

        String sql = Query.updateMember;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, member.getActive());
            pstmt.setInt(2, member.getProjectId());
            pstmt.setInt(3, member.getMemberId());
            int rowsUpdated = db.updateSQL(pstmt);
            System.out.println("\n Member update:");
            System.out.println("Member updated. Records Updated = " + rowsUpdated);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRisk(Risk risk) {

        String sql = Query.updateRisk;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, risk.getRiskStatusId());
            pstmt.setInt(2, risk.getProjectId());
            pstmt.setInt(3, risk.getRiskId());
            int rowsUpdated = db.updateSQL(pstmt);
            System.out.println("\n Risk update:");
            System.out.println("Risk updated. Records Updated = " + rowsUpdated);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
