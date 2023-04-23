package circuitryapp;

import circuitryapp.components.Battery;
import circuitryapp.components.Component;
import circuitryapp.components.Node;
import circuitryapp.components.Resistor;
import circuitryapp.components.Wire;
import circuitryapp.components.Component.ComponentType;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    Parent nodeSelectionRoot;
    private int nodeNum;
    private int resistorNum;
    private int batteryNum;
    private int wireNum;

    @Override
    public void start(Stage primaryStage) throws Exception {
        elementSelectionRoot = FXMLLoader.load(getClass().getResource("elementSelectionScreen.fxml"));
        resistorSelectionRoot = FXMLLoader.load(getClass().getResource("resistorSelectionScreen.fxml"));
        batterySelectionRoot = FXMLLoader.load(getClass().getResource("batterySelectionScreen.fxml"));
        BorderPane root = new BorderPane();

        nodeNum = 1;
        resistorNum = 1;
        batteryNum = 1;
        wireNum = 1;

        circuit = new Circuit();

        // Scene
        setScreenSize(root);

        // Menu
        setUpMenu();

        grid = new Pane();
        setUpGrid();

        // grid.setAlignment(Pos.CENTER);
        root.setCenter(grid);
        root.setTop(menubar);
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
        squares[gridY][gridX] = null;
    }

    public void dragged(MouseEvent event, Square square) {
        square.setX(square.getX() + event.getX());
        square.setY(square.getY() + event.getY());
        square.draw();
    }

    public void release(MouseEvent event, Square square) {
        int gridX = (int) square.getX() / squareSize;
        int gridY = (int) square.getY() / squareSize;
        square.setX(squareSize * gridX);
        square.setY(squareSize * gridY);
        square.draw();
        squares[gridY][gridX] = square;
        // System.out.println("gridX: " + gridX);
        // System.out.println("gridY: " + gridY);
    }

    public void clicked(MouseEvent event, Component component) {
        if (event.getButton() == MouseButton.SECONDARY) {
            System.out.println("Right button clicked");
            if (component.getType() == ComponentType.Resistor) {
                openResistorSelectWindow(component.getID());
            } else if (component.getType() == ComponentType.Battery) {
                openBatterySelectWindow(component.getID());
            }
        } else {
            ImageView iv = component.getSquare().getImageView();
            iv.setRotate(iv.getRotate() + 90);
            if (iv.getRotate() == 360)
                iv.setRotate(0);
            System.out.println(iv.getRotate());
        }
    }

    public String squareMatrixtoString(Square[][] m) {
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
        MenuItem clearGrid = new MenuItem("Clear Grid");
        clearGrid.setOnAction(clearGrid());
        MenuItem preset = new MenuItem("Load Preset Circuit");
        preset.setOnAction(loadPreset());
        Menu SelectionMenu = new Menu("Selection");
        Menu ViewMenu = new Menu("View");
        Menu RunMenu = new Menu("Run");
        MenuItem runmenu1 = new MenuItem("Run");
        runmenu1.setOnAction(run());
        Menu HelpMenu = new Menu("Help");
        FileMenu.getItems().addAll(filemenu1, filemenu2, filemenu3);
        EditMenu.getItems().addAll(addcomp, clearGrid, preset);
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
        squares = new Square[gridHeightSquares][gridWidthSquares];
        gridMatrix = new Rectangle[gridWidthSquares][gridHeightSquares];
        for (int i = 0; i < gridHeight; i += squareSize) {
            for (int j = 0; j < gridWidth; j += squareSize) {
                Rectangle rect = new Rectangle(j, i, squareSize, squareSize);
                rect.setFill(Color.WHITE);
                //rect.setStroke(Color.BLACK);
                gridMatrix[j / squareSize][i / squareSize] = rect;
                grid.getChildren().add(rect);
            }
        }
    }

    private EventHandler<ActionEvent> loadPreset() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Battery b = new Battery("battery1", 1);
                Image batteryImage = new Image("file:src/circuitryapp/battery_clear_bkgrd.png", 75, 75, true, true);
                ImageView batteryIV = new ImageView(batteryImage);
                addComponentToGrid(b, batteryIV, 1, 2);
                circuit.addNode(b);
                Tooltip t1 = new Tooltip("ID: battery1 \nVoltage: 1.0 V");
                Tooltip.install(batteryIV, t1);

                Resistor r = new Resistor("resistor1", 1);
                Image resistorImage = new Image("file:src/circuitryapp/resistor_clear_bkgrd.png", 75, 75, true, true);
                ImageView resistorImageView = new ImageView(resistorImage);
                addComponentToGrid(r, resistorImageView, 3, 3);
                circuit.addNode(r);
                Tooltip t2 = new Tooltip("ID: resistor1 \nResistance: 1.0 Ω");
                Tooltip.install(resistorImageView, t2);

                Node node1 = new Node("node1");
                Image node1Image = new Image("file:src/circuitryapp/node.png", 75, 75, true, true);
                ImageView node1ImageView = new ImageView(node1Image);
                addComponentToGrid(node1, node1ImageView, 1, 1);
                node1ImageView.setRotate(90);
                circuit.addNode(node1);

                Node node2 = new Node("node2");
                Image node2Image = new Image("file:src/circuitryapp/node.png", 75, 75, true, true);
                ImageView node2ImageView = new ImageView(node2Image);
                addComponentToGrid(node2, node2ImageView, 1, 4);
                node2ImageView.setRotate(180);
                circuit.addNode(node2);

                Node node3 = new Node("node3");
                Image node3Image = new Image("file:src/circuitryapp/node.png", 75, 75, true, true);
                ImageView node3ImageView = new ImageView(node3Image);
                addComponentToGrid(node3, node3ImageView, 3, 1);
                circuit.addNode(node3);

                Node node4 = new Node("node4");
                Image node4Image = new Image("file:src/circuitryapp/node.png", 75, 75, true, true);
                ImageView node4ImageView = new ImageView(node4Image);
                addComponentToGrid(node4, node4ImageView, 3, 4);
                node4ImageView.setRotate(270);
                circuit.addNode(node4);

                Wire wire1 = new Wire("wire1");
                Image wire1Image = new Image("file:src/circuitryapp/wire.png", 75, 75, true, true);
                ImageView wire1ImageView = new ImageView(wire1Image);
                addComponentToGrid(wire1, wire1ImageView, 1, 3);
                circuit.addWire(wire1);

                Wire wire2 = new Wire("wire2");
                Image wire2Image = new Image("file:src/circuitryapp/wire.png", 75, 75, true, true);
                ImageView wire2ImageView = new ImageView(wire2Image);
                addComponentToGrid(wire2, wire2ImageView, 2, 1);
                wire2ImageView.setRotate(90);
                circuit.addWire(wire2);

                Wire wire3 = new Wire("wire3");
                Image wire3Image = new Image("file:src/circuitryapp/wire.png", 75, 75, true, true);
                ImageView wire3ImageView = new ImageView(wire3Image);
                addComponentToGrid(wire3, wire3ImageView, 2, 4);
                wire3ImageView.setRotate(90);
                circuit.addWire(wire3);

                Wire wire4 = new Wire("wire4");
                Image wire4Image = new Image("file:src/circuitryapp/wire.png", 75, 75, true, true);
                ImageView wire4ImageView = new ImageView(wire4Image);
                addComponentToGrid(wire4, wire4ImageView, 3, 2);
                circuit.addWire(wire4);
            }
        };
    }

    public Square addComponentToGrid(Component c, ImageView iv) {
        Square square = new Square(0, 0, iv, c);
        int x = 0;
        int y = 0;
        boolean conditionMet = false;
        if (!(squares[0][0] == null)) {
            for (int i = 0; i < squares.length; i++) {
                for (int j = 1; j < squares[i].length; j++) {
                    if (squares[i][j] == null) {
                        squares[i][j] = square;
                        x = j;
                        y = i;
                        conditionMet = true;
                        break;
                    }
                }
                if (conditionMet)
                    break;
            }
        } else
            squares[0][0] = square;
        c.setSquare(square);
        square.setX(squareSize * x);
        square.setY(squareSize * y);
        grid.getChildren().add(iv);
        square.draw();
        return square;
    }

    public void addComponentToGrid(Component c, ImageView iv, int i, int j) {
        Square square = new Square(0, 0, iv, c);
        squares[i][j] = square;
        c.setSquare(square);
        square.setX(squareSize * j);
        square.setY(squareSize * i);
        grid.getChildren().add(iv);
        square.draw();
        iv.setOnMousePressed(event -> pressed(event, square));
        iv.setOnMouseDragged(event -> dragged(event, square));
        iv.setOnMouseReleased(event -> release(event, square));
        iv.setOnMouseClicked(event -> clicked(event, c));
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

                Button wire = (Button) scene.lookup("#wire-button");
                Image wireImage = new Image("file:src/circuitryapp/wire.png", 75, 75, true, true);
                ImageView wireImageView = new ImageView(wireImage);
                wire.setGraphic(wireImageView);
                wire.setContentDisplay(ContentDisplay.TOP);
                wire.setOnAction(e -> {
                    createWire();
                    newWindow.close();
                });

                Button node = (Button) scene.lookup("#node-button");
                Image nodeImage = new Image("file:src/circuitryapp/node.png", 75, 75, true, true);
                ImageView nodeImageView = new ImageView(nodeImage);
                node.setGraphic(nodeImageView);
                node.setContentDisplay(ContentDisplay.TOP);
                node.setOnAction(e -> {
                    createNode();
                    newWindow.close();
                });

                newWindow.show();
            }
        };
    }

    private void openResistorSelectWindow() {
        openResistorSelectWindow("");
    }

    private void openResistorSelectWindow(String id) {
        StackPane layout = new StackPane();
        layout.getChildren().setAll(resistorSelectionRoot);
        Scene scene = new Scene(layout, 600, 400);

        Stage newWindow = new Stage();
        newWindow.setTitle("New Resistor");
        newWindow.setScene(scene);
        newWindow.setX(200);
        newWindow.setY(100);

        final double[] resistanceVal = { 0 };
        TextField resistanceField = (TextField) scene.lookup("#resistance-input");
        resistanceField.setText("");

        if (id != "") {
            try {
                Resistor r = (Resistor) circuit.GetPartById(id);
                resistanceField.setText(Double.toString(r.getResistance()));

                resistanceField.setOnAction(e -> {
                    String resistanceInput = resistanceField.getText();
                    if (isNumeric(resistanceInput)) {
                        resistanceVal[0] = Double.parseDouble(resistanceInput);
                        r.changeResistance(resistanceVal[0]);
                        Tooltip t = new Tooltip("ID: " + id + "\nResistance: " + resistanceVal[0] + " Ω");
                        ImageView iv = r.getSquare().getImageView();
                        Tooltip.install(iv, t);
                    } else {
                        System.out.println("Not a number");
                    }
                    newWindow.close();
                });
            } catch (Exception ex) {
                System.out.println("Invalid id");
            }
        } else {
            resistanceField.setOnAction(e -> {
                String resistanceInput = resistanceField.getText();
                if (isNumeric(resistanceInput)) {
                    resistanceVal[0] = Double.parseDouble(resistanceInput);
                } else {
                    System.out.println("Not a number");
                }
                Image image = new Image("file:src/circuitryapp/resistor_clear_bkgrd.png", 75, 75, true, true);
                ImageView iv = new ImageView(image);
                String resId = "resistor" + resistorNum;
                resistorNum++;
                Resistor r = new Resistor(resId, resistanceVal[0]);
                Square square = addComponentToGrid(r, iv);
                iv.setOnMousePressed(event -> pressed(event, square));
                iv.setOnMouseDragged(event -> dragged(event, square));
                iv.setOnMouseReleased(event -> release(event, square));
                iv.setOnMouseClicked(event -> clicked(event, r));
                Tooltip t = new Tooltip("ID: " + resId + "\nResistance: " + resistanceVal[0] + " Ω");
                Tooltip.install(iv, t);
                circuit.addNode(r);
                newWindow.close();
            });
        }
        newWindow.show();
    }

    private void openBatterySelectWindow() {
        openBatterySelectWindow("");
    }

    private void openBatterySelectWindow(String id) {
        StackPane layout = new StackPane();
        layout.getChildren().setAll(batterySelectionRoot);
        Scene scene = new Scene(layout, 600, 400);

        Stage newWindow = new Stage();
        newWindow.setTitle("New Battery");
        newWindow.setScene(scene);
        newWindow.setX(200);
        newWindow.setY(100);

        final double[] voltageVal = { 0 };
        TextField voltageField = (TextField) scene.lookup("#voltage-input");
        voltageField.setText("");

        if (id != "") {
            try {
                Battery b = (Battery) circuit.GetPartById(id);
                voltageField.setText(Double.toString(b.getVoltage()));

                voltageField.setOnAction(e -> {
                    String voltageInput = voltageField.getText();
                    if (isNumeric(voltageInput)) {
                        voltageVal[0] = Double.parseDouble(voltageInput);
                        b.changeVoltage(voltageVal[0]);
                        Tooltip t = new Tooltip("ID: " + id + "\nVoltage: " + voltageVal[0] + " V");
                        ImageView iv = b.getSquare().getImageView();
                        Tooltip.install(iv, t);
                    } else {
                        System.out.println("Not a number");
                    }
                    newWindow.close();
                });
            } catch (Exception ex) {
                System.out.println("Invalid id");
            }
        } else {
            voltageField.setOnAction(e -> {
                String voltageInput = voltageField.getText();
                if (isNumeric(voltageInput)) {
                    voltageVal[0] = Double.parseDouble(voltageInput);
                } else {
                    System.out.println("Not a number");
                }
                Image image = new Image("file:src/circuitryapp/battery_clear_bkgrd.png", 75, 75, true, true);
                ImageView iv = new ImageView(image);
                String batId = "battery" + batteryNum;
                batteryNum++;
                Battery b = new Battery(batId, voltageVal[0]);
                Square square = addComponentToGrid(b, iv);
                iv.setOnMousePressed(event -> pressed(event, square));
                iv.setOnMouseDragged(event -> dragged(event, square));
                iv.setOnMouseReleased(event -> release(event, square));
                iv.setOnMouseClicked(event -> clicked(event, b));
                Tooltip t = new Tooltip("ID: " + b.getID() + "\nVoltage: " + b.getVoltage() + " V");
                Tooltip.install(iv, t);
                circuit.addNode(b);
                newWindow.close();
            });
        }

        newWindow.show();
    }

    public void createWire() {
        Image image = new Image("file:src/circuitryapp/wire.png", 75, 75, true, true);
        ImageView iv = new ImageView(image);
        String id = "wire" + wireNum;
        wireNum++;
        Wire w = new Wire(id);
        Square square = addComponentToGrid(w, iv);
        iv.setOnMousePressed(event -> pressed(event, square));
        iv.setOnMouseDragged(event -> dragged(event, square));
        iv.setOnMouseReleased(event -> release(event, square));
        iv.setOnMouseClicked(event -> clicked(event, w));
    }

    public void createNode() {
        String id = "node" + nodeNum;
        nodeNum++;
        Image image = new Image("file:src/circuitryapp/node.png", 75, 75, true, true);
        ImageView iv = new ImageView(image);
        Node n = new Node(id);
        Square square = addComponentToGrid(n, iv);
        iv.setOnMousePressed(event -> pressed(event, square));
        iv.setOnMouseDragged(event -> dragged(event, square));
        iv.setOnMouseReleased(event -> release(event, square));
        iv.setOnMouseClicked(event -> clicked(event, n));
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private EventHandler<ActionEvent> clearGrid() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                grid.getChildren().removeAll(grid.getChildren());
                setUpGrid();
            }
        };
    }

    private EventHandler<ActionEvent> run() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                connectComponents();
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 300, 200);

                Stage newWindow = new Stage();
                newWindow.setTitle("Results");
                newWindow.setScene(scene);
                newWindow.setX(500);
                newWindow.setY(300);
                layout.setAlignment(Pos.CENTER);

                Text voltage = new Text("Total voltage: " + circuit.getTotalVoltage() + " V"); 
                Text resistance = new Text("Total resistance: " + circuit.getTotalResistance() + " Ω");
                Text current = new Text("Total current: " + circuit.getTotalCurrent() + " A");

                layout.getChildren().addAll(voltage, resistance, current);

                newWindow.show();
            }
        };
    }

    private void connectComponents() {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                Square s = squares[i][j];
                if (s != null) {
                    if (s.getComponent().getType() == ComponentType.Battery) {
                        cycle(s, i, j);
                    }
                }
            }
        }
    }

    private void cycle(Square battery, int row, int col) {
        int i = row;
        int j = col;
        Square s = battery;
        int prevI = i;
        int prevJ = j;
        do {
            Component c = s.getComponent();
            if (c.getType() == ComponentType.Node) {
                if (s.getImageView().getRotate() == 0) {
                    if (j < prevJ) {
                        c.setInComp(squares[i][j + 1].getComponent());
                        c.setOutComp(squares[i - 1][j].getComponent());
                        prevI = i;
                        prevJ = j;
                        i--;
                    } else {
                        c.setInComp(squares[i - 1][j].getComponent());
                        c.setOutComp(squares[i][j + 1].getComponent());
                        prevJ = j;
                        prevI = i;
                        j++;
                    }
                } else if (s.getImageView().getRotate() == 90) {
                    if (i < prevI) {
                        c.setInComp(squares[i + 1][j].getComponent());
                        c.setOutComp(squares[i][j + 1].getComponent());
                        prevJ = j;
                        prevI = i;
                        j++;
                    } else {
                        c.setInComp(squares[i][j + 1].getComponent());
                        c.setOutComp(squares[i + 1][j].getComponent());
                        prevI = i;
                        prevJ = j;
                        i++;
                    }
                } else if (s.getImageView().getRotate() == 180) {
                    if (j > prevJ) {
                        c.setInComp(squares[i][j - 1].getComponent());
                        c.setOutComp(squares[i + 1][j].getComponent());
                        prevI = i;
                        prevJ = j;
                        i++;
                    } else {
                        c.setInComp(squares[i + 1][j].getComponent());
                        c.setOutComp(squares[i][j - 1].getComponent());
                        prevJ = j;
                        prevI = i;
                        j--;
                    }
                } else {
                    if (i > prevI) {
                        c.setInComp(squares[i - 1][j].getComponent());
                        c.setOutComp(squares[i][j - 1].getComponent());
                        prevJ = j;
                        prevI = i;
                        j--;
                    } else {
                        c.setInComp(squares[i][j - 1].getComponent());
                        c.setOutComp(squares[i - 1][j].getComponent());
                        prevI = i;
                        prevJ = j;
                        i--;
                    }
                }
            } else {
                if (s.getImageView().getRotate() == 0 || s.getImageView().getRotate() == 180) {
                    if (j >= prevJ) {
                        c.setInComp(squares[i][j - 1].getComponent());
                        c.setOutComp(squares[i][j + 1].getComponent());
                        prevJ = j;
                        prevI = i;
                        j++;
                    } else {
                        c.setInComp(squares[i][j + 1].getComponent());
                        c.setOutComp(squares[i][j - 1].getComponent());
                        prevJ = j;
                        prevI = i;
                        j--;
                    }
                } else {
                    if (i > prevI) {
                        c.setInComp(squares[i - 1][j].getComponent());
                        c.setOutComp(squares[i + 1][j].getComponent());
                        prevI = i;
                        prevJ = j;
                        i++;
                    } else {
                        c.setInComp(squares[i + 1][j].getComponent());
                        c.setOutComp(squares[i - 1][j].getComponent());
                        prevI = i;
                        prevJ = j;
                        i--;
                    }
                }
            }
            if (c.getType() == ComponentType.Wire) {
                circuit.addWire((Wire) c);
            } else
                circuit.addNode(c);
            s = squares[i][j];
        } while (!s.equals(battery));
    }
}
