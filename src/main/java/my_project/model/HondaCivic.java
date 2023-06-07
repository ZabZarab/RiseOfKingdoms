package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import java.awt.*;

public class HondaCivic extends Vehicle {

    private GraphicalObject civicL = new GraphicalObject("src/main/resources/graphic/civicL.png");
    private GraphicalObject civicR = new GraphicalObject("src/main/resources/graphic/civicR.png");

    public HondaCivic(double x, double y) {
        super(x, y);
        this.width = 38;
        this.height = 18;
    }

    @Override
    public void draw(DrawTool drawTool){
        //HONDA CIVIIIIIIIC!!!!! (ohne laptop)
        if(right == true)drawTool.drawImage(civicR.getMyImage(),x,y);
        else drawTool.drawImage(civicL.getMyImage(),x,y);
    }
}
