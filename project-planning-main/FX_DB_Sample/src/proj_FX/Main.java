package proj_FX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //Controller myController = root.getController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Controller myController = loader.getController();

        //Set Data to FXML through controller
        myController.setProjectData();

        primaryStage.setTitle("Project Manager");
        primaryStage.setScene(new Scene(root, 300, 275));

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
