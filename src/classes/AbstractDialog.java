package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

/**
 * Klasa, która tworzy i opisuje wszystkie elementy dodatkowego okna dialogowego
 * @author Sebastian Urwan
 */
public abstract class AbstractDialog extends JDialog{
    
    /** link do katalogu z czcionką */
    private URL fontUrl;
    
    /** zewnętrzna czcionka */
    public Font retroFont;
    
    /** definicja koloru białego */
    public Color whiteColour        = new Color(245, 245, 245);
    
    /** definicja koloru czarnego */
    public Color blackColour        = new Color(0, 0, 0);
    
    /** definicja koloru czerwonego */
    public Color redColour          = new Color(255, 143, 159);
    
    /** definicja panelu gromadzącego wszystkie obiekty interfejsu */
    public JPanel panel             = new JPanel();
    
    /** definicja linii tekstowej */
    public JTextField textField     = new JTextField(12);
    
    /** definicja pola tekstowego */
    public JTextArea textArea       = new JTextArea(18, 30);
    
    /** definicja etykiety */
    public JLabel label             = new JLabel();
    
    /** definicja przycisku */
    public JButton button           = new JButton();
    
    /**
     * Konstruktor klasy abstrakcyjnej wywołuje tworzy JDialog oraz wywołuje metody opisujące wszystkie obiekty występujące w dodatkowym oknie 
     * @param width opisuje szerokość dodatkowego okna dialogowego
     * @param height opisuje wysokość dodatkowego okna dialogowego
     */
    public AbstractDialog(int width, int height){
        super();
        
        setModal(true);
        setUndecorated(true);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        setUpFont();
        setUpLabel();
        setUpButton();        
    }
    
    /**
     * Metoda ustawia parametry etykiety JLabel
     */
    public void setUpLabel(){
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(whiteColour);
        label.setFocusable(false);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setFont(retroFont);
    }
    
    /**
     * Metoda ustawia parametry przycisku JButton
     */
    public void setUpButton(){
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(whiteColour);        
        button.setIcon(new ImageIcon(getClass().getResource("../graphics/btn.png"))); // NOI18N        
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);        
        button.setFont(retroFont);
        
        button.addMouseListener(new MouseAdapter() {            
            public void mouseClicked(MouseEvent evt) {
                buttonMouseClicked(evt);
            }
            
            public void mouseEntered(MouseEvent evt) {
                buttonMouseEntered(evt);
            }
            
            public void mouseExited(MouseEvent evt) {
                buttonMouseExited(evt);
            }
        });
    }
        
    /**
     * Abstrakcyjna metoda służąca do indywidualnego zaimplemetowania przez klasy dziedziczące
     * @param evt 
     */
    public abstract void buttonMouseClicked(MouseEvent evt);    
    
    /**
     * Obsługa parametrów przycisku po najechaniu myszką na obiekt
     * @param evt nie używane
     */
    private void buttonMouseEntered(MouseEvent evt) {
        button.setForeground(redColour);
        button.setIcon(new ImageIcon(getClass().getResource("../graphics/btn2.png")));
    }                                      

    /**
     * Obsługa parametrów przycisku po wyjechaniu myszką z obiektu
     * @param evt nie używane
     */
    private void buttonMouseExited(java.awt.event.MouseEvent evt){
        button.setForeground(whiteColour);
        button.setIcon(new ImageIcon(getClass().getResource("../graphics/btn.png")));
    } 
        
    /**
     * Metoda ustawia parametry linii tekstowej JTextField
     * @param textToSet przesłany tekst będzie ustawiony jako tekst do wyświetlenia na elemencie
     */
    public void setUpTextField(String textToSet){
        textField = new JTextField(textToSet, 12);        
        textField.setMaximumSize(textField.getPreferredSize());                
        textField.setHighlighter(null);   
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setPreferredSize(new Dimension(220, 30));
        textField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));                
        textField.setBackground(blackColour);
        textField.setForeground(whiteColour);        
        textField.setOpaque(false);
        textField.setDropTarget(null);
        textField.setFont(retroFont);        
        
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                keyCounter(e);
            }
        });
    }
    
    /**
     * Metoda gotowa do nadpisania przez Nickanem i DecodingSection
     * Będzie zliczać wpisane znaki i chronić przed wpisaniem odpowiednich znaków
     * @param e odczytuje aktualne klawisze
     */
    public void keyCounter(KeyEvent e){}
    
    /**
     * Metoda ustawia parametry pola tekstowego JTextArea
     */
    public void setUpTextArea(){
        textArea.setForeground(whiteColour);
        textArea.setBackground(blackColour);
        textArea.setFocusable(false);
        textArea.setBorder(null);
        textArea.setBounds(160, 100, 300, 400);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(retroFont);
    }
        
    /**
     * Metoda ustawiająca parametry panelu
     * Panel zostaje dodany ze wszystkimi komponentami do dodatkowego okna 
     */
    public void setUpPanel(){
        panel.setBackground(Color.BLACK);                
        add(panel);
        setVisible(true);
        pack();
    }
                
    /**
     * Przygotowanie zewnętrznej czcionki do użycia 
     */
    public void setUpFont(){
        try {            
            fontUrl = getClass().getResource("../graphics/kongtext.ttf");
            retroFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontUrl.getPath())).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();            
            ge.registerFont(retroFont);                                    
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }
}