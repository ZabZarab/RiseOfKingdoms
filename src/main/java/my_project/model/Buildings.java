package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Repräsentiert eine Kugel (einen Kreis), der in eine Schlange eingefügt werden soll. Dabei muss jeder QueueBall immer
 * seinen Vorgänger kennen, damit er zu ihm Abstand halten kann.
 */
public abstract class Buildings extends InteractiveGraphicalObject {

    /**
     * Erzeugt einen neuen QueueBall
     * @param x Startposition x
     * @param y Startposition y
     */
    String id;
    protected int price;
    protected int borderRadius;
    protected boolean dragStreet;
    protected boolean addStreet;
    protected boolean drawStreet;
    protected int reward;

    public Buildings(int x, int y, String id){
        this.x = x;
        this.y = y;
        this.id = id;
        this.price = price;
        this.dragStreet = false;
        this.addStreet = false;
        borderRadius = 60;
    }


    /**
     * Selbsterklärend: zeichnet den die optische Repräsentation eines Ball-Objekts. Wird vom Framework automatisch aufgerufen (jeden Frame 1x).
     */
    @Override
    public void draw(DrawTool drawTool) {
        if(id!=null){
            drawTool.setCurrentColor(100, 100, 100, 50);
            drawTool.drawFilledCircle(x+width/2, y+height/2, borderRadius);
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


    @Override
    public void mousePressed(MouseEvent e) {
        dragStreet = false;
        addStreet = false;
        if(this.collidesWith(e.getX(), e.getY())){
            dragStreet = true;
            drawStreet = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        drawStreet = false;
        if(this.collidesWith(e.getX(), e.getY())){
            addStreet = true;
        }

    }

    public boolean isDragStreet() {
        return dragStreet;
    }

    public boolean isAddStreet() {
        return addStreet;
    }

    public void setDragStreet(boolean dragStreet) {
        this.dragStreet = dragStreet;
    }

    public void setAddStreet(boolean addStreet) {
        this.addStreet = addStreet;
    }

    public boolean isDrawStreet() {
        return drawStreet;
    }

    public int getBorderRadius() {
        return borderRadius;
    }
    public int getReward(){return reward;}
}
