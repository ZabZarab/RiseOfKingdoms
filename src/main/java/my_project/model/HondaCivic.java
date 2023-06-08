package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import KAGO_framework.model.abitur.datenstrukturen.List;

import java.awt.*;

public class HondaCivic extends Vehicle {

    private GraphicalObject civicL = new GraphicalObject("src/main/resources/graphic/civicL.png");
    private GraphicalObject civicR = new GraphicalObject("src/main/resources/graphic/civicR.png");

    public HondaCivic(double x, double y, List<Buildings> pathList) {
        super(x, y, pathList);
        this.width = 38;
        this.height = 18;
        this.markiplier = 1.0;
    }

    @Override
    public void draw(DrawTool drawTool){
        //HONDA CIVIIIIIIIC!!!!! (ohne laptop)
        if(right == true)drawTool.drawImage(civicR.getMyImage(),x,y);
        else drawTool.drawImage(civicL.getMyImage(),x,y);
    }

}
