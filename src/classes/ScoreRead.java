package classes;

import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Box;

public class ScoreRead extends AbstractDialog{
    
    ArrayList<Scores> dataToSort = new ArrayList<Scores>();
    
    public ScoreRead() throws IOException{
        super(300, 350);                        

        button.setText("Back");
        setUpTextArea();
        read();
        
        panel.setBackground(Color.BLACK);                
        panel.add(Box.createVerticalStrut(20)); 
        panel.add(textArea);  
        panel.add(Box.createVerticalStrut(5));                        
        panel.add(button);        
        panel.add(Box.createVerticalStrut(10));  
        setUpPanel();       
    }    
    
    public void read(){             
        
        String line1, line2;        
        int numberOfLines = 0;
                
        try{            
            BufferedReader input = new BufferedReader(new FileReader("data.txt"));            
            
            // reading data from file
            while(((line1 = input.readLine()) != null) && ((line2 = input.readLine()) != null)){
                dataToSort.add(new Scores(line1, Integer.parseInt(line2)));                                                
            }                  
            
            // sorting
            Collections.sort(dataToSort, new SortData()); 
                        
            if(dataToSort.size() < 20)
                numberOfLines = dataToSort.size();
            else
                numberOfLines = 20;
                    
            // displaying result in fixed order
            for(int i = 0; i < numberOfLines; i++){
                if(i < 9)
                    textArea.append("   " + "0" + (i+1) + ". " + dataToSort.get(i) + "\n");
                else
                    textArea.append("   " + (i+1) + ". " + dataToSort.get(i) + "\n");
            }           
        }
        catch (IOException e){            
            e.printStackTrace();
        }
    }   
    
    public void buttonMouseClicked(MouseEvent evt) {                                                      
        dispose();
    }                                     
}