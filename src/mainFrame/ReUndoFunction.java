package mainFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

import model.*;


/**
 * Die Klasse ToolBarReUndo implementiert die Funktionen Undo und Redo um zuvor hinzugefügte, gelöschte oder verschobene Punkte
 * wieder rückgängig zu machen oder wieder herzustellen.
 * @author Stefan Colutto 9513140
 *
 */
public class ReUndoFunction{
    private JButton bLeft; //Button zum Zurücksetzen von Punkten
    private JButton bRight; //Button zum Zurückholen von Punkten
    private LinkedList<Point> redoPoints = new LinkedList<Point>();
    //in dieser Liste werden die Punkte gespeichert welche durch die Funktion redo zurückgeholt werden
    private LinkedList<Point> undoPoints = new LinkedList<Point>();
    //in dieser Liste werden die Punkte gespeichert welche gelöscht worden sind
    private LinkedList<Point> draggedPointsUndo = new LinkedList<Point>();
    //in dieser Liste werden die Punkte gespeichert welche verschoben worden sind
    private LinkedList<Point> draggedPointsRedo = new LinkedList<Point>();
    /*in dieser Liste werden die Punkte gespeichert um die durch die Liste draggedPointsUndo zurückgenommenen Punkte wieder 
     * herzustellen*/
    private LinkedList<String> undo = new LinkedList<String>();
    //in dieser Liste werden Strings gespeichert um zu Wissen was die Funktion Undo als nächstes machen soll
    private LinkedList<String> redo = new LinkedList<String>();
  //in dieser Liste werden Strings gespeichert um zu Wissen was die Funktion Redo als nächstes machen soll
    
    
    public ReUndoFunction(Draw draw, MainToolBar mainToolBar) {
        Font font = new Font("Arial", Font.BOLD, 15);
        bLeft = new JButton("Undo");
        bLeft.setFont(font);
        bLeft.setEnabled(false);//der Button wird darf noch nicht Gedrückt werden
        
        bRight = new JButton("Redo");
        bRight.setFont(font);
        bRight.setEnabled(false);//der Button wird darf noch nicht Gedrückt werden
        
        mainToolBar.add(bLeft);
        mainToolBar.add(bRight);
        
        
        
        bLeft.addActionListener(new ActionListener() {
            /**
             * Der Button bLeft soll die Funktionen der Klasse ToolBarPoints rückgängig machen können.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                bRight.setEnabled(true);//der Button für Redo kann verwendet werden da die Undo Funktion gedrückt wurde
                String function = undo.removeLast();//es wird festgestellt was als letzte Funktion angewendet wurde
                String[] deletePoints = function.split(" ");
                redo.add(function); 
                //diese zuvor ausgelesene Funktion wird der Redo Funktin übergeben, da diese Rückgängig gemacht werden kann
                if(function.equals("dragged")) {//es wird überprüft ob als letzte Funktion ein Punkt verschoben wurde
                        Point draggedPoint = draggedPointsUndo.removeLast();
                        //es wird der Punkt ausgelesen welcher verschoben wurde
                        Point originallyPoint = draggedPointsUndo.removeLast();
                        //es wird der Punkt ausgelesen mit den Koordinaten vor dem Verschieben
                        draggedPointsRedo.add(originallyPoint);
                        draggedPointsRedo.add(draggedPoint);
                        //die Punkte werden der Redo Funktion hinzugefügt damit sie später wieder rückgängig gemacht werden können
                        originallyPoint.setLocation(draggedPoint.getOldPosX(), draggedPoint.getOldPosY());
                        //der verschobene Punkt bekommt seine ursprünglichen Koordinaten wieder übergeben
                }else if(deletePoints[0].equals("delete")){//es wird überprüft ob als letzte Funktion ein Punkt gelöscht wurde
                    Point point = undoPoints.removeLast();//der letzte Punkt wird aus der Liste ausgelesen
                    redoPoints.add(point);
                    //der Punkt wird der Funktion Redo hinzugefügt um wieder rückgängig gemacht werden zu können
                    PointOrganisation.addPoint(point, Integer.valueOf(deletePoints[1]));
                    //der Punkt wird wieder am vorherigen Platz der Punktemenge hinzugefügt
                }else {
                    int amount = Integer.parseInt(function);
                    /*fals keine der obigen Funktionen in Frage kommt wurden Punkte hinzugefzügt welche wieder gelöscht werden sollen
                     *im String steht die Anzahl der Punkte welche gelöscht werden sollen, diese werden in eine Integer Variable 
                     *umgewandelt */
                    for(int i=0; i<amount; i++) {//die Schleife wird wiederholt bis alle Punkte gelöscht wurden
                        redoPoints.add(PointOrganisation.removeLastPoint());
                      //der Punkt welcher gelöscht werden soll wird der Funktion Redo übergeben und aus der Punktemenge gelöscht
                    }
                }
                if(PointOrganisation.getPoints().size()>0) {
                    //die Algorithmen dürfen nur berechnet werden wenn in der Punktemenge mindestens 1 Punkt enthalten ist
                    ConvexHullAlgorithm.calculateConvexHull(true);
                    BiggestIncludedCircle.calculateBiggestIncludedCircle(true);
                }else PointOrganisation.deleteAllPoints();
                //fals keine Punkte mehr enthalten sind werden die Punktemenge und die Konvexe Hülle gelöscht
                draw.repaint();//die Zeichenfläche muss nach jeder Ausführung neu gezeichnet werden
                if(undo.isEmpty())
                    //falls die Funktion Undo keine Aufgaben mehr zum Ausführen hat muss der Button bLeft gesperrt werden
                    bLeft.setEnabled(false);
            }
        });
        
        bRight.addActionListener(new ActionListener() {
            /**
             * Der Button bRight soll die Funktion der Undo Funktion wieder rückgängig machan können.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                bLeft.setEnabled(true);//der Button für Undo kann verwendet werden da die Redo Funktion gedrückt wurde
                String function = redo.removeLast();//es wird festgestellt was als letzte Funktion angewendet wurde
                String[] deletePoints = function.split(" ");
                undo.add(function);
                //diese zuvor ausgelesene Funktion wird der Undo Funktin übergeben, da diese Rückgängig gemacht werden kann
                if(function.equals("dragged")) {//es wird überprüft ob als letzte Funktion ein Punkt verschoben wurde
                    
                        Point draggedPoint = draggedPointsRedo.removeLast();
                        //es wird der Punkt ausgelesen welcher verschoben wurde
                        Point originallyPoint = draggedPointsRedo.removeLast();
                        //es wird der Punkt ausgelesen mit den Koordinaten vor dem Verschieben
                        draggedPointsUndo.add(originallyPoint);
                        draggedPointsUndo.add(draggedPoint);
                        //die Punkte werden der Undo Funktion hinzugefügt damit sie später wieder rückgängig gemacht werden können
                        originallyPoint.setLocation(draggedPoint.getNewPosX(), draggedPoint.getNewPosY());
                        //der verschobene Punkt bekommt seine ursprünglichen Koordinaten wieder übergeben
                }else if(deletePoints[0].equals("delete")){//es wird überprüft ob als letzte Funktion ein Punkt gelöscht wurde
                    Point point = redoPoints.removeLast();
                    undoPoints.add(point);
                    PointOrganisation.removePoint(point);
                    //der Punkt welcher gelöscht werden soll wird der Funktion Undo übergeben und aus der Punktemenge gelöscht
                }else {
                    int amount = Integer.parseInt(function);
                    /*fals keine der obigen Funktionen in Frage kommt wurden Punkte gelöscht welche wieder hinzugefzügt werden sollen
                     *im String steht die Anzahl der Punkte welche hinzugefügt werden sollen, diese werden in eine Integer Variable 
                     *umgewandelt */
                    for(int i=0; i<amount; i++) {//die Schleife wird wiederholt bis alle Punkte hinzugefügt wurden
                        Point point = redoPoints.removeLast();//der letzte Punkt wird aus der Liste ausgelesen
                        undoPoints.add(point);
                        //der Punkt wird der Funktion Undo hinzugefügt um wieder rückgängig gemacht werden zu können
                        PointOrganisation.addPoint(point);//der Punkt wird wieder der Punktemenge hinzugefügt
                    }
                }
                if(PointOrganisation.getPoints().size()>0) {
                    //die Algorithmen dürfen nur berechnet werden wenn in der Punktemenge mindestens 1 Punkt enthalten ist
                    ConvexHullAlgorithm.calculateConvexHull(true);
                    BiggestIncludedCircle.calculateBiggestIncludedCircle(true);
                }else PointOrganisation.deleteAllPoints();
                //fals keine Punkte mehr enthalten sind werden die Punktemenge und die Konvexe Hülle gelöscht
                draw.repaint();//die Zeichenfläche muss nach jeder Ausführung neu gezeichnet werden
                if(redo.isEmpty())
                    //falls die Funktion Redo keine Aufgaben mehr zum Ausführen hat muss der Button bRight gesperrt werden
                    bRight.setEnabled(false);
            }
        });
    }
    /**
     * Die Methode addDraggedpoint fügt der Liste draggedPointsUndo die Punkte hinzu, welche in der Klasse ToolBarPoints  
     * verschoben werden können.
     * @param dragged Die Variable dragged ist vom Typ Point und wird der Liste draggedPointsUndo hinzugefügt.
     */
    void addDraggedPoint(Point dragged) {
        draggedPointsUndo.add(dragged);
    }
    /**
     * Die Methode addUndo fügt der Liste undo einen String hinzu, dieser zeigt an welche Funktionen in der Klasse ToolBarPoints
     * ausgeführt wurden.
     * @param a Der String a kann eine Zahl enthalten welche angibt wieviel Punkte random hinzugefügt wurden oder enthält eine eins 
     * falls nur ein Punkt hinzugefügt wurde. Außerdem sind noch die Funktionen Verschieben mit dem String dragged und die Funktion 
     * löschen mit dem String delete enthalten.
     */
    void addUndo(String a) {
        undo.add(a);
        bLeft.setEnabled(true);//der Undo Button wird entsperrt da in der Zeichenfläche Punkte hinzugefügt worden sind
    }
    /**
     * Die Mehtode addPointUndo fügt die Punkte hinzu welche später wieder mit der Undo Funktion rückgängig gemacht werden können.
     * Nur falls Punkte verschoben werden werden die Punkte in die Liste draggedPointsUndo hinzugefügt.
     * @param a Die Variable a ist vom Typ Point und wird der Liste undoPoints hinzugefügt.
     */
    void addPointUndo(Point a) {
        undoPoints.add(a);
    }
    /**
     * Die Methode deleteAll wird aufgerufen falls eine neue Zeichenfläche erstellt werden soll, da alle Undo und Redo Funktionen
     * damit hinfällig werden.
     */
    void deleteAll() {
        bLeft.setEnabled(false); //der Button muss wieder gesperrt werden da alle Punkte und Strings gelöscht wurden
        bRight.setEnabled(false); //der Button muss wieder gesperrt werden da alle Punkte und Strings gelöscht wurden
        redoPoints.removeAll(redoPoints);
        /*alle Listen der ToolBarReUndo werden gelöscht damit die Funktionen von Unod und Redo wieder neu 
         * gestartet werden können*/
        undoPoints.removeAll(undoPoints);
        redo.removeAll(redo);
        undo.removeAll(undo);
        draggedPointsRedo.removeAll(draggedPointsRedo);
        draggedPointsUndo.removeAll(draggedPointsUndo);
    }
    /**
     * Die Methode deleteRedo soll die Punkte und die Strings der Funktion Redo löschen falls die Liste redo nicht Leer ist.
     * Dadurch können keine Punkte rückgängig gemacht werden die zuvor gelöscht wurden falls neue Punkte hinzugefügt worden sind.
     */
    void deleteRedo() {
        if(!redo.isEmpty()) {//es wird abgefragt ob die Liste redo leer ist
            redo.clear();
            redoPoints.clear();
            draggedPointsRedo.clear();
            bRight.setEnabled(false);
            //nach dem löschen muss die Schaltfläche disabled werden da Redo keine Funktionen mehr gespeichert hat
        }
    }
    
}
