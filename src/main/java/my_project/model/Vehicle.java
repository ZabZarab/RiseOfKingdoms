package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;

import java.awt.*;

public abstract class Vehicle extends InteractiveGraphicalObject {

    protected double time;
    protected boolean yes;
    protected double x1;
    protected double y1;
    protected double x2;
    protected double y2;

    protected Vehicle(double x, double y){
        this.x = x;
        this.y = y;
        this.time = 0;
        this.yes = false;
    }
    @Override
    public void draw(DrawTool drawTool){

    }
    @Override
    public void update(double dt){
        if(yes == false) time = dt;
        //driveToOneHouse(100, 100, 200, 200 , dt);
    }

    public void driveToOneHouse(double x1, double y1, double x2, double y2, double dt){
        //this.yes = true;
        if(x2 <= x1 && y2 <= y1){
            this.x = x-getTime()*20;
            this.y = y-getTime()*20;
        }
        if(x1 < x2 && y2 <= y1 ){
            this.x = x+getTime()*20;
            this.y = y-getTime()*20;
        }
        if(y1 <= y2 && x1 < x2 ){
            this.x = x+getTime()*20;
            this.y = y+getTime()*20;
        }
        if(y1 <= y2 && x2 <= x1){
            this.x = x-getTime()*20;
            this.y = y-getTime()*20;
        }
        System.out.println("X: " + (int) x + " Y: " +(int) y);
    }

    public double getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
}
