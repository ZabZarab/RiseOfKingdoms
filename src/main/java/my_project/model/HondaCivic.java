package my_project.model;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class HondaCivic extends Vehicle {


    public HondaCivic(double x, double y) {
        super(x, y);
        this.width = 20;
        this.height = 10;
    }

    @Override
    public void draw(DrawTool drawTool){
        //Car
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawFilledRectangle(x, y, width, height);
        drawTool.drawCircle(x+5, y+height, 5);
        drawTool.drawCircle(x+15, y+height, 5);
    }
}
