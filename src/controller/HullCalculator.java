package controller;

import java.io.IOException;
import java.util.LinkedList;

import de.feu.propra18.interfaces.IHullCalculator;
import model.*;
/**
 * Die Klasse HullCalculator implementiert das Interface IHullCalculator mit den Methoden um zu überprüffen ob alle Testfälle 
 * richtig verarbeitet werden.
 * @author Stefan Colutto 9513140
 *
 */
public class HullCalculator implements IHullCalculator {

    @Override
    public void addPoint(int x, int y) {
        PointOrganisation.addPoint(new Point(x, y));
        
    }


    @Override
    public void addPointsFromArray(int[][] pointArray) {
        for(int i=0;i<pointArray.length;i++) {
            //das Array wird mit der Variable i durchlaufen und die Punkte werden der Punktemenge hinzugefügt
            PointOrganisation.addPoint(new Point(pointArray[i][0], pointArray[i][1]));
        }
        
    }


    @Override
    public void addPointsFromFile(String fileName) throws IOException {
        Input.loadFile(fileName);
        
    }


    @Override
    public void clear() {
        PointOrganisation.deleteAllPoints();
        
    }


    @Override
    public int[][] getConvexHull() {
        LinkedList<Point> list = PointOrganisation.getPointsConvexHull();
//        BiggestIncludedCircle.calculateBiggestIncludedCircle(false);
        //die konvexe Hülle wird berechnet und der Liste list übergeben
        int[][] convexHull = new int[list.size()][2];
        //das Array in welchen die Punkte der konvexen Hülle gespeichert werden wird erstellt
        int i = 0;
        while(i<list.size()) {//die  Punkte der konvexen Hülle werden in das Array geschrieben
            convexHull[i][0] = (int) list.get(i).getX();
            convexHull[i][1] = (int) list.get(i).getY();
            i++;
        }
        return convexHull;
    }


    @Override
    public String getEmail() {
        return "stefan.colutto@studium.fernuni-hagen.de";
    }


    @Override
    public String getMatrNr() {
        return "q9513140";
    }


    @Override
    public String getName() {
        return "Stefan Colutto";
    }


    @Override
    public double getGEKCenterX() {
        return PointOrganisation.getXCircle();
    }


    @Override
    public double getGEKCenterY() {
        return PointOrganisation.getYCircle();
    }


    @Override
    public double getGEKRadius() {
        return PointOrganisation.getCircleRadius();
    }
}
