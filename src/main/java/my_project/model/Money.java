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

public class Money extends InteractiveGraphicalObject {

    private int money;

    public Money(){
        this.money = 10000;
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(Color.RED);
        drawTool.drawText(1000, 630, "Cash$$$:" + money);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
