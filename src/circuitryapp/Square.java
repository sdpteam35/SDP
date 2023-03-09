package circuitryapp;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Square {
    private double x;
    private double y;
    private double radius;
    private ImageView iv;

    public Square(double x, double y, ImageView iv){
        this.x = x;
        this.y = y;
        this.iv = iv;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    /*
    public void setColor(Color color){
        c.setFill(color);
    }
    */

    public void draw(){
        iv.setTranslateX(x);
        iv.setTranslateY(y);
    }
}
