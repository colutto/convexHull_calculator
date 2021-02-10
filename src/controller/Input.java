package controller;


import java.io.*;
import java.util.*;

import javax.swing.*;

import mainFrame.*;
import model.*;


/**
 * Die Klasse Input ist nur zum einlesen der Dateien verwendet.
 * @author Stefan Colutto 9513140
 *
 */
public class Input{
    private static LinkedList<Double> listWidth = new LinkedList<Double>();//zur Speicherung der eingelesenen x Koordinaten
    private static LinkedList<Double> listHeight = new LinkedList<Double>();//zur Speicherung der eingelesenen y Koordinaten
/**
 * Die statische Methode loadFile wird verwendet um eine Datei einzulesen und in Koordinaten zur berechnung der Konvexen 
 * Hülle umzuwandeln.
 * @param file Der Parameter file wird von der Klasse MenuFile{@link mainFrame.MenuFile} 
 * übergeben und stellt die zu öffnende Datei dar.  
 * @param mainFrame Der Parameter mainFrame wird von der Klasse MenuFile{@link mainFrame.MenuFile} 
 * übergeben und wird zum Sperren des 
 * Hauptbildschirms benötigt.
 * @param draw Wird benötigt um auf die Methode zurückzugreifen, welche die Punktegröße zurück gibt.
 * @param scrollPane Wird benötigt um das Panel in dem die Zeichenfläche liegt sichtbar zu machen und wenn nötig mit Scrollbalken 
 * zu versehen.
 * @param menuFile Wird benötigt um das Menu nach dem Ladevorgang zu aktualisieren damit auf neue Funktionen zugegriffen werden kann.
 */
    public static void loadFile(File file,MainFrame mainFrame, Draw draw, JScrollPane scrollPane, MenuFile menuFile) {
        String line;                        //String um die einzelne Zeile abzuspeichern
        BufferedReader in = null;           //lest die Daten aus der Datei und gibt sie in einzelnen Zeilen aus
        String[] point = new String[2];     //zum Separieren der beiden Punkte in der Zeile
        double x;
        double y;
        PointOrganisation.deleteAllPoints();//die alten Punkte müssen alle gelöscht werden bevor die neuen Punkte geladen werden
        try {
            in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) {
                //dem String line wird die nächste Zeile übergeben solange die Zeile nicht leer ist
                point = line.trim().split("( )+");
                /*Separiert die zwei Punkte in der Zeile welche mittels Leerzeichen getrennt sind. Mit der Methode 
                  trim fallen die Leerzeichen davor und dahinter weg. Mit der Methode split werden die zwei Zahlen
                  getrennt egal wieviel Leerzeichen dazwischen sind. */
                if(point[0].matches("[+-]?[0-9]*")&&point[1].matches("[+-]?[0-9]*")) {
                    //überprüft ob die eingelesenen Strings aus Zahlen bestehen
                    x = Double.parseDouble(point[0]);      //wandelt die x Koordinate in einen Integer Wert um
                    y = Double.parseDouble(point[1]);      //wandelt die y Koordinate in einen Integer Wert um
                    listWidth.add(x);
                    listHeight.add(y);
                    PointOrganisation.addPoint(new Point(x, y));
                }
            }
            PointOrganisation.setChanged(false);
        } catch (FileNotFoundException e) {//fängt die Exception des FileReader ab falls dieser den übergebene File nicht findet
            JOptionPane.showMessageDialog(mainFrame, "Die Datei konnte nicht gefunden werden!", "Error", JOptionPane.ERROR_MESSAGE);
        }catch(IOException e) {//fängt die vom BufferedReader durch die Methode readLine entstandene Exception ab 
            JOptionPane.showMessageDialog(mainFrame, "Fehler beim Lesen der Datei!", "Error", JOptionPane.ERROR_MESSAGE);
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
            /*fängt die beiden Exceptions ab welche entstehen können falls in der
             Datei die Punkte nicht richtig abgespeichert worden sind */
          JOptionPane.showMessageDialog(mainFrame, "Falsche strukturierung der Datei!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        Point max = new Point(0, 0);
        Point min = new Point(0, 0);
        try {                                                         
            max = findMaxDrawSize(listWidth, listHeight); //sucht die größte x und y Koordinate und gibt diese in einem Punkt aus
            min = findMinDrawSize(listWidth, listHeight); //sucht die kleinste x und y Koordinate und gibt diese in einem Punkt aus
        } catch (NoSuchElementException e) { 
            //falls keine Zahlen eingelesen werden konnten wird in den beiden Methoden eine NoSuchElementException ausgelöst
            JOptionPane.showMessageDialog(mainFrame, "Es konnten keine Zahlen in der Datei gefunden werden!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        ConvexHullAlgorithm.calculateConvexHull(true);
        BiggestIncludedCircle.calculateBiggestIncludedCircle(true);
        mainFrame.setEnabled(true);         //nach dem Dialog kann der MainFrame wieder auf Benutzt werden
        mainFrame.setVisible(true);         //der MainFrame wird wieder als vorderstes Fenster angezeigt
        draw.setPreferredSize((int)max.getX(), (int)max.getY(), (int)min.getX(), (int)min.getY());
        /*Die Größe der Zeichenfläche wird erstellt wobei die Zeichenfläche bei negativen Punkten größer wird und durch 
         * draw.getPointSize werden auch die am Rand liegenden Punkte gezeichnet, da die Zeichenfläche vergrößert wird*/
        scrollPane.setViewportView(draw);  /*das scrollPane bekommt das panelDraw übergeben damit es wenn nötig mit Scrollbalken 
                                            dargestellt werden kann*/
        PointOrganisation.setChanged(false);    /*false, da die Punktemenge schon abgespeichert ist und erst wieder bei Änderungen
                                                 abgespeichert werden muss*/
    }
    
    /**
     * Die Methode loadFile wird für das einlesen der Testfälle verwendet, da keine Zeichenfläche vorhanden ist muss die größe der 
     * Zeichenfläche nicht bestimmt werden.
     * @param fileName Ist ein String Parameter und gibt den Namen der Datei an welcher geladen werden soll.
     */
    public static void loadFile(String fileName) {
        String line = "";                   //String um die einzelne Zeile abzuspeichern
        BufferedReader in = null;           //lest die Daten aus der Datei und gibt sie in einzelnen Zeilen aus
        String[] point = new String[2];     //zum Separieren der beiden Punkte in der Zeile
        double x;
        double y;
        PointOrganisation.deleteAllPoints();
        try {
            in = new BufferedReader(new FileReader(fileName));
            while ((line = in.readLine()) != null) {
                point = line.trim().split("( )+");  
                /*Separiert die zwei Punkte in der Zeile welche mittels Leerzeichen getrennt sind. Mit der Methode 
                 trim fallen die Leerzeichen davor und dahinter weg. Mit der Methode split werden die zwei Zahlen
                 getrennt egal wieviel Leerzeichen dazwischen sind. */
                
                if(point.length>1&&point[0].matches("[+-]?[0-9]*")&&point[1].matches("[+-]?[0-9]*")) {
                    //überprüft ob die eingelesenen Strings aus Zahlen bestehen
                    x = Double.valueOf(point[0]);
                    y = Double.valueOf(point[1]);
                    PointOrganisation.addPoint(new Point(x, y));
                }
            }
            PointOrganisation.setChanged(false);
        } catch (FileNotFoundException e) {//fängt die Exception des FileReader ab falls dieser den übergebene File nicht findet
            System.out.println("Die Datei konnte nicht gefunden werden!");
        }catch(IOException e) {//fängt die vom BufferedReader durch die Methode readLine entstandene Exception ab 
            System.out.println("Fehler beim Lesen der Datei!");
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
            /*fängt die beiden Exceptions ab welche entstehen können falls in der
              Datei die Punkte nicht richtig abgespeichert worden sind */
            System.out.println("Falsche strukturierung der Datei!:"+fileName+" "+point[0]);
        }
        ConvexHullAlgorithm.calculateConvexHull(false);
        BiggestIncludedCircle.calculateBiggestIncludedCircle(false);
        
    }
    /**
     * Die Methode sucht den maximalen Wert der x und y Koordinaten zur Berechnung der Zeichenfläche.
     * @param listWidth Enthält alle x Koordinaten der eingelesenen Punkte.
     * @param listHeight Enthält alle x Koordinaten der eingelesenen Punkte.
     * @return Gibt einen Punkt zurück in welchen die größte x und y Koordinate gespeichert ist.
     */
    @SuppressWarnings("hiding")
    private static Point findMaxDrawSize(LinkedList<Double> listWidth,LinkedList<Double> listHeight) {
        double maxX = Collections.max(listWidth);
        double maxY = Collections.max(listHeight);
        return new Point(maxX,maxY);
    }
    /**
     * Die Methode sucht den minimalen Wert der x und y Koordinaten zur Berechnung der Zeichenfläche.
     * @param listWidth Enthält alle x Koordinaten der eingelesenen Punkte.
     * @param listHeight Enthält alle x Koordinaten der eingelesenen Punkte.
     * @return Gibt einen Punkt zurück in welchen die kleinste x und y Koordinate gespeichert ist.
     */
    @SuppressWarnings("hiding")
    private static Point findMinDrawSize(LinkedList<Double> listWidth,LinkedList<Double> listHeight) {
        double minX = Collections.min(listWidth);
        double minY = Collections.min(listHeight);
        return new Point(minX, minY);
    }
}
