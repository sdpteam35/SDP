package circuitryapp;

import circuitryapp.components.Battery;
import circuitryapp.components.Component;
import circuitryapp.components.Resistor;
import circuitryapp.components.Component.ComponentType;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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

    private Scene scene;
    private int squareSize = 75;
    private int gridHeightSquares = 5;
    private int gridWidthSquares = 10;
    private int gridHeight = squareSize * gridHeightSquares;
    private int gridWidth = squareSize * gridWidthSquares;
    Rectangle[][] gridMatrix;
    Square[][] squares;
    MenuBar menubar;
    Label mouseCoord;
    Pane grid;
    Circuit circuit;
    Parent elementSelectionRoot;
    Parent resistorSelectionRoot;
    Parent batterySelectionRoot;
    //double resistanceVal;


    @Override
    public void start(Stage primaryStage) throws Exception{
        elementSelectionRoot = FXMLLoader.load(getClass().getResource("elementSelectionScreen.fxml"));
        resistorSelectionRoot = FXMLLoader.load(getClass().getResource("resistorSelectionScreen.fxml"));
        batterySelectionRoot = FXMLLoader.load(getClass().getResource("batterySelectionScreen.fxml"));
        BorderPane root = new BorderPane();

        circuit = new Circuit();

        // Scene
        setScreenSize(root);
        
        // Menu
        setUpMenu();

        setUpGrid();

        // Mouse coordinates label
        mouseCoord = new Label();
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                String coord = "x: " + event.getSceneX() + " y: " + event.getSceneY();
                mouseCoord.setText(coord);
                //mouseCoord.setText(squareMatrixtoString(squares));
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
        squares[gridY][gridX] = null;
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
        squares[gridY][gridX] = square;
    }

    public void clicked(MouseEvent event, Square square) {
        ImageView iv = square.getImageView();
        iv.setRotate(iv.getRotate() + 90);
    }

    public String squareMatrixtoString(Square[][] m) {
        String s = "[";
        for(int i = 0; i < m.length; i++) {
            s += "[";
            for(int j = 0; j < m[i].length; j++) {
                /*
                if(m[i][j] == null) s += "None, ";
                else s += "Square, ";
                if(j == m[i].length - 1) s += "]\n";
                */
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
        int screenHeight = (int)screenSize.getMaxY();
        int screenWidth = (int)screenSize.getMaxX();
        scene = new Scene(root, screenHeight, screenWidth);
    }

    public void setUpGrid() {
        grid = new Pane();
        squares = new Square[gridHeightSquares][gridWidthSquares];
        //ArrayList<Square> squares = new ArrayList<Square>();
        gridMatrix = new Rectangle[gridWidthSquares][gridHeightSquares];
        for(int i = 0; i < gridHeight; i += squareSize) {
            for(int j = 0; j < gridWidth; j += squareSize) {
                Rectangle rect = new Rectangle(j, i, squareSize, squareSize);
                rect.setFill(Color.WHITE);
                //rect.setStroke(Color.BLACK);
                gridMatrix[j/squareSize][i/squareSize] = rect;
                grid.getChildren().add(rect);
            }
        }
    }

    public Square addComponentToGrid(Component c, ImageView iv) {
        Square square = new Square(0, 0, iv, c);
        int x = 0;
        int y = 0;
        boolean conditionMet = false;
        if(!(squares[0][0] == null)) {
            for(int i = 0; i < squares.length; i++) {
                for(int j = 1; j < squares[i].length; j++) {
                    if(squares[i][j] == null) {
                        squares[i][j] = square;
                        x = j;
                        y = i;
                        conditionMet = true;
                        break;
                    }
                }
                if(conditionMet) break;
            }
        }
        else squares[0][0] = square;
        square.setX(squareSize * x);
        square.setY(squareSize * y);
        grid.getChildren().add(iv);
        square.draw();
        circuit.addNode(c);
        Tooltip t;
        if(c.getType() == ComponentType.Resistor) {
            Resistor r = (Resistor)square.getComponent();
            t = new Tooltip("Resistance: " + r.getResistance());
        }
        else {
            Battery b = (Battery)square.getComponent();
            t = new Tooltip("Voltage: " + b.getVoltage());
        }
        Tooltip.install(iv, t);
        return square;
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
                Image resistorImage = new Image("file:src/circuitryapp/resistor_clear_bkgrd.png", 75, 75, true, true);
                ImageView resistorImageView = new ImageView(resistorImage);
                resistor.setGraphic(resistorImageView);
                resistor.setContentDisplay(ContentDisplay.TOP);
                resistor.setOnAction(e -> {
                    openResistorSelectWindow();
                    newWindow.close();
                });

                Button battery = (Button) scene.lookup("#battery-button");
                Image batteryImage = new Image("file:src/circuitryapp/battery_clear_bkgrd.png", 75, 75, true, true);
                ImageView batteryImageView = new ImageView(batteryImage);
                battery.setGraphic(batteryImageView);
                battery.setContentDisplay(ContentDisplay.TOP);
                battery.setOnAction(e -> {
                    openBatterySelectWindow();
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

        final double[] resistanceVal = {0};
        TextField resistance = (TextField) scene.lookup("#resistance-input");
        resistance.setOnAction(e -> {
            String text = resistance.getText();
            if (isNumeric(text)) {
                resistanceVal[0] = Double.parseDouble(text);
                System.out.println(resistanceVal);
            } else {
                System.out.println("Not a number");
            }
            Image image = new Image("file:src/circuitryapp/resistor_clear_bkgrd.png", 75, 75, true, true);
            ImageView iv = new ImageView(image);
            Resistor r = new Resistor("resistor1", resistanceVal[0]);
            Square square = addComponentToGrid(r, iv);
            iv.setOnMousePressed(event -> pressed(event, square));
            iv.setOnMouseDragged(event -> dragged(event, square));
            iv.setOnMouseReleased(event -> release(event, square));
            iv.setOnMouseClicked(event -> clicked(event, square));
            newWindow.close();
        });

        newWindow.show();
    }

    private void openBatterySelectWindow() {
        StackPane layout = new StackPane();
        layout.getChildren().setAll(batterySelectionRoot);
        Scene scene = new Scene(layout, 600, 400);

        Stage newWindow = new Stage();
        newWindow.setTitle("New Resistor");
        newWindow.setScene(scene);
        newWindow.setX(200);
        newWindow.setY(100);

        final double[] voltageVal = {0};
        TextField voltage = (TextField) scene.lookup("#voltage-input");
        voltage.setOnAction(e -> {
            String text = voltage.getText();
            if (isNumeric(text)) {
                voltageVal[0] = Double.parseDouble(text);
                System.out.println(voltageVal);
            } else {
                System.out.println("Not a number");
            }
            Image image = new Image("file:src/circuitryapp/battery_clear_bkgrd.png", 75, 75, true, true);
            ImageView iv = new ImageView(image);
            Battery b = new Battery("battery", voltageVal[0]);
            Square square = addComponentToGrid(b, iv);
            iv.setOnMousePressed(event -> pressed(event, square));
            iv.setOnMouseDragged(event -> dragged(event, square));
            iv.setOnMouseReleased(event -> release(event, square));
            iv.setOnMouseClicked(event -> clicked(event, square));
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