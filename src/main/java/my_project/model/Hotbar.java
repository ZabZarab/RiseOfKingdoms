package my_project.model;

import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import my_project.control.ProgramController;
import my_project.model.HouseBig;
import my_project.model.HouseSmall;
import my_project.view.InputManager;


public class Hotbar extends InteractiveGraphicalObject {

    private boolean sHB = false;
    private boolean bHB = false;

    private Mouse mouse;


    public Hotbar(){
        this.x = 0;
        this.height = 100;
        this.y = 700-height;
        this.width = Config.WINDOW_WIDTH;
        mouse = new Mouse();
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
        //Cars
        drawTool.drawRectangle(x+140+25+125, y+5 , 125, 80);
        drawTool.drawRectangle(x+140+150+125+10, y+5 , 125, 80);
        //Mouse Position
    }


    @Override
    public void update(double dt){

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(mouse.getxPos());
        if (e.getX() >= 5 && e.getX() <= 130 && e.getY() >= 605 && e.getY() <= 685 && e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Mutter*innen");
            sHB = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    public boolean getSHB(){
        return sHB;
    }

    public boolean getBHB(){
        return bHB;
    }
    


}
