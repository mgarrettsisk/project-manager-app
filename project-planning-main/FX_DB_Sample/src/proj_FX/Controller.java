package proj_FX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static String url = "jdbc:mysql://localhost:3306/project_manager?useSSL=false&useUnicode=true&serverTimezone=UTC";
    private static String user = "kim";
    private static String password = "test1234";

    //Query query = new Query();

    @FXML
    public ComboBox<Project> projectsCbo = new ComboBox<>();
    public ComboBox<Phase> phasesCbo = new ComboBox<>();
    public TextField projectname;
    public TextField projectdesc;
    public TextField owner;

    @FXML
    public void projectsAction() {

        Project project = projectsCbo.getSelectionModel().getSelectedItem();
        projectname.setText(project.getProjectName());
        projectdesc.setText(project.getProjectDesc());
        owner.setText(project.getOwner());
        int projectId = project.getProjectId();
    }

    public void phasesAction() {

        Phase phase = phasesCbo.getSelectionModel().getSelectedItem();
        String phaseDesc = phase.getPhaseDesc();
        int phaseId = phase.getPhaseId();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setProjectData() {

        String sql = Query.selectProjectAll;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            projectsCbo.getItems().clear();

            ObservableList<Project> projects = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                System.out.println(rs.getString("project_name"));
                //projectsCbo.getItems().addAll(rs.getString("project_name"));
                projects.addAll(new Project(rs.getInt("project_id"),rs.getString("project_name"),
                        rs.getString("project_desc"), rs.getString("owner")));
            }
            projectsCbo.setItems(projects);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setPhaseData() {

        String sql = Query.selectProjectAll;
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            phasesCbo.getItems().clear();

            ObservableList<Phase> phases = FXCollections.observableArrayList();
            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                System.out.println(rs.getString("phase_desc"));
                phases.addAll(new Phase(rs.getInt("phase_id"),
                        rs.getString("phase_desc")));
            }
            phasesCbo.setItems(phases);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void addProject() {

        String sql = Query.insertProject;
        Project project = new Project(projectname.getText(), projectdesc.getText(), owner.getText());
        Database db = new Database(url, user, password);

        try (Connection conn = db.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getProjectDesc());
            pstmt.setString(3, project.getOwner());
            int id = db.insertSQL(pstmt);
            System.out.println("Project added. project_id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
