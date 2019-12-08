package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

public abstract class AbstractDialog extends JDialog{
    
    private URL fontUrl;
    public Color whiteColour        = new Color(245, 245, 245);
    public Color blackColour        = new Color(0, 0, 0);
    public Color redColour          = new Color(255, 143, 159);
    public Font retroFont;    
    public JPanel panel             = new JPanel();    
    public JTextField textField     = new JTextField(12);
    public JTextArea textArea       = new JTextArea(18, 30);
    public JLabel label             = new JLabel();
    public JButton button           = new JButton();
    
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
    
    //--- Label ---------------------
    //-------------------------------
    public void setUpLabel(){
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(whiteColour);
        label.setFocusable(false);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setFont(retroFont);
    }
    
    //--- Button --------------------
    //-------------------------------
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
    
    public abstract void buttonMouseClicked(MouseEvent evt);    
    
    private void buttonMouseEntered(MouseEvent evt) {
        button.setForeground(redColour);
        button.setIcon(new ImageIcon(getClass().getResource("../graphics/btn2.png")));
    }                                      

    private void buttonMouseExited(java.awt.event.MouseEvent evt){
        button.setForeground(whiteColour);
        button.setIcon(new ImageIcon(getClass().getResource("../graphics/btn.png")));
    } 
    
    //--- TextField -----------------
    //-------------------------------
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
    
    public void keyCounter(KeyEvent e){}
    
    //--- TextArea ------------------
    //-------------------------------
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
        
    //--- Panel ---------------------
    //-------------------------------
    public void setUpPanel(){
        panel.setBackground(Color.BLACK);                
        add(panel);
        setVisible(true);
        pack();
    }
        
    //--- Font ----------------------
    //-------------------------------
    public void setUpFont(){
        try {            
            fontUrl = getClass().getResource("kongtext.ttf");
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