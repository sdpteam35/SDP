package circuitryapp;

import circuitryapp.components.Component;
import circuitryapp.components.Resistor;
import circuitryapp.components.Component.ComponentType;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    Square[][] squares;
    int[][] nodesPresent;
    MenuBar menubar;
    Label mouseCoord;
    Pane grid;
    Circuit circuit;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        // Scene
        setScreenSize(root);
        
        // Menu
        setUpMenu();

        setUpGrid();

        //set up nodesPresent matrix
        nodesPresent = new int[gridWidthSquares][gridHeightSquares];
        for(int i = 0; i < nodesPresent.length; i++) {
            for(int j = 0; j < nodesPresent[i].length; j++) {
                nodesPresent[i][j] = 0;
            }
        }

        circuit = new Circuit();

        //Change cursor to whichever circuit element is being used
        //(Controlled by "circuitElement")
        //Example of resistor being shown on UI is below.
        //Will need to have an event listener likely to control which circuit element is replacing the cursor.
        //Allow keyboard input -- on "r", show resistor, etc. (i.e. create "hotkeys")
        //Below can be edited out as needed :) Just trying to get something rolling.
        Image image = new Image("file:src/circuitryapp/resistor_clear_bkgrd.png", 75, 75, true, true);
        //Image image = new Image("file:src/circuitryapp/resistor2.jpg", 75, 75, true, true);
        ImageView iv = new ImageView(image);
        Resistor r = new Resistor("resistor1", ComponentType.Resistor, 0);

        Square square = addComponentToGrid(r, iv);

        iv.setOnMousePressed(event -> pressed(event, square));
        iv.setOnMouseDragged(event -> dragged(event, square));
        iv.setOnMouseReleased(event -> release(event, square));

        // Mouse coordinates label
        mouseCoord = new Label();
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                String coord = "x: " + event.getSceneX() + " y: " + event.getSceneY();
                mouseCoord.setText(coord);
                //mouseCoord.setText(matrixtoString(nodesPresent));
            }
        });

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
        int gridX = (int)square.getX() / squareSize;
        int gridY = (int)square.getY() / squareSize;
        squares[gridX][gridY] = null;
        nodesPresent[gridY][gridX] = 0;
    }

    public void dragged(MouseEvent event, Square square) {
        square.setX(square.getX() + event.getX());
        square.setY(square.getY() + event.getY());
        square.draw();
    }

    public void release(MouseEvent event, Square square){
        int gridX = (int)square.getX() / squareSize;
        int gridY = (int)square.getY() / squareSize;
        //square.setX(squareSize/2 + squareSize * gridX);
        //square.setY(squareSize/2 + squareSize * gridY);
        square.setX(squareSize * gridX);
        square.setY(squareSize * gridY);
        square.draw();
        squares[gridX][gridY] = square;
        nodesPresent[gridY][gridX] = 1;
    }

    public String matrixtoString(int[][] m) {
        String s = "[";
        for(int i = 0; i < m.length; i++) {
            s += "[";
            for(int j = 0; j < m[i].length; j++) {
                if(j == m[i].length - 1) s += m[i][j] + "]\n";
                else s += m[i][j] + ", ";
            }
        }
        s += "]";
        return s;
    }

    public void setUpMenu() {
        menubar = new MenuBar();
        Menu FileMenu = new Menu("File");
        MenuItem filemenu1 = new MenuItem("New");
        MenuItem filemenu2 = new MenuItem("Open");
        MenuItem filemenu3 = new MenuItem("Save");
        Menu EditMenu = new Menu("Edit");
        MenuItem addcomp = new MenuItem("Add component...");
        Menu SelectionMenu = new Menu("Selection");
        Menu ViewMenu = new Menu("View");
        Menu RunMenu = new Menu("Run");
        MenuItem runmenu1 = new MenuItem("Run");
        Menu HelpMenu = new Menu("Help");
        FileMenu.getItems().addAll(filemenu1, filemenu2, filemenu3);
        EditMenu.getItems().addAll(addcomp);
        RunMenu.getItems().addAll(runmenu1);
        menubar.getMenus().addAll(FileMenu, EditMenu, SelectionMenu, ViewMenu, RunMenu, HelpMenu);
    }

    public void setScreenSize(BorderPane root) {
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        int screenHeight = (int)screenSize.getMaxY();
        int screenWidth = (int)screenSize.getMaxX();
        scene = new Scene(root, screenHeight, screenWidth);
    }

    public void setUpGrid() {
        grid = new Pane();
        squares = new Square[gridHeightSquares][gridWidthSquares];
        //ArrayList<Square> squares = new ArrayList<Square>();
        gridMatrix = new Rectangle[gridHeightSquares][gridWidthSquares];
        for(int i = 0; i < gridHeight; i += squareSize) {
            for(int j = 0; j < gridWidth; j += squareSize) {
                Rectangle rect = new Rectangle(i, j, squareSize, squareSize);
                rect.setFill(Color.WHITE);
                //rect.setStroke(Color.BLACK);
                gridMatrix[i/squareSize][j/squareSize] = rect;
                grid.getChildren().add(rect);
            }
        }
    }

    public Square addComponentToGrid(Component c, ImageView iv) {
        Square square = new Square(0, 0, iv, c);
        squares[0][0] = square;
        nodesPresent[0][0] = 1;
        grid.getChildren().add(iv);
        square.draw();
        circuit.addNode(c);
        return square;
    }
}