package classes;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Klasa dziedzicząca po klasie AbstractDialog
 * Ustawia własne parametry gotowych obiektów
 * @author Sebastian Urwan
 */
public class Nickname extends AbstractDialog{
        
    /** przechowywanie tekstu przesłanego z klasy MainFrame */
    public String nickname = "";
    
    /** przechowywanie przycisku przesłanego z klasy MainFrame */
    public JButton nicknameButton;
    
    /** pole przechowuje liczbę wpisanych znaków */
    private int charCounter;
           
    /**
     * Ustawia parametry obiektów okna dialogowego
     * @param nickname pseudonim który będzie zaktualizowany w klasie głównej - MainFreame
     * @param nicknameButton przycisk, którego tekst będzie zaktualizowany w klasie głównej - MainFreame
     */
    public Nickname(String nickname, JButton nicknameButton){
        super(200, 200);
        
        //this.copyFrame = mainFrame;
        this.nickname = nickname;
        this.nicknameButton = nicknameButton;
        this.charCounter = this.nickname.length();

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
        
    /**
     * Metoda sprawdza długość wpisanego pseudonimu i blokuje określone klawisze
     * @param e odczytuje informacje o klawiszach
     */
    @Override
    public void keyCounter(KeyEvent e){    
        char c = e.getKeyChar();
        
        if(((c >= ' ' && c <= '~') || c == KeyEvent.VK_SPACE) && charCounter <= 8){   // ascii from 32 to 126, up to 8 characters
            charCounter++;
        }                
        else if(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE){        // backspace and delete key handling
            String tempNick = textField.getText();
            charCounter = tempNick.length();
        }
        else if(c == KeyEvent.VK_ENTER){
            this.nickname = textField.getText();        
            this.nicknameButton.setText(textField.getText());
            dispose();                                                          
        }
        else{
            e.consume();                                                        // skip others
        }         
    }
    
    /**     
     * @return zwraca wpisany pseudonik w postaci łańcucha znaków
     */
    public String getNickname(){
        return this.nickname;
    }
    
    /**
     * Implementacja metody abstrakcyjnej - aktualizacja wpisanego pseudonimu i zamknięcie dodatkowego okna
     * @param evt nie używany
     */
    @Override
    public void buttonMouseClicked(MouseEvent evt) {                                      
        this.nickname = textField.getText();
        this.nicknameButton.setText(textField.getText());
        dispose();
    }                                        
}