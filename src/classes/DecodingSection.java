package classes;

import java.awt.Component;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import javax.swing.*;

/**
 * Klasa dziedzicząca po klasie AbstractDialog
 * Ustawia własne parametry gotowych obiektów
 * @author Sebastian Urwan
 */
public class DecodingSection extends AbstractDialog{
        
    /** możliwe fragmenty kodu do zebrania  */
    final String[][] charCodes          =  {{"0000", "A"}, {"0001", "B"}, {"0010", "C"}, {"0011", "D"}, 
                                            {"0100", "E"}, {"0101", "F"}, {"0110", "G"}, {"0111", "H"}, 
                                            {"1000", "I"}, {"1001", "J"}, {"1010", "K"}, {"1011", "L"},
                                            {"1100", "M"}, {"1101", "N"}, {"1110", "O"}, {"1111", "P"}
                                           };
    
    /** przechowywanie pola tekstowego przesłanego z klasy MainFrame */
    private JTextField pointsLabel      = new JTextField(5);
    
    /** przy pomocy etykiety będzie wyświetlana pomoc przy dekodowaniu */
    private JLabel codeAid              = new JLabel();
    
    /** flaga przechowująca stan logiczny umożliwiający dalsze wpisywanie odpowiedzi */
    private boolean isTyping            = true;
    
    /** pole przechowuje liczbę wpisanych znaków */
    private int charCounter             = 0;
    
    /** pole przechowuje liczbę pozostałych bonusowych punktów */
    private int bonusPoint              = 1000;
    
    /** pole przechowuje maksymalną liczbę bonusowych punktów */
    final private int MAX_BONUS         = 1000;
    
    /** pole przechowujące tekst z zebranym kodem przesłany parametrem od klasy Digits */
    private String collectedCode        = "";
    
    /** pole przechowuje poprawne fragmenty zdekowodanej wiadomości */
    private String[] validCode          = new String[3];
    
    /** pole przechowujące aktualny poziom rozgrywki przesłany z klasy Digits */
    public int copyLevel                = 0;
        
    /** pole przechowujące aktualną liczbę punktów przesłaną z klasy Digits */
    public int copyPoints               = 0;
    
    /** pole przechowujące aktualny pseudonim gracza przesłany z klasy Digits */
    public String nickname              = "";    
    
    /**
     * Ustawia parametry obiektów okna dialogowego oraz wyświetla zebrany kod do dekodowania
     * @param collectedCode zebrany ciąg 12 bitów
     * @param points parametr potrzebny do aktualizacji i dodania bonusu do zebranych punktów
     * @param level parametr potrzebny do wyświetlenia aktualnego numeru poziomu
     * @param nickname parametr potrzebny do przesłania dla okna GameOver w momencie przegrania tury
     */
    public DecodingSection(String collectedCode, int points, int level, String nickname){
        super(640, 480);
                                          
        this.collectedCode = collectedCode;
        this.copyPoints = points;
        this.copyLevel = level;        
        this.nickname = nickname;
        
        codeAid.setAlignmentX(Component.CENTER_ALIGNMENT);        
        codeAid.setFocusable(false);
        codeAid.setHorizontalTextPosition(SwingConstants.CENTER);        
        codeAid.setIcon(new ImageIcon(getClass().getResource("../graphics/codeAid.png")));
        
        pointsLabel.setBackground(new java.awt.Color(0, 0, 0));
        pointsLabel.setForeground(new java.awt.Color(245, 245, 245));
        pointsLabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pointsLabel.setBorder(null);
        pointsLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pointsLabel.setFocusable(false);
        pointsLabel.setOpaque(false);     
        pointsLabel.setFont(retroFont); 
        
        // displaying collected code with spaces
        label.setText("Level " + copyLevel + ": " + collectedCode.substring(0,4) + " " + collectedCode.substring(4,8) + " " + collectedCode.substring(8,12));
        button.setText("Decode");        
        setUpTextField("");
        ganerateValidCode();
        getPoints();
        
        panel.add(Box.createVerticalStrut(10));
        panel.add(codeAid);
        panel.add(Box.createVerticalStrut(10));
        panel.add(pointsLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(label);
        panel.add(Box.createVerticalStrut(15));        
        panel.add(textField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(button);                
        setUpPanel();
    }
    
    /**
     * Metoda, która jest czasowo wykonywana i co określony czas zabiera bonusowe punkty.
     * Koniec bonusowych punktów oznacza przegraną.
     */
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
    
    /**
     * Metoda sprawdza długość wpisanej odpowiedzi i blokuje określone klawisze
     * @param e odczytuje informacje o klawiszach
     */
    @Override
    public void keyCounter(KeyEvent e){        
        char key = e.getKeyChar();

        if(Character.isLowerCase(key))                                          // changing lower letter into upper 
            e.setKeyChar(Character.toUpperCase(key));      

        if((key >= 'a' && key <= 'z') && charCounter <= 2) {                    // chars only from a to z & only 3 characters
                charCounter++;            
        }                                
        else if(key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE){    // backspace and delete key handling
            String tempCode = textField.getText();
            charCounter = tempCode.length();
        }
        else if(key == KeyEvent.VK_ENTER)                                       // backspace and delete key handling
            checkCode();                    
        else
            e.consume();                                                        // skip others
    }
    
    /**
     * Metoda generująca słowną prawidłową odpowiedź na podstawie zebranego kodu
     */
    private void ganerateValidCode(){
        int counter = 0;
        
        for(int i = 0; i < 3; i++){                                             // each (4bit) part of collected code
            for(int j = 0; j < 16; j++){
                if(collectedCode.substring(counter, counter+4).equals(charCodes[j][0])) // searching equals series of bit with current 4bit collected series
                    validCode[i] = charCodes[j][1];                             // getting character corresponding with bit series
            }
            counter += 4;                                                       // move to the next 4 bit of collected binary code
        }
        
        //System.out.println(validCode[0] + validCode[1] + validCode[2]);
    }
    
    /**
     * Metoda sprawdzająca poprawność wpisanej przez gracza odpowiedzi
     */    
    void checkCode(){                
        if(textField.getText().equals(validCode[0] + validCode[1] + validCode[2])){                        
            MainFrame.currentStage = GameStage.stage1;
            MainFrame.currentStage.changeFlag(true);
            copyLevel++;            
            copyPoints += bonusPoint;
            bonusPoint = MAX_BONUS;
            dispose();                                                          // remove currently diplayed internal frame = next level
        }
        else{
            logicStageDefeat();
        }
    }
        
    /**
     * Metoda ustawiająca parametry po podaniu błędej odpowiedzi
     */    
    private void logicStageDefeat(){                
        bonusPoint = MAX_BONUS;                                                 // reseting parameters
        isTyping = false;
        dispose();                                                              // remove currently diplayed internal frame
        MainFrame.currentStage = GameStage.menu;
        MainFrame.currentStage.changeFlag(true);
        
        new GameOver(nickname, copyPoints);                                     // go to game over internal frame        
    }

    /**
     * Implementacja metody abstrakcyjnej - po wciśnięciu przycisku nie można zmieniać odpowiedzi
     * @param evt nie używany
     */
    @Override
    public void buttonMouseClicked(java.awt.event.MouseEvent evt) {
        isTyping = false;
        checkCode();
    }   
}