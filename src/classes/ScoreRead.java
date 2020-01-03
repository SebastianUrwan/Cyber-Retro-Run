package classes;

import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.Box;

/**
 * Klasa, której zadaniem jest wyświetlanie (w dodatkowym oknie) listy 20 najlepszych posegragowanych wyników wraz z nickami
 * @author Sebastian Urwan
 */
public class ScoreRead extends AbstractDialog{
    
    /** wyniki odczytane z pliku, które następnie będą segregowane */
    ArrayList<Scores> dataToSort = new ArrayList<Scores>();
    
    /**
     * Konstruktor klasy, który ustawia rozmiary dodatkowego okna
     */
    public ScoreRead(){
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
    
    /**
     * Metoda wczytująca plik tekstowy, odczytujący zawartość do tablicy, przekazująca tablicę do posegregowania i wyświetlająca 20 wyników
     */
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
    
    /**
     * Metoda zamykająca okno dialogowe
     * @param evt nie używany
     */
    @Override
    public void buttonMouseClicked(MouseEvent evt) {                                                      
        dispose();
    }                                     
}