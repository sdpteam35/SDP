package circuitryapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1600, 2560);
        
        MenuBar menubar = new MenuBar();

        Menu FileMenu = new Menu("File");
        MenuItem filemenu1 = new MenuItem("New");
        MenuItem filemenu2 = new MenuItem("Save");

        Menu EditMenu = new Menu("Edit");
        MenuItem addcomp = new MenuItem("Add component...");

        Menu SelectionMenu = new Menu("Selection");

        Menu ViewMenu = new Menu("View");

        Menu RunMenu = new Menu("Run");
        MenuItem runmenu1 = new MenuItem("Run");

        Menu HelpMenu = new Menu("Help");

        FileMenu.getItems().addAll(filemenu1, filemenu2);
        EditMenu.getItems().addAll(addcomp);
        RunMenu.getItems().addAll(runmenu1);

        menubar.getMenus().addAll(FileMenu, EditMenu, SelectionMenu, ViewMenu, RunMenu, HelpMenu);

        root.setTop(menubar);
        primaryStage.setTitle("Circuitry Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}