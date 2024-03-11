package model;

import java.util.LinkedList;

import model.EdgeSet.Edge;

/**
 * Mit der Klasse BiggestIncludedCircle wird der größte enthaltene Kreis berechnet der Mittelpunkt und der Kreisradius werden 
 * danach der Klasse Point Organisation für die Ausgabe übergeben.
 * @author Stefan Colutto 9513140
 *
 */
public class BiggestIncludedCircle {

    /**
     * Die Methode calculateBiggestIncludedCircle enthaltet den gesamten Algorithmus zur berechnung des größten ethaltenen Kreises
     * Die Methode muss bei jeder Änderung der Punktemenge ausgeführt werden um immer den aktuell größtmöglichen Kreis 
     * darstellen zu können. Falls die vom Algorithmus zur berechnung der Konvexen Hülle erhaltenen Punkte kleiner drei sind, wird
     * der erste Punkt der konvexen Hülle als Mittelpunkt des Kreises verwendet und der Kreisradius wird auf null gesetzt.
     * @param draw Nach dem berechnen des größten enthaltenen Kreises muss die Zeichenfläche neu gezeichnet werden, allerdings
     * nur fals es auch eine Zeichenfläche gibt und dies wird mit dem boolean draw erreicht.
     */
    public static void calculateBiggestIncludedCircle(boolean draw) {
        LinkedList<Point> convexHull = PointOrganisation.getPointsConvexHull();//die Konvexe Hülle wird geladen
        if(convexHull.size()>=3) {//die Konvexe Hülle muss mindestens 3 Punkte enthalen um einen Kreis zu finden
            EdgeSet edgeSet = new EdgeSet();//ein neues EdgeSet wird erstellt
            Heap heap = new Heap();//ein neuer Heap wird erstellt
            Circle circle; 
            int i = 0;
            while(i<convexHull.size()-1) {
                //alle Kanten werden erstellt, jede Kante erhählt 2 Punkte der Konvexen Hülle
                circle = new Circle(convexHull.get(i++), convexHull.get(i));//die Kante wird erstellt 
                edgeSet.addEdge(circle);//die Kante wird hinzugefügt
            }
            if(!draw) {
                /*zum Testen des gößten enthaltenen Kreises muss bei Ausführen mittels des Tester Programms die letzte Kante
                 * manuell hinzugefügt werden */
                circle = new Circle(convexHull.get(i), convexHull.get(0));//die letzte Kante wird erzeugt
                edgeSet.addEdge(circle);//die letzte Kante wird hinzugefügt
            }
            int j = 0;
            while(j<edgeSet.getSize())//der Kreis von jeder Kante muss berechnet werden 
                calculateCircle(edgeSet.getEdge(j++));
            int h = 0;
            while(h<edgeSet.getSize()) {
                /*der Heap wird mit Elementen aus der Liste vom edgeSet gefüllt und nach dem Kreis beginnend der davor 
                 * berechnet wurde sortiert*/
                IElem node = edgeSet.getEdge(h++).getElem();//das h. Objekt vom Typ IElem wird aus der Liste edgeSet ausgegeben
                heap.addNode(node);
            }
            while(edgeSet.getSize()>3) {
                //die Schleife läuft bis nur noch 3 Kanten übrig sind, da dann alle Kanten die gleichen Kreise haben
                IElem elem = heap.getMinimum();//der minimale Kreis wird aus dem Heap ausgegeben
                IElem previous = edgeSet.getPreviousEdge(elem.getEdge());
                //die laut der Liste edgeSet vorherige Kante des Minimums wird einer Variable zugewiesen
                IElem next = edgeSet.getNextEdge(elem.getEdge());
                //die laut der Liste edgeSet nächste Kante des Minimums wird einer Variable zugewiesen
                edgeSet.deleteEdge(elem.getEdge());//die Minimale Kante wird aus der Liste esgeSet gelöscht
                calculateCircle(previous.getEdge());//der Kreis der vorherigen Kante des Minimums wird neu berechnet
                calculateCircle(next.getEdge());//der Kreis der nächsten Kante des Minimums wird neu berechnet
                heap.reHeapNode(previous.getNode());//die neu berechnete vorherige Kante wird im Heap neu angeordnet
                heap.reHeapNode(next.getNode());//die neu berecnete nächste Kante wird im Heap neu angeordnet
            }
            IElem result = edgeSet.getEdge(0).getElem();
            /*von den drei übrig gebliebenen Kanten wird die erste in der Liste edgeSet ausgewählt, da alle drei gleich sind
             * spielt die Auswahl allerdings keine Rolle*/
            PointOrganisation.setBiggestInnerCircle(result.getXCircle(),result.getYCircle(), result.getCircleRadius());
            //das Ergebnis wird der Klasse PointOrganisation zur Asugabe übergeben
        }else {
            /*falls die Konvexe Hülle nicht drei Punkte enthalten hat kann kein Kreis berechnet werden und es wird
             * für die Koordinaten des Kreises der erste Punkt der Konvexen Hülle verwendet, für den Kreisradius wird
             * konstant null eingesetzt*/
            double xCircle = convexHull.get(0).getX();//die x Koordinate des Kreises 
            double yCircle = convexHull.get(0).getY();//die y Koordinate des Kreises
            PointOrganisation.setBiggestInnerCircle(xCircle, yCircle, 0);
          //das Ergebnis wird der Klasse PointOrganisation zur Asugabe übergeben
        }
        
    }
    /**
     * Die Methode calculateCircle berechnet durch und für die übergebene Kante den Mittelpunkt und den Kreisradius 
     * des größten enthaltenen Kreises und überschreibt die Werte der Kante.
     * @param inEdge Die übergebene Variable ist vom Typ Edge und wird verwendet um für die Kante den größtmöglichen Kreis 
     * zu finden.
     */
      private static void calculateCircle(Edge inEdge) {
          double previousXPointA = inEdge.getPrevious().getElem().getPointA().getX();
          //x Koordinate des ersten Punktes der vorherigen Kante
          double previousYPointA = inEdge.getPrevious().getElem().getPointA().getY();
          //y Koordinate des ersten Punktes der vorherigen Kante
          double previousXPointB = inEdge.getPrevious().getElem().getPointB().getX();
          //x Koordinate des zweiten Punktes der vorherigen Kante
          double previousYPointB = inEdge.getPrevious().getElem().getPointB().getY();
          //y Koordinate des zweiten Puntes der vorherigen Kante
        
          double xPointA = inEdge.getElem().getPointA().getX();
          //x Koordinate des ersten Punktes der übergebenen Kante
          double yPointA = inEdge.getElem().getPointA().getY();
          //y Koordinate des ersten Punktes der übergebenen Kante
          double xPointB = inEdge.getElem().getPointB().getX();
          //x Koordinate des zweiten Punktes der übergebenen Kante
          double yPointB = inEdge.getElem().getPointB().getY();
          //y Koordinate des zweiten Punktes der übergebenen Kante 
            
          double nextXPointA = inEdge.getNext().getElem().getPointA().getX();
          //x Koordinate des ersten Punktes der nächsten Kante
          double nextYPointA = inEdge.getNext().getElem().getPointA().getY();
          //y Koordinate des ersten Punktes der nächsten Kante
          double nextXPointB = inEdge.getNext().getElem().getPointB().getX();
          //x Koordinate des zweiten Punktes der nächsten Kante
          double nextYPointB = inEdge.getNext().getElem().getPointB().getY();
          //y Koordinate des zweiten Punktes der nächsten Kante
          
          /*es folgt die Berechnung der Koordinaten des Kreises und der Radius des Kreises*/
          double dxAB = xPointA-xPointB;
          double dyAB = yPointA-yPointB;
          double dAB = Math.sqrt((dxAB * dxAB) + (dyAB * dyAB));
    
          double dxCD = nextXPointA-nextXPointB;
          double dyCD = nextYPointA-nextYPointB;
          double dCD = Math.sqrt((dxCD * dxCD) + (dyCD * dyCD));
    
          double dxEF = previousXPointA-previousXPointB;
          double dyEF = previousYPointA-previousYPointB;
          double dEF = Math.sqrt((dxEF * dxEF) + (dyEF * dyEF));
              
          double alpha11 = dAB * (nextYPointB-nextYPointA);
          double alpha12 = dCD * (yPointB-yPointA);
          double alpha1 = alpha11 - alpha12;
              
          double alpha21 = dCD * (previousYPointB-previousYPointA);
          double alpha22 = dEF * (nextYPointB-nextYPointA);
          double alpha2 = alpha21 - alpha22;
              
          double beta11 = dCD * (xPointB-xPointA);
          double beta12 = dAB * (nextXPointB-nextXPointA);
          double beta1 = beta11 - beta12;
              
          double beta21 = dEF* (nextXPointB-nextXPointA);
          double beta22 = dCD * (previousXPointB-previousXPointA);
          double beta2 = beta21 - beta22;
              
          double gamma11 = nextXPointA*(nextYPointB-nextYPointA);
          double gamma12 = nextYPointA*(nextXPointB-nextXPointA);
          double gamma13 = gamma11 - gamma12;
          double gamma14 = dAB * gamma13;
              
          double gamma15 = yPointA*(xPointB-xPointA);
          double gamma16 = xPointA*(yPointB-yPointA);
          double gamma17 = gamma15 - gamma16;
          double gamma18 = dCD * gamma17;
          double gamma1 = gamma14 + gamma18;
          
          double gamma21 = previousXPointA*(previousYPointB-previousYPointA);
          double gamma22 = previousYPointA*(previousXPointB-previousXPointA);
          double gamma23 = gamma21 - gamma22;
          double gamma24 = dCD * gamma23;
          
          double gamma25 = nextYPointA*(nextXPointB-nextXPointA);
          double gamma26 = nextXPointA*(nextYPointB-nextYPointA);
          double gamma27 = gamma25 - gamma26;
          double gamma28 = dEF * gamma27;
          double gamma2 = gamma24 + gamma28;
          
          double dH1 = alpha1*beta2;
          double dH2 = alpha2*beta1;
          double dH = dH1-dH2;
          
          double dU1 = gamma1*beta2;
          double dU2 = gamma2*beta1;
          double dU = dU1-dU2;
          
          double dV1 = alpha1*gamma2;
          double dV2 = alpha2*gamma1;
          double dV = dV1-dV2;
          
          double Mx = dU/dH;
          double My = dV/dH;
          
          double innCircleRadius1 = xPointA-Mx;
          double innCircleRadius2 = yPointB-yPointA;
          double innCircleRadius3 = yPointA-My;
          double innCircleRadius4 = xPointA-xPointB;
          double innCircleRadius5 = innCircleRadius1 * innCircleRadius2;
          double innCircleRadius6 = innCircleRadius3 * innCircleRadius4;
          double innCircleRadius7 = innCircleRadius5 + innCircleRadius6;
          double innCircleRadius = innCircleRadius7 / dAB;
          
          IElem elem = inEdge.getElem();//aus der übergebenen Kante wird das Objekt vom Typ IElem einer Variable zugeordnet
          elem.setResults(Mx, My, innCircleRadius);//dem Objekt elem werden die neu berechneten Werte übergeben
        }
}
