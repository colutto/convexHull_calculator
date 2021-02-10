package mainFrame;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;

import model.*;

/**
 * Die Klasse MainFrameToolBar stellt die Operationen zur Verfügung welche in der Tool Bar ausgewählt werden können.
 *@author Stefan Colutto 9513140
 */
public class MainToolBar extends JToolBar implements IPointListener{

    private JToggleButton delete; //kann ausgewählt werden um Punkte zu löschen
    private JToggleButton drag; //kann ausgewählt werden um Punkte zu verschieben
    private JToggleButton addPoint; //kann ausgewählt werden um Punkte hinzuzufügen
    private JComboBox<String> numberPoints; //daring wird ausgewählt wieviel Punkte zufällig hinzugefügt werden sollen
    private JButton addPoints; //die in der JComboBox numberPoints ausgewählte Anzahl wird hinzugefügt
    private Point dragPoint;  //darin wird der Punkt gespeichert der verschoben werden soll
    //ist der Punkt der in der Methode mousePressed gefunden wird und in der Methode mouseDragged verschoben wird
    private boolean foundDragPoint = false; //wird true falls ein Punkt gefunden wird welcher verschoben werden kann
    private Draw draw; //die Zeichenfläche muss beim verschieben, löschen und hinzufügen von Punkten neu gezeichnet werden
    private String[] zähler = {"10","50","100","500","1000"};
    private ReUndoFunction toolBarReUndo;
    
    
    MainToolBar(Draw draw) {
        this.draw = draw;
        
        delete = new JToggleButton("Löschen");
        drag = new JToggleButton("Verschieben");
        addPoint = new JToggleButton("Einfügen");
        numberPoints = new JComboBox<String>(zähler);
        addPoints = new JButton("Punkte einfügen");
        addPoints.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));//es wird ein bisschen ein Abstand zu den Undo und Redo Pfeilen hergestellt
        
        add(delete);
        add(drag);
        add(addPoint);
        add(numberPoints);
        add(addPoints);
        toolBarReUndo = new ReUndoFunction(draw, this);
        
        delete.addActionListener(new ActionListener() {
            @Override
            /**
             * Die Methode wird aufgerufen wenn auf den delete Button gedrückt wird und sorgt dafür das die beiden 
             * Buttons drag und addPoint zurückgesetzt werden.
             */
            public void actionPerformed(ActionEvent e) {
                drag.setSelected(false);
                addPoint.setSelected(false);
            }
        });
        drag.addActionListener(new ActionListener() {
            /**
             * Die Methode wird aufgerufen wenn auf den drag Button gedrückt wird und sorgt dafür das die beiden 
             * Buttons delete und addPoint zurückgesetzt werden.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                delete.setSelected(false);
                addPoint.setSelected(false);
            }
        });
        addPoint.addActionListener(new ActionListener() {
            /**
             * Die Methode wird aufgerufen wenn auf den addPoint gedrückt wird und sorgt dafür das die beiden Buttons 
             * delete und drag zurückgesetzt werden.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                delete.setSelected(false);
                drag.setSelected(false);
            }
        });
        
       addPoints.addActionListener(new ActionListener() {
           /**
            * Mit den Button addPoints werden eine vorher eingestellte Anzahl von Punkten zufällig in die Zeichenfläche eingefügt.
            */
            @Override
            public void actionPerformed(ActionEvent e) {
                int reallyAdded = 0;//wird verwendet damit die Undo und Redo Funktion die richtige Anzahl von Punkten bekommt
                String Item = (String)numberPoints.getSelectedItem();   
                //Anzahl der eingestellten Punkte beim Drücken des Buttons addPoints
                int amount = Integer.parseInt(Item);  //umwandlung der Anzahl der eingestellten Punkte in eine Integer Variable
                for(int i=0;i<amount;i++) {
                    int x = (int)(Math.random()*draw.getWidth());
                    if(x>draw.getWidth()/2)
                        /*da der Nullpunkt der Zeichenfläche oben links ist, aber der Nullpunkt der Punkte in der Mitte der
                         * Zeichenfläche liegt müssen die random Points umgerechnet werden */
                        x = x-draw.getWidth();
                    //erzeugt einen zufälligen x Wert in der vorgegebenen Zeichenfläche
                    int y = (int)(Math.random()*draw.getHeight());  
                    /*da der Nullpunkt der Zeichenfläche oben links ist, aber der Nullpunkt der Punkte in der Mitte der
                     * Zeichenfläche liegt müssen die random Points umgerechnet werden */
                    if(y>draw.getHeight()/2)
                        y = y-draw.getHeight();
                    //erzeugt einen zufälligen x Wert in der vorgegebenen Zeichenfläche
                    if(PointOrganisation.addPoint(new Point(x, y))==true) {
                        /*es wird kontrolliert ob wirklich ein Punkt hinzugefügt wurde und es sich nicht 
                         * um einen doppelten Punkt handelt*/
                        reallyAdded++;
                    }
                    //fügt einen neuen Punkt mit den vorher berechneten Werten in die Punktemenge ein
                }
                toolBarReUndo.addUndo(String.valueOf(reallyAdded));
                //wird hinzugefügt um nachher die richtige Anzahl an Punkten wieder zu löschen
                ConvexHullAlgorithm.calculateConvexHull(true);
                BiggestIncludedCircle.calculateBiggestIncludedCircle(true);
                draw.repaint();
            }
        });
    }
    @Override
    /**
     * Die Methode fügt einen neuen Punkt mit den übergebenen x und y Werten ein, falls der Button addPoint 
     * ausgewählt wurde oder löscht den ersten Punkt der Punktemenge der in einem gewissen Umkreis von den 
     * übergebenen x und y Werten liegt, falls der Button delete ausgewählt wurde.
     */
    public void setPoint(int x, int y) {        
        if(addPoint.isSelected()) {   //überprüft ob der Button addPoint ausgewählt ist
            PointOrganisation.addPoint(new Point(x,y)); //fügt einen neuen Punkt in die Punktemenge ein
            toolBarReUndo.addUndo("1"); //fügt einen String hinzu für die Undo Funktion in der ToolBarReUndo
            toolBarReUndo.deleteRedo(); 
            //Redo muss gelöscht werden falls neue Punkte hinzugefügt werden, da die Punkte nicht mehr hinzugefügt werden können
            ConvexHullAlgorithm.calculateConvexHull(true);
            BiggestIncludedCircle.calculateBiggestIncludedCircle(true);
        }
        
        if(delete.isSelected()) {   //überprüft ob der Button delete ausgewählt ist
            Iterator<Point> points = PointOrganisation.getPoints().iterator();  
            //erzeugt einen Iterator der Liste der Punktemenge zum durchlaufen
            boolean found = false;  //falls gefunden wird das durchlaufen der Liste vorzeitig beendet
            while(points.hasNext()&&!found) {
                Point point = (Point)points.next();
                if(point.getBoundingBox().contains(x,y)) {  
                    //es wird überprüft ob der Punkt in der Nähe der übergebenen x und y Werte liegt
                    found = true; 
                    /*falls der Punkte in der Nähe der übergebenen x und y Werte liegt wird die Schleife mittels 
                     * der Variable found abgebrochen*/
                    toolBarReUndo.addPointUndo(point);
                    toolBarReUndo.addUndo("delete "+PointOrganisation.getPointIndex(point));
                    /*übergibt den String delete damit Redo und Undo Funktion die richtige Funktion ausführt 
                     * an dem String angehengt is eine Integer Variable die den Index des gelöschten Punktes hinzufügt*/
                    PointOrganisation.removePoint(point);   //der Punkt der gefuden wurde wird von der Punktemenge entfernt
                }
                if(PointOrganisation.getPoints().isEmpty()) 
                    /*es wird überprüft ob die Punktemenge leer ist, wenn sie leer ist soll die Variable welche für das abfragen ob
                     gespeichert werden soll oder nicht wieder auf false gesetzt werden, da bei leerer Punktemenge nichts 
                     abgespeichert werden muss*/
                    PointOrganisation.setChanged(false);
                else {
                    ConvexHullAlgorithm.calculateConvexHull(true);
                    BiggestIncludedCircle.calculateBiggestIncludedCircle(true);
                }
                /*falls noch Punkte in der Punktemenge von PointOrganisation sind wird der Konvex Hull Algorithmus 
                 * nach dem löschen aufgerufen*/
            }
        }
        draw.repaint();
    }
    @Override
    /**
     * Die Methode verchiebt den Punkt der vorher in der Methode mousePressed ausgewählt wurde, falls der Button drag 
     * ausgewählt wurde.
     */
    public void mouseDragged(int x, int y, int pointSize) {
        int width = draw.getWidth()/2;   //holt sich aus der Variable sizeDraw den x Wert der Zeichenfläche
        int height = draw.getHeight()/2; //holt sich aus der Variable sizeDraw den y Wert der Zeichenfläche
        if(foundDragPoint&&((-width+pointSize/2<x&&x<width-pointSize/2)&&(-height+pointSize/2<y&&y<height-pointSize/2))) {
            /*es wird abgefragt ob ein Punkt in der Methode mousePressed gefunden wurde und  
              der Punkte nicht außerhalb der Zeichenfläche verschoben wird*/
            dragPoint.updateBoundingBox(x, y);      //die Bounding Box muss auf die neuen Koordinaten aktualisiert werden  
            dragPoint.setLocation(x, y); //die Koordinaten des Punktes müssen aktualisiert werden
            ConvexHullAlgorithm.calculateConvexHull(true);
            BiggestIncludedCircle.calculateBiggestIncludedCircle(true);
            draw.repaint();
        }
    }
    @Override
    /**
     * Die Methode wählt den ersten Punkt der Punkteliste aus welcher in einem gewissen Umkreis um den Mauszeiger liegt, 
     * falls der Button drag ausgewählt wurde.
     */
    public void mousePressed(int x, int y) {
        foundDragPoint = false;     
        /*wird auf false gesetzt damit in der Methode mouseDragged nicht auf einen leeren Punkt oder 
         * auf einen zuvor verschobenen Punkt zugegriffen wird*/
        if(drag.isSelected()) {     //es wird überprüft ob der Button drag ausgewählt ist
            Iterator<Point> points = PointOrganisation.getPoints().iterator();
            //erzeugt einen Iterator der Liste der Punktemenge zum durchlaufen
            while(points.hasNext()&&!foundDragPoint) {
                /*sucht einen Punkt in der Liste welcher im Umkreis um die gegebenen Koordinaten liegt und 
                 * falls gefunden wird die Liste abgebrochen*/
                dragPoint = (Point)points.next();   
                //globale Variable auf welcher auch in der Methode mouseDraggeg zugegriffen wird um den Punkt zu verschieben
                if(dragPoint.getBoundingBox().contains(x,y)) {
                    //es wird überprüft ob der Punkt in der Nähe der übergebenen x und y Werte liegt
                    foundDragPoint = true;      //wird auf true gesetzt falls der Punkt gefunden wurde
                    toolBarReUndo.addUndo("dragged"); //wird hinzugefügt damit man weiß welche Funktion zurückgenommen werden muss
                    toolBarReUndo.addDraggedPoint(dragPoint);//der ursprüngliche Punkt wird hinzugefügt
                }
            }
        }
        
    }
    /**
     * Die Methode getToolBarReUndo gibt die RedoUndo TollBar zurück um außerhalb der Klasse MainToolBar auf sie zugreifen zu können.
     * @return Das Objekt toolBArReUndo welches die Funktionen der Redo und Undo Funktion realisiert.
     */
    ReUndoFunction getToolBarReUndo() {
        return toolBarReUndo;
    }
    @Override
    /**
     * Die Methode mouseReleased wird durch das Interface IPointListener eingefügt und wird verwendet um den Verschobenen Punkt
     * in die Liste der Undo Redo Funktion einzufügen um die Verschiebung wieder rückgängig machen zu können.
     */
    public void mouseReleased() {
        if(foundDragPoint)
            toolBarReUndo.addDraggedPoint(new Point(dragPoint));
        
    }
    
}
