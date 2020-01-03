package classes;

import java.awt.event.*;
import javax.swing.*;

/**
 * Klasa dziedzicząca po klasie AbstractDialog
 * Ustawia własne parametry gotowych obiektów
 * @author PC
 */
public class GameOver extends AbstractDialog{
   
    /** kopia liczby punktów przesłanych po zakończonej rozgrywce */
    private int finalPoints = 0;
    
    /**
     * Konstruktor okna po przegranej turze
     * Wyświetlanie zdobytych punktów
     * @param nickname pseudonim gracza z ostatniej rozgrywki
     * @param points punkty gracza zebrane z ostatniej rozgrywki
     */
    public GameOver(String nickname, int points){
        super(200, 200); 
        finalPoints = points;
        
        new ScoreSave().save(nickname, Integer.toString(finalPoints));
                
        label.setText("Your score " + Integer.toString(finalPoints));
        button.setText("Play again");

        panel.add(Box.createVerticalStrut(65));
        panel.add(label);                
        panel.add(Box.createVerticalStrut(10));
        panel.add(button);        
        setUpPanel();
    }    
    
    /**
     * Wciśnięcie przycisku spowoduje usunięcie dodatkowego okna i zresetuje liczbę punktów
     * @param evt nie używany
     */
    public void buttonMouseClicked(MouseEvent evt) {                                                      
        dispose();
        finalPoints = 0;
    }                                     
}