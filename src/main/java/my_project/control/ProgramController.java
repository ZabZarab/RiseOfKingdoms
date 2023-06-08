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
    private List<HondaCivic> hondaList; // Referenz auf ein Object der Klasse List mit dem ContentType Vehicle - Verwaltet alle Hondas in einer Liszt
    private List<Truck> truckList; // Referenz auf ein Object der Klasse List mit dem ContentType Vehicle - Verwaltet alle Truckos in einer Liszt
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
    private Vertex vertex;


    //test
    HondaCivic testCar;
    List<Buildings> tempTBP;

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
        hondaList = new List<>();
        truckList = new List<>();
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

        //test
        addSHouse(400,300);
        addSHouse(700,300);
        buildingsList.toFirst();
        Buildings temp = buildingsList.getContent();
        buildingsList.next();
        addStreet(temp, buildingsList.getContent());
        temp = buildingsList.getContent();
        buildingsList.next();
        addStreet(temp, buildingsList.getContent());

        testCar = new HondaCivic(500, 300, testBuildPath());
        viewController.draw(testCar);

        tempTBP = testBuildPath();


       // System.out.println(dijkstra(allBuildings.getVertex("b0"), allBuildings.getVertex("b2")));
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
        //Car Adding simulator
        if(hotbar.isAddHonda() && player.getMoney() >= 500){
            //TODO-01 car add methode sobald wir es haben
            hotbar.setAddHonda(false);
            hotbar.setAmountOfCar(hotbar.getAmountOfCar()+1);
            hotbar.setAmountOfHonda(hotbar.getAmountOfHonda()+1);
            player.setMoney(player.getMoney()-500);
            HondaCivic civic = new HondaCivic(-100,-100, null);
            hondaList.append(civic);
            viewController.draw(civic);
        }
        if(hotbar.isAddTruck() && player.getMoney() >= 2500){
            //TODO-01 car add methode sobald wir es haben
            hotbar.setAddTruck(false);
            hotbar.setAmountOfCar(hotbar.getAmountOfCar()+1);
            hotbar.setAmountOfTruck(hotbar.getAmountOfTruck()+1);
            player.setMoney(player.getMoney()-2500);
            Truck truck = new Truck(-100,-100, null);
            truckList.append(truck);
        }
        //if(!carS.collidesWith(carS.getX2(),carS.getY())) carS.driveToOneHouse(carS.getX(), carS.getY(), 0, 400, dt);
        //if(!carB.collidesWith(carB.getX2(),carB.getY())) carB.driveToOneHouse(carB.getX(), carB.getY(), 0, 400, dt);
        if(selectHonda()!=null){
            HondaCivic honda = selectHonda();
            honda.driveToOneHouse(500,500,1000,600,dt);
        }

        // test

        drive(testCar, dt);

        /*List<Vertex> tempList = allBuildings.getVertices();
        allBuildings.getVertices().toFirst();
        Vertex temp = allBuildings.getVertices().getContent();
        allBuildings.getVertices().next();*/




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

        carS = new HondaCivic(340, 630, null);
        viewController.draw(carS);
        carB = new Truck(460,630, null);
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
    public HondaCivic selectHonda(){
        if(!hondaList.isEmpty()) {
            hondaList.toFirst();
            if (hondaList.hasAccess() && !hondaList.getContent().doesHasTask()) {
                HondaCivic honda = hondaList.getContent();
                honda.setHasTask(true);
                return honda;
            } else {
                hondaList.next();
            }
        }
        return null;
    }

    public Truck selectTruck(){
        truckList.toFirst();
        if(truckList.hasAccess()&&!truckList.getContent().doesHasTask()){
            Truck truck = truckList.getContent();
            truck.setHasTask(true);
            return truck;
        }else{
            truckList.next();
        }
        return null;
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
            allBuildings.addEdge(new Edge(v1 , v2 , b1.getDistanceTo(b2)));
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
                if(buildingsList.getContent().getDistanceToPoint(mouse.getxPos(), mouse.getyPos())< buildingsList.getContent().getBorderRadius() + 10) return false;
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


    public void drive(Vehicle car, double dt){

        if(!car.getPathList().isEmpty() && car.getPathList().hasAccess()){
            car.getPathList().toFirst();
            Buildings from = car.getPathList().getContent();
            car.getPathList().next();
            Buildings to = car.getPathList().getContent();
            double x1 = from.getX();
            double y1 = from.getY();
            double x2 = to.getX();
            double y2 = to.getY();

            //System.out.println(from);
            //System.out.println(to);

            if(!car.collidesWith(to)){
                car.driveToOneHouse(x1,y1,x2,y2,dt);
            }else{
                car.getPathList().toFirst();
                car.getPathList().remove();
                car.getPathList().next();
            }

        }
    }

    /** wandelt die Liste vo Dijkstra so um damit wir damit arbeiten können
     * @param pList die Liste die vom Dijkstra zurückgegeben werden sollte
     * @return output ist die liste die in Buildings umwandelt
     */
    public List<Buildings> buildingPathList(List<Vertex> pList){

        if(!pList.isEmpty()){
            List<Buildings> output = new List<>();
            pList.toFirst();
            while(pList.hasAccess()){
                buildingsList.toFirst();
                while(buildingsList.hasAccess()){
                    if(buildingsList.getContent().getId().equals(pList.getContent().getID())) System.out.println(buildingsList.getContent().getId()); output.append(buildingsList.getContent());
                    buildingsList.next();
                }
                pList.next();
            }
            output.toFirst();
            return output;
        }


        return null;
    }


    //Dijakstra

    public List<Vertex> dijkstra(Vertex start, Vertex end){
        List<Vertex> finalPath = new List<>();  // Der kürzeste weg in einer liste von Vertexs

        List<Vertex> vertices = allBuildings.getVertices(); // Liste von allen buildings
        vertices.toFirst(); // wollen auf das erste
        //allBuildings.setAllVertexMarks(false);
        while(vertices.hasAccess()) {  // läuft die Liste durch von allen Knoten und
            // setzt sie alle auf nicht markiert und mit dem Score(distance) "unendlich"
            // sowie den Vertex previous auf null
            vertices.getContent().setMark(false);
            vertices.getContent().setScore(Double.POSITIVE_INFINITY);
            vertices.getContent().setPrevious(null);
            vertices.next();
        }
        start.setScore(0); // Setzt den Start knoten den Score 0, da die distance ja 0 ist

        while(true) { // Die Schleife beginnt und wird solange ausgeführt,
            // bis der Endknoten (end) erreicht wird oder bis festgestellt wird,
            // dass kein Pfad zum Endknoten existiert.

            Vertex current = nodeWithLowestScore(); // Innerhalb der Schleife wird der Knoten mit dem niedrigsten score
            // (über die Methode nodeWithLowestScore()) ausgewählt und als current markiert.
            // Hier ist es der Start knoten weil er den niedrigsten score hat

            if (current == null || current.getScore() == Double.POSITIVE_INFINITY) {
                return null; // Kein Pfad zum Endknoten gefunden
            }

            current.setMark(true); // dann wird er true gesetzt

            if (current == end) {
                // Der Endknoten wurde erreicht, erstelle den finalen Pfad
                Vertex pathNode = end;
                while (pathNode != null) {
                    finalPath.insert(pathNode);
                    pathNode = pathNode.getPrevious();
                }
                return finalPath;
            }

            List<Vertex> neighbours = allBuildings.getNeighbours(current); // liste der Nachbarn vom Start wird erstellt
            neighbours.toFirst();
            while(neighbours.hasAccess()) { //Für jeden Nachbarknoten, der nicht markiert ist, wird geprüft,
                // ob die aktuelle Entfernung (score) plus die Kosten der Kante
                // zu diesem Nachbarn kleiner ist als der bisherige score des Nachbarn.
                if(!neighbours.getContent().isMarked()) {
                    double newScore = current.getScore()+ allBuildings.getEdge(current,neighbours.getContent()).getWeight();
                    if(newScore < neighbours.getContent().getScore()) {
                        //Wenn ja, wird der score des Nachbarn aktualisiert und der Nachbarknoten wird zum finalPath hinzugefügt.
                        neighbours.getContent().setScore(newScore);
                        //finalPath.append(neighbours.getContent());
                        neighbours.getContent().setPrevious(current);
                    }
                    neighbours.getContent().setMark(true);
                }
                neighbours.next();
                System.out.println("hie");
            }

        }
    }

    /**
     *Die Methode nodeWithLowestScore() wird verwendet, um in jeder Iteration des Hauptalgorithmus
     * den Knoten mit dem niedrigsten score auszuwählen.
     * Dieser Knoten wird dann als der aktuelle Knoten (current) betrachtet,
     * und seine Nachbarknoten werden überprüft und gegebenenfalls aktualisiert.
     * Indem der Knoten mit dem niedrigsten score ausgewählt wird,
     * wird der Algorithmus schrittweise den kürzesten Pfad zum Endknoten bestimmen.
     */
    private Vertex nodeWithLowestScore() {
        List<Vertex> vertices = allBuildings.getVertices();// Liste mit allen buildings die im Graphen sind
        if(!vertices.isEmpty()){
            vertices.toFirst();
            Vertex result = vertices.getContent();// current wird als erstes vorläufiges Ergebnis festgelegt

            while(vertices.hasAccess()) {
                if(!vertices.getContent().isMarked() &&
                        vertices.getContent().getScore() <= result.getScore()) {
                    result = vertices.getContent();
                }
                vertices.next();
            }
            //Während der Durchlaufschleife wird für jeden Knoten überprüft,
            // ob er nicht markiert ist und ob
            // sein score kleiner oder gleich dem score des bisherigen Ergebnisknotens (result) ist.

            return result; // Nachdem alle Knoten überprüft wurden,
            // wird das vorläufige Ergebnis (result) zurückgegeben.
            // Dieser Knoten hat den niedrigsten score unter den nicht markierten Knoten.
        }
        return  null;
    }

    public List<Buildings> testBuildPath() {
        List<Buildings> output = new List<>();
        buildingsList.toFirst();
        output.append(buildingsList.getContent());
        buildingsList.next();
        output.append(buildingsList.getContent());
        buildingsList.next();
        output.append(buildingsList.getContent());
        output.toFirst();
        return output;
    }



    /////////////////////////////////////// PAUL DIJKSTRA
    /*/**
     * rechnet durch den Dijkstra-Algorithmus die kürzeste Route zwischen zwei Punkten im Graphen aus
     * @param location Anfangspunkt des Autos
     * @return gibt eine Liste von Strings zurück, die dann die einzelnen Vertecies sind, durch
     * die das Auto fahren muss um das Ziel zu erreichen
     */
    /*public List<String[]> calculateBestRoutesWithDijkstra(String location) {
        Graph graph = restauraant.getHouseGraph(); //zur Lesbarkeit
        List<Vertex> verteces = graph.getVertices();
        List<Vertex> queue = new List<>(); //Warteschlange der Objekte, die noch durchgenommen werden sollen
        queue.append(graph.getVertex(location));

        List<String[]> distances = new List<>(); //Liste distances mit Distanzen von location zu allen anderen Knoten und mit Vorgängern
        verteces.toFirst();
        //Alle Distanzen werden auf unendlich gesetzt außer die von dem Standort, die ist 0, Vorgänger ist leerer String
        while (verteces.hasAccess()) {
            if(verteces.getContent().getID().equals(location)) distances.append(new String[]{location, "0", location});
            else distances.append(new String[]{verteces.getContent().getID(), String.valueOf(Integer.MAX_VALUE), ""});
            verteces.next();
        }

        List<Vertex> neighbours = graph.getNeighbours(graph.getVertex(location));
        neighbours.toFirst();
        while(neighbours.hasAccess()) {
            //jeder Nachbar wird in die Queue hinzugefügt (natürlich nach richtiger Reihenfolge)
            insertInQueue(queue, neighbours.getContent(), distances, location, graph);

            //Distanzen und Vorgänger der Nachbarn des Standorts werden hinzugefügt
            distances.toFirst();
            while (distances.hasAccess() && !Objects.equals(neighbours.getContent().getID(), distances.getContent()[0])) {
                distances.next();
            }
            distances.getContent()[1] = String.valueOf(getTimeToPass(location, distances.getContent()[0]));
            distances.getContent()[2] = location;

            neighbours.next();
        }

        graph.getVertex(location).setMark(true); //besucht
        queue.toFirst();
        queue.remove();

        //bis macht der algo alles richtig
        //dies ist der Startpunkt, nun beginnt der eigentliche Algorithmus, that's were the magic happens

        while(!queue.isEmpty()) {
            queue.toFirst();
            double currentDistance = findDoubleInDistancesList(distances, queue.getContent()); //Distanz vom Startpunkt bis zu dem current Objekt der queue

            neighbours = graph.getNeighbours(graph.getVertex(queue.getContent().getID())); //Nachbarn des aktuellen Objekts der Queue
            neighbours.toFirst();
            while (!neighbours.isEmpty()) {
                //alle Nachbarn, die nicht markiert sind werden in die Queue gepackt und dabei richtig sortiert (Queue hat immer richtige Reihenfolge)
                insertInQueue(queue, neighbours.getContent(), distances, queue.getContent().getID(), graph);
                //prüfen, ob die distances Liste aktuell ist -> ggf. updaten
                distances.toFirst(); //greifen auf die distances Liste zu
                queue.toFirst();
                //suchen von den selben Neighbour Objekt
                if(!graph.getVertex(neighbours.getContent().getID()).isMarked()) {
                    while (distances.hasAccess() && !Objects.equals(neighbours.getContent().getID(), distances.getContent()[0])) {
                        distances.next();
                    }
                    if (currentDistance + getTimeToPass(queue.getContent().getID(), neighbours.getContent().getID())
                            < Double.parseDouble(distances.getContent()[1])) { //wenn der Weg kleiner ist
                        distances.getContent()[1] = String.valueOf(
                                currentDistance + getTimeToPass(queue.getContent().getID(), neighbours.getContent().getID())
                        );
                        distances.getContent()[2] = queue.getContent().getID();
                        //wenn der Weg größer ist passiert nichts
                    }
                }
                neighbours.remove();
            }

            queue.getContent().setMark(true);
            queue.remove();
        }

        graph.setAllVertexMarks(false);
        return distances;
    }

    /**
     * fügt in die queue einen neuen Nachbarn hinzu, dabei wird darauf geachtet, dass die Liste nach den Distanzen
     * aufsteigend sortiert ist
     * @param queue die Liste mit allen Knoten, die noch abarbeitet werden müssen
     * @param toInsert Vertex, was in die queue hinzugefügt werden sollen
     * @param distances distances Liste
     *
     * @param currentVertex toInsert ist der Nachbar von currentVertex
     */
    /*private boolean insertInQueue(List<Vertex> queue, Vertex toInsert, List<String[]> distances, String currentVertex, Graph graph) {
        if(graph.getVertex(toInsert.getID()).isMarked()) return false;

        queue.toFirst();
        while (queue.hasAccess()) {
            if (queue.getContent().equals(toInsert)) return false;
            queue.next();
        }

        distances.toFirst();
        while (distances.hasAccess() && distances.getContent()[0].equals(currentVertex)) {
            distances.next();
        }
        double currentDistance = Double.parseDouble(distances.getContent()[1]);

        queue.toFirst();
        queue.next();
        if(!queue.hasAccess()) {
            queue.append(toInsert);
        } else {
            while(queue.hasAccess() &&
                    currentDistance + getTimeToPass(currentVertex, toInsert.getID()) >
                            findDoubleInDistancesList(distances, queue.getContent()))
            {
                queue.next();
            }
            if(!queue.hasAccess()) {
                queue.append(toInsert);
            } else {
                queue.insert(toInsert);
            }
        }
        return true;
    }

    /**
     * für Dijkstra: findet die Distanz zu dem Knoten durch das Suchen in der distances Liste
     * @param distances
     * @param toFind
     * @return Distanz als double
     */
    /*public double findDoubleInDistancesList(List<String[]> distances, Vertex toFind){
        distances.toFirst();
        while (distances.hasAccess() && !Objects.equals(distances.getContent()[0], toFind.getID())) {
            distances.next();
        }
        return Double.parseDouble(distances.getContent()[1]);
    }

    /**
     * findet heraus wie lange das Auto braucht, um von dem einen Vertex zum anderen zu kommen
     * dabei wird angenommen, dass eine Längeneinheit eine Minute braucht, um diese zu überqueren
     * (Idee: Dauer ist vom Auto abhängig, einige Autos sind langsamer, einige schneller)
     * @param location Anfangspunkt des Autos
     * @param destination Ziel
     * @return Dauer
     */
    /*private double getTimeToPass(String location, String destination) {
        double[] locCoordinates = restaurant.getHouseGraph().getVertex(location).getCoordinates();
        double[] desCoordinates = restaurant.getHouseGraph().getVertex(destination).getCoordinates();
        double length = Math.sqrt(
                Math.pow(Math.abs(locCoordinates[0] - desCoordinates[0]), 2) +
                        Math.pow(Math.abs(locCoordinates[1] - desCoordinates[1]), 2)
        );
        return length;
    } */

}
