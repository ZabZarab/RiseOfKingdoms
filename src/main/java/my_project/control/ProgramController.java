package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.Graph;
import KAGO_framework.model.abitur.datenstrukturen.Vertex;
import my_project.model.Buildings;
import my_project.model.Hotbar;
import my_project.model.HouseSmall;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute


    // Referenzen
    private ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private Graph allBuildings; // Referenz auf ein Objekt der Klasse Graph - Verwaltet alle Gebäude als Knoten und Straßen als Kanten
    private int idCounter; // Zählt die Anzahl der erstellten Gebäude mit, um ID's für Die Gebäude zu generieren

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
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        // Erstelle ein Objekt der Klasse Ball und lasse es zeichnen
        addAll();
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){

    }

    public void addAll(){
        //Alle am Anfang benötigte Objekte werden hinzugefügt und gezeichnet
        drawUI();
    }

    public void drawUI(){
        // Zeichnet alle Elemente der Nutzeroberfläche
        Hotbar hotbar = new Hotbar();
        viewController.draw(hotbar);

        drawHouse(50, 640, 30, 20);
    }

    public void addHouse(int x, int y, int width, int height){
        //Erstellt und zeichnet ein Haus als Objekt und einen Knoten mit einer ID
        String id = "b" + idCounter; // Erstellt eine ID
        HouseSmall houseSmall = new HouseSmall(x,y,width,height,id);
        viewController.draw(houseSmall);
        allBuildings.addVertex(new Vertex(id));
    }

    public void drawHouse(int x, int y, int width, int height){
        //Erstellt und zeichnet ein Haus als Objekt (Nur zum zeichnen --> Keine Veränderung im Graphen)
        HouseSmall houseSmall =new HouseSmall(x, y, width, height, null);
        viewController.draw(houseSmall);
    }
}
