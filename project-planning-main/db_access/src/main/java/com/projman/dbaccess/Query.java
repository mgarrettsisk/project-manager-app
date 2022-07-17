package com.projman.dbaccess;

public class Query {

    //-- SELECT queries
    // Get all phases.
    public static String selectPhaseAll =
            "SELECT phase_id, phase_desc " +
            "FROM project_manager.phase " +
            "ORDER BY phase_desc ASC";
    // Get all projects.
    public static String selectProjectAll =
            "SELECT project_id, project_name, project_desc, owner " +
            "FROM project_manager.project " +
            "ORDER BY project_name;";
    // Get Member by project id.
    public static String selectMemberByProject(int project_id) {
        return "SELECT member_id, member_name, project_id, active " +
                "FROM project_manager.member " +
                "WHERE project_id = " + project_id +
                " AND active = 1";
    }
    // Get all requirements.
    public static String selectRequirementAll =
            "SELECT req_id, req_desc, req_type, project_id " +
            "FROM project_manager.requirement";
    // Get requirements by project id.
    public static String selectRequirementByProject(int project_id) {
        return "SELECT req_id, req_desc, req_type " +
                "FROM project_manager.requirement r " +
                "WHERE project_id = " + project_id;
    }
    // Get risk by project id.
    public static String selectRiskByProject(int project_id) {
        return "SELECT risk_id, risk_desc, rs.risk_status_id " +
                "FROM project_manager.risk r " +
                "INNER JOIN project_manager.risk_status rs " +
                "ON r.risk_status_id = rs.risk_status_id " +
                "WHERE project_id = " + project_id;
    }
    // Get all requirement types.
    public static String selectRequirementTypeAll =
            "SELECT req_type, req_type_desc " +
            "FROM project_manager.requirement_type " +
            "ORDER BY req_type;";
    // Get all risk statuses.
    public static String selectRiskStatusAll =
            "SELECT risk_status_id, risk_status_desc " +
            "FROM project_manager.risk_status " +
            "ORDER BY risk_status_id;";
    // Get all project type by project id.
    public static String selectProjectTimeByProject(int project_id) {
        return "SELECT pt_id, hours, member_id, phase_id, work_date, project_id " +
                "FROM project_manager.project_time " +
                "WHERE project_id = " + project_id;
    }

    //-- INSERT queries.
    // Insert a project.
    public static String insertProject =
            "INSERT INTO project_manager.project " +
            "(project_name, project_desc, owner) " +
            "VALUES(?, ?, ?)";
    // Insert a member.
    public static String insertMember =
            "INSERT INTO project_manager.member " +
            "(member_name, project_id, active) " +
            "VALUES(?, ?, ?)";
    // Insert a risk.
    public static String insertRisk =
            "INSERT INTO project_manager.risk " +
            "(risk_desc, risk_status_id, project_id) " +
            "VALUES (?, ?, ?)";
    // Insert a requirement.
    public static String insertRequirement =
            "INSERT INTO project_manager.requirement " +
                    "(req_desc, req_type, project_id) " +
                    "VALUES (?, ?, ?)";
    //Insert Time
    public static String insertProjectTime =
            "INSERT INTO project_manager.project_time " +
            "(hours, member_id, phase_id, work_date, project_id) " +
            "VALUES (?, ?, ?, ?, ?);";


    //-- UPDATE queries.
    // Update a project.
    public static String updateProject =
            "UPDATE project_manager.project " +
            "SET " +
            "project_name = ?, " +
            "project_desc = ?, " +
            "owner = ? " +
            "WHERE project_id = ?;";
    // Update a member.
    public static String updateMember =
            "UPDATE project_manager.member " +
            "SET " +
            "active = ? " +
            "WHERE member_id = ?;";
    // Update a risk.
    public static String updateRisk =
            "UPDATE project_manager.risk " +
            "SET " +
            "risk_status_id = ? " +
            "WHERE project_id = ? AND risk_id = ?;";

    // Report queries
    public static String reportMemberAllTime(int project_id) {
        return "SELECT m.member_name, p.phase_desc, pt.work_date, SUM(pt.hours) as hours " +
                "FROM project_manager.project_time pt " +
                "   left outer join project_manager.member m on pt.member_id = m.member_id " +
                "   inner join project_manager.phase p on pt.phase_id = p.phase_id " +
                "where pt.project_id =  " + project_id +
                " group by m.member_name, p.phase_desc, pt.work_date;";
    }
    public static String reportMemberDateTime(int project_id) {
        return "SELECT m.member_name, pt.work_date, SUM(pt.hours) as hours " +
                "FROM project_manager.project_time pt " +
                "   inner join project_manager.member m on pt.member_id = m.member_id " +
                "where pt.project_id =  " + project_id +
                " group by m.member_name, pt.work_date;";
    }

    public static String reportMemberPhaseTime(int project_id) {
        return "SELECT m.member_name, p.phase_desc, SUM(pt.hours) as hours " +
                "FROM project_manager.project_time pt " +
                "   inner join project_manager.member m on pt.member_id = m.member_id " +
                "   inner join project_manager.phase p on pt.phase_id = p.phase_id " +
                "where pt.project_id =  " + project_id +
                " group by m.member_name, p.phase_desc;";
    }

    public static String reportMemberTime(int project_id) {
        return "SELECT m.member_name" +
                ", ifnull(SUM(pt.hours), 0) as hours " +
                "FROM project_manager.member m " +
                "left outer join project_manager.project_time pt on pt.member_id = m.member_id " +
                "where m.project_id =  " + project_id +
                " group by m.member_name;";
    }

    public static String reportPhaseDateHours(int project_id) {
        return "SELECT p.phase_desc, pt.work_date, SUM(pt.hours) as hours " +
                "FROM project_manager.project_time pt " +
                "   inner join project_manager.phase p on pt.phase_id = p.phase_id " +
                "where pt.project_id =  " + project_id +
                " group by p.phase_desc, pt.work_date;";
    }

    public static String reportDateHours(int project_id) {
        return "SELECT pt.work_date, SUM(pt.hours) as hours " +
                "FROM project_manager.project_time pt " +
                "   inner join project_manager.member m on pt.member_id = m.member_id " +
                "where pt.project_id =  " + project_id +
                " group by pt.work_date;";
    }

    public static String reportPhaseHours(int project_id) {
        return "SELECT p.phase_desc, SUM(pt.hours) as hours " +
                "FROM project_manager.project_time pt " +
                "   inner join project_manager.phase p on pt.phase_id = p.phase_id " +
                "where pt.project_id =  " + project_id +
                " group by p.phase_desc;";
    }

    public static String reportHours(int project_id) {
        return "SELECT SUM(pt.hours) as hours " +
                "FROM project_manager.project_time pt " +
                "where pt.project_id =  " + project_id +
                ";";
    }

    public static String reportRisk(int project_id) {
        return "SELECT risk_id, risk_desc, rs.risk_status_desc " +
                "FROM project_manager.risk r " +
                "INNER JOIN project_manager.risk_status rs " +
                "ON r.risk_status_id = rs.risk_status_id " +
                "WHERE project_id = " + project_id;
    }

    public static String reportRequirement(int project_id) {
        return "SELECT req_id, req_desc, rt.req_type_desc " +
                "FROM project_manager.requirement r " +
                "INNER JOIN project_manager.requirement_type rt " +
                "ON r.req_type = rt.req_type " +
                "WHERE project_id = " + project_id;
    }

}



