package my_project.model;

import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import java.awt.*;
import java.awt.event.MouseEvent;
import my_project.model.HouseBig;
import my_project.model.HouseSmall;
import my_project.view.InputManager;

public class Mouse extends InteractiveGraphicalObject{

    private int xPos;
    private int yPos;

    public Mouse(){

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xPos = e.getX(); // X-Position der Maus
        yPos = e.getY(); // X-Position der Maus
        System.out.println("fu");
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
