package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.Edge;
import KAGO_framework.model.abitur.datenstrukturen.Graph;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Vertex;
import my_project.model.*;
import my_project.view.InputManager;

import java.awt.event.MouseEvent;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute
    private int idCounter; // Zählt die Anzahl der erstellten Gebäude mit, um ID's für Die Gebäude zu generieren

    // Referenzen
    private ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private Graph allBuildings; // Referenz auf ein Objekt der Klasse Graph - Verwaltet alle Gebäude als Knoten und Straßen als Kanten
    private Hotbar hotbar;
    public Mouse mouse;
    private HouseSmall houseSmall;
    private HouseBig houseBig;
    private Money money;

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;
        allBuildings = new Graph();
        mouse = new Mouse();
        money = new Money();
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        // Erstelle ein Objekt der Klasse Ball und lasse es zeichnen
        addAll();
        //Geld
        viewController.draw(money);
        viewController.register(money);
        //Maus
        viewController.register(mouse);
        //Haus klein
        houseSmall = new HouseSmall(mouse.getxPos(), mouse.getyPos(), null);
        viewController.draw(houseSmall);
        //haus groß
        houseBig = new HouseBig(mouse.getxPos(), mouse.getyPos(), null);
        viewController.draw(houseBig);
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){

        //Drag and drop vom kleinen haus
        if(hotbar.getSHB() && money.getMoney() >= 1000){
            houseSmall.setX(mouse.getxPos());
            houseSmall.setY(mouse.getyPos());
            System.out.println(mouse.getxPos());
        }else{
            houseSmall.setX(50);
            houseSmall.setY(630);
        }
        if(hotbar.isAddSHouse() && hotbar.getSHB() == false && money.getMoney() >= 1000){
            addSHouse(mouse.getxPos(), mouse.getyPos());
            hotbar.setAddSHouse(false);
            hotbar.setAmountOfSmallH(hotbar.getAmountOfSmallH()+1);
            money.setMoney(money.getMoney()-houseSmall.getPrice());
        }

        //Drag and drop vom großen haus
        if(hotbar.getBHB() && money.getMoney() >= 2000){
            houseBig.setX(mouse.getxPos());
            houseBig.setY(mouse.getyPos());
            System.out.println(mouse.getxPos());
        }else{
            houseBig.setX(175);
            houseBig.setY(630);
        }
        if(hotbar.isAddBHouse() && hotbar.getBHB() == false && money.getMoney() >= 2000){
            addBHouse(mouse.getxPos(), mouse.getyPos());
            hotbar.setAddBHouse(false);
            hotbar.setAmountOfBigH(hotbar.getAmountOfBigH()+1);
            money.setMoney(money.getMoney()-houseBig.getPrice());
        }
        hotbar.setAmountOfBuildings(hotbar.getAmountOfBigH()+hotbar.getAmountOfSmallH());
        //if( houseSmall.collidesWith(mouse) == true);

    }

    public void addAll(){
        //Alle am Anfang benötigte Objekte werden hinzugefügt und gezeichnet
        drawUI();
    }

    public void drawUI(){
        // Zeichnet alle Elemente der Nutzeroberfläche
        hotbar = new Hotbar();
        viewController.draw(hotbar);
        viewController.register(hotbar);



        drawSHouse(50,630);
        drawBHouse(175, 630);

    }

    public void addSHouse(int x, int y){
        //Erstellt und zeichnet ein Haus als Objekt und einen Knoten mit einer ID
        String id = "b" + idCounter; // Erstellt eine ID
        HouseSmall houseSmall = new HouseSmall(x,y,id);
        viewController.draw(houseSmall);
        allBuildings.addVertex(new Vertex(id));
        idCounter++;
    }
    public void addBHouse(int x, int y){
        //Erstellt und zeichnet ein Haus als Objekt und einen Knoten mit einer ID
        String id = "b" + idCounter; // Erstellt eine ID
        HouseBig houseBig = new HouseBig(x,y,id);
        viewController.draw(houseBig);
        allBuildings.addVertex(new Vertex(id));
        idCounter++;
    }

    public void drawSHouse(int x, int y){
        //Erstellt und zeichnet ein Haus als Objekt (Nur zum zeichnen --> Keine Veränderung im Graphen)
        HouseSmall houseSmall =new HouseSmall(x, y,null);
        viewController.draw(houseSmall);
    }

    public void drawBHouse(int x, int y){
        //Erstellt und zeichnet ein Haus als Objekt (Nur zum zeichnen --> Keine Veränderung im Graphen)
        HouseBig houseBig =new HouseBig(x, y,null);
        viewController.draw(houseBig);
    }

    public boolean addStreet(Buildings b1, Buildings b2){
        Vertex v1 = allBuildings.getVertex(b1.getId());
        Vertex v2 = allBuildings.getVertex(b2.getId());
        if(v2!=null && v1!=null){
            List<Vertex> b = allBuildings.getNeighbours(v1);
            b.toFirst();
            while (b.hasAccess()){
                if(b.getContent() == v2) return false;
                b.next();
            }
            allBuildings.addEdge(new Edge(v1 , v2 , 1));
            return true;
        }
        return false;
    }
}
