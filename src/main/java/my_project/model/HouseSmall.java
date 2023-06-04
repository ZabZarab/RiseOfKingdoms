package my_project.model;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class HouseSmall extends Buildings{
    /**
     * Erzeugt einen neuen QueueBall
     *
     * @param x      Startposition x
     * @param y      Startposition y
     * @param width
     * @param height
     */
    String id;

    public HouseSmall(int x, int y, int width, int height, String id) {
        super(x, y, width, height, id);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.darkGray);
        drawTool.drawFilledRectangle(x, y, width, height);
        drawTool.drawFilledPolygon(x, y, x+width, y , x+width/2, y-height/2);
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.drawPolygon(x, y, x+width, y , x+width/2, y-height/2);
    }
}
