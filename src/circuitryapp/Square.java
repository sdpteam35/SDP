package circuitryapp;

import circuitryapp.components.Component;
import circuitryapp.components.Wire;
import javafx.scene.image.ImageView;

public class Square {
    private double x;
    private double y;
    private ImageView iv;
    private Component c;
    private Wire w;

    public Square(double x, double y, ImageView iv, Component c){
        this.x = x;
        this.y = y;
        this.iv = iv;
        this.c = c;
        this.w = null;
    }

    public Square(double x, double y, ImageView iv, Wire w){
        this.x = x;
        this.y = y;
        this.iv = iv;
        this.w = w;
        this.c = null;
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

    public Component getComponent() {
        return c;
    }

    public Wire getWire() {
        return w;
    }

    public void draw(){
        iv.setTranslateX(x);
        iv.setTranslateY(y);
    }
}
