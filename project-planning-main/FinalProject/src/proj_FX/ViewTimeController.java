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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewTimeController{


    @FXML
    public ComboBox<Project> projectsCbo = new ComboBox<>();
    public CheckBox chkMember = new CheckBox();
    public CheckBox chkPhase = new CheckBox();
    public CheckBox chkDate = new CheckBox();
    public TableView<ReportMemberTime> tvTimeReport = new TableView<>();
    public TableView<ReportPhaseTime> tvPhaseTimeReport = new TableView<>();
    public TableView<ReportRequirement> tvReqReport = new TableView<>();
    public TableView<ReportRisk> tvRiskReport = new TableView<>();

    public void initialize() {
        // Initialization code can go here.
        // The parameters url and resources can be omitted if they are not needed

        loadProjectData();
    }

    @FXML
    public void projectSelected() {

        Project project = projectsCbo.getSelectionModel().getSelectedItem();
        int projectId = project.getProjectId();
        tvRiskReport.setVisible(true);
        tvReqReport.setVisible(true);
        tvTimeReport.setVisible(true);
        loadReport(projectId);
        loadRequirement(projectId);
        loadRisk(projectId);
    }



    public void loadProjectData() {

        String sql = Query.selectProjectAll;
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            projectsCbo.getItems().clear();

            ObservableList<Project> projects = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                System.out.println(rs.getString("project_name"));
                projects.addAll(new Project(rs.getInt("project_id"),rs.getString("project_name"),
                        rs.getString("project_desc"), rs.getString("owner")));
            }
            projectsCbo.setItems(projects);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void loadReport(int projectId) {

        tvTimeReport.getItems().clear();
        tvTimeReport.getColumns().clear();
        tvPhaseTimeReport.getItems().clear();
        tvPhaseTimeReport.getColumns().clear();
        tvTimeReport.setVisible(false);
        tvPhaseTimeReport.setVisible(false);

        TableColumn memberCol = new TableColumn("Member Name");
        TableColumn phaseCol = new TableColumn("Phase");
        TableColumn dateCol = new TableColumn("Work Date");
        TableColumn hoursCol = new TableColumn("Hours");

        if (chkMember.isSelected()) {
            tvTimeReport.setVisible(true);
            memberCol.setCellValueFactory(
                    new PropertyValueFactory<ReportMemberTime, String>("memberName")
            );
            phaseCol.setCellValueFactory(
                    new PropertyValueFactory<ReportMemberTime, String>("phaseDesc")
            );
            dateCol.setCellValueFactory(
                    new PropertyValueFactory<ReportMemberTime, String>("workDate")
            );
            hoursCol.setCellValueFactory(
                    new PropertyValueFactory<ReportMemberTime, String>("hours")
            );
        }
        else {
            tvPhaseTimeReport.setVisible(true);
            phaseCol.setCellValueFactory(
                    new PropertyValueFactory<ReportPhaseTime, String>("phaseDesc")
            );
            dateCol.setCellValueFactory(
                    new PropertyValueFactory<ReportPhaseTime, String>("workDate")
            );
            hoursCol.setCellValueFactory(
                    new PropertyValueFactory<ReportPhaseTime, String>("hours")
            );
        }

        String sql;
        if (chkMember.isSelected()) {
            if (chkPhase.isSelected() && chkDate.isSelected())
                sql = Query.reportMemberAllTime(projectId);
            else if (chkPhase.isSelected())
                sql = Query.reportMemberPhaseTime(projectId);
            else if (chkDate.isSelected())
                sql = Query.reportMemberDateTime(projectId);
            else
                sql = Query.reportMemberTime(projectId);
        }
        else if (chkPhase.isSelected() && chkDate.isSelected())
            sql = Query.reportPhaseDateHours(projectId);
        else if (chkPhase.isSelected())
            sql = Query.reportPhaseHours(projectId);
        else if (chkDate.isSelected())
            sql = Query.reportDateHours(projectId);
        else
            sql = Query.reportHours(projectId);

        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            ObservableList<ReportMemberTime> memberTimes = FXCollections.observableArrayList();
            ObservableList<ReportPhaseTime> phaseTimes = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                if (chkMember.isSelected()) {
                    if (chkPhase.isSelected() && chkDate.isSelected()) {
                        memberTimes.addAll(new ReportMemberTime(rs.getString("member_name"),
                                rs.getString("phase_desc"), rs.getInt("hours"),
                                rs.getDate("work_date")));
                    } else if (chkPhase.isSelected()) {
                        memberTimes.addAll(new ReportMemberTime(rs.getString("member_name"),
                                rs.getString("phase_desc"), rs.getInt("hours")));
                    } else if (chkDate.isSelected()) {
                        memberTimes.addAll(new ReportMemberTime(rs.getString("member_name"),
                                 rs.getDate("work_date"),rs.getInt("hours")));
                    } else  {
                        memberTimes.addAll(new ReportMemberTime(rs.getString("member_name"),
                                rs.getInt("hours")));

                    }
                    tvTimeReport.setItems(memberTimes);
                } else {
                    if (chkPhase.isSelected() && chkDate.isSelected()) {
                        phaseTimes.addAll(new ReportPhaseTime(rs.getString("phase_desc"),
                                rs.getInt("hours"),
                                rs.getDate("work_date")));
                    } else if (chkPhase.isSelected()) {
                        phaseTimes.addAll(new ReportPhaseTime(rs.getString("phase_desc"),
                                rs.getInt("hours")));
                    } else if (chkDate.isSelected()) {
                        phaseTimes.addAll(new ReportPhaseTime(rs.getDate("work_date"),
                                rs.getInt("hours")));
                    } else {
                        phaseTimes.addAll(new ReportPhaseTime(rs.getInt("hours")));
                    }
                    tvPhaseTimeReport.setItems(phaseTimes);
                }
            }

            if (chkMember.isSelected()) {
                memberCol.prefWidthProperty().bind(tvTimeReport.widthProperty().multiply(0.3));
                phaseCol.prefWidthProperty().bind(tvTimeReport.widthProperty().multiply(0.3));
                hoursCol.prefWidthProperty().bind(tvTimeReport.widthProperty().multiply(0.2));
                dateCol.prefWidthProperty().bind(tvTimeReport.widthProperty().multiply(0.2));
                if (chkPhase.isSelected() && chkDate.isSelected()) {
                    tvTimeReport.getColumns().addAll(memberCol, phaseCol, hoursCol, dateCol);
                }
                else if (chkPhase.isSelected()) {
                    tvTimeReport.getColumns().addAll(memberCol, phaseCol, hoursCol);
                }
                else if (chkDate.isSelected()) {
                    tvTimeReport.getColumns().addAll(memberCol, hoursCol, dateCol);
                }
                else {
                    tvTimeReport.getColumns().addAll(memberCol, hoursCol);
                }
            }
            else {
                memberCol.prefWidthProperty().bind(tvPhaseTimeReport.widthProperty().multiply(0.3));
                phaseCol.prefWidthProperty().bind(tvPhaseTimeReport.widthProperty().multiply(0.3));
                hoursCol.prefWidthProperty().bind(tvPhaseTimeReport.widthProperty().multiply(0.2));
                dateCol.prefWidthProperty().bind(tvPhaseTimeReport.widthProperty().multiply(0.2));
                if (chkPhase.isSelected() && chkDate.isSelected()) {
                    tvPhaseTimeReport.getColumns().addAll(phaseCol, hoursCol, dateCol);
                }
                else if (chkPhase.isSelected()) {
                    tvPhaseTimeReport.getColumns().addAll(phaseCol, hoursCol);
                }
                else if (chkDate.isSelected()) {
                    tvPhaseTimeReport.getColumns().addAll(hoursCol, dateCol);
                }
                else {
                    tvPhaseTimeReport.getColumns().addAll(hoursCol);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadRequirement(int projectId) {

        tvReqReport.getItems().clear();
        tvReqReport.getColumns().clear();

        TableColumn reqCol = new TableColumn("Requirement Description");
        TableColumn typeCol = new TableColumn("Type");
        reqCol.prefWidthProperty().bind(tvReqReport.widthProperty().multiply(0.7));
        typeCol.prefWidthProperty().bind(tvReqReport.widthProperty().multiply(0.3));

        reqCol.setCellValueFactory(
                new PropertyValueFactory<ReportRequirement,String>("reqDesc")
        );
        typeCol.setCellValueFactory(
                new PropertyValueFactory<ReportRequirement,String>("reqType")
        );

        String sql = Query.reportRequirement(projectId);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            ObservableList<ReportRequirement> reqs = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                reqs.addAll(new ReportRequirement(rs.getString("req_desc"),
                        rs.getString("req_type_desc")));
            }
            tvReqReport.setItems(reqs);
            tvReqReport.getColumns().addAll(reqCol, typeCol);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadRisk(int projectId) {

        tvRiskReport.getItems().clear();
        tvRiskReport.getColumns().clear();

        TableColumn riskCol = new TableColumn("Risk Description");
        TableColumn statusCol = new TableColumn("Risk Status");
        riskCol.prefWidthProperty().bind(tvRiskReport.widthProperty().multiply(0.7));
        statusCol.prefWidthProperty().bind(tvRiskReport.widthProperty().multiply(0.3));

        riskCol.setCellValueFactory(
                new PropertyValueFactory<ReportMemberTime,String>("riskDesc")
        );
        statusCol.setCellValueFactory(
                new PropertyValueFactory<ReportMemberTime,String>("riskStatusDesc")
        );

        String sql = Query.reportRisk(projectId);
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            ObservableList<ReportRisk> risks = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                risks.addAll(new ReportRisk(rs.getString("risk_desc"),
                        rs.getString("risk_status_desc")));
            }
            tvRiskReport.setItems(risks);
            tvRiskReport.getColumns().addAll(riskCol, statusCol);

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
