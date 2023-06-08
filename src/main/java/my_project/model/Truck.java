package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class Truck extends Vehicle{
    private GraphicalObject truckL = new GraphicalObject("src/main/resources/graphic/truckL.png");
    private GraphicalObject truckR = new GraphicalObject("src/main/resources/graphic/truckR.png");

    public Truck(double x, double y) {
        super(x, y);
        this.width = 50;
        this.height = 33;
        this.markiplier = 1.5;
    }

    @Override
    public void draw(DrawTool drawTool){
        //Truck-Kun
        if(right == true)drawTool.drawImage(truckR.getMyImage(),x,y);
        else drawTool.drawImage(truckL.getMyImage(),x,y);
    }
}
