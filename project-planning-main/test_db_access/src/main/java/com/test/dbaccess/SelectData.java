package com.test.dbaccess;

import com.projman.dbaccess.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectData {

    public static String url;
    public static String user;
    public static String password;

    public void selectProject() {

        String sql = Query.selectProjectAll;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            System.out.println("\n Project");
            while (rs.next()) {  // loop
                System.out.println(rs.getInt("project_id") + ", " +
                        rs.getString("project_name") + ", " +
                        rs.getString("project_desc") + ", " +
                        rs.getString("owner"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectMember(int projectId) {

        String sql = Query.selectMemberByProject(projectId);
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            System.out.println("\n Member by projectId: " + projectId);
            while (rs.next()) {  // loop
                System.out.println(rs.getInt("member_id") + ", " +
                        rs.getString("member_name") + ", " +
                        //rs.getInt("project_id") + ", " +
                        rs.getBoolean("active"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectRisk(int projectId) {

        String sql = Query.selectRiskByProject(projectId);
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            System.out.println("\n Risk by projectId: " + projectId);
            while (rs.next()) {  // loop
                System.out.println(rs.getInt("risk_id") + ", " +
                        rs.getString("risk_desc") + ", " +
                        rs.getString("risk_status_desc"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectRequirement(int projectId) {

        String sql = Query.selectRequirementByProject(1);
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            System.out.println("\n Requirement by projectId: " + projectId);
            while (rs.next()) {  // loop
                System.out.println(rs.getInt("req_id") + ", " +
                        rs.getString("req_desc") + ", " +
                        rs.getString("req_type_desc"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void selectPhase() {

        String sql = Query.selectPhaseAll;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            System.out.println("\n Phase");
            while (rs.next()) {  // loop
                System.out.println(rs.getInt("phase_id") + ", " +
                    rs.getString("phase_desc"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectRiskStatus() {

        String sql = Query.selectRiskStatusAll;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            System.out.println("\n Risk Status");
            while (rs.next()) {  // loop
                System.out.println(rs.getInt("risk_status_id") + ", " +
                        rs.getString("risk_status_desc"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectProjectTime(int projectId) {

        String sql = Query.selectProjectTimeByProject(1);
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            System.out.println("\n Project Time by projectId: " + projectId);
            while (rs.next()) {  // loop
                System.out.println(rs.getInt("pt_id") + ", " +
                        rs.getInt("hours") + ", " +
                        rs.getInt("member_id") + ", " +
                        rs.getInt("phase_id") + ", " +
                        rs.getDate("work_date"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
