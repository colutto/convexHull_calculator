package model;

import model.EdgeSet.Edge;
import model.Heap.Node;
/**
 * Die Klasse Circle enthält die Koordinaten des Kreises, den Kreisradius und die Punkte die die Kante definieren. Mit dem Interface 
 * IElem werden die Methoden für die Verwendung im Heap und im EdgeSet bereitgestellt und zur sortierung wird die Methode 
 * compareTo verwendet.
 * @author Stefan Colutto 9513140
 *
 */
class Circle implements IElem {
    private double xCircle;//die x Koordinate des Kreises
    private double yCircle;//die y Koordinate des Kreises
    private double circleRadius = 0;//der Radius des Kreises
    private Edge edge;//die Kante die dem Kreis zugeordnet ist
    private Node node;//der Knoten der dem Kreis zugeordnet ist
    private Point a;//der erste Punkt der Kante
    private Point b;//der zweite Punkt der Kante
    
    Circle(Point a, Point b){
        this.a = a;
        this.b = b;
    }
    /**
     * Mit der implementierten Methode getCircleRadius von IELem wird der Kreisradius ausgegeben.
     */
    @Override
    public double getCircleRadius() {
        return circleRadius;
    }
    /**
     * Mit der implementierten Methode setEdge von IElem wird die Kante zum Circle Objekt zugeordnet.
     */
    @Override
    public void setEdge(Edge edge) {
        this.edge = edge;
        
    }
    /**
     * Mit der implementierten Methode setNode von IELem wird der Knoten zum Circle Objekt zugeordnet.
     */
    @Override
    public void setNode(Node node) {
        this.node = node;
        
    }
    /**
     * Mit der implementierten Methode getPointA wird der erste Punkt der zugeordneten Kante ausgegeben.
     */
    @Override
    public Point getPointA() {
        return a;
    }
    /**
     * Mit der implementierten Methode getPointB wird der zweite Punkt der zugeordneten Kante ausgegeben.
     */
    @Override
    public Point getPointB() {
       return b;
    }
    /**
     * Mit der implementierten Methode getEdge kann auf die zugeordnete Kante des Circle Objekts zugegriffen werden.
     */
    @Override
    public Edge getEdge() {
        return edge;
    }
    /**
     * Mit der implementierten Methode getNode kann auf den zugeordneten Knoten des Circle Objekts zugegriffen werden.
     */
    @Override
    public Node getNode() {
        return node;
    }
    /**
     * Mit der implementierten Methode setResults werden die Koordinaten des Kreismittelpunkts und die Kreisradius übergeben.
     */
    @Override
    public void setResults(double x, double y, double circleRadius) {
        this.xCircle = x;
        this.yCircle = y;
        this.circleRadius = circleRadius;
        
    }
    /**
     * Mit der implementierten Methode getXCircle wird die x Koordinate des Kreismittelpunktes übergeben.
     */
    @Override
    public double getXCircle() {
        return this.xCircle;
    }
    /**
     * Mit der implementierten Methode getYCircle wird die y Koordinate des Kreismittelpunktes übergeben.
     */
    @Override
    public double getYCircle() {
        return this.yCircle;
    }
    /**
     * Mit der implementierten Methode compareTo werden die Kreisradien verglichen und durch den zurückgegebenen Integer Wert 
     * wird entschieden welcher der beiden Circle Objekte den größeren Kreisradius hat.
     */
    @Override
    public int compareTo(IElem o) {
        if (this.circleRadius<o.getCircleRadius())//es wird überprüft ob die Variable o größer ist
            return -1;
        else if(this.circleRadius>o.getCircleRadius())//es wird überprüft ob die Variable o kleiner ist
            return 1;
        else//bei Gleichheit wird 0 ausgegeben
            return 0;
    }
}
