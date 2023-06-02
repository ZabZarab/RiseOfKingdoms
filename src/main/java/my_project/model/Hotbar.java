package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import KAGO_framework.Config;

import java.awt.*;

public class Hotbar extends GraphicalObject {

    public Hotbar(){
        this.x = 0;
        this.height = 100;
        this.y = 700-height;
        this.width = 1200;
    }

    @Override
    public void draw(DrawTool drawTool){
        //Hotbar
        drawTool.setCurrentColor(Color.GRAY);
        drawTool.drawFilledRectangle(x, y, width, height);
        //Buttons
        //House
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawRectangle(x+5, y+5 , 125, 80);
        drawTool.drawRectangle(x+140, y+5 , 125, 80);

        drawTool.drawRectangle(x+5, y+5 , 125, 80);
        drawTool.drawRectangle(x+140, y+5 , 125, 80);
    }


    @Override
    public void update(double dt){

    }
}
