package proj_FX;

import com.projman.dbaccess.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import com.projman.dbaccess.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AddTimeController {

    @FXML
    public ComboBox<Project> projectsCbo = new ComboBox<>();
    public ComboBox<Member> membersCbo = new ComboBox<>();
    public ComboBox<Phase> phasesCbo = new ComboBox<>();
    public TextField enteredhours = new TextField();
    public DatePicker enteredDate = new DatePicker();

    public void initialize() {
        // Initialization code can go here.
        // The parameters url and resources can be omitted if they are not needed

        loadProjectData();
        loadPhase();

    }

    @FXML
    public void projectSelected() {

        Project project = projectsCbo.getSelectionModel().getSelectedItem();
        int projectId = project.getProjectId();
        loadMemberData(projectId);
    }

    public void addHours() {

        Project project = projectsCbo.getSelectionModel().getSelectedItem();
        int projectId = project.getProjectId();
        Member member = membersCbo.getSelectionModel().getSelectedItem();
        int memberId = member.getMemberId();
        Phase phase = phasesCbo.getSelectionModel().getSelectedItem();
        int phaseId = phase.getPhaseId();

        String sql = Query.insertProjectTime;
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setInt(1, Integer.parseInt(enteredhours.getText()));
            pstmt.setInt(2, member.getMemberId());
            pstmt.setInt(3, phase.getPhaseId());
            pstmt.setDate(4, java.sql.Date.valueOf(enteredDate.getValue()));
            pstmt.setInt(5, member.getProjectId());
            int id = db.insertSQL(pstmt);
            enteredhours.setText(null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void validateHours()
    {

        String testHours = enteredhours.getText();
        String newHours = "";
        for(int i = 0; i < testHours.length(); i++)
        {
            char c = testHours.charAt(i);
            Boolean flag = Character.isDigit(c);
            if (flag || c == '.')
                newHours+= c;

        }
        enteredhours.setText(newHours);
        enteredhours.positionCaret(newHours.length());

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadPhase() {

        String sql = Query.selectPhaseAll;
        Database db = new Database(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            phasesCbo.getItems().clear();

            ObservableList<Phase> phases = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                phases.addAll(new Phase(rs.getInt("phase_id"),
                        rs.getString("phase_desc")));
            }
            phasesCbo.setItems(phases);

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
