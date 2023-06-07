package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import java.awt.*;

/**
 * Repräsentiert eine Kugel (einen Kreis), der in eine Schlange eingefügt werden soll. Dabei muss jeder QueueBall immer
 * seinen Vorgänger kennen, damit er zu ihm Abstand halten kann.
 */
public abstract class Buildings extends GraphicalObject {

    /**
     * Erzeugt einen neuen QueueBall
     * @param x Startposition x
     * @param y Startposition y
     */
    String id;
    protected int price;
    protected boolean unplacapleCircle = false; //if true, draws red radius blabla

    public Buildings(int x, int y, String id){
        this.x = x;
        this.y = y;
        this.id = id;
        this.price = price;
        radius = 60;
    }


    /**
     * Selbsterklärend: zeichnet den die optische Repräsentation eines Ball-Objekts. Wird vom Framework automatisch aufgerufen (jeden Frame 1x).
     */
    @Override
    public void draw(DrawTool drawTool) {
        if(id!=null){
            drawTool.setCurrentColor(100, 100, 100, 50);
            drawTool.drawFilledCircle(x+width/2, y+height/2, radius);
        }


    }

    /**
     * Wird mit jedem Frame vom Framework aufgerufen und dient zur Manipulation des Objekts im Verlauf
     * der Zeit.
     * @param dt die Sekunden, die seit dem letzten Aufruf von update vergangen sind
     */
    @Override
    public void update(double dt){

    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setUnplacapleCircle(boolean unplacapleCircle) {
        this.unplacapleCircle = unplacapleCircle;
    }

    public boolean isUnplacapleCircle() {
        return unplacapleCircle;
    }
}
