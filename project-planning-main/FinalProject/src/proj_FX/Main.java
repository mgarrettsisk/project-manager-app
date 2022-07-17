package proj_FX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Properties;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        Controller myController = loader.getController();

        //Set Data to FXML through controller
        myController.setProjectData();

        primaryStage.setTitle("Project Manager");
        primaryStage.setScene(new Scene(root, 566, 537));

        primaryStage.show();
        root.requestFocus();

    }

    public static void main(String[] args) throws Exception {

        Utilities util = new Utilities();
        Properties config = util.getPropsFile("src/resources/config.ini");
        System.setProperty("url", config.getProperty("url"));
        System.setProperty("user", config.getProperty("user"));
        System.setProperty("password", config.getProperty("password"));

        launch(args);
    }



}
