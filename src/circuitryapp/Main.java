package circuitryapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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
    Parent elementSelectionRoot;
    Parent resistorSelectionRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        elementSelectionRoot = FXMLLoader.load(getClass().getResource("../fxml/elementSelectionScreen.fxml"));
        resistorSelectionRoot = FXMLLoader.load(getClass().getResource("../fxml/resistorSelectionScreen.fxml"));

        BorderPane root = new BorderPane();

        // Scene
        setScreenSize(root);

        // Menu
        setUpMenu();

        setUpGrid();

        // set up nodesPresent matrix
        nodesPresent = new int[gridWidthSquares][gridHeightSquares];
        for (int i = 0; i < nodesPresent.length; i++) {
            for (int j = 0; j < nodesPresent[i].length; j++) {
                nodesPresent[i][j] = 0;
            }
        }

        // Change cursor to whichever circuit element is being used
        // (Controlled by "circuitElement")
        // Example of resistor being shown on UI is below.
        // Will need to have an event listener likely to control which circuit element
        // is replacing the cursor.
        // Allow keyboard input -- on "r", show resistor, etc. (i.e. create "hotkeys")
        // Below can be edited out as needed :) Just trying to get something rolling.
        Image image = new Image("file:src/circuitryapp/resistor.png", 75, 75, true, true);
        ImageView iv = new ImageView(image);

        int posX = 0;
        int posY = 0;
        Square square = new Square(posX, posY, iv);
        squares[0][0] = square;
        nodesPresent[0][0] = 1;
        grid.getChildren().add(iv);
        square.draw();

        iv.setOnMousePressed(event -> pressed(event, square));
        iv.setOnMouseDragged(event -> dragged(event, square));
        iv.setOnMouseReleased(event -> release(event, square));

        // Mouse coordinates label
        mouseCoord = new Label();
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String coord = "x: " + event.getSceneX() + " y: " + event.getSceneY();
                mouseCoord.setText(coord);
                // mouseCoord.setText(matrixtoString(nodesPresent));
            }
        });

        // grid.setAlignment(Pos.CENTER);
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
        int gridX = (int) square.getX() / squareSize;
        int gridY = (int) square.getY() / squareSize;
        squares[gridX][gridY] = null;
        nodesPresent[gridY][gridX] = 0;
    }

    public void dragged(MouseEvent event, Square square) {
        square.setX(square.getX() + event.getX());
        square.setY(square.getY() + event.getY());
        square.draw();
    }

    public void release(MouseEvent event, Square square) {
        int gridX = (int) square.getX() / squareSize;
        int gridY = (int) square.getY() / squareSize;
        // square.setX(squareSize/2 + squareSize * gridX);
        // square.setY(squareSize/2 + squareSize * gridY);
        square.setX(squareSize * gridX);
        square.setY(squareSize * gridY);
        square.draw();
        squares[gridX][gridY] = square;
        nodesPresent[gridY][gridX] = 1;
    }

    public String matrixtoString(int[][] m) {
        String s = "[";
        for (int i = 0; i < m.length; i++) {
            s += "[";
            for (int j = 0; j < m[i].length; j++) {
                if (j == m[i].length - 1)
                    s += m[i][j] + "]\n";
                else
                    s += m[i][j] + ", ";
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
        addcomp.setOnAction(openComponentWindow());
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
        int screenHeight = (int) screenSize.getMaxY();
        int screenWidth = (int) screenSize.getMaxX();
        scene = new Scene(root, screenHeight, screenWidth);
    }

    public void setUpGrid() {
        grid = new Pane();
        squares = new Square[gridHeightSquares][gridWidthSquares];
        // ArrayList<Square> squares = new ArrayList<Square>();
        gridMatrix = new Rectangle[gridHeightSquares][gridWidthSquares];
        for (int i = 0; i < gridHeight; i += squareSize) {
            for (int j = 0; j < gridWidth; j += squareSize) {
                Rectangle rect = new Rectangle(i, j, squareSize, squareSize);
                rect.setFill(Color.BLACK);
                rect.setStroke(Color.WHITE);
                gridMatrix[i / squareSize][j / squareSize] = rect;
                grid.getChildren().add(rect);
            }
        }
    }

    private EventHandler<ActionEvent> openComponentWindow() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                StackPane layout = new StackPane();
                layout.getChildren().setAll(elementSelectionRoot);
                Scene scene = new Scene(layout, 600, 400);

                Stage newWindow = new Stage();
                newWindow.setTitle("Component List");
                newWindow.setScene(scene);
                newWindow.setX(200);
                newWindow.setY(100);

                Button resistor = (Button) scene.lookup("#resistor-button");
                resistor.setOnAction(e -> {
                    openResistorSelectWindow();
                    newWindow.close();
                });

                newWindow.show();
            }
        };
    }

    private void openResistorSelectWindow() {
        StackPane layout = new StackPane();
        layout.getChildren().setAll(resistorSelectionRoot);
        Scene scene = new Scene(layout, 600, 400);

        Stage newWindow = new Stage();
        newWindow.setTitle("New Resistor");
        newWindow.setScene(scene);
        newWindow.setX(200);
        newWindow.setY(100);

        TextField resistance = (TextField) scene.lookup("#resistance-input");
        resistance.setOnAction(e -> {
            String text = resistance.getText();
            if (isNumeric(text)) {
                double resistanceVal = Double.parseDouble(text);
                System.out.println(resistanceVal);
            } else {
                System.out.println("Not a number");
            }
            newWindow.close();
        });

        newWindow.show();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}