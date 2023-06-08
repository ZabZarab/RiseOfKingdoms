package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class Main extends Buildings{
    GraphicalObject MAIN = new GraphicalObject("src/main/resources/graphic/main.png");
    public Main(int x, int y, String id){
        super(x,y,id);
        this.width = 46;
        this.height = 94;
        borderRadius = 90;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(MAIN.getMyImage(),x,y);
    }
}
