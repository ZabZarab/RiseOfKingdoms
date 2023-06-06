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

    public Mouse(){
        this.x = InputManager.mouseX;
        this.y = InputManager.mouseY;
    }
}
