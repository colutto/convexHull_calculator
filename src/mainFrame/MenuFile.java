package mainFrame;


import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import controller.Input;
import controller.Output;
import model.PointOrganisation;

/**
 * Die Klasse MenuFile stellt das Menu File in der Menuleiste dar und Beinhaltet die Operationen 
 * New, OpenFile, Close, Close All, Save, Save All, Exit.
 * @author Stefan Colutto 9513140
 *
 */
public class MenuFile extends JMenu {

    private JMenuItem setPreferedSize = new JMenuItem("Set Prefered Size");//Menüpunkt um die Größe der Zeichenfläche einzustellen
    private JMenuItem openFile = new JMenuItem("Open File");//Menüpunkt zum öffnen von Dateien
    private JMenuItem New = new JMenuItem("New");//Menüpunkt um eine neue Zeichenfläche zu erstellen
    private JMenuItem save = new JMenuItem("Save");//Menüpunkt um die aktuelle Zeichenfläche zu speichern
    private JMenuItem exit = new JMenuItem("Exit");//Menüpunkt um das Programm zu beenden
    private MainFrame mainFrame;//der MainFrame wird verwendet um das Hauptfenster im SizeDialog zu sperren
    
  
    MenuFile(MainFrame mainFrame, JScrollPane scrollPane, Draw draw, SizeDialog dialog, MainToolBar mainToolBar) {
        super("File");
        this.mainFrame = mainFrame;
        add(New);
        add(openFile);
        add(save);
        add(setPreferedSize);
        add(exit);
        
        exit.addActionListener(new ActionListener() {
            /**
             * Durch diese überschriebene Methode des ActionListeners kann im Menü File durch anklicken von Exit die 
             * Anwendung geschlossen werden.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int yes_no = JOptionPane.NO_OPTION;     //Variable um festzustellen ob auf Ja oder auf Nein geklickt wurde
                int isSaved = 0;    //es werden verschiedene Fehlermeldungen im int gespeichert und darauf reagiert
                if(PointOrganisation.isChanged()) {     
                //es wird nur Nachgefragt falls die Punktemenge vom letzten Speicherpunkt verändert wurde
                    yes_no = JOptionPane.showConfirmDialog(mainFrame, "Would you like to save the previous assignment?", "Warning", JOptionPane.YES_NO_OPTION);
                }
                if(yes_no==JOptionPane.YES_OPTION)      //Abfrage ob abgespeichert werden soll oder nicht
                    isSaved = savePoints();             //die Punkte werden gespeichert
                switch (isSaved) {                      //Switch Anweisung um die vershiedenen Fehlermeldungen zu verarbeiten
                case 1: break;
                case 2: JOptionPane.showMessageDialog(mainFrame, "Error saving Points!", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                default: System.exit(0);
                }
            }
        });
        openFile.addActionListener(new ActionListener() {/*Erstellt einen JFileChooser zum Öffnen einer Datei  */
            /**
             * Durch diese überschriebene Methode des ActionListeners kann im Menü File durch anklicken von Open File 
             * eine neue Datei eingelesen werden.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                PointOrganisation.deleteAllPoints();
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("../Tester/data"));
                int return_value = chooser.showOpenDialog(mainFrame);//öffnet den showOpenDialog und sperrt den MainFrame
                if(return_value == JFileChooser.APPROVE_OPTION) {//überprüfft ob eine Datei ausgewählt wurde 
                    Input.loadFile(chooser.getSelectedFile(),mainFrame,draw,scrollPane,MenuFile.this);
                    /*öffnet die statische Methode der Klasse Input und übergibt den ausgewählten File
                     und den mainFrame zum sperren des MainFrame falls der File in der Methode 
                     loadFile nicht gefunden wird*/
                }
            }
        });
        save.addActionListener(new ActionListener() {
            /**
             * Die Methode wird augerufen wenn im Menü File auf die Save Fläche gedrückt wird und wird verwendet 
             * um die zuvor erstellte Punktemenge abzuspeichern.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int isSaved;
                isSaved = savePoints();
                if(isSaved==0) { 
                    PointOrganisation.setChanged(false);
                }
            }
        });
        
        setPreferedSize.addActionListener(new ActionListener() {
            /**
             * Die Methode wird aufgerufen wenn im Menü File auf die New Fläche gedrückt wird und öffnet einen Dialog 
             * der die Breite und die Höhe der Zeichenfläche als Eingabe erwartet, wird diese erfolgreich eingegeben 
             * wird eine neue Zeichenfläche erstellt und es werden neue Funktionen für die Anwendung freigeschalten.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setEnabled(false);
                dialog.setLocation(mainFrame.getLocation().x+((mainFrame.getWidth()/2)-dialog.getX()/2),mainFrame.getLocation().y+((mainFrame.getHeight()/2)-dialog.getY()/2));
                dialog.setVisible(true);    //zeigt den Dialog für die Eingabe der Breite und Höhe an
            }
        });
        /**
         * Die Methode schließt die aktuelle Zeichenfläche, vorher wird noch überprüft ob diese bereits abgespeichert wurde.
         */
        New.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int yes_no = JOptionPane.NO_OPTION;   //Variable um festzustellen ob auf Ja oder auf Nein geklickt wurde
                int isSaved = 0;      //es werden verschiedene Fehlermeldungen im int gespeichert und darauf reagiert
                if(PointOrganisation.isChanged()) 
                    //es wird nur Nachgefragt falls die Punktemenge vom letzten Speicherpunkt verändert wurde
                    yes_no = JOptionPane.showConfirmDialog(mainFrame, "Would you like to save the previous assignment?", "Warning", JOptionPane.YES_NO_OPTION);
                if(yes_no==JOptionPane.NO_OPTION) {     //Abfrage ob abgespeichert werden soll oder nicht
                    PointOrganisation.deleteAllPoints();    //alle Punkte werden gelöscht
                    PointOrganisation.setChanged(false); //es muss nicht mehr wegen Speichern gefragt werden
                    mainToolBar.getToolBarReUndo().deleteAll(); //alle gespeicherten Redu und Undo Funktionen löschen
                }else {
                    isSaved = savePoints();             //die Punkte werden abgespeichert
                    switch (isSaved) {                  //Switch Anweisung um die vershiedenen Fehlermeldungen zu verarbeiten
                    case 1: break;                      //es passiert nichts fals das Fenster zum Speichern abgebrochen wird
                    case 2: JOptionPane.showMessageDialog(mainFrame, "Error saving Points!", "Error", JOptionPane.ERROR_MESSAGE);
                    //falls ein Fehler beim Speichern auftritt
                            break;
                    default: {
                        scrollPane.setViewportView(null);      
                        PointOrganisation.deleteAllPoints(); //alle bestehenden Punkte müssen gelöscht werden
                        PointOrganisation.setChanged(false); //es muss nicht mehr wegen Speichern gefragt werden
                        mainToolBar.getToolBarReUndo().deleteAll();//alle gespeicherten Redu und Undo Funktionen löschen
                        break;
                    }
                    }
                }
                draw.repaint();
            }
        });
    }
    /**
     * Die Methode wird augerufen wenn im Menü File auf die Save Fläche gedrückt wird und wird verwendet um die 
     * zuvor erstellte Punktemenge abzuspeichern.
     * @return Eine Integer Variable die Anzeigt ob richtig gespeichert worder ist.
     */
    int savePoints() {
        int isSaved = 0;            //isSaved wird verwendet um Sicherzustellen dass die Punkte wirklich abgespeichert sind 
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("../Tester/data"));
        int return_value = chooser.showSaveDialog(mainFrame);//öffnet den showOpenDialog und sperrt den MainFrame
        if(return_value == JFileChooser.APPROVE_OPTION) {//überprüfft ob eine Datei ausgewählt wurde 
           isSaved = Output.safeFile(chooser.getSelectedFile(),mainFrame);
           /*öffnet die statische Methode der Klasse Output und übergibt den ausgewählten File
             und den mainFrame zum sperren des MainFrame falls der File in der Methode 
             loadFile nicht gefunden wird*/
        }else
            isSaved = 1;//damit das Programm nicht automatisch geschlossen wird
        return isSaved;
    }
    
}
