package model;


import java.util.LinkedList;

/**
 * Die Klasse PointOrganisation speichert die alle eingefügten Punkte und die Punkte der konvexen Hülle
 * @author Stefan Colutto 9513140
 *
 */
public class PointOrganisation {

    private static volatile LinkedList<Point> allPoints = new LinkedList<Point>();//Liste welch alle Punkte enthält
    private static LinkedList<Point> pointsConvexHull = new LinkedList<Point>();//Liste welche die Punkte der Konvexen Hülle enthält
    private static boolean changed = false; //Variable welche anzeigt ob sich die Liste allPoints verändert hat
    private static double circleRadius;
    private static double xCircle;
    private static double yCircle;
    
    /**
     * Fügt einen neuen Punkt am Ende der Liste allPoints hinzu.
     * @param point Point aus der Klasse Point.
     * @return Ein boolean welcher angibt ob der Punkt hinzugefügt wurde oder nicht.
     */
    public static boolean addPoint(Point point){
        changed = true;    
        /*die Punktemenge hat sich Verändert dadurch muss die Variable zum abfragen 
         * ob agespeichert werden soll auf true gesetzt werden*/
        if(!allPoints.contains(point)) {  //es werden nur Punkte hinzugefügt welche nicht schon in der Punktemenge enthalten sind
            allPoints.add(point);   //fügt den übergebenen Punkt am Ende der Liste allPoints ein
            return true;
        }else return false;
    }
    /**
     * Die Methode gibt den ersten Punkt der Liste allPoints zurück.
     * @return Point aus der Klasse Point.
     */
    public static Point getPoint() {
        return allPoints.getFirst();
    }
    /**
     * Die Methode gibt die Liste allPoints zurück. 
     * @return Die LinkedList allPoints.
     */
    public static LinkedList<Point> getPoints() {
        return allPoints;
    }
    /**
     * Fügt einen neuen Punkt am Ende der Liste pointsConvexHull hinzu.
     * @param inConvexHull Point aus der Klasse Point.
     */
    public static void addConvexHull(LinkedList<Point> inConvexHull) {
        pointsConvexHull = inConvexHull;
    }
    /**
     * Die Methode gibt den ersten Punkt der Liste pointsConvexHull zurück.
     * @return Point aus der Klasse Point.
     */
    public static Point getPointConvexHull() {
        return pointsConvexHull.getFirst();
    }
    /**
     * Die Methode gibt die Liste pointsConvexHull zurück. 
     * @return Die LinkedList allPoints.
     */
    public static LinkedList<Point> getPointsConvexHull() {
        return pointsConvexHull;
    }
    /**
     * Die Methode gibt die Variable changed zurück.
     * @return Boolean changed um anzuzeigen ob sich die Punktemenge verändert hat.
     */
    public static boolean isChanged() {
        return changed;
    }
    /**
     * Die Methode übergibt der Variablen changed einen neuen Wert.
     * @param changed Boolean changed um anzuzeigen ob sich die Punktemenge verändert hat.
     */
    public static void setChanged(boolean changed) {
        PointOrganisation.changed = changed;
    }
    /**
     * Die Methode löscht die gesamte Liste allPoints und die gesamte Liste pointsConvexHull.
     */
    public static void deleteAllPoints() {
        allPoints.removeAll(allPoints);
        pointsConvexHull.removeAll(pointsConvexHull);
        PointOrganisation.circleRadius = 0;
    }
    /**
     * Die Methode löscht einen übergebenen Punkt aus der Liste allPoints.
     * @param point Point aus der Klasse Point.
     */
    public static void removePoint(Point point) {
        changed = true; //changed wird auf true gesetzt, da sich die Liste allPoints verändert hat
        allPoints.remove(point);
    }
    /**
     * Die Methode removeLastPoint löscht den letzten Punkt der Liste allPoints und setzt changed auf true da sich die Punktemenge
     * verändert hat.
     * @return Gibt den letzten Punkt der Liste allPoints zurück.
     */
    public static Point removeLastPoint() {
        changed = true;
        return allPoints.removeLast();
    }
    /**
     * Die Methode setBiggestInnerCircle übergibt den Mittelpunkt des größten enthaltenen Kreises und die größe des Kreises
     * an die Variablen circleRadius, xCircle und yCircle.
     * @param x Gibt die x Koordinate des Kreises an.
     * @param y Gibt die y Koordinate des Kreises an.
     * @param circleRadius Gibt die größe des Kreises an.
     */
    public static void setBiggestInnerCircle(double x, double y, double circleRadius) {
        PointOrganisation.circleRadius = circleRadius;
        PointOrganisation.xCircle = x;
        PointOrganisation.yCircle = y;
    }
    /**
     * Die Methode addPoint fügt einen Punkt der Liste allPoints an einem bestimmten Index hinzu.
     * @param point Der Punkt welcher der Liste hinzugefügt werden soll.
     * @param index Ein Integer Wert welcher angibt wo der Punkt in der Liste hinzugefügt werden soll.
     */
    public static void addPoint(Point point, int index) {
        if(!allPoints.contains(point)) {  //es werden nur Punkte hinzugefügt welche nicht schon in der Punktemenge enthalten sind
            allPoints.add(index, point);
        }
    }
    /**
     * Die Methode getPointIndex liefert einen Integer Wert zurück der angibt wo sich der Punkt in der Liste allPoints befindet.
     * @param point Der Punkt von dem der Index zurückgegeben wird.
     * @return Einen Integer Wert der den Index des übergebenen Punktes angibt.
     */
    public static int getPointIndex(Point point) {
        return allPoints.indexOf(point);
    }
    /**
     * Die Methode getCircleRadius gibt die größe des Kreises aus.
     * @return Ein Double Wert der die größe des Kreises ausgibt.
     */
    public static double getCircleRadius() {
        return PointOrganisation.circleRadius;
    }
    /**
     * Die Methode getXCircle gibt die x Koordinate des Kreises aus.
     * @return Ein Double Wert der die x Koordinate des Kreises ausgibt.
     */
    public static double getXCircle() {
        return PointOrganisation.xCircle;
    }
    /**
     * Die Methode getYCircle gibt die y Koordinate des Kreises aus.
     * @return Ein Double Wert der die x Koordinate des Kreises ausgibt.
     */
    public static double getYCircle() {
        return PointOrganisation.yCircle;
    }
}
