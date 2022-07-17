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

public class AddProjectController {

    @FXML
    public TextField projectname;
    public TextArea projectdesc;
    public TextField owner;
    public Label membernamelabel;
    public Label risklabel;
    public Label riskstatuslabel;
    public Label requirementlabel;
    public ComboBox<Member> membersCbx = new ComboBox<>();
    public ComboBox<Risk> riskdescCbo = new ComboBox<>();
    public ChoiceBox<RiskStatus> riskstatusCbx = new ChoiceBox<>();
    public ComboBox<Requirement> requirementdescCbo = new ComboBox<>();
    public Button addproject;
    public Button addremovemember;
    public Button addrisk;
    public Button addrequirement;
    public Label requirementtypelabel;
    public RadioButton radFunctional;
    public RadioButton radNonFunctional;
    public ToggleGroup tg = new ToggleGroup();

    private static int projectID = 0;

    private int saveMemberId;
    private int saveReqId;
    private int saveRiskId;

    public void initialize() {

        loadRiskStatus();
        addproject.setDisable(true);

        radFunctional.setToggleGroup(tg);
        radNonFunctional.setToggleGroup(tg);

    }

    public void addProject() {

        String sql = Query.insertProject;
        Project project = new Project(projectname.getText(), projectdesc.getText(), owner.getText());
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getProjectDesc());
            pstmt.setString(3, project.getOwner());
            projectID = db.insertSQL(pstmt);
            System.out.println("Project added. project_id = " + projectID);
            membernamelabel.setDisable(false);
            membersCbx.setDisable(false);
            membernamelabel.setDisable(false);
            risklabel.setDisable(false);
            riskdescCbo.setDisable(false);
            riskstatuslabel.setDisable(false);
            riskstatusCbx.setDisable(false);
            requirementlabel.setDisable(false);
            requirementdescCbo.setDisable(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRemoveMember() {

        if (addremovemember.getText().equals("Add")) {
            String sql = Query.insertMember;
            Member member = new Member(membersCbx.getEditor().getText(), projectID, 1);
            Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ) {
                pstmt.setString(1, member.getMemberName());
                pstmt.setInt(2, member.getProjectId());
                pstmt.setInt(3, member.getActive());
                int id = db.insertSQL(pstmt);
                System.out.println("Member added. member_id = " + id);
                loadMemberData(projectID);

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
                loadMemberData(projectID);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRisk() {

        RiskStatus riskStatus = riskstatusCbx.getSelectionModel().getSelectedItem();
        Risk risk = new Risk(riskdescCbo.getEditor().getText(), riskStatus.getRiskStatusId(), projectID);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        if (addrisk.getText().equals("Add")) {
            String sql = Query.insertRisk;
            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ) {
                pstmt.setString(1, risk.getRiskDesc());
                pstmt.setInt(2, risk.getRiskStatusId());
                pstmt.setInt(3, risk.getProjectId());
                int riskId = db.insertSQL(pstmt);
                System.out.println("Risk added. risk_id = " + riskId);
                loadRisk(projectID);
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
                loadRisk(projectID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        addrisk.setText("Add");
        addrisk.setDisable(true);
    }

    public void addRequirement() {

        Requirement req = new Requirement(requirementdescCbo.getEditor().getText(), radFunctional.isSelected() ? 1 : 2, projectID);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));
        if (addrequirement.getText().equals("Add")) {
            String sql = Query.insertRequirement;

            try (Connection conn = db.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ) {
                pstmt.setString(1, req.getReqDesc());
                pstmt.setInt(2, req.getReqType());
                pstmt.setInt(3, req.getProjId());
                int reqId = db.insertSQL(pstmt);
                System.out.println("requirement added. id = " + reqId);
                loadRequirement(projectID);

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
                loadRequirement(projectID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        addrequirement.setText("Add");
        addrequirement.setDisable(true);
    }

    @FXML
    public void memberSelected() {

        SingleSelectionModel selectionModel = membersCbx.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        addremovemember.setDisable(false);
        if (selectedIndex >= 0) {
            addremovemember.setText("Remove");
            Member member = membersCbx.getSelectionModel().getSelectedItem();
            saveMemberId = member.getMemberId();
        }
    }

    @FXML
    public void riskdescSelected() {

        SingleSelectionModel selectionModel = riskdescCbo.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        addrisk.setDisable(false);
        if (selectedIndex >= 0) {
            addrisk.setText("Change");
            Risk risk = riskdescCbo.getSelectionModel().getSelectedItem();
            riskstatusCbx.getSelectionModel().select(risk.getRiskStatusId() - 1);
            saveRiskId = risk.getRiskId();
        }
    }

    @FXML
    public void requirementdescSelected() {

        SingleSelectionModel selectionModel = requirementdescCbo.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        addrequirement.setDisable(false);
        radNonFunctional.setDisable(false);
        radFunctional.setDisable(false);
        if (selectedIndex >= 0) {
            addrequirement.setText("Change");
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
    public void projectnameChanged() {
        addproject.setDisable(projectname.getText().length() <= 0 ||
                projectdesc.getText().length() <= 0 ||
                owner.getText().length() <= 0);
    }

    @FXML
    public void projectdescChanged() {
        addproject.setDisable(projectname.getText().length() <= 0 ||
                projectdesc.getText().length() <= 0 ||
                owner.getText().length() <= 0);
    }

    @FXML
    public void ownerChanged() {
        addproject.setDisable(projectname.getText().length() <= 0 ||
                projectdesc.getText().length() <= 0 ||
                owner.getText().length() <= 0);
    }

    @FXML
    public void memberTextChanged() {
        if (membersCbx.getEditor().getText().length() > 0) {
            addremovemember.setDisable(false);
            addremovemember.setText("Add");
            saveMemberId = -1;
        }
        else
            addremovemember.setDisable(true);
    }

    @FXML
    public void riskdescCboTextChanged() {
        addrisk.setDisable(riskdescCbo.getEditor().getText().length() <= 0);
        if (riskdescCbo.getEditor().getText().length() > 0) {
            addrisk.setDisable(false);
            addrisk.setText("Add");
        }
        else
            addrisk.setDisable(true);
    }

    @FXML
    public void requirementTextChanged() {
        if (requirementdescCbo.getEditor().getText().length() > 0) {
            addrequirement.setDisable(false);
            addrequirement.setText("Add");
        }
        else
            addrequirement.setDisable(true);

        radNonFunctional.setDisable(false);
        radFunctional.setDisable(false);
    }

    public void loadMemberData(int projectId) {

        String sql = Query.selectMemberByProject(projectId);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            membersCbx.getItems().clear();

            ObservableList<Member> members = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                System.out.println(rs.getString("member_name"));
                members.addAll(new Member(rs.getInt("member_id"),rs.getString("member_name"),
                        rs.getInt("project_id"), rs.getInt("active")));
            }
            membersCbx.setItems(members);
            membersCbx.setValue(null);
            addremovemember.setText("Add");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadRiskStatus() {

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
                riskStatuses.addAll(new RiskStatus(rs.getInt("risk_status_id"),
                        rs.getString("risk_status_desc")));
            }
            riskstatusCbx.setItems(riskStatuses);

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
