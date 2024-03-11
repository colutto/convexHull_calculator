package mainFrame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



/**
 * Die Klasse SizeDialog fragt den Benutzer nach der Größe der Zeichenfläche, bis diese nicht eingegeben wurde können 
 * einige Funktionen der Anwendung nicht ausgeführt werden.
 * @author Stefan Colutto 9513140
 *
 */
public class SizeDialog extends JDialog {
    
    private JButton confirm;     //Button zum Bestätigen der Eingabe
    private final int sizeX = 350; //die Länge des Fensters
    private final int sizeY = 150; //die Höhe des Fensters
    private JButton cancel;    //Button zum Abbrechen des Dialogs
    private JLabel text;                                 
    private JLabel width;      
    private JLabel height;
    private JTextField inputWidth; //das Feld in dem vom Benutzer die gewünschte Länge der Zeichenfläche eingetragen wird
    private JTextField inputHeight; //das Feld in dem vom Benutzer die gewünschte Höhe der Zeichenfläche eingetragen wird
    private JPanel panelInput;  //Panel zum anordnen der JLabel width und height und der JTextField inputWidth und inputHeight
    
    
    SizeDialog(MainFrame mainFrame, JScrollPane scrollPane, Draw draw) {
        super(mainFrame, "Choose Size", JDialog.DEFAULT_MODALITY_TYPE);
        setSize(sizeX, sizeY);
        setResizable(false);
        setLayout(null);
        
        panelInput = new JPanel();  
        panelInput.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        width = new JLabel("Width in Pixel:");
        panelInput.add(width);
        
        inputWidth = new JTextField();
        inputWidth.setPreferredSize(new Dimension(50,20));
        panelInput.add(inputWidth);
        
        
        height = new JLabel("Height in Pixel:");
        panelInput.add(height);
        
        inputHeight = new JTextField();
        inputHeight.setPreferredSize(new Dimension(50,20));
        panelInput.add(inputHeight);
        
        
        text = new JLabel("Please enter an appropriate Size except less or equal zero:");
        text.setLocation(5, 15);
        text.setSize(350,20);
        add(text);
        
        panelInput.setLocation(0, 40);
        panelInput.setSize(340, 20);
        add(panelInput);
        
        confirm = new JButton("Confirm");
        confirm.setSize(80, 30);
        confirm.setLocation(50, 80);
        add(confirm);
        
        cancel = new JButton("Cancel");
        cancel.setSize(80, 30);
        cancel.setLocation(215, 80);
        add(cancel);
        setEnabled(true);
       
        confirm.addActionListener(new ActionListener() {
            /**
             * Die Methode wandelt die eingegebenen Strings in Integer Zahlen um und überprüft ob es sich wirklich 
             * um Zahlen handelt und die Zahlen <0 sind. 
             */
            @SuppressWarnings("hiding")
            @Override
            public void actionPerformed(ActionEvent e) {
                String width = inputWidth.getText();
                String height = inputHeight.getText();
                inputWidth.setText("");
                inputHeight.setText("");
                boolean isExcepted = false;
                int x = 0;
                int y = 0;
                try {
                    x = Integer.valueOf(width);     //wandelt den String width in eine Integer Zahl um
                    y = Integer.valueOf(height);    //wandelt den String height in eine Integer Zahl um
                    if (x<=0||y<=0)                 //falls die Integer Zahlen x oder y nicht <0 sind wird eine Exception ausgelöst 
                        throw new Exception();
                } catch (Exception e2) {  
                    /*die Exception für die Umwandlung von String zu Integer und die Exception in der If Klausel 
                     * werden hier abgefangen*/
                    JOptionPane.showMessageDialog(SizeDialog.this, "Please enter a correct Integer", "Wrong Input", JOptionPane.ERROR_MESSAGE);
                    isExcepted = true;
                }
                if(!isExcepted) {
                    dispose();                          //stellt den Dialog wieder auf nicht Sichtbar
                    mainFrame.setEnabled(true);         //nach dem Dialog kann der MainFrame wieder auf Benutzt werden
                    mainFrame.setVisible(true);         //der MainFrame wird wieder als vorderstes Fenster angezeigt
                    draw.setPreferredSize(x, y, 0, 0);
                    scrollPane.setViewportView(draw);  
                    //das scrollPane bekommt das panelDraw übergeben damit es wenn nötig mit Scrollbalken dargestellt werden kann
                    
                }else
                    setVisible(true);                   //falls die Eingabe falsch war wird der Dialog erneut angezeigt
            }
        });
        
        cancel.addActionListener(new ActionListener() {
            /**
             * Die Methode wird verwendet um den Dialog vorzeitig zu beenden.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                mainFrame.setEnabled(true);     //nach dem Dialog kann der MainFrame wieder auf Benutzt werden
                mainFrame.setVisible(true);     //der MainFrame wird wieder als vorderstes Fenster angezeigt
            }
        });
        
        
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                mainFrame.setEnabled(true);      //nach dem Dialog kann der MainFrame wieder benutzt werden
                mainFrame.setVisible(true);      //der MainFrame wird wieder als vorderstes Fenster angezeigt
            }
        });
    }
    /**
     * Die Methode gibt die Breite des Dialogs zurück.
     * @return Eine Integer Zahl die die Breite des Dialogs darstellt.
     */
    int getSizeX() {
        return sizeX;
    }
    /**
     * Die Methode gibt die Höhe des Dialogs zurück.
     * @return Eine Integer Zahl die die Höhe des Dialogs darstellt.
     */
    int getSizeY() {
        return sizeY;
    }
}
