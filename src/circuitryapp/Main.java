package circuitryapp;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private Node circuitElement;
    private Scene scene;
    private int squareSize = 75;
    private int gridHeightSquares = 10;
    private int gridWidthSquares = 5;
    private int gridHeight = squareSize * gridHeightSquares;
    private int gridWidth = squareSize * gridWidthSquares;
    private int numSquares = gridHeightSquares * gridWidthSquares;
    private int gridSize = squareSize * gridHeightSquares;
    Rectangle[][] gridMatrix;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        // Scene
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        int screenHeight = (int)screenSize.getMaxY();
        int screenWidth = (int)screenSize.getMaxX();
        scene = new Scene(root, screenHeight, screenWidth);
        
        // Menu
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

        Pane grid = new Pane();
        //GridPane grid = new GridPane();
        /*
        for (int i=0; i<gridWidth; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.CENTER);
            grid.getRowConstraints().add(rowConstraints);

            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(colConstraints);
        }
        */


        // Grid
        ArrayList<Square> squares = new ArrayList<Square>();
        gridMatrix = new Rectangle[gridHeightSquares][gridWidthSquares];
        for(int i = 0; i < gridHeight; i += squareSize) {
            for(int j = 0; j < gridWidth; j += squareSize) {
                Rectangle rect = new Rectangle(i, j, squareSize, squareSize);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                gridMatrix[i/squareSize][j/squareSize] = rect;
                //squares[i/squareSize][j/squareSize] = new Square()
                grid.getChildren().add(rect);
            }
        }

        int radius = squareSize / 3;
        Circle c = new Circle(radius);
        c.setFill(Color.YELLOW);
        c.setStroke(Color.BLUE);
        int posX = squareSize / 2;
        int posY = squareSize / 2;
        Square square = new Square(posX, posY, radius, c);
        squares.add(square);
        grid.getChildren().add(c);
        square.draw();

        c.setOnMousePressed(event -> pressed(event, square));
        c.setOnMouseDragged(event -> dragged(event, square));
        c.setOnMouseReleased(event -> release(event, square));

        // Mouse coordinates label
        Label mouseCoord = new Label();
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                String coord = "x: " + event.getSceneX() + " y: " + event.getSceneY();
                mouseCoord.setText(coord);
            }
        });

        //Change cursor to whichever circuit element is being used
        //(Controlled by "circuitElement")
        //Example of resistor being shown on UI is below.
        //Will need to have an event listener likely to control which circuit element is replacing the cursor.
        //Allow keyboard input -- on "r", show resistor, etc. (i.e. create "hotkeys")
        //Below can be edited out as needed :) Just trying to get something rolling.
        Image image = new Image("file:src/circuitryapp/resistor.jpg", 75, 75, true, true);
        scene.setCursor(new ImageCursor(image));

        //grid.setAlignment(Pos.CENTER);
        root.setCenter(grid);
        root.setTop(menubar);
        root.setBottom(new StackPane(mouseCoord));
        primaryStage.setTitle("Circuitry Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void pressed(MouseEvent event, Square square) {
        square.setColor(Color.ORANGE);
    }

    public void dragged(MouseEvent event, Square square) {
        square.setX(square.getX() + event.getX());
        square.setY(square.getY() + event.getY());
        square.draw();
    }

    public void release(MouseEvent event, Square square){
        int gridX = (int)square.getX() / squareSize;
        int gridY = (int)square.getY() / squareSize;
        square.setX(squareSize/2 + squareSize * gridX);
        square.setY(squareSize/2 + squareSize * gridY);
        square.draw();
    }

}