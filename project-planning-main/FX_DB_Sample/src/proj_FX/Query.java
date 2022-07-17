package proj_FX;

public class Query {

    public static String selectPhaseAll = "SELECT phase_id, phase_desc FROM project_manager.phase ORDER BY phase_desc ASC";

    public static String selectProjectAll = "SELECT project_id, project_name, project_desc, owner FROM project_manager.project ORDER BY project_name;";

    public static String selectMemberByProject(int project_id) {
        return "SELECT member_id, member_name FROM project_manager.member WHERE project_id = " + project_id;
    }

    public static String selectRequirementAll = "SELECT member_id, member_name, project_id from project_manager.member WHERE active = 'true' ORDER BY member_name";

    public static String selectRequirementTypeAll = "SELECT req_type, req_type_desc FROM project_manager.requirement_type ORDER BY req_type;";

    public static String selectRiskStatusAll = "SELECT risk_status_id, risk_status_desc FROM project_manager.risk_status ORDER BY risk_status_id;";

    public static String insertProject = "INSERT INTO project_manager.project(project_name, project_desc, owner) VALUES(?, ?, ?)";


}
