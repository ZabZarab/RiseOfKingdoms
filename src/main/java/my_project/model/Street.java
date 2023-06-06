package my_project.model;

import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import my_project.model.HouseBig;
import my_project.model.HouseSmall;

public class Street extends InteractiveGraphicalObject {

    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public Street(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(DrawTool drawTool){
        //Street line
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawLine(x1,y1,x2,y2);
    }


}
