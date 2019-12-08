package classes;

import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class GameOver extends AbstractDialog{
    
    public GameOver() throws IOException{
        super(200, 200); 
        new ScoreSave().save(MainFrame.nickname, Integer.toString(MainFrame.points));
                
        label.setText("Your score " + Integer.toString(MainFrame.points));
        button.setText("Play again");

        panel.add(Box.createVerticalStrut(65));
        panel.add(label);                
        panel.add(Box.createVerticalStrut(10));
        panel.add(button);        
        setUpPanel();
    }    
    
    public void buttonMouseClicked(MouseEvent evt) {                                                      
        dispose();
        MainFrame.points = 0;
    }                                     
}