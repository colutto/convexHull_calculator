package mainFrame;



import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.PointOrganisation;

/**
 * Die Klasse MainFrame ist die Klasse welche alle Sichtbaren Objekte der Anwendung miteinander verbindet. 
 * @author Stefan Colutto 9513140
 */
public class MainFrame extends JFrame  {

    private MenuFile menuFile;
    private MenuHelp menuHelp;
    private JMenuBar menuBar;
    private MainToolBar mainToolBar;
    private SizeDialog dialog;
    private JScrollPane scrollPane;
    private Draw draw;
  
    public MainFrame() {
        
        setSize(1300,800);
        setTitle("Convex Hull Calculator");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);       
        //damit die Anwendung falls man auf x im rechten oberen Rand drückt nicht sofort geschlossen wird
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int yes_no = JOptionPane.NO_OPTION;  //Variable um festzustellen ob auf Ja oder auf Nein geklickt wurde
                int isSaved = 0;  //es werden verschiedene Fehlermeldungen im int gespeichert und darauf reagiert
                if(PointOrganisation.isChanged()) { 
                    //es wird nur Nachgefragt falls die Punktemenge vom letzten Speicherpunkt verändert wurde
                    yes_no = JOptionPane.showConfirmDialog(MainFrame.this, "Would you like to save the previous assignment?", "Warning", JOptionPane.YES_NO_OPTION);
                }
                if(yes_no==JOptionPane.YES_OPTION)   //Abfrage ob abgespeichert werden soll oder nicht
                    isSaved = menuFile.savePoints(); //die Punkte werden gespeichert
                switch (isSaved) {  //Switch Anweisung um die vershiedenen Fehlermeldungen zu verarbeiten
                case 1: break; //falls ein Problem beim abspeichern auftritt wird das Programm nicht geschlossen
                default: System.exit(0); //das Programm wird sofort beendet
                }
            }
        });
        
        scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //das JScrollPane wird erzeugt und falls benötigt kann horizontal und vertikal gescrollt werden
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); //vertikale Scrollinggeschwindigkeit
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); //horizontale Scrollinggeschwindigkeit
        scrollPane.getViewport().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                scrollPane.repaint();
                //das ScrollPane und die darin liegende Zeichenfläche müssen nach jedem scrollen neu gezeichnet werden
            }    
        });
        draw = new Draw();
        scrollPane.setViewportView(draw);//die Zeichenfläche wird dem ScrollPane hinzugefügt
        
        mainToolBar = new MainToolBar(draw);
        mainToolBar.setSize(600, 50);
        draw.setPointListener(mainToolBar);
   
        dialog = new SizeDialog(this,scrollPane,draw);
        
        menuFile = new MenuFile(this,scrollPane,draw,dialog,mainToolBar);
        
        menuHelp = new MenuHelp(this);
        menuBar = new JMenuBar();
        menuBar.add(menuFile);
        menuBar.add(Box.createHorizontalGlue());//wird verwendet damit das Help Menü am rechten Bildschirmrand angezeigt wird
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);
        
        add(mainToolBar,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);
        
        setVisible(true);
        draw.setPreferredSize(draw.getWidth(), draw.getHeight(), 0, 0);
        /*wird verwendet um der Zeichenfläche die Größe zuzuordnen um den Nullpunkt zu bestimmen damit die Punkte richtig
         * abgespeichert werden und wieder neu eingelesen werden können*/
    }
    
}
