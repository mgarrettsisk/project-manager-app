package sample;

import ,javafx.fxml.FXML;
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
    public ComboBox projectsCbo;
    public TextField projectname;
    public TextField projectdesc;
    public TextField owner;

    @FXML
    public void projectsAction() {
        projectname.setText(projectsCbo.getSelectionModel().getSelectedItem().toString());
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

            while (rs.next()) {  // loop
                // Now add the comboBox addAll statement
                System.out.println(rs.getString("project_name"));
                projectsCbo.getItems().addAll(rs.getString("project_name"));
                projectsCbo.setId(rs.getString("project_id"));
            }
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
