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
    private boolean addSHouse = false;
    private boolean addBHouse = false;
    private int amountOfBuildings;
    private int amountOfSmallH;
    private int amountOfBigH;


    public Hotbar(){
        this.x = 0;
        this.height = 100;
        this.y = 700-height;
        this.width = Config.WINDOW_WIDTH;
        this.amountOfBigH = 0;
        this.amountOfSmallH = 0;
        this.amountOfBuildings = 0;
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

        //AmountsOf
        drawTool.drawText(x+800, y+30, "Amount of Buildings: " + amountOfBuildings);
        drawTool.drawText(x+800, y+50, "Small Houses: " + amountOfSmallH);
        drawTool.drawText(x+800, y+70, "Big Houses: " + amountOfBigH);
    }


    @Override
    public void update(double dt){

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() >= 5 && e.getX() <= 130 && e.getY() >= 605 && e.getY() <= 685 && e.getButton() == MouseEvent.BUTTON1) {
            sHB = true;
        }
        if (e.getX() >= 140 && e.getX() <= 265 && e.getY() >= 605 && e.getY() <= 685 && e.getButton() == MouseEvent.BUTTON1) {
            bHB = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(sHB == true){
            sHB = false;
            if(!addSHouse && e.getY() <580){
                addSHouse = true;
            }
        }
        if(bHB == true){
            bHB = false;
            if(!addBHouse && e.getY() <570){
                addBHouse = true;
            }
        }
    }

    public boolean getSHB(){
        return sHB;
    }

    public boolean getBHB(){
        return bHB;
    }

    public boolean isAddSHouse() {
        return addSHouse;
    }

    public void setAddSHouse(boolean addSHouse) {
        this.addSHouse = addSHouse;
    }

    public boolean isAddBHouse() {
        return addBHouse;
    }

    public void setAddBHouse(boolean addBHouse) {
        this.addBHouse = addBHouse;
    }

    public int getAmountOfBuildings() {
        return amountOfBuildings;
    }

    public void setAmountOfBuildings(int amountOfBuildings) {
        this.amountOfBuildings = amountOfBuildings;
    }

    public int getAmountOfSmallH() {
        return amountOfSmallH;
    }

    public void setAmountOfSmallH(int amountOfSmallH) {
        this.amountOfSmallH = amountOfSmallH;
    }

    public int getAmountOfBigH() {
        return amountOfBigH;
    }

    public void setAmountOfBigH(int amountOfBigH) {
        this.amountOfBigH = amountOfBigH;
    }
}
