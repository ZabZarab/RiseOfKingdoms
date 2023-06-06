package my_project.model;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class HouseBig extends Buildings{

    /**
     * Erzeugt einen neuen QueueBall
     *
     * @param x      Startposition x
     * @param y      Startposition y
     * @param width
     * @param height
     */
    String id;

    public HouseBig(int x, int y, String id) {
        super(x, y, id);
        this.width = 45;
        this.height = 30;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.BLUE);
        drawTool.drawFilledRectangle(x, y, width, height);
        drawTool.drawFilledPolygon(x, y, x+width, y , x+width/2, y-height/2);
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawPolygon(x, y, x+width, y , x+width/2, y-height/2);
    }
}
