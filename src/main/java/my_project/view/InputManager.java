package my_project.view;

import KAGO_framework.model.InteractiveGraphicalObject;
import my_project.control.ProgramController;
import java.awt.event.MouseEvent;

/**
 * Realisiert ein Objekt, dass alle Eingaben empfängt und dann danach passende Methoden
 * im ProgramController aufruft
 */
public class InputManager extends InteractiveGraphicalObject {

    public static int mouseX;
    public static int mouseY;

    private ProgramController programController;

    /**
     * Objekterzeugung
     * @param programController Nötig als Objekt vom Controllerbereich, das informiert wird
     *  Nötig, um den Aufruf der Interface-Methoden sicherzustellen
     */
    public InputManager(ProgramController programController){
        this.programController = programController;

    }


    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX(); // X-Position der Maus
        mouseY = e.getY(); // X-Position der Maus
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }


}
