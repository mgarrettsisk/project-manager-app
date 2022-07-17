package proj_FX;

import com.projman.dbaccess.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ModifyProjectController {

    @FXML
    public TextArea projectdesc;
    public TextField owner;
    public Label membernamelabel;
    public Label risklabel;
    public Label riskstatuslabel;
    public Label requirementlabel;
    public ComboBox<Project> projectsCbx = new ComboBox<>();
    public ComboBox<Member> membersCbo = new ComboBox<>();
    public ChoiceBox<RiskStatus> riskstatusCbx = new ChoiceBox<>();
    public ComboBox<Risk> riskdescCbo = new ComboBox<>();
    public ComboBox<Requirement> requirementdescCbo = new ComboBox<>();
    public Button updateproject;
    public Button addremovemember;
    public Button addchangerisk;
    public Button addchangerequirement;
    public RadioButton radFunctional;
    public RadioButton radNonFunctional;
    public ToggleGroup tg = new ToggleGroup();

    private int saveMemberId;
    private int saveReqId;
    private int saveRiskId;

    public void initialize() {
        // Initialization code can go here.
        // The parameters url and resources can be omitted if they are not needed

        loadProjectData();
        LoadRiskStatus();

        addremovemember.setDisable(true);
        updateproject.setDisable(true);
        addchangerequirement.setDisable(true);
        addchangerisk.setDisable(true);
        radFunctional.setToggleGroup(tg);
        radNonFunctional.setToggleGroup(tg);
    }

    public void loadProjectData() {

        String sql = Query.selectProjectAll;
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            projectsCbx.getItems().clear();

            ObservableList<Project> projects = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                System.out.println(rs.getString("project_name"));
                projects.addAll(new Project(rs.getInt("project_id"),rs.getString("project_name"),
                        rs.getString("project_desc"), rs.getString("owner")));
            }
            projectsCbx.setItems(projects);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadMemberData(int projectId) {

        String sql = Query.selectMemberByProject(projectId);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            membersCbo.getItems().clear();

            ObservableList<Member> members = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                members.addAll(new Member(rs.getInt("member_id"),rs.getString("member_name"),
                        rs.getInt("project_id"), rs.getInt("active")));
            }
            membersCbo.setItems(members);
            membersCbo.setValue(null);
            addremovemember.setText("Add");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadRisk(int projectId) {

        String sql = Query.selectRiskByProject(projectId);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            riskdescCbo.getItems().clear();

            ObservableList<Risk> risks = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                risks.addAll(new Risk(rs.getInt("risk_id"),
                        rs.getString("risk_desc"),
                        rs.getInt("risk_status_id"),
                        projectId));
            }
            riskdescCbo.setItems(risks);
            riskdescCbo.setValue(null);
            riskstatusCbx.setValue(null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadRequirement(int projectId) {

        String sql = Query.selectRequirementByProject(projectId);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            requirementdescCbo.getItems().clear();

            ObservableList<Requirement> reqs = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                reqs.addAll(new Requirement(rs.getInt("req_id"),
                        rs.getString("req_desc"),
                        rs.getInt("req_type"),
                        projectId));
            }
            requirementdescCbo.setItems(reqs);
            requirementdescCbo.setValue(null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void projectSelected() {

        Project project = projectsCbx.getSelectionModel().getSelectedItem();
        if (project != null) {
            projectdesc.setText(project.getProjectDesc());
            owner.setText(project.getOwner());
            int projectId = project.getProjectId();
            loadMemberData(projectId);
            loadRisk(projectId);
            loadRequirement(projectId);

            if (membersCbo.getEditor().getText().length() == 0)
                addremovemember.setText("Add");
            else
                addremovemember.setText("Remove");
        }
    }

    @FXML
    public void memberSelected() {

        SingleSelectionModel selectionModel = membersCbo.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        addremovemember.setDisable(false);
        if (selectedIndex >= 0) {
            addremovemember.setText("Remove");
            Member member = membersCbo.getSelectionModel().getSelectedItem();
            saveMemberId = member.getMemberId();
        }
    }

    @FXML
    public void riskdescSelected() {

        SingleSelectionModel selectionModel = riskdescCbo.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        addchangerisk.setDisable(false);
        if (selectedIndex >= 0) {
            addchangerisk.setText("Change");
            Risk risk = riskdescCbo.getSelectionModel().getSelectedItem();
            riskstatusCbx.getSelectionModel().select(risk.getRiskStatusId() - 1);
            saveRiskId = risk.getRiskId();
        }
    }

    @FXML
    public void requirementdescSelected() {

        SingleSelectionModel selectionModel = requirementdescCbo.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();

        if (selectedIndex >= 0) {
            addchangerequirement.setDisable(false);
            addchangerequirement.setText("Change");
            Requirement req = requirementdescCbo.getSelectionModel().getSelectedItem();
            requirementdescCbo.getSelectionModel().select(req.getReqId() - 1);
            if (req.getReqType() == 1)
                radFunctional.setSelected(true);
            else
                radNonFunctional.setSelected(true);
            saveReqId = req.getReqId();
        }
    }

    @FXML
    public void projectdescChanged() {
        updateproject.setDisable(projectdesc.getText().length() <= 0 ||
                owner.getText().length() <= 0);
    }

    @FXML
    public void ownerChanged() {
        updateproject.setDisable(projectdesc.getText().length() <= 0 ||
                owner.getText().length() <= 0);
    }


    @FXML
    public void textChanged() {
        if (membersCbo.getEditor().getText().length() > 0) {
            addremovemember.setDisable(false);
            addremovemember.setText("Add");
            saveMemberId = -1;
        }
        else
            addremovemember.setDisable(true);
    }

    @FXML
    public void requirementTextChanged() {
        if (requirementdescCbo.getEditor().getText().length() > 0) {
            addchangerequirement.setDisable(false);
            addchangerequirement.setText("Add");
        }

        radNonFunctional.setDisable(false);
        radFunctional.setDisable(false);
    }

    @FXML
    public void riskdescCboTextChanged() {
        addchangerisk.setDisable(riskdescCbo.getEditor().getText().length() <= 0);
        if (riskdescCbo.getEditor().getText().length() > 0) {
            addchangerisk.setDisable(false);
            addchangerisk.setText("Add");
        }

        riskstatusCbx.setDisable(false);
    }

    public void updateProject() {

        String sql = Query.updateProject;
        Project project = projectsCbx.getSelectionModel().getSelectedItem();
        int projectId = project.getProjectId();
        String projectName = project.getProjectName();
        project = new Project(projectId, projectName, projectdesc.getText(), owner.getText());
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.NO_GENERATED_KEYS)
        ) {
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getProjectDesc());
            pstmt.setString(3, project.getOwner());
            pstmt.setInt(4, project.getProjectId());
            db.updateSQL(pstmt);
            System.out.println("Project updated.");
            loadProjectData();
            projectsCbx.getSelectionModel().select(project);
            projectsCbx.setValue(project);
            projectsCbx.setPromptText(project.getProjectName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRemoveMember() {

        Project project = projectsCbx.getSelectionModel().getSelectedItem();
        int projectId = project.getProjectId();

        if (addremovemember.getText().equals("Add")) {
            String sql = Query.insertMember;
            Member member = new Member(membersCbo.getEditor().getText(), projectId, 1);
            Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ) {
                pstmt.setString(1, member.getMemberName());
                pstmt.setInt(2, member.getProjectId());
                pstmt.setInt(3, member.getActive());
                int id = db.insertSQL(pstmt);
                System.out.println("Member added. member_id = " + id);
                loadMemberData(projectId);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String sql = Query.updateMember;
            Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.NO_GENERATED_KEYS)
            ) {
                pstmt.setInt(1, 0);
                pstmt.setInt(2, saveMemberId);
                db.updateSQL(pstmt);
                System.out.println("Member updated.");
                loadMemberData(projectId);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRisk() {

        Project project = projectsCbx.getSelectionModel().getSelectedItem();
        int projectId = project.getProjectId();
        RiskStatus riskStatus = riskstatusCbx.getSelectionModel().getSelectedItem();
        Risk risk = new Risk(riskdescCbo.getEditor().getText(), riskStatus.getRiskStatusId(), projectId);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        if (addchangerisk.getText().equals("Add")) {
            String sql = Query.insertRisk;
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ) {
                pstmt.setString(1, risk.getRiskDesc());
                pstmt.setInt(2, risk.getRiskStatusId());
                pstmt.setInt(3, risk.getProjectId());
                int riskId = db.insertSQL(pstmt);
                System.out.println("Risk added. risk_id = " + riskId);
                loadRisk(projectId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {

            String sql = Query.updateRisk;
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.NO_GENERATED_KEYS)
            ) {
                pstmt.setString(1, risk.getRiskDesc());
                pstmt.setInt(2, risk.getRiskStatusId());
                pstmt.setInt(3, saveRiskId);
                db.updateSQL(pstmt);
                System.out.println("risk changed. ");
                loadRisk(projectId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        addchangerisk.setText("Add/Change");
        addchangerisk.setDisable(true);
    }

    public void addRequirement() {

        Project project = projectsCbx.getSelectionModel().getSelectedItem();
        int projectId = project.getProjectId();
        Requirement req = new Requirement(requirementdescCbo.getEditor().getText(), radFunctional.isSelected() ? 1 : 2, projectId);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        if (addchangerequirement.getText().equals("Add")) {
            String sql = Query.insertRequirement;
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ) {
                pstmt.setString(1, req.getReqDesc());
                pstmt.setInt(2, req.getReqType());
                pstmt.setInt(3, req.getProjId());
                int reqId = db.insertSQL(pstmt);
                System.out.println("requirement added. id = " + reqId);
                loadRequirement(projectId);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {

            String sql = Query.updateRequirement;
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.NO_GENERATED_KEYS)
            ) {
                pstmt.setString(1, req.getReqDesc());
                pstmt.setInt(2, req.getReqType());
                pstmt.setInt(3, saveReqId);
                db.updateSQL(pstmt);
                System.out.println("requirement changed. ");
                loadRequirement(projectId);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        addchangerequirement.setText("Add/Change");
        addchangerequirement.setDisable(true);

    }

    public void LoadRiskStatus() {

        String sql = Query.selectRiskStatusAll;
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            riskstatusCbx.getItems().clear();

            ObservableList<RiskStatus> riskStatuses = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                System.out.println(rs.getString("risk_status_desc"));
                riskStatuses.addAll(new RiskStatus(rs.getInt("risk_status_id"),
                        rs.getString("risk_status_desc")));
            }
            riskstatusCbx.setItems(riskStatuses);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void changeNavHomeButtonPushed(ActionEvent event) throws IOException {

        Parent homeParent = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene homeScene = new Scene(homeParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(homeScene);
        window.show();
        homeParent.requestFocus();

    }

    @FXML
    public void changeNavAddProjectButtonPushed(ActionEvent event) throws IOException {

        Parent addProjectParent = FXMLLoader.load(getClass().getResource("addproject.fxml"));
        Scene addProjectScene = new Scene(addProjectParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProjectScene);
        window.show();
        addProjectParent.requestFocus();

    }

    @FXML
    public void changeNavAddTimeButtonPushed(ActionEvent event) throws IOException {

        Parent addTimeParent = FXMLLoader.load(getClass().getResource("AddTime.fxml"));
        Scene addTimeScene = new Scene(addTimeParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addTimeScene);
        window.show();
        addTimeParent.requestFocus();

    }

    @FXML
    public void changeNavViewProjectButtonPushed(ActionEvent event) throws IOException {

        Parent viewProjectParent = FXMLLoader.load(getClass().getResource("viewtime.fxml"));
        Scene viewProjectScene = new Scene(viewProjectParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewProjectScene);
        window.show();
        viewProjectParent.requestFocus();

    }

    @FXML
    public void changeNavModProjectButtonPushed(ActionEvent event) throws IOException {

        Parent modProjectParent = FXMLLoader.load(getClass().getResource("modifyproject.fxml"));
        Scene modProjectScene = new Scene(modProjectParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(modProjectScene);
        window.show();
        modProjectParent.requestFocus();

    }
}
