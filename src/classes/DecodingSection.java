package classes;

import java.awt.event.KeyEvent;
import java.io.IOException;
import static java.lang.Thread.sleep;
import javax.swing.*;

public class DecodingSection extends AbstractDialog{
    
    // char possibilities
    final String[][] charCodes          =  {{"0000", "A"}, {"0001", "B"}, {"0010", "C"}, {"0011", "D"}, 
                                            {"0100", "E"}, {"0101", "F"}, {"0110", "G"}, {"0111", "H"}, 
                                            {"1000", "I"}, {"1001", "J"}, {"1010", "K"}, {"1011", "L"},
                                            {"1100", "M"}, {"1101", "N"}, {"1110", "O"}, {"1111", "P"}
                                           };
    
    private JTextField pointsLabel      = new JTextField(5);
    private boolean isTyping            = true;
    private int charCounter             = 0;
    private int bonusPoint              = 1000;
    final private int MAX_BONUS         = 1000;
    private String collectedCode        = "";
    private String[] validCode          = new String[3];    
    
    public DecodingSection(String _collectedCode) throws IOException{
        super(640, 480);
          
        this.collectedCode = _collectedCode;        
        pointsLabel.setBackground(new java.awt.Color(0, 0, 0));
        pointsLabel.setForeground(new java.awt.Color(245, 245, 245));
        pointsLabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pointsLabel.setBorder(null);
        pointsLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pointsLabel.setFocusable(false);
        pointsLabel.setOpaque(false);     
        pointsLabel.setFont(retroFont); 
        
        // displaying collected code with spaces
        label.setText("Level " + MainFrame.level + ": " + collectedCode.substring(0,4) + " " + collectedCode.substring(4,8) + " " + collectedCode.substring(8,12));
        button.setText("Decode");        
        setUpTextField("");
        ganerateValidCode();
        getPoints();
        
        panel.add(Box.createVerticalStrut(20));
        panel.add(pointsLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(label);
        panel.add(Box.createVerticalStrut(15));        
        panel.add(textField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(button);                
        setUpPanel();
    }
    
    private void getPoints(){
        Thread t = new Thread(){
            public void run(){
                while(true){
                    try{
                        if(isTyping){               
                            if(bonusPoint > 0){
                                sleep(100);
                                bonusPoint--;
                                pointsLabel.setText(Integer.toString(bonusPoint));  // update displayed left points
                            }
                            else{                               
                                logicStageDefeat();                             // time is exceeded
                            }                            
                        }                        
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    
    @Override
    public void keyCounter(KeyEvent e){        
        char key = e.getKeyChar();

        if(Character.isLowerCase(key)) {                                        // changing lower letter into upper 
            e.setKeyChar(Character.toUpperCase(key));
        }

        if(key >= 'a' && key <= 'z') {                                          // chars only from a to z
            if(charCounter <= 2)                                                // only 3 characters
                charCounter++;                                                  
            else
                e.consume();                                                    // skip others
        }                                
        else if(key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE){    // backspace and delete key handling
            String tempCode = textField.getText();
            charCounter = tempCode.length();
        }
        else if(key == KeyEvent.VK_ENTER)                                       // backspace and delete key handling
            checkCode();                    
        else
            e.consume();                    
    }

    // generate code to comparison with player's answer
    private void ganerateValidCode(){
        int counter = 0;
        
        for(int i = 0; i < 3; i++){                                             // each (4bit) part of collected code
            for(int j = 0; j < 16; j++){
                if(collectedCode.substring(counter, counter+4).equals(charCodes[j][0])) // searching equals series of bit with current 4bit collected series
                    validCode[i] = charCodes[j][1];                             // getting character corresponding with bit series
            }
            counter += 4;                                                       // move to the next 4 bit of collected binary code
        }
        
        System.out.println(validCode[0] + validCode[1] + validCode[2]);
    }
    
    // checking player's answer
    void checkCode(){
        System.out.println("Klik: " + textField.getText());
        
        if(textField.getText().equals(validCode[0] + validCode[1] + validCode[2])){                        
            MainFrame.currentStage = MainFrame.GameStage.stage1;
            MainFrame.mainWindow.checkStage();
            MainFrame.level++;            
            MainFrame.points += bonusPoint;
            bonusPoint = MAX_BONUS;
            dispose();                                                          // remove currently diplayed internal frame = next level
        }
        else{
            logicStageDefeat();
        }
    }
        
    private void logicStageDefeat(){                
        bonusPoint = MAX_BONUS;                                                 // reseting parameters
        isTyping = false;
        dispose();                                                              // remove currently diplayed internal frame
        MainFrame.currentStage = MainFrame.GameStage.menu;
        MainFrame.mainWindow.checkStage();

        try {                                                                   // go to game over internal frame
            new GameOver();                                    
        }catch(Exception ex){
            ex.printStackTrace();
        }  
    }

    public void buttonMouseClicked(java.awt.event.MouseEvent evt) {
        isTyping = false;
        checkCode();
    }   
}