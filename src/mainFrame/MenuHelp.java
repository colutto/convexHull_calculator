package mainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



/**
 * Die Klasse MenuHelp stellt das Hilfe Menu zur verfügung.
 * @author Stefan Colutto 9513140
 */
public class MenuHelp extends JMenu {

    private final int dialogSizeWidth = 500; //die Länge des Dialogfensters
    private final int dialogSizeHeight = 300; // die Höhe des Dialogfensters
    private final Color colorPanel = new Color(238, 238, 238); //die Hintergrundfarbe des gesamten Dialogfensters
    private final Color colorShadow = new Color(150, 150, 150); //die Farbe des Schattens der JList
    private final String General = "Das Program Convex Hull Calculator berechnet die Konvexe Hülle von Punkten. Die Punkte können "
            + "selbst erstellt werden, zufällig Eingefügt werden oder von einer Text Datei geladen werden. Durch löschen "
            + "und verschieben von Punkten kann die konvexe Hülle ebenfalls verändert werden. Die konvexe Hülle wird nach "
            + "jeder Aktion des Benutzers neu berechnet und angezeigt.";
    private final String addPoints = "Durch auswählen der Funktion Einfügen der Tool Bar können Punkte neu hinzugefügt werden. "
            + "Der Punkt wird genau in der Mitte des Mauszeigers hinzugefügt. Punkte können nur auf der Zeichenfläche hinzugefügt "
            + "werden, die Größe der Punkte kann nicht verändert werden. ";
    private final String deletePoints = "Durch auswählen der Funktion Löschen der Tool Bar können Punkte gelöscht werden. "
            + "Beim löschen von Punkten muss nur in einem voreingestellten und nicht veränderbaren Umkreis des zu löschenden "
            + "Punktes gedrückt werden. Beim Löschen von Punkten wird die Konvexe Hülle sofort neu berechnet. ";
    private final String dragPoints = "Durch auswählen der Funktion Verschieben der Tool Bar könnnen Punkte verschoben werden. "
            + "Beim Verschieben von Punkten muss nur in einem voreingestellten und nicht veränderbaren Umkreis des zu "
            + "verschiebenden Punktes gedrückt werden. Punkte können nur bis zum Rand der Zeichenfläche verschoben werden."
            + "Die Konvexe Hülle wird beim Verschieben von Punkten automatische neu berechnet.";
    private final String addRandomPoints = "Mit der ausklappbaren Liste in der Tool Bar kann die Anzahl der zufällig "
            + "hinzufügbaren Punkte eingestellt werden, mit einem Klick auf den Button Punkte einfügen wird die eingestellte "
            + "Anzahl an Punkten in die Zeichenfläche eingefügt und die Konvexe Hülle automatisch berechnet. "
            + "Die zufällig eingefügten Punkte können wieder gelöscht und verschoben werden, außerdem können neue Punkte "
            + "hinzugefügt werden. ";
    private final String undo = "Mit den linken Pfeil können die Funktionen auf den Punkten wie Einfügen, Löschen, Verschieben oder"
            + "zufällige Punkte hinzufügen Rückgängig gemacht werden. Die Funktion funktioniert allerdings nur wenn bereits"
            + "mindestens ein Punkt hinzugefügt wurde. Es wird immer die letzte ausgeführte Funktion auf den Punkten"
            + "Rückgängig gemacht.";
    private final String redo = "Mit den rechten Pfeil könnnen die Funktionen der Undo Funktion Rückgängig gemacht werden."
            + "Diese Funktion funktioniert natürlich nur falls zuvor bereits Punkte mit der Undo Funktion Rückgängig"
            + "gemacht wurden. Es wird immer die letzte ausgeführte Undo Funktion Rückgängig gemacht.";
    private final String[] instructionsText = {General,addPoints,deletePoints,dragPoints,addRandomPoints,undo,redo};
    //in diesem String Array stehen die genauen beschreibungen der Menu Punkte 
    private final String[] menu = {"Generell","Punkte einfügen","Punkte löschen","Punkte verschieben","Zufällige Punkte einfügen",
                                    "Undo Funktion","Redo Funktion"};
    //in diesem String Array stehen die auswählbaren Menu Punkte
    
    MenuHelp(MainFrame mainFrame) {
        super("Help"); //der Super Konstruktor von der geerbten Klasse wird aufgerufen um dem JMenu einen sichtbaren Namen zu geben
        JMenuItem instructions = new JMenuItem("Instructions"); 
        add(instructions); //ein neuer Unterpunkt wird dem Menu Help hinzugefügt
        
        JDialog dialog = new JDialog(mainFrame, "Instructions", JDialog.DEFAULT_MODALITY_TYPE);
        /*es wird ein neuer Dialog für den Unterpunkt instructions erstellt. Das mainFrame wird übergeben damit es wärend der
         * Ausführung des Dialoges gesperrt werden kann, dafür wird auch JDialog.DEFAULT_MODALITY_TYPE benötigt */ 
        dialog.setSize(dialogSizeWidth, dialogSizeHeight);
        dialog.setResizable(false); //der Dialog kann nicht mehr in der Größe verändert werden
        
        JList<String> menuPoints = new JList<String>(menu);
        //es wird eine Liste erzeugt, wo die verschiedenen Funktionen der Anwendung ausgewählt werden können führ mehr Erläuterungen
        menuPoints.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //es kann immer nur ein Punkt in der Liste ausgeählt werden
        menuPoints.setBackground(colorPanel);
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);// es wird ein leerer äußerer Rand erzeugt
        Border bevelBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.black , colorShadow);
        //der eigentliche Rand der Liste wird erzeugt
        Border compBorder = BorderFactory.createCompoundBorder(bevelBorder, BorderFactory.createEmptyBorder(5,5,5,5));
        /*der Rand bevelBorder wird mit einem inneren leeren Rand kombiniert um einen Abstand zwischen Rand und
         * den Elementen der Liste zu bekommen*/ 
        Border menuPointsBorder = BorderFactory.createCompoundBorder(emptyBorder,compBorder);
        //der vorher erzeugte leere äußere Rand und der vorher kombinierte Rand werden zu einem Rand kombiniert
        menuPoints.setBorder(menuPointsBorder); //der fertige Rand wird für die Liste gesetzt
        dialog.add(menuPoints,BorderLayout.WEST);
        
        JPanel panelText = new JPanel(); 
        /*in dem Panel wird die ausführliche Darstellung der in der Liste ausgewählten Elemente dargestellt und der Button
         * mit dem man das Dialogfenster schließen kann*/
        panelText.setBackground(colorPanel);
        panelText.setLayout(new BorderLayout());
        
        Box boxButton = new Box(BoxLayout.LINE_AXIS);
        /*in dieser Box wird der Button zum schließen des Dialogfensters eingefügt. Im Konstruktor wird ein Integer Wert
         * übergeben damit die Elemente von Links nach Rechts eingefügt werden*/
        
        JTextArea text = new JTextArea(); //in der TextArea wird die eigentliche Erklärung der Funktionen übernommen
        text.setEditable(false); //in der TextArea soll kein Text durch den Benutzer hinzugefügt werden können
        text.setLineWrap(true); // wenn in der Zeile kein Platz mehr vorhanden ist wird eine nächste Zeile begonnen
        text.setWrapStyleWord(true); //Wörter werden nicht abgeschnitten sondern es wird eine neue Zeile begonnen
        Border bevelBorderLowered = BorderFactory.createLineBorder(Color.black, 2);
        //es wird ein Rand mit einer schwazen Linie der Stärke 2 erzeugt
        TitledBorder titledBorder = new TitledBorder(bevelBorderLowered, "", TitledBorder.LEFT, TitledBorder.CENTER);
        //dem vorher erzeugten Rand wird ein Titel hinzugefügt
        Font font = new Font("Serif", Font.BOLD, 18); //eine neue Schriftart wird erstellt
        titledBorder.setTitleFont(font); //die vorher erstellte Schriftart wird für den Titel verwendet
        text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 30), titledBorder));
        /*der TextArea wird ein neuer Rand hinzugefügt. Der vorher erzeugte Rand wird mit einem leeren Rand kombiniert
         * damit wird ein Abstand zwischen Dialogfenster und TextArea erzeugt */
        text.setBackground(colorPanel);
        JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //das JScrollPane wird erzeugt und falls benötigt kann horizontal und vertikal gescrollt werden
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); //vertikale Scrollinggeschwindigkeit
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); //horizontale Scrollinggeschwindigkeit
        scrollPane.setBackground(colorPanel);
        scrollPane.setBorder(null); //die sichtbare Grenze vom JScrollPane wird nicht mehr angezeigt
        scrollPane.setViewportView(text); //die Zeichenfläche wird dem ScrollPane hinzugefügt
        
        JButton bExit = new JButton("Close"); //der Button zum schließen des Dialogfensters 
        boxButton.add(Box.createHorizontalGlue()); 
        //der Box wird ein leerer Abstand hinzugefügt damit der nachher hinzugefügte Button links in der Box dargestellt wird
        boxButton.add(bExit);
        
        panelText.add(scrollPane,BorderLayout.CENTER);
        panelText.add(boxButton,BorderLayout.SOUTH);
        
        dialog.add(panelText,BorderLayout.CENTER);
       
        instructions.addActionListener(new ActionListener() {
            /*dem JMenuItem instructions wird ein Action Listener hinzugefügt, damit beim Klicken auf den Punkt Instructions
             * das Dialogfenster mit den Erläuterungen angezeigt wird*/
            /**
             * Durch den hinzugefügten Action Listener im JMenuItem instructions kann das Dialogfenster geöffnet werden.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
            }
        });
        bExit.addActionListener(new ActionListener() {
            /*dem Button bExit wird ein Action Listener hinzugefügt, damit kann das Dialogfenster wieder geschlossen werden*/
            /**
             * Durch den hinzugefügten Action Listener im JButton bExit kann das Dialogfenster beendet werden.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        menuPoints.addListSelectionListener(new ListSelectionListener() {
            /*der Liste mit den unterschiedlichen Kapiteln wird ein List Selection Listener hinzugefügt, mit diesem wird
             * die Funktion realisiert bei der bei Anklicken eines Elementes in der Liste der entsprechende Text in der 
             * TextArea angezeigt wird*/
            /**
             * Durch das hinzugügen des List Selection Listener zur JList menuPoints wird die eigentliche Funktion 
             * des Dialogfensters realisiert. Beim Anklicken eines Elementes aus der Liste wird ein 
             * erklärender Text im Dialogfenster angezeigt.
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                text.setText(instructionsText[menuPoints.getSelectedIndex()]);
                titledBorder.setTitle(menu[menuPoints.getSelectedIndex()]);
            }
        });
    }
    
    
}
