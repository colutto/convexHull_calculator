package controller;

import java.io.*;
import java.util.Iterator;

import javax.swing.JOptionPane;

import mainFrame.MainFrame;
import model.Point;
import model.PointOrganisation;
/**
 * Die Klasse Output wird nur zum abspeichern der aktuellen Punkte verwendet.
 * @author Stefan Colutto 9513140
 *
 */
public class Output {
    /**
     * Die statische Methode safeFile wird von der Klasse MenuFile aufgerufen um die Punkte der momentan bearbeitenden 
     * Konvexen Hülle zu berechnen.
     * @param file Der file Parameter gibt die Datei an in welche abgespeichert werden soll.
     * @param mainFrame Der mainFrame Parameter wird für die Exception benötigt um bei Aufruf des Dialog Fensters den Fokus 
     * vom MainFrame zum Dialog zu lenken.
     * @return Eine Integer Variable die Anzeigt ob richtig gespeichert worder ist. 
     */
    public static int safeFile(File file,MainFrame mainFrame) {
        String x;
        String y;
        int isSaved = 0; //isSaved wird verwendet um Sicherzustellen dass die Punkte wirklich abgespeichert sind
        BufferedWriter out = null;
        Iterator<Point> pointList = PointOrganisation.getPoints().iterator();
        //Iterator zum durchlaufen der Liste der Klasse PointOrganisation
        Point point = null;
        try {
            out = new BufferedWriter(new FileWriter(new File(file.getAbsolutePath())));
            /*Es wird ein neuer BufferedWriter erzeugt und ein neuer File erzeugt   
              welcher den Pfad des zuvor ausgewählten Files bekommt*/
            while(pointList.hasNext()) {
                //die while Schleife läuft bis alle Elemente der Liste der PointOrganisation Klasse abgearbeitet sind
                point = (Point)pointList.next();
                x = String.valueOf((int)point.getX());
                y = String.valueOf((int)point.getY());
                out.write(x+" ");//die x Position und y Position werden mittels Leerzeichen getrennt in die Datei geschrieben
                out.write(y);
                out.newLine();//für jeden neuen Punkt wird eine neue Zeile in der Datei erzeugt
            }
            out.flush();
            out.close();//der BufferedWriter wird geschlossen
        } catch (IOException e) {//fängt die Exception des File Writers ab und gibt einen entsprechenden Dialog aus
            JOptionPane.showMessageDialog(mainFrame, "Error saving Points!", "Error", JOptionPane.ERROR_MESSAGE);
            isSaved = 1;  //falls beim Abspeichern eine Exception ausgelöst wird darf das Programm nicht geschlossen werden
        }
        return isSaved;
    }
}
