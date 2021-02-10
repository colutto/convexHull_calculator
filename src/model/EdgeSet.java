package model;

/**
 * Die Klasse EdgeSet erzeugt eine Liste mit Zeigern auf die erste und letzte Kante der Liste. Außerdem können Kanten gelöscht 
 * und hinzugefügt werden, die Größe kann abgefragt werden und die Kante an einer bestimmten Position kann ausgegeben werden.
 * @author Stefan Colutto 9513140
 *
 */
public class EdgeSet {
    private Edge first; //zeigt auf die erste Kante
    private Edge last; //zeigt auf die letzte Kante
    private int size = 0; //enthählt die Anzahl der Kanten in der Liste

   
    /**
     * Es wird eine neue Kante erzeugt und ihr das Objekt vom Typ IElem übergeben. Die erzeugte Kante wird an das Ende der Liste
     * eingefügt.
     * @param elem Elem vom Typ IElem wird der Kante übergeben.
     */
    void addEdge(IElem elem) {
        Edge secondLast = last; 
        //es wird eine neue Kante erstellt die nach dem Einfügen der neuen Kante auf die vorletzte Kante zeigt
        Edge help = new Edge(elem);//eine neue Kante wird erstellt und das Objekt vom Typ IElem wird der Kante übergeben
        last = help;//der Zeiger auf die letzte Kante wird aktualisiert 
        if(secondLast!=null) {
            //es wird überprüft ob die Kante die erste Kante in der Liste ist oder nicht um den Zeiger first richtig zu setzten
            secondLast.next = last;//die vorletzte Kante bekommt die neu eingefügte Kante als nächste Kante übergeben 
            last.previous = secondLast; //der Zeiger auf die vorherige Kante der letzten Kante bekommt die vorletzte Kante übergeben
            last.next = first;//der Zeiger der letzten Kante bekommt den Listenanfang übergeben
            first.previous = last;
            //der Zeiger auf die vorherige Kante der ersten Kante in der Liste bekommt die letzte Kante übergeben
        }else first = last;
        //falls es sich um die erste Kante handelt wird der Zeiger first auf die gleiche Kante wie der Zeiger last gestsellt
        size++;//nach erfolgreichem hinzufügen einer Kante wird die größe um eins erhöht
    }
    
   
    /**
     * Die Methode getNextEdge gibt das Objekt der nächsten Kante der übergebenen Kante zurück.
     * @param edge Die Kante wird benötigt um auf die nächste Kante zugreifen zu können.
     * @return Es wird ein Objekt vom Typ IElem zurückgegeben.
     */
    IElem getNextEdge(Edge edge) {
       return edge.getNext().getElem();
    }
    /**
     * Die Methode getPreviousEdge gibt das Objekt der vorherigen Kante der übergebenen Kante zurück.
     * @param edge Die Kante wird benötigt um auf die vorherige Kante zugreifen zu können.
     * @return Es wird ein Objekt vom Typ IElem zurückgegeben.
     */
    IElem getPreviousEdge(Edge edge) {
       return edge.getPrevious().getElem();
    }
    /**
     * Die Methode getEdge gibt die Kante aus welche sich an der übergebenen Position befindet
     * @param index Index ist eine Integer Variable und gibt die Position der Kante an welche zurückgegeben werden soll 
     * @return Es wird ein Objekt vom Typ Edge zurückgegeben.
     */
    Edge getEdge(int index) {
        Edge back = first;//es wird ein neuer Zeiger zum Durchlaufen der Liste erzeugt
        for(int i = 0; i<index; i++) {//die for Schleife wird durchlaufen bis i=index-1 
            back = back.next;//der Zeiger wird immer um eins nach vorne verschoben
        }
        return back;
    }
    
    /**
     * Die Methode getSize gibt die Größe der aktuellen Liste an.
     * @return Eine Integer Variable mit der größe der aktuellen Liste.
     */
    int getSize() {
        return size;
    }
    
    /**
     * Die Methode deleteEdge löscht die übergebene Kante aus der Liste und fügt die vorherige und nachfolgende Kante zusammen.
     * @param edge Ist ein Objekt vom Typ Edge und stellt die zu löschende Kante dar.
     */
    void deleteEdge(Edge edge) {
        if(edge==first) {//falls die erste Kante der Liste gelöscht wird muss der first Zeiger umgestellt werden
            first = edge.next;
        }
        if(edge==last) {//falls die letzte Kante der Liste gelöscht wird muss der last Zeiger umgestellt werden
            last = edge.getPrevious();
        }
        Edge previous = edge.previous;//ein Zeiger welcher auf die vorherige Kante der zu löschenden Kante zeigt wird erstellt
        Edge next = edge.next;//ein Zeiger welcher auf die nächste Kante der zu löschenden Kante zeigt wird erstellt
        previous.next = next;// der Zeiger der vorherigen Kante wird auf die nächste Kante der zu löschenden Kante gestellt
        next.previous = previous;//der Zeiger der nächsten Kante wird auf die vorherige Kante der zu löschenden Kante gestellt
        size--;//die Größe der Liste muss um eins verringert werden
    }


    /**
     * Die Klasse Edge stellt die Objekte der Liste dar und besitzt Zeiger auf die vorherige und nächste Kante der Liste.
     * Außerdem enthählt die Kante das Objekt elem vom Typ IElem.
     * @author Stefan Colutto 9513140
     *
     */
    class Edge {
        private Edge previous;//Zeiger auf die vorherige Kante der Kante
        private Edge next;//Zeiger auf die nächste Kante der Kante
        private IElem elem;//ist ein Objekt vom Typ IElem und enthählt alle wichtigen Methoden zur Berechnung des Kreises
        
        
        private Edge(IElem elem) {
            elem.setEdge(this);//dem Objekt elem wird die Kante in welcher es gespeichert wird übergeben
            this.elem = elem;
            /*das Object elem wird in der Klasse Edge einer globalen Variable übergeben um auch in der Klasse EdgeSet 
             * darauf zugreifen zu können */
        }

        /**
         * Die Methode getNext gibt die nächste Kante dieser Kante zurück.
         * @return Ein Objekt vom Typ Edge.
         */
        Edge getNext() {
            return next;
        }
        /**
         * Die Methode getPrevious gibt die vorherige Kante dieser Kante zurück.
         * @return Ein Objekt vom Typ Edge.
         */
        Edge getPrevious() {
            return previous;
        }
        /**
         * Die Methode getElem gibt das in dieser Kante enthaltene Objekt vom Typ IElem zurück.
         * @return Ein Objekt vom Typ IElem.
         */
        IElem getElem() {
            return elem;
        }
        
        
        


       
       
    }
    
}
