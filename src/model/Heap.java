package model;


/**
 * Die Klasse Heap sortiert die Knoten nach der größe des berechneten Kreises. Der kleinste Kreis steht in der Wurzel und kann in 
 * log(n) Zeit ausgegeben werden. 
 * @author Stefan Colutto 9513140
 *
 */
public class Heap {

    private Node root;//zeigt auf den obersten Knoten des Heap
    private Node lastLevelLeftNode;//zeigt auf den ganz linken Knoten auf die letzte Ebene die vollständig gefüllt ist
    
    /**
     * Die Methode addNode fügt einen neuen Knoten am Ende der Liste ein und ruft anschließend die Methode reHeapAddedNode auf
     * um den Knoten richtig einzusortieren.
     * @param elem Ein Objekt vom Typ IElem welches dem neuen Knoten übergeben wird.
     */
    void addNode(IElem elem) {
        if(root==null) {//falls es noch keinen Knoten gibt wird der erste Knoten zur Wurzel
            root=new Node(elem);
            lastLevelLeftNode = root;//die erste vollständig gefüllte Ebene ist die erste nach einfügen des ersten Knotens
        }else {//falls schon mehrere Knoten existieren wird mit Hilfe des Zeigers lastLevelLeftNode ein freier Platz gesucht
            boolean added = false;//wird verwendet um die Schleife zu verlassen sobald der Knoten hinzugefügt wurde
            Node search;//mit search wird die Ebene in welcher ein freier Platz gesucht wird durchlaufen
            Node previousSearch;
            /*previousSearch wird verwendet um den im Knoten enthaltenen Zeiger nextNodeInLevel zwischen einem
             * rechten und einem linken Knoten herzustellen*/
            while(added==false) {
                search = lastLevelLeftNode;//search wird auf den Anfang der letzten vollgefüllten Ebene gesetzt
                previousSearch = lastLevelLeftNode;//previousSearch wird auf den Anfang der letzten vollgefüllten Ebene gesetzt
                do {
                    added = addLeftOrRight(elem, search, previousSearch);
                    /*es wird die Methode addLeftOrRight aufgerufen um zu prüfen ob der linke und rechte Knoten von search
                     * schon besetzt sind*/
                    if(added==false&&search!=null) {
                        /*falls kein Knoten gefunden wurde und das Ende der Ebene noch nicht erreicht wurde wird
                         * previousSearch auf search gestellt damit dieser bis auf den Anfang immer einen Knoten hinter search
                         * ist danach wird search um eins weitergestellt*/
                        previousSearch = search;
                        search = search.nextNodeInLevel;
                    }
                } while (search!=null&&added==false);
                //die Schleife bricht ab falls ein Knoten gefunden wurde oder falls das Ende der Ebene erreicht wurde
                if(search==null) {//lastLevelLeftNode darf nur um eins nach unten gesetzt werden falls die Ebene komplett voll ist
                    lastLevelLeftNode = lastLevelLeftNode.left;
                }
            }
            reHeapAddedNode();//nach jedem hinzufügen eines Knoten muss dieser mit der Methode reHeapAddedNode sortiert werden
        }
    }
    /**
     * Die Methode addLeftOrRight wird nur in der Klasse Heap aufgerufen und überprüft ob ein neuer Knoten mit dem Objeckt elem
     * links oder rechts von father hinzugefügt werden kann. Die Methode gibt true zurück falls der Knoten hinzugefügt werden
     * konnte und false wenn dies nicht möglich war. 
     * @param elem Ein Objekt vom Typ IElem welches dem neuen Knoten übergeben wird.
     * @param father Ein Knoten an dem der neue Knoten falls möglich links oder rechts hinzugefügt wird
     * @param previousFather Ein Knoten welcher gebraucht wird um den Zeiger nextNodeInLevel vom vorherigen rechten Knoten den
     * linken Knoten vom nächsten Knoten zuzuordnen.
     * @return Ein Boolean ob ein neuer Knoten erstellt wurde oder nicht.
     */
    private boolean addLeftOrRight(IElem elem, Node father, Node previousFather) {
        boolean added = false;//added wird am Schluss der Methode zurückgegeben und zeigt an ob ein Knoten gefunden wurde oder nicht
        if(father.left==null) {
            /*es wird kontrolliert ob der Linke Knoten von father frei ist, falls dieser frei ist wird auf der linken Seite 
             * ein Knoten hinzugefügt und der Zeiger von father auf den linke Sohn gestellt*/
            father.left = new Node(elem);
            father.left.father = father;
            if(previousFather!=father)
                /*wenn in der links und rechts von der Wurzel hinzugefügt wird gibt es noch keinen rechten Vorgänger vom 
                 *linken neuen Knoten */
                previousFather.right.nextNodeInLevel = father.left;
            added = true;
        }else if(father.right == null) {
            /*das selbe wie beim linken Sohn von father gilt auch für den rechten Sohn mit der Ausnahme, dass hier nicht wegen 
             * eines vorgänger Knotens kontrolliert werden muss da es bei rechts immer einen Vorgänger gibt */
            father.right = new Node(elem);
            father.right.father = father;
            father.left.nextNodeInLevel = father.right;
            added = true;
        }
        return added;
    }
    
    /**
     * Die Methode getMinimum gibt das Minimum aus der Wurzel zurück.
     * @return Ein Objekt vom Typ IElem.
     */
    IElem getMinimum() {
        IElem elem = root.elem;// das Minimum befindet sich in der Wurzel und wird einer Variable zugeordnet
        reHeapLastNode();//der Heap muss neu geordnet werden 
        return elem;
    }
    /**
     * Die Methode reHeapLastNode wird aufgerufen nachdem das Minimum ausgegeben wurde um den Heap eine neue Wurzel zu geben. Es wird
     * der letzte Knoten in die Wurzel übernommen und danach die Methode compareNodesDown aufgerufen um den Knoten nach unten 
     * sinken zu lassen.
     */
    private void reHeapLastNode() {
        Node lastNode = getLastNode(true);
        /*der letzte Knoten des Heap wird gesucht und einer Variable zugeordnet. True gibt an, dass der letzte Knoten gelöscht 
         * werden kann*/
        changeNodes(root, lastNode);//der letzte Knoten wird mit der Wurzel getauscht da diese als Minimum ausgegeben wird
            compareNodesDown(root);//die Methode wird aufgerufen um den Knoten nach unten sinken zu lassen falls nötig
    }
    /**
     * Die Methode compareNodesDown wird nur in der Klasse Heap verwendet um den übergebenen Knoten nach unten sinken zu lassen.
     * Dabei wird stets kontrolliert ob der Knoten mit dem rechten oder linken Sohn getauscht werden soll bis der Knoten 
     * schließlich mit keinem der beiden Söhne getauscht werden kann.
     * @param lastNode Der Knoten welcher mit seinen Söhnen getauscht werden soll bis zu seiner endgültigen Position.
     */
    private void compareNodesDown(Node lastNode) {
        boolean found = false;//found wird benötigt um die Schleife zu beenden falls nicht mehr getauscht werden kann
        while(found==false) {
            if(lastNode.left!=null&&lastNode.right!=null) {
                //für den ersten Teil muss kontrolliert werden ob beide Söhne vom Knoten lastNode vorhanden sind
                if(lastNode.left.elem.compareTo(lastNode.right.elem)<0) {
                    //die beiden Söhne von lastNode werden verglichen um herauszufinden welcher der beiden kleiner ist
                    if(lastNode.elem.compareTo(lastNode.left.elem)>0) {
                        /*falls der linke Knoten kleiner ist wird jetzt kontrolliert ob er auch wirklich mit seinem Vater 
                         * getauscht werden soll*/
                        changeNodes(lastNode, lastNode.left);
                        lastNode = lastNode.left;
                    }else if(lastNode.elem.compareTo(lastNode.right.elem)>0) {
                        //falls der linke Knoten nicht getauscht werden kann wird geprüft ob der rechte Knoten getauscht werden kann
                        changeNodes(lastNode, lastNode.right);
                        lastNode = lastNode.right;
                    }else found = true;
                    /*falls links und rechts nicht getauscht werden kann kann nicht mehr weiter getauscht werden und die Schleife 
                     * wird abgebrochen*/
                }else if(lastNode.elem.compareTo(lastNode.right.elem)>0) {
                    //das ganze wird jetzt für nochmal wiederholt falls der rechte Knoten kleiner als der linke war
                    changeNodes(lastNode, lastNode.right);
                    lastNode = lastNode.right;
                }else if(lastNode.elem.compareTo(lastNode.left.elem)>0) {
                    changeNodes(lastNode, lastNode.left);
                    lastNode = lastNode.left;
                }else found = true;//falls wieder keiner der beiden Knoten getauscht werden kann wird die Schleife wieder abgebrochen
            }else if(lastNode.left!=null&&lastNode.elem.compareTo(lastNode.left.elem)>0) {
                //falls es keinen rechten Knoten gibt wird hier überprüft ob der linke Knoten getauscht werden kann
                changeNodes(lastNode, lastNode.left);
                lastNode = lastNode.left;
            }else found = true;//falls auch dieser Knoten nicht getauscht werden kann wird die Schleife abgebrochen
        }
    }
    /**
     * Die Methode reHeapAddedNode nimmt den letzten Knoten im Heap und tauscht diesen immer wieder mit seinem Vater bis 
     * nicht mehr getauscht werden kann.
     */
    private void reHeapAddedNode() {
        Node lastNode = getLastNode(false);
        //es wird der letzte Knoten gesucht da dieser ja auch jener ist welcher als letztes hinzugefügt wurde
        boolean found = false;
        while(found==false&&lastNode.father!=null) {
            //die Schleife wird durchlaufen bis nicht mehr getauscht werden kann oder die Wurzel erreicht wurde
            if(lastNode.elem.compareTo(lastNode.father.elem)<0) {
                //der Knoten wird mit seinem Vater verglichen und wird getauscht falls er kleiner als seiner Vater ist
                changeNodes(lastNode, lastNode.father);
                lastNode = lastNode.father;//nach dem Tausch wird der Knoten um eins nach oben gesetzt
            }else found = true;//wenn nicht mehr getauscht werden kann wird die Schleife abgebrochen
        }
    }
    /**
     * Die Methode getLastNode sucht den letzten Knoten im Heap und gibt diesen zurück, falls der Methode true übergeben wurde 
     * löscht die Methode zusätzlich noch alle Verbindungen vom Knoten und zum Knoten.
     * @param delete Ist eine Variable vom Typ boolean und wird auf true gesetzt falls alle Verbindungen zum gefundenen Knoten
     * gelöscht werden sollen, ansonsten muss false übergeben werden.
     * @return Gibt ein Objekt vom Typ Node zurück, welcher der letzte Knoten im Heap ist.
     */
    private Node getLastNode(boolean delete) {
        Node search = lastLevelLeftNode;//search wird auf die letzte voll gefüllte Ebene gestellt
        Node previousSearch = lastLevelLeftNode;
        /*previousSearch soll immer einen Knoten hinter search bleiben um den Zeiger vom rechten Knoten des Vorgänger Knotens
         * zum linken Knoten des aktuellen Knoten zu löschen*/
        while(true) {
            /*die Schleife wird solange durchlaufen bis der letzte Knoten gefunden wurde und mittels return die Schleife 
             * verlassen wird*/
            if((search.nextNodeInLevel!=null&&search.nextNodeInLevel.left==null)||search.nextNodeInLevel==null) {
                /*es wird überprüft ob es einen weiteren Knoten gibt und ob dieser schon einen linken Sohn hat, falls der nächste 
                 * Knoten einen linken Sohn hat wird search und previousSearch um einen Knoten nach rechts verschoben außer es
                 * handelt sich bereits um den letzten Knoten in dieser Ebene*/
                if(search.left!=null&&search.right!=null) {
                    if(delete) {//es wird überprüft ob der Knoten nur ausgegeben werden soll oder auch gelöscht werden soll
                        Node returnNodeRight = search.right;
                        search.left.nextNodeInLevel = null;
                        search.right = null;
                        returnNodeRight.father = null;
                        returnNodeRight.nextNodeInLevel = null;
                        return returnNodeRight;
                    }
                    return search.right;
                }
                else if(search.left!=null&&search.right==null) {
                    //es wird geprüft ob der Knoten nur einen linken Sohn hat
                    if(delete) { //es wird überprüft ob der Knoten nur ausgegeben werden soll oder auch gelöscht werden soll
                        if(previousSearch!=search)
                            /*falls der linke Sohn gelöscht werden soll muss die Verbindung zum rechten Sohn des vorherigen Knoten
                             * gelöscht werden außer beim ersten Knoten in dieser Ebene*/
                            previousSearch.right.nextNodeInLevel = null;
                        Node returnNodeLeft = search.left;
                        search.left = null;
                        returnNodeLeft.father = null;
                        returnNodeLeft.nextNodeInLevel = null;
                        if(lastLevelLeftNode.left==null) 
                            /*wenn der letzte linke Sohn in der Ebene gelöscht wird muss der Zeiger lastLevelLeftNode um eins nach
                             * oben gesetzt werden*/
                            lastLevelLeftNode = lastLevelLeftNode.father;
                        return returnNodeLeft;
                        }
                    return search.left;
                }
            } else {
                //falls es noch weitere Knoten in dieser Ebene gibt wird search und previousSearch um eins nach rechts verschoben 
                previousSearch = search;
                search = search.nextNodeInLevel;
            }
        }
    }
    /**
     * Die Methode changeNodes tauscht die Objekte vom Typ IElem miteinander aus, dabei werden die Knoten nicht getauscht.
     * @param a Der Knoten der mit dem zweiten übergebenen Knoten getauscht werden soll.
     * @param b Der Knoten der mit dem ersten übergebenen Knoten getauscht werden soll.
     */
    private void changeNodes(Node a, Node b) {
        IElem elem = a.elem;//es wird eine Hilfsvariable vom Typ IElem erzeugt und ihr die Variable vom Knoten a übergeben
        a.elem = b.elem;//a bekommt das Objekt elem vom Knoten b zugeordnet
        a.elem.setNode(a);
        /*da oben das Objekt elem zwischen Knoten a und b getauscht wurde, bekommt auch elem mittels der Methode setNode
         * einen neuen Knoten übergeben*/
        b.elem = elem;//b bekommt das Objekt elem von der Hilfsvariablen elem zugeordnet
        b.elem.setNode(b);
        //wie bei elem von Knoten a muss auch hier die Methode setNode aufgerufen werden um den Knoten zu aktualisieren
    }
    /**
     * Die Methode printHeap gibt alle Knoten des Heap in der Reihenfolge von oben nach unten und links nach rechts in
     * der Konsole aus.
     */
    public void printHeap() {
        Node search;//der Knoten search wird verwendet um die Ebenen nach und nach zu durchlaufen
        Node leftNode = root;
        /*leftNode ist immer der am weitesten links stehende Knoten in der Ebene und wird immer um eins nach unten gesetzt
         * wenn search alle Knoten in dieser Ebene durchlaufen hat*/
        do {
           search = leftNode;
           do {
               System.out.println(search.elem.getPointA()+" "+search.elem.getPointB()+" "+search.elem.getCircleRadius());
               search = search.nextNodeInLevel;
           } while (search!=null);
           //die Schleife wird solange durchlaufen bis alle Knoten in dieser Ebene einmal besucht wurden
           leftNode = leftNode.left;
        }while(leftNode!=null);
        //diese Schleife wird solange durchlaufen bis es keinen linken Nachfolger zum hinabsteigen gibt
    }
    /**
     * Die Methode reHeapNode bekommt einen Knoten übergeben bei dem kontrolliert wird ob der Knoten mit seinem Vater oder mit 
     * seinem Söhnen getauscht werden kann und zwar solange bis der Knoten entweder in der Wurzel angelangt ist oder nach unten 
     * sinkt bis er nicht mehr getauscht werden kann.
     * @param node Der Knoten der nach oben oder nach unten getauscht werden soll bis er seine richtige Position gefunden hat.
     */
    void reHeapNode(Node node) {
        boolean found = false;//Variable zum Beenden der Schleife falls nicht mehr getauscht werden kann
        boolean isTop = false;
        //falls der Knoten nach oben nicht getauscht werden konnte muss kontrolliert werden ob der Knoten nach unten sinken kann 
        while(found==false&&node.father!=null) {
            //die Schleife wird abgebrochen falls nicht mehr getauscht werden kann oder falls der Knoten schon in der Wurzel steht
            if(node.elem.compareTo(node.father.elem)<0) {
                //es wird überprüft ob der Knoten mit seinem Vater getauscht werden kann
              changeNodes(node, node.father);
              node = node.father;
              isTop = true;//wird auf true gestellt damit nicht mehr kontrolliert wird ob der Knoten nach unten sinken kann
            } else {
                found = true;
            } 
        }
        if(!isTop)
            //es wird überprüft ob der Knoten bereits nach oben getauscht wurde oder nicht
            compareNodesDown(node);//die Methode lässt den Knoten nach unten sinken falls möglich
    }
    /**
     * Die Klasse Node stellt die Knoten des Heaps dar. Node beinhaltet Zeiger auf den linken und rechten Sohn auf den Vater und 
     * auf den nächsten Knoten in der Ebene. Die Klasse Node bekommt das Objekt elem vom Typ IElem übergeben in dem die größe und
     * die Koordinaten des Mittelpunktes des Kreises der Kante aus dem EdgeSet enthalten sind.
     * @author Stefan Colutto 9513140
     *
     */
    class Node {
        private IElem elem;//enthählt die Berechnung des Kreises und des Mittelpunktes
        private Node left;//der linke Sohn des Knoten
        private Node right;//der rechte Sohn des Knoten
        private Node father;//der Vater des Knoten
        private Node nextNodeInLevel;//der nächste Knoten in der gleichen Ebene
        
        private Node(IElem elem) {
            elem.setNode(this);//damit elem den Knoten kennt in dem elem liegt
            this.elem = elem;//damit auf elem auch in der Klasse Heap zugegriffen werden kann
        }
       
    }
}
