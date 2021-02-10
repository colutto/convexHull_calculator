package model;

import java.math.BigInteger;
import java.util.*;

/**
 * Die Klasse ConvexHullAlgorithmus bietet eine statische Methode zum berechnen der Punktemenge an.
 * @author Stefan Colutto 9513140
 *
 */
public class ConvexHullAlgorithm{

    private static LinkedList<Point> rightTopContour = new LinkedList<Point>();//rechte untere Kontur der äußeren Punkte
    private static LinkedList<Point> rightDownContour = new LinkedList<Point>();//rechte obere Kontur der äußeren Punkte
    private static LinkedList<Point> leftTopContour = new LinkedList<Point>();//linke untere Kontur der äußeren Punkte
    private static LinkedList<Point> leftDownContour = new LinkedList<Point>();//linke obere Kontur der äußeren Punkte
    private static LinkedList<Point> sortedPoints = new LinkedList<Point>();
    //die sortierte Liste für die berechnung der linken unteren und oberen Kontur
    private static LinkedList<Point> sortedPoints2 = new LinkedList<Point>();
    //die zweite sortierte Liste für die berechnung der rechten unteren und oberen Kontur 
    private static Point leftMinYSoFar;
    //nach dem Ermitteln der Kontur ist es der kleinste y Punkt welcher mit dem Punkt rightMinYSoFar übereinstimmt
    private static Point leftMaxYSoFar;
    //nach dem Ermitteln der Kontur ist es der größte y Punkt welcher mit dem Punkt rightMaxYSoFar übereinstimmt
    private static Point rightMinYSoFar;
    //nach dem Ermitteln der Kontur ist es der kleinste y Punkt welcher mit dem Punkt rightMinYSoFar übereinstimmt
    private static Point rightMaxYSoFar;
    //nach dem Ermitteln der Kontur ist es der größte y Punkt welcher mit dem Punkt rightMaxYSoFar übereinstimmt
    
    
    
    
    /**
     * Die statische Methode calculateConvexHull berechnet die konvexe Hülle der Punktemenge und gibt diese in einer Liste zurück.
     * @param drawing Ist ein Boolean zum Entscheiden ob eine Zeichenfläche existiert welche neu gezeichnet werden muss, oder 
     * ob es sich um einen Konsolentest handelt.
     */
    @SuppressWarnings("unchecked")
    public static void calculateConvexHull(boolean drawing){
        rightDownContour.removeAll(rightDownContour);//die alten berechneten Punkte werden gelöscht
        rightTopContour.removeAll(rightTopContour);
        leftDownContour.removeAll(leftDownContour);
        leftTopContour.removeAll(leftTopContour);
        sortedPoints = (LinkedList<Point>) PointOrganisation.getPoints().clone();
        //es wird eine komplett neue Liste erzeugt mit den Punkten der PointOrganisation
        Collections.sort(sortedPoints);     
        //die Liste wird nach x und y Koordinaten wie im Interface Comparable implementiert sortiert
        sortedPoints2 = (LinkedList<Point>) sortedPoints.clone();
        /*es wird eine zweite Liste mit den gleichen Punkten wie in sortetPoints erstellt damit die Liste für die Kontur 
         * der rechten Seite rückwärts durchlaufen werden kann*/
        Iterator<Point> point = sortedPoints.iterator();
        Point right = sortedPoints2.removeLast();
        rightMaxYSoFar = right;
        /*die beiden Punkte rightMaxYSoFar und rightMinYSoFar zum bestimmen der rechten Kontur 
         * werden am Anfang auf den rechten Extrempunkt gesetzt*/
        rightMinYSoFar = right;
        rightTopContour.add(right);//die Konturen der rechten Seite beginnen mit dem letzten Punkt der sortierten Liste
        rightDownContour.add(right);
        Point left = point.next();
        leftMaxYSoFar = left;
        /*die beiden Punkte leftMaxYSoFar und leftMinYSoFar zum bestimmen der linken Kontur werden am Anfang 
         * auf den linken Extrempunkt gesetzt*/
        leftMinYSoFar = left;
        leftTopContour.add(left);//die Konturen der linken Seite beginnen mit dem ersten Punkt der sortierten Liste
        leftDownContour.add(left);
        while(point.hasNext()) {
            /*es wird durch die Liste sortedPoints von links nach rechts und in der Liste sortedPoints2 
             * von rechts nach links iteriert*/
            left = point.next();   
            /*dabei werden die Punkte die jeweils größer oder gleich sind als die bisher gefundenen Punkte 
             * welche in den Variablen leftMinYSoFar, leftMaxYSoFar*/
            if(left.getY()<=leftMinYSoFar.getY()&&leftMinYSoFar!=rightMinYSoFar) {   
                /*rightMinYSoFar und rightMaxYSoFar gespeichert sind als neue größte Punkte in den Variablen 
                 * gespeichert die vorher gespeicherten*/
                leftDownContour.add(left);  //Punkte werden in die Listen der Teilkonturen hinzugefügt
                leftMinYSoFar = left;
            }else
                if(left.getY()>=leftMaxYSoFar.getY()&&leftMaxYSoFar!=rightMaxYSoFar) {
                    leftTopContour.add(left);
                    leftMaxYSoFar = left;
                }
        }
        while(!sortedPoints2.isEmpty()) {
            right = sortedPoints2.removeLast();
            if(right.getY()<=rightMinYSoFar.getY()&&leftMinYSoFar!=rightMinYSoFar) {
                rightDownContour.add(right);
                rightMinYSoFar = right;
            }else
                if(right.getY()>=rightMaxYSoFar.getY()&&leftMaxYSoFar!=rightMaxYSoFar) {
                    rightTopContour.add(right);
                    rightMaxYSoFar = right;
                }
        }
        
      
        
        if(leftDownContour.size()>2)    //das bereinigen der Kontur ist nur mit mindestens 3 Variablen nötig
            calculateDownConvexHull(leftDownContour);   
        //die Kontur wird im Algorithmus bereinigt damit der kürzeste Weg gefunden werden kann
        if(rightDownContour.size()>2)
            calculateTopConvexHull(rightDownContour);
        if(rightTopContour.size()>2)
            calculateDownConvexHull(rightTopContour);
        if(leftTopContour.size()>2)
            calculateTopConvexHull(leftTopContour);
        
        
        
        rightDownContour.removeLast();
        /*der letzte Punkt dieser Menge existiert bereits in der Menge in welche diese Punkten hinzugefügt werden, 
         * damit keine doppelten Punkte auftreten wird der Punkt gelöscht*/
        while(!rightDownContour.isEmpty())
            leftDownContour.add(rightDownContour.removeLast());
        //die Punkte der vier Teilkonturen werden der Teilkontur leftDownContour nach und nach hinzugefügt
        
        
        rightTopContour.removeFirst();
        /*der letzte Punkt dieser Menge existiert bereits in der Menge in welche diese Punkten hinzugefügt werden, 
         * damit keine doppelten Punkte auftreten wird der Punkt gelöscht*/
        while(!rightTopContour.isEmpty())
            leftDownContour.add(rightTopContour.removeFirst());
        //die Punkte der vier Teilkonturen werden der Teilkontur leftDownContour nach und nach hinzugefügt
        
        leftTopContour.removeLast();
        /*der letzte Punkt dieser Menge existiert bereits in der Menge in welche diese Punkten hinzugefügt werden, 
         * damit keine doppelten Punkte auftreten wird der Punkt gelöscht*/
        while(!leftTopContour.isEmpty()) 
            leftDownContour.addLast(leftTopContour.removeLast());
        //die Punkte der vier Teilkonturen werden der Teilkontur leftDownContour nach und nach hinzugefügt
        if(leftDownContour.size()>1&&drawing==false)
            leftDownContour.removeLast();
        PointOrganisation.addConvexHull(leftDownContour);
    }
    /**
     * Die private Methode calculateDownConvexHull bereinigt die oberen beiden Konturen, sodass nur noch die Punkte welche 
     * für die konvexe Hülle benötigt werden übrig bleiben.
     * @param contour Bekommt die Liste übergeben, die bereinigt werden soll.
     */
    private static void calculateDownConvexHull(LinkedList<Point> contour) {
        Point a = contour.get(0);//übergibt den ersten Punkt der übergebenen Liste
        Point b = contour.get(1);//übergibt den zweiten Punkt der übergebenen Liste
        Point c = b;
        boolean isFirst = false;
        /*falls beim Suchen der richtigen Determinanten bis zum ersten Punkt zurückgelaufen wird muss dort die zweite 
         * while Schleife abgebrochen werden*/
        while(c!=contour.getLast()) {//die Liste wird solange bereinigt bis der letzte Punkt der Liste kontrolliert wurde
            c = contour.get(contour.indexOf(c)+1);
            /*nach jedem Durchlauf der ersten while Schleife muss der Punkt c um eines erhöht werden, damit die Liste 
             * nach und nach bereinigt werden kann*/
            isFirst = false;
            /*nach jedem Durchlauf der ersten while Schleife muss isFirst auf False gesetzt werden damit 
             * der Algorithmus in die zweite while Schleife wieder eintreten kann*/
            if(LinksRechtsTest(a, b, c)>=0) {
                /*es wird überprüft ob der Punkt c links oder rechts von der Geraden der Punkte a und b liegt, 
                 * damit wird entschieden ob es sich um eine hereinragende Ecke handelt*/
                while(!isFirst&&(LinksRechtsTest(a, b, c)>=0)) {
                    /*in dieser Schleife werden wird mittels a und b solange zurück wandern, bis sich eine Gerade findet 
                     * welche links beim Punkt c vorbei läuft oder wir auf den ersten Punkt der Liste stoßen*/
                    contour.remove(b);
                    //die Punkte welche bei der Suche nach der passenden Geraden auftauchen werden aus der Liste gelöscht
                    b = a;//hiermit wird b nach hinten gesetzt
                    if(a!=contour.getFirst())
                        //a darf nur nach hinten gesetzt werden falls a noch nicht der letzte Punkt der Liste ist
                        a = contour.get(contour.indexOf(a)-1);//a wird um einen Punkt nahc hinten gesetzt
                    else 
                        isFirst = true;//falls a der letzte Punkt sein sollte wird die Liste mittels isFirst abgebrochen
                }
            }
            b = c;//b wird nach vorne gesetzt damit die Liste weiter durchwandert werden kann
            a = contour.get(contour.indexOf(b)-1);
            //a wird einen Punkt hinter b gesetzt damit die nächste Gerade kontrolliert werden kann
        }
    }
    /**
     * Die private Methode LinksRechtsTest berechnet die Determinante der Punkte a,b und c und entscheidet ob der Punkt c 
     * links,rechts oder auf der Geraden der Punkte a und b liegt.
     * @param a Der Punkt a vom Typ Point zur berechnung der Determinanten.
     * @param b Der Punkt b vom Typ Point zur berechnung der Determinanten.
     * @param c Der Punkt c vom Typ Point zur berechnung der Determinanten.
     * @return Gibt einen Integer Wert zurück. Beim Wert 1 liegt der Punkt c links von der Geraden, beim Wert -1 liegt der
     * Punkt c rechts von der Geraden und beim Wert 0 liegt der Punkt c auf der Geraden.
     */
    private static int LinksRechtsTest(Point a,Point b,Point c) {
        BigInteger firstCalculation = BigInteger.valueOf((int) b.getY());
        firstCalculation = firstCalculation.subtract(BigInteger.valueOf((int) c.getY()));
        firstCalculation = firstCalculation.multiply(BigInteger.valueOf((int) a.getX()));
        
        BigInteger secondCalculation = BigInteger.valueOf((int) c.getY());
        secondCalculation = secondCalculation.subtract(BigInteger.valueOf((int) a.getY()));
        secondCalculation = secondCalculation.multiply(BigInteger.valueOf((int) b.getX()));
        
        BigInteger thirdCalculation = BigInteger.valueOf((int) a.getY());
        thirdCalculation = thirdCalculation.subtract(BigInteger.valueOf((int) b.getY()));
        thirdCalculation = thirdCalculation.multiply(BigInteger.valueOf((int) c.getX()));
        
        
        firstCalculation = firstCalculation.add(secondCalculation);
        firstCalculation = firstCalculation.add(thirdCalculation);
        int comparison = firstCalculation.compareTo(BigInteger.ZERO);
        if(comparison<0)//es wird überprüft ob der Punkt c links, rechts oder auf der Geraden liegt 
            return 1;
            else if(comparison>0)
                return -1;
                else return 0;
            
    }
    /**
     * Die private Methode calculateDownConvexHull bereinigt die unteren beiden Konturen, sodass nur noch die Punkte welche 
     * für die konvexe Hülle benötigt werden übrig bleiben.
     * @param contour Bekommt die Liste übergeben, die bereinigt werden soll.
     */
    private static void calculateTopConvexHull(LinkedList<Point> contour) {
        Point a = contour.get(0);//übergibt den ersten Punkt der übergebenen Liste
        Point b = contour.get(1);//übergibt den zweiten Punkt der übergebenen Liste
        Point c = b;
        boolean isFirst = false;
        /*falls beim Suchen der richtigen Determinanten bis zum ersten Punkt zurückgelaufen wird muss dort 
         * die zweite while Schleife abgebrochen werden*/
        while(c!=contour.getLast()) {//die Liste wird solange bereinigt bis der letzte Punkt der Liste kontrolliert wurde
            c = contour.get(contour.indexOf(c)+1);
            /*nach jedem Durchlauf der ersten while Schleife muss der Punkt c um eines erhöht werden, 
             * damit die Liste nach und nach bereinigt werden kann*/
            isFirst = false;
            /*nach jedem Durchlauf der ersten while Schleife muss isFirst auf False gesetzt werden damit der Algorithmus 
             * in die zweite while Schleife wieder eintreten kann*/
            if(LinksRechtsTest(a, b, c)<=0) {
                /*es wird überprüft ob der Punkt c links oder rechts von der Geraden der Punkte a und b liegt, 
                 * damit wird entschieden ob es sich um eine hereinragende Ecke handelt*/
                while(!isFirst&&(LinksRechtsTest(a, b, c)<=0)) {
                    /*in dieser Schleife werden wird mittels a und b solange zurück wandern, bis sich eine Gerade findet 
                     * welche links beim Punkt c vorbei läuft oder wir auf den ersten Punkt der Liste stoßen*/
                    contour.remove(b);
                    //die Punkte welche bei der Suche nach der passenden Geraden auftauchen werden aus der Liste gelöscht
                    b = a;//hiermit wird b nach hinten gesetzt
                    if(a!=contour.getFirst())
                        //a darf nur nach hinten gesetzt werden falls a noch nicht der letzte Punkt der Liste ist
                        a = contour.get(contour.indexOf(a)-1);//a wird um einen Punkt nahc hinten gesetzt
                    else 
                        isFirst = true;//falls a der letzte Punkt sein sollte wird die Liste mittels isFirst abgebrochen
                }
            }
            b = c;//b wird nach vorne gesetzt damit die Liste weiter durchwandert werden kann
            a = contour.get(contour.indexOf(b)-1);
            //a wird einen Punkt hinter b gesetzt damit die nächste Gerade kontrolliert werden kann
        }
    }
    
    
    
}
