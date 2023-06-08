package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;
import KAGO_framework.model.abitur.datenstrukturen.List;

import java.awt.*;

public abstract class Vehicle extends InteractiveGraphicalObject {

    protected double time;
    /*protected double x1;
    protected double y1;
    protected double x2;
    protected double y2;*/
    protected double mX;
    protected double mY;
    protected boolean right;
    protected boolean hasTask;
    protected boolean taskCompleted;
    protected double markiplier;
    protected boolean arrived;
    protected List<Buildings> pathList;


    protected Vehicle(double x, double y, List<Buildings> pathList){
        this.x = x;
        this.y = y;
        this.time = 0;
        this.arrived = false;
        this.mX = 15;
        this.pathList = pathList;
    }
    @Override
    public void draw(DrawTool drawTool){

    }
    @Override
    public void update(double dt){
        //driveToOneHouse(100, 100, 200, 200 , dt);
    }

    public void driveToOneHouse(double x1, double y1, double x2, double y2, double dt){
        //this.yes = true;
        if(!this.collidesWith(x2,y2) && arrived == false){;
            this.mY = mX * ((y2-y1)/(x2-x1));
            if(x2 <= x1 && y2 <= y1){ //oben links
                this.x = x-dt*mX;
                this.y = y-dt*mY;
                right = false;
            }
            if(x1 <= x2 && y2 <= y1 ){ //oben rechts
                this.x = x+dt*mX;
                this.y = y-dt*mY;
                right = true;
            }
            if(y1 <= y2 && x1 <= x2 ){//unten rechts
                this.x = x+dt*mX;
                this.y = y+dt*mY;
                right = true;
            }
            if(y1 <= y2 && x2 <= x1){// unten links
                this.x = x-dt*mX;
                this.y = y-dt*mY;
                right = false;
            }
        }else {
            hasTask = false; 
            taskCompleted = true;
            arrived = true;
        }
        //System.out.println("X: " + (int) x + " Y: " +(int) y);
        //System.out.println(mY);
    }

    public double getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    /*public double getX1() {
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
    }*/

    public boolean getRight(){return right;}

    public boolean doesHasTask(){return hasTask;}

    public void setHasTask(boolean hasTask){
        this.hasTask = hasTask;
    }

    public boolean isTaskCompleted(){return taskCompleted;}

    public double getMarkiplier(){return markiplier;}
    public void setTaskCompleted(boolean taskCompleted){
        this.taskCompleted = taskCompleted;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public List<Buildings> getPathList() {
        return pathList;
    }
}

