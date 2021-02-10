package mainFrame;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

import model.Point;
import model.PointOrganisation;
/**
 * Die Klasse Draw zeichnet die Komplete Darstellung aller Punkte und der Konvexen Hülle.
 * @author Stefan Colutto 9513140
 *
 */
public class Draw extends JPanel implements ISetPointListener {

    private final int pointSize = 4;    //die größe der gezeichneten Punkte 
    private IPointListener pointListener;   
    //Listener welche auf die Mausaktionen reagiert um Punkte einzufügen, zu löschen und zu verschieben
    private LinkedList<Point> convexHull;
    private int fullWidth = 0;
    private int fullHeight = 0;
    
    Draw() {
        
        setBackground(Color.white);
        
        addMouseListener(new MouseAdapter() {
            /**
             * Die Methode wird zum Einfügen von Punkten verwendet und wird aufgerufen sobald mit der Maus auf 
             * die Zeichenfläche geglickt wird.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX()-(fullWidth/2);
                int y = (fullHeight/2)-e.getY();
                pointListener.setPoint(x, y);
                //die setPoint Methode des Interfaces PointListener wird aufgerufen um einen Punkt einzufügen
            }
            /**
             * Die Methode wird zum Verschieben von Punkten benötigt und wird aufgerufen sobald mit der Maus auf 
             * die Zeichenfläche gedrückt wird.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()-(fullWidth/2);
                int y = (fullHeight/2)-e.getY();
                pointListener.mousePressed(x, y);   
                /*die mousePressed Methode des Interfaces PointListener wird aufgerufen um den Punkt auszuwählen 
                 welcher verschoben werden soll*/
            }
            /**
             * Die Methode wird zum zurücksetzen von Verschobenen Punkten verwendet.
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                pointListener.mouseReleased();
            }
            
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            /**
             * Die Methode wird zum Verschieben von Punkten benötigt und wird solange aufgerufen bis 
             * die Maustaste wieder losgelassen wird.
             */
            public void mouseDragged(MouseEvent e) {
                int x = e.getX()-(fullWidth/2);
                int y = (fullHeight/2)-e.getY();
                pointListener.mouseDragged(x, y,pointSize); 
                /*die Methode mouseDragged des Interfaces Point Listener wird aufgerufen um den in der Methode 
                  mousePressed ausgesuchten Punkt zu verschieben*/
            }   
            
        });
    } 
    
    @Override
    /**
     * Zeichnet alle Komponenten auf der Zeichenfläche.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);        
        //das Graphics g Objekt wird der überschriebenen Mehthode paintComponent übergeben um den Hintergrund zu zeichnen
        Graphics2D g2 = (Graphics2D)g;  //ein neues Graphics Objekt wird erzeugt um die Punkte und die Konvexe Hülle zu zeichnen
        g2.setColor(Color.black);       
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);    
        //AntiAliasing(Kantenglättung) wird eingeschaltet 
        Iterator<Point> points = PointOrganisation.getPoints().iterator();      
        //ein Iterator aus der Liste der Punkte wird erzeugt um die Liste zu durchlaufen
        while(points.hasNext()) {
            Point point = points.next();
            g2.fillOval((int)point.getX()-(pointSize/2)+(this.fullWidth/2), (this.fullHeight/2)-(int)point.getY()-(pointSize/2), pointSize, pointSize);    
            /*der Punkt wird gezeichnet dabei wird der Nullpunkt des Punktes in die Mitte verschoben*/
        }
        convexHull = PointOrganisation.getPointsConvexHull();
        int i = 0; //Zähler für die while Schleife und zur auswahl der Punkte aus der Variable ConvexHull
        Point c;
        Point h;
        while(i<convexHull.size()-1) {
            c = convexHull.get(i++);
            //der i-te Punkt wird ausgewählt und danach um 1 erhöht damit der nächste Punkt ausgewählt werden kann
            h = convexHull.get(i);
            //i wird nicht um 1 erhöht damit beim nächsten Schleifendurchlauf auf diesen Punkt nochmals zugegriffen werden kann
            g2.drawLine((int)c.getX()+(this.fullWidth/2),(this.fullHeight/2)-(int)c.getY() , (int)h.getX()+(this.fullWidth/2), (this.fullHeight/2)-(int)h.getY());//die Linie zwischen Punkt c und h wird gezeichnet
        }
        
        if(PointOrganisation.getCircleRadius()!=0) {
            int x = (int)PointOrganisation.getXCircle()+(this.fullWidth/2);
            int y = (this.fullHeight/2)-(int)PointOrganisation.getYCircle();
            int circleDiameter = (int)PointOrganisation.getCircleRadius()*2;
            g2.drawOval(x-(circleDiameter/2), y-(circleDiameter/2), circleDiameter, circleDiameter);
        }
    }

    @Override
    /**
     * Die Methode wird benötigt um eine Klasse die das Interface PointListener implementiert in diese Klasse zu laden.
     */
    public void setPointListener(IPointListener pointListener) {
        this.pointListener = pointListener;
        
    }
    /**
     * Berechnet und Stellt die passende Größe der Zeichenfläche ein.
     * @param maxX Die maximale Größe der Koordinate X.
     * @param maxY Die maximale Größe der Koordinate Y.
     * @param minX Die minimale Größe der Koordinate X.
     * @param minY Die minimale Größe der Koordinate Y.
     */
    public void setPreferredSize(int maxX, int maxY, int minX, int minY) {
        if(this.fullWidth<=maxX&&this.fullHeight<=maxY) {
            this.fullWidth = maxX +(-minX);  
            //die maximale Länge der Zeichnefläche wird mit der maximalen x Koordinate und der minimalen x Koordinate erzeugt 
            this.fullHeight = maxY + (-minY);    
            //die maximale Höhe der Zeichnefläche wird mit der maximalen y Koordinate und der minimalen y Koordinate erzeugt 
        }
        setPreferredSize(new Dimension(fullWidth,fullHeight));
//        maxX = maxX+this.pointSize;
//        maxY = maxY+this.pointSize;
//        minX = minX-this.pointSize;
//        minY = minY-this.pointSize;
    }
    /**
     * Die Methode wird verwendet um die Größe des Punktes auch außerhalb der Klasse abzufragen.
     * @return Die größe der gezeichneten Punkte als Integer.
     */
    public int getPointSize() {
        return pointSize;
    }
    
    
   

    
    

}
