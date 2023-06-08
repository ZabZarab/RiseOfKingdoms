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

public class Player extends InteractiveGraphicalObject {

    private int money;
    private boolean isClicked;

    public Player(){
        this.money = 10000;
        this.isClicked = false;
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(Color.RED);
        drawTool.drawText(650, 630, "Cash$$$:" + money);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }


    @Override
    public void mouseClicked(MouseEvent e){
        this.isClicked = false;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}
