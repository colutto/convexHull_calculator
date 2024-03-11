package model;

import java.awt.geom.Ellipse2D;

/**
 * Die Klasse Point erbt von der Klasse Point und erweitert diese um eine unsichtbare BoundingBox. 
 * @author Stefan Colutto 9513140
 *
 */
public class Point extends java.awt.geom.Point2D.Double implements Comparable<Point> {
    
    private Ellipse2D boundingBox = null; //Bounding Box um herauszufinden ob ein Punkt in der Nähe des Mauszeigers liegt oder nicht
    private final int boundingBoxSize = 20; //die Größe der Bounding Box
    private double newPosX;
    private double newPosY;
    private double oldPosX;
    private double oldPosY;
    
    public Point(double x, double y) {
        super(x,y); 
        this.oldPosX = x;
        this.oldPosY = y;
        /*die übergebenen Koordinaten werden weiter an die Superklasse geleitet da ansonsten die contain Methode in der addPoint
         *Methode in der Klasse PointOrganistaion nicht benutzt werden kann*/
        boundingBox = new Ellipse2D.Double(this.x-boundingBoxSize/2, this.y-boundingBoxSize/2, boundingBoxSize, boundingBoxSize);
        //die Bounding Box wird erzeugt mit den selben Koordinaten wie der Punkte und einer fixen Größe
    }
    public Point(Point point) {//der Konstruktor wird in der Klasse MainToolBar für die Redo und Undo Funktion verwendet
        this.oldPosX = point.oldPosX;
        this.oldPosY = point.oldPosY;
        this.newPosX = point.x;
        this.newPosY = point.y;
        point.oldPosX = point.x;
        //beim übergebenen Punkt wird die Variable mit der alten Position des Punktes auf die neue Position des Punktes gesetzt
        point.oldPosY = point.y;
        //beim übergebenen Punkt wird die Variable mit der alten Position des Punktes auf die neue Position des Punktes gesetzt
    }
    /**
     * Die Methode macht die Bounding Box außerhalb der Klasse abfragbar.
     * @return Circle mit den selben Koordinate aber einer anderen Größe als der auf der Zeichenfläche dargestellte Punkt.
     */
    public Ellipse2D getBoundingBox() {
        return boundingBox;
    }
    /**
     * Die Methode übergibt der BoundingBox neue Koordinaten und stellt die
     * @param posX Integer Koordinate x.
     * @param posY Integer Koordinate y.
     */
    public void updateBoundingBox(int posX,int posY) {
        boundingBox.setFrame(posX-boundingBoxSize/2,posY-boundingBoxSize/2,boundingBoxSize,boundingBoxSize);
    }
    /**
     * Die überschriebene Methode wird verwendet um die Punkte miteinander vergleichen zu können damit die Menge 
     * an Punkten in der Klasse Konvex Hull sortiert werden kann.
     */
    @Override
    public int compareTo(Point o) {
        if(this.x<o.getX())
            return -1;
            else if(this.x>o.getX())
                return 1;
                else {
                    if(this.y<o.getY())
                        return -1;
                        else if(this.y>o.getY())
                            return 1;
                            else return 0;
                }
    }
    /**
     * Die Methode getOldPosX gibt die Variable oldPosX aus.
     * @return Die double Variable oldPosX.
     */
    public double getOldPosX() {
        return oldPosX;
    }
    /**
     * Die Methode getOldPosY gibt die Variable oldPosY aus.
     * @return Die double Variable oldPosY.
     */
    public double getOldPosY() {
        return oldPosY;
    }
    /**
     * Die Methode getNewPosX gibt die Variable newPosX aus.
     * @return Die double Variable newPosX.
     */
    public double getNewPosX() {
        return newPosX;
    }
    /**
     * Die Methode getNewPosY gibt die Variable newPosY aus.
     * @return Die double Variable newPosY.
     */
    public double getNewPosY() {
        return newPosY;
    }
    
   
}
