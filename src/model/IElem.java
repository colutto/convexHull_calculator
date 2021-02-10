package model;

import model.EdgeSet.Edge;
import model.Heap.Node;
/**
 * Das Inteface IElem muss die Klasse implementieren die den Algorithmus in der Klasse BiggestIncludedCircle verwenden möchte.
 * Die Klasse kann durch die Methoden des Interfaces in der Klasse EdgeSet und Heap verwendet werden.
 * @author Stefan Colutto 9513140
 *
 */
public interface IElem extends Comparable<IElem>{

    Point getPointA();
    Point getPointB();
    void setEdge(Edge edge);
    void setNode(Node node);
    double getCircleRadius();
    void setResults(double x, double y, double circleRadius);
    Edge getEdge();
    Node getNode();
    double getXCircle();
    double getYCircle();
     
    
    
}
