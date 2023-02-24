package circuitryapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private Node circuitElement;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        int screenHeight = (int)screenSize.getMaxY();
        int screenWidth = (int)screenSize.getMaxX();
        Scene scene = new Scene(root, screenHeight, screenWidth);
        
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

        GridPane grid = new GridPane();
        int height = 5;
        int width = 10;
        grid.setAlignment(Pos.CENTER);
        Rectangle[][] gridMatrix = new Rectangle[height][width];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                Rectangle rect = new Rectangle(75, 75);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                gridMatrix[i][j] = rect;
                grid.add(rect, j, i);
            }
        }

        //Change cursor to whichever circuit element is being used
        //(Controlled by "circuitElement")
        //Example of resistor being shown on UI is below.
        //Will need to have an event listener likely to control which circuit element is replacing the cursor.
        //Allow keyboard input -- on "r", show resistor, etc. (i.e. create "hotkeys")
        //Below can be edited out as needed :) Just trying to get something rolling.
        Image image = new Image("file:src/circuitryapp/resistor.jpg", 75, 75, true, true);
        scene.setCursor(new ImageCursor(image));

        root.setCenter(grid);
        root.setTop(menubar);
        primaryStage.setTitle("Circuitry Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}