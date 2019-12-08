package classes;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class Nickname extends AbstractDialog{
        
    private String nickname;    
    private int charCounter;
            
    public Nickname(String _nickname) throws IOException{
        super(200, 200);
        
        this.nickname = _nickname;
        this.charCounter = nickname.length();             

        setUpLabel();
        label.setText("Your name");                
        setUpTextField(this.nickname);                
        setUpButton();
        button.setText("Confirm");
        
        panel.add(Box.createVerticalStrut(50));
        panel.add(label);
        panel.add(Box.createVerticalStrut(15));        
        panel.add(textField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(button);
        setUpPanel();        
    }
        
    public void keyCounter(KeyEvent e){    
        char c = e.getKeyChar();
        
        if((c >= ' ' && c <= '~') || c == KeyEvent.VK_SPACE) {                  // ascii from 32 to 126
            if(charCounter <= 8)                                                // only 9 characters
                charCounter++;
            else
                e.consume();                                                    // skip others
        }                
        else if(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE){        // backspace and delete key handling
            String tempNick = textField.getText();
            charCounter = tempNick.length();
        }
        else if(c == KeyEvent.VK_ENTER){
            MainFrame.nickname = textField.getText();        
            MainFrame.nickButton.setText(textField.getText());
            dispose();                                                          
        }
        else{
            e.consume();
        }         
    }
    
    public void buttonMouseClicked(MouseEvent evt) {                                      
        MainFrame.nickname = textField.getText();        
        MainFrame.nickButton.setText(textField.getText());
        dispose();
    }                                        
}