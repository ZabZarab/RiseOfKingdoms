package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.Edge;
import KAGO_framework.model.abitur.datenstrukturen.Graph;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Vertex;
import my_project.model.*;

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
    private List<Buildings> buildingsList; // Referenz auf ein Objekt der Klasse List mit dem ContentType Buildings - Verwaltet alle Gebäude in einer Liste

    private Hotbar hotbar;
    public Mouse mouse;
    private HouseSmall houseSmall;
    private HouseBig houseBig;
    private Main MAIN;
    private Player player;
    private HondaCivic carS;
    private Truck carB;
    private Street drawStreet;

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
        buildingsList = new List<>();
        mouse = new Mouse();
        player = new Player();

    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        // Erstelle ein Objekt der Klasse Ball und lasse es zeichnen
        addAll();
        //Geld
        viewController.draw(player);
        viewController.register(player);
        //Maus
        viewController.register(mouse);
        //MAIN
        addMAINHouse(500,300);
        //Haus klein
        houseSmall = new HouseSmall(0, 0, null);
        viewController.draw(houseSmall);
        //haus groß
        houseBig = new HouseBig(0, 0, null);
        viewController.draw(houseBig);

        drawStreet = new Street(0,0,0,0);
        viewController.draw(drawStreet);
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){

        //Drag and drop vom kleinen haus
        if(hotbar.getSHB() && player.getMoney() >= 1000){
            houseSmall.setX(mouse.getxPos()-houseSmall.getWidth()/2);
            houseSmall.setY(mouse.getyPos()-houseSmall.getHeight()/2);
        }else{
            houseSmall.setX(50);
            houseSmall.setY(630);
        }
        //Loslassen
        if(hotbar.isAddSHouse() && hotbar.getSHB() == false && player.getMoney() >= 1000 && checkIfCollides()){
            addSHouse(mouse.getxPos()- (int) houseSmall.getWidth()/2, mouse.getyPos()- (int) houseSmall.getHeight()/2);
            hotbar.setAddSHouse(false);
            hotbar.setAmountOfSmallH(hotbar.getAmountOfSmallH()+1);
            player.setMoney(player.getMoney()-houseSmall.getPrice());
        } else{
            hotbar.setAddSHouse(false);
        }

        //Drag and drop vom großen haus
        if(hotbar.getBHB() && player.getMoney() >= 2000){
            houseBig.setX(mouse.getxPos()-houseBig.getWidth()/2);
            houseBig.setY(mouse.getyPos()-houseBig.getHeight()/2);
        }else{
            houseBig.setX(175);
            houseBig.setY(630);
        }
        if(hotbar.isAddBHouse() && hotbar.getBHB() == false && player.getMoney() >= 2000 && checkIfCollides()){
            addBHouse(mouse.getxPos()- (int) houseBig.getWidth()/2, mouse.getyPos()- (int) houseBig.getHeight()/2);
            hotbar.setAddBHouse(false);
            hotbar.setAmountOfBigH(hotbar.getAmountOfBigH()+1);
            player.setMoney(player.getMoney()-houseBig.getPrice());
        }else{
            hotbar.setAddBHouse(false);
        }
        hotbar.setAmountOfBuildings(hotbar.getAmountOfBigH()+hotbar.getAmountOfSmallH());
        if(!carS.collidesWith(carS.getX2(),carS.getY())) carS.driveToOneHouse(carS.getX(), carS.getY(), 0, 400, dt);
        if(!carB.collidesWith(carB.getX2(),carB.getY())) carB.driveToOneHouse(carB.getX(), carB.getY(), 0, 400, dt);
        //System.out.println(carS.isGo());
        //Drag and drop zeichnen und erstellen von Straßen
        if(dragStreetBuildingCheck()!=null) {
            if(addStreetBuildingCheck()!=null && allBuildings.getEdge(allBuildings.getVertex(addStreetBuildingCheck().getId()), allBuildings.getVertex(dragStreetBuildingCheck().getId()))==null && !addStreetBuildingCheck().equals(dragStreetBuildingCheck())){
                System.out.println("oh no");
                addStreet(dragStreetBuildingCheck(), addStreetBuildingCheck());
                dragStreetBuildingCheck().setDragStreet(false);
                addStreetBuildingCheck().setAddStreet(false);
                drawStreet.setPositions(0,0,0,0);
            }else{
                if(dragStreetBuildingCheck().isDrawStreet()){
                    drawStreet.setPositions(dragStreetBuildingCheck().getX() + dragStreetBuildingCheck().getWidth()/2, dragStreetBuildingCheck().getY()+ dragStreetBuildingCheck().getHeight()/2, mouse.getxPos(), mouse.getyPos());
                }else{
                    drawStreet.setPositions(0,0,0,0);
                }

            }

        }
        //addStreetBuildingCheck();
        //dragStreetBuildingCheck();

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

        carS = new HondaCivic(100, 100);
        viewController.draw(carS);
        carB = new Truck(200,100);
        viewController.draw(carB);



        drawSHouse(50,630);
        drawBHouse(175, 630);

    }

    public void addSHouse(int x, int y){
        //Erstellt und zeichnet ein Haus als Objekt und einen Knoten mit einer ID
        String id = "b" + idCounter; // Erstellt eine ID
        HouseSmall hS = new HouseSmall(x,y,id);
        viewController.draw(hS);
        allBuildings.addVertex(new Vertex(id));
        buildingsList.append(hS);
        viewController.register(hS);
        idCounter++;
    }
    public void addBHouse(int x, int y){
        //Erstellt und zeichnet ein Haus als Objekt und einen Knoten mit einer ID
        String id = "b" + idCounter; // Erstellt eine ID
        HouseBig hB = new HouseBig(x,y,id);
        viewController.draw(hB);
        allBuildings.addVertex(new Vertex(id));
        buildingsList.append(hB);
        viewController.register(hB);
        idCounter++;
    }
    public void addMAINHouse(int x, int y){
        //Erstellt und zeichnet ein Haus als Objekt und einen Knoten mit einer ID
        String id = "b" + idCounter; // Erstellt eine ID
        Main MAIN = new Main(x,y,id);
        viewController.draw(MAIN);
        allBuildings.addVertex(new Vertex(id));
        buildingsList.append(MAIN);
        viewController.register(MAIN);
        idCounter++;
    }

    public void drawSHouse(int x, int y){
        //Erstellt und zeichnet ein Haus als Objekt (Nur zum zeichnen --> Keine Veränderung im Graphen)
        HouseSmall hS =new HouseSmall(x, y,null);
        viewController.draw(hS);
    }

    public void drawBHouse(int x, int y){
        //Erstellt und zeichnet ein Haus als Objekt (Nur zum zeichnen --> Keine Veränderung im Graphen)
        HouseBig hB =new HouseBig(x, y,null);
        viewController.draw(hB);
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
            Street street = new Street(b1.getX()+b1.getWidth()/2, b1.getY()+b1.getHeight()/2,b2.getX()+b2.getWidth()/2,b2.getY()+b2.getHeight()/2);
            viewController.draw(street);
            return true;
        }
        return false;
    }

    public boolean checkIfCollides(){
        buildingsList.toFirst();
        if(!buildingsList.isEmpty()){
            while(buildingsList.hasAccess()){
                if(buildingsList.getContent().getDistanceToPoint(mouse.getxPos(), mouse.getyPos())<70) return false;
                buildingsList.next();
            }
            return true;
        }
        return true;

    }


    public Buildings dragStreetBuildingCheck(){
        //Gibt ein Objekt der Liste buildingsList züruck, von welchem in dem Moment gedragged wird, falls vorhanden
        if(!buildingsList.isEmpty()){
            buildingsList.toFirst();
            while(buildingsList.hasAccess()){
                if(buildingsList.getContent().isDragStreet()) return buildingsList.getContent();
                buildingsList.next();
            }
        }
        return null;
    }

    public Buildings addStreetBuildingCheck(){
        //Gibt ein Objekt der Liste buildingsList züruck, auf welches gedragged wurde, falls vorhanden
        if(!buildingsList.isEmpty()){
            buildingsList.toFirst();
            while(buildingsList.hasAccess()){
                if(buildingsList.getContent().isAddStreet()) return buildingsList.getContent();
                buildingsList.next();
            }
        }
        return null;
    }
}
