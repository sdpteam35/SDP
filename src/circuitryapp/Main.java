package circuitryapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/main.fxml"));
        Parent root = loader.load();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)screenSize.getHeight();
        int width = (int)screenSize.getWidth();
        Scene scene = new Scene(root, height, width);
        scene.getStylesheets().add(getClass().getResource("../css/styling.css").toExternalForm());
        
        VBox mainVbox = (VBox)scene.lookup("#vbox-main");
        mainVbox.setFillWidth(true);

        primaryStage.setTitle("Circuitry");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}