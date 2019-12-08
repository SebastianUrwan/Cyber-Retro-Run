package classes;

import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

public class Digits extends JLabel implements ActionListener{
        
    BufferedImage digitImg = ImageIO.read(MainWindow.class.getResourceAsStream("/retrorun/digits.png"));
    
    public Heart heart;
    public JTextField tf;
    
    final private short MAX_LIFES           = 3;
    private short life                      = 3;    
    
    private final ArrayList<Integer> code   = new ArrayList<Integer>();         // add digit 0 and 1 
    private String codeToString             = "";                               // converting collected code to string - display
    private String codeToSend               = "";
    
    private int generatedPosition;
    private int generatedDigit;
    
    private boolean canEnter                = true;
    
    final private int digitWidth            = 30;
    final private int START_X               = 640;
    private int currentDigitX               = 640;
    public int velocityX                    = 0;                                // changed when player choose level of difficulty
    
    final private int imgYPos               = 0;
    final private int digitHeight           = 34;
    final private int HIGH_Y                = 325;
    final private int LOW_Y                 = 396;   
    private int currentDigitY               = 325;
    private Timer timer;
    
    public Digits(JPanel jpanel, JTextField codeLabel) throws IOException{               
        super();
        
        heart = new Heart();
        tf = codeLabel;
        tf.setText(codeToString);
        
        generatePosition();
        generateDigit();
        
        this.setOpaque(false);
        jpanel.add(this, new AbsoluteConstraints(currentDigitX, currentDigitY, digitWidth, digitHeight));
        jpanel.setComponentZOrder(this, 0);
        
        jpanel.add(heart, new AbsoluteConstraints(heart.labelX, heart.labelY, heart.labelWidth, heart.labelHeight));
        jpanel.setComponentZOrder(heart, 10);
        heart.setImg(life);
        
        jpanel.revalidate();                 
        
        timer = new Timer(6, this);
        timer.start();        
    }
           
    // generating random digit between 0 to 9
    private void generateDigit(){
        int MIN = 0;
        int MAX = 2;
        generatedDigit = (int)(Math.random() * (MAX - MIN));        
       
        // setting proper part of graphics to jLabel
        this.setIcon(new ImageIcon(digitImg.getSubimage(digitWidth * generatedDigit, imgYPos, digitWidth, digitHeight)));
    }
    
    // generating random hight between low or high
    private void generatePosition(){
        int MIN = 0;
        int MAX = 100;
        generatedPosition = (int)(Math.random() * ((MAX - MIN) + 1)) + MIN;        
       
        if(generatedPosition < 50)
            currentDigitY = LOW_Y;
        else if(generatedPosition >= 50)
            currentDigitY = HIGH_Y;
    }
    
    // checking if character 
    private void addCode(){
        if(canEnter){
            code.add(generatedDigit);            
            codeToString += code.get(code.size()-1);
            tf.setText(codeToString);
                        
            if(code.size() == 12){                                              // enough binary code length = go to decoding section (stage2)
                currentDigitX = START_X;                                               // set digit posiotion on right of the window frame
                MainFrame.currentStage = MainFrame.GameStage.stage2;
                MainFrame.mainWindow.checkStage();
                
                codeToSend = codeToString;
                clear();                                                        // reseting parametrs when player returns from stage2
                try{                    
                    new DecodingSection(codeToSend);
                }catch(Exception e){
                    e.printStackTrace();
                }                
            }                  
        }        
    }
    
    private void takeLife(){                                                    // update player's life
        if(canEnter){
            life--;           
            heart.setImg(life);
        }        
    }
    
    public void actionPerformed(ActionEvent e){
        timer.start();
        
        if(MainFrame.currentStage.equals(MainFrame.GameStage.stage1)){          // if player is in the first stage (arcade)
            Area areaA = new Area(MainFrame.character.getBounds());
            Area areaB = new Area(MainFrame.obstacle.getBounds());

            if(areaA.intersects(areaB.getBounds2D())){                          // checking collision between digit and character
                if(generatedDigit >= 0 && generatedDigit <= 1){                    
                    addCode();
                    canEnter = false;                                           // ONLY ONCE PLAYER CAN ENTER CURRENT DIGIT                                 
                    currentDigitX = START_X;
                    setLocation(currentDigitX, currentDigitY);
                    MainFrame.points += 10;                                      
                    generatePosition();                                         // new parameters for the next digit                
                    generateDigit();                                            
                    MainFrame.playSound("collect");                             // sound of colleting digit
                }
                else if(generatedDigit >= 2 && generatedDigit <= 9){
                    if(life > 1){
                        takeLife();
                        canEnter = false;                                       // ONLY ONCE PLAYER CAN ENTER CURRENT DIGIT
                        currentDigitX = START_X;
                        setLocation(currentDigitX, currentDigitY);                                                                                
                        generatePosition();
                        generateDigit();
                        
                        if(MainFrame.points > 0)
                            MainFrame.points -= 10;
                    }
                    else{
                        clear();
                        currentDigitX = START_X;
                        MainFrame.currentStage = MainFrame.GameStage.menu;
                        MainFrame.checkStage();
                        
                        try {
                            new GameOver();                                    
                        }catch(IOException ex){
                        }      
                    }
                }                                
            }             
            else{
                canEnter = true;                                                // player can again enter next digit
                
                currentDigitX -= velocityX;                                     // digit movement to the left of the screen
                setLocation(currentDigitX, currentDigitY);                      // update location
                                
                if(getLocation().x + digitHeight <= 0){                         // when player don't hit the digit
                    generatePosition();                                         // new parameters
                    generateDigit();
                    currentDigitX = START_X;                                    // digit starts from right of the frame
                    setLocation(currentDigitX, currentDigitY);                  // update location
                }
            }
            
            // player can but avoid collision with 0 or 1! 
            if(generatedDigit >= 0 && generatedDigit <= 1 && MainFrame.character.getLocation().x - 2 == currentDigitX + digitWidth && canEnter){
                if(life > 1)                                                    
                    takeLife();
                else{
                    currentDigitX = START_X;
                    clear();
                    MainFrame.currentStage = MainFrame.GameStage.menu;
                    MainFrame.mainWindow.checkStage();
                    
                    // internal frame with overall score from current game
                    try {
                        new GameOver();                                    
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }      
                }
            }
        }
    }
    
    public void clear(){
        code.clear();
        codeToString = "";
        tf.setText("");
        life = 3;
        heart.setImg(life);
    }
}