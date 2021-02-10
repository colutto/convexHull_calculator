package mainFrame;

/**
 * Das Inteface implementiert die Methoden um Punkte auf der Zeichenfläche hinzuzufügen, zu löschen und zu verschieben.
 * @author Stefan Colutto 9513140
 *
 */
public interface IPointListener {
    void setPoint(int x, int y);
    void mouseDragged(int x,int y, int pointSize);
    void mousePressed(int x,int y);
    void mouseReleased();
}
