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

/**
 * Klasa odpowiedzialna za wygenerowanie JLabela, który będzie przyjmował grafikę zgodną z wygenerowaną cyfrą
 * Klasa zajmuje się sprawdzaniem warunku dodania kodu bądź zabrania życia bohaterowi
 * @author Sebastian Urwan
 */
public class Digits extends JLabel implements ActionListener{
        
    /** przechowuje grafikę zawierającą wszystkie cyfry od 0 do 9 - powoduje uniknięcie potrzeby wczytywania wielu grafik */
    BufferedImage digitImg = ImageIO.read(MainFrame.class.getResourceAsStream("../graphics/digits.png"));
    
    /** obiekt odpowiedzialny za wyświetlania grafiki prezentującej aktualną liczbę żyć */
    public Heart heart;
           
    /** maksymalna liczba żyć */
    final private short MAX_LIVES           = 3;
    
    /** aktualna liczba żyć */
    private short lives                     = MAX_LIVES;
    
    /** kod trafiający w postaci int */
    private final ArrayList<Integer> code   = new ArrayList<Integer>();         // add digit 0 and 1 
    
    /** pole przechowujący kod w postaci napisu wyświetlanego przez JTextField */
    private String codeToString             = "";                               // converting collected code to string - display
    
    /** ostateczny zebrany kod wysłany do klasy DecodingSection */
    private String codeToSend               = "";
    
    /** zmienna przechowująca pozycję góra/dół */
    private int generatedPosition;
    
    /** zmienna przechowująca wygenerowaną cyfrę od 0 do 9 */
    private int generatedDigit;
    
    /** flaga decydująca o tym, czy bohater zetknął się już z cyfrą - jeśli tak canEnter = false */
    private boolean canEnter                = true;
    
    /** szerokość cyfry */
    final private int digitWidth            = 30;
    
    /** startowa pozycja cyfry - schowana za oknem */
    final private int START_X               = 640;
    
    /** aktualna pozycja X cyfry */
    private int currentDigitX               = 640;
    
    /** początkowa szybkość cyfry - wartość zostanie zaktualizowana po wybraniu poziomu trudności rozgrywki */
    public int velocityX                    = 0;
    
    /** wysokość grafiki z której będzie odczytywany fragment obrazu */
    final private int imgYPos               = 0;
    
    /** wysokość cyfry */
    final private int digitHeight           = 34;
    
    /** górna pozycja Y cyfry */
    final private int HIGH_Y                = 325;
    
    /** dolna pozycja Y cyfry */
    final private int LOW_Y                 = 396;   
    
    /** aktualna pozycja Y cyfry */
    private int currentDigitY               = 325;
    
    /** pole przechowujące aktualną liczbę punktów */
    public int points                       = 0;
    
    /** pole przechowujące aktualny poziom */
    public int level                        = 1;
    
    /** kopia obiektu bohaterra */
    public JLabel character;
    
    /** kopia obiektu wyświetlającego punkty */
    public JTextField pointsDisplay;
    
    /** kopia obiektu wyświetlającego kod */
    public JTextField codeDisplay;    
    
    /** kopia pseudonimu gracza */
    public String nickname;       
    
    /** zegar, który automatycznie będzie wywoływał metodę actionPerformed */
    private Timer timer;        

    /** obiekt dodatkowego okna części logicznej */
    public DecodingSection decodeFrame;        
    
    /**
     * Konstruktor ustawia wartości parametrów przesłanych obiektów oraz ustala opóźnienie zegara (z takim opóźnieniem będzie aktualizowana pozycja cyfry
     * @param jpanel kontener do którego będzie dodany obiekt cyfry
     * @param codeDisplay parametr potrzebny w celu aktualizacji wyświetlanego kodu
     * @param pointsDisplay parametr potrzebny w celu aktualizacji wyświetlanych punktów
     * @param character obiekt JLabel wykorzystany w celu sprawdzenia kolizji z cyfrą
     * @param nickname parametr potrzebny do przesłania dla okna GameOver w momencie przegrania tury
     * @throws IOException wyjątek obsługujący niepowodzenie wczytania grafik cyfr z pliku
     */
    public Digits(JPanel jpanel, JTextField codeDisplay, JTextField pointsDisplay, JLabel character, String nickname) throws IOException{
        super();                
        
        this.codeDisplay = codeDisplay;
        this.codeDisplay.setText(codeToString);        
        this.pointsDisplay = pointsDisplay;
        this.pointsDisplay.setText(Integer.toString(this.points));
        this.character = character;        
        this.nickname = nickname;
                
        heart = new Heart();                
        
        generatePosition();
        generateDigit();
        
        this.setOpaque(false);
        jpanel.add(this, new AbsoluteConstraints(currentDigitX, currentDigitY, digitWidth, digitHeight));
        jpanel.setComponentZOrder(this, 0);
        
        jpanel.add(heart, new AbsoluteConstraints(heart.labelX, heart.labelY, heart.labelWidth, heart.labelHeight));
        jpanel.setComponentZOrder(heart, 10);
        heart.setImg(lives);
        
        jpanel.revalidate();                 
        
        timer = new Timer(6, this);
        timer.start();        
    }
               
    /**
     * Metoda generuje i zapisuje do zmiennej cyfrę od 0 do 9
     */
    private void generateDigit(){
        int min = 0;
        int max = 10;
        generatedDigit = (int)(Math.random() * (max - min));        
       
        // setting proper part of graphics to jLabel
        this.setIcon(new ImageIcon(digitImg.getSubimage(digitWidth * generatedDigit, imgYPos, digitWidth, digitHeight)));
    }
        
    /**
     * Metoda generuje i zapisuje do zmiennej jaką pozycję przyjmie cyfra - góra czy dół
     */
    private void generatePosition(){
        int min = 0;
        int max = 100;
        generatedPosition = (int)(Math.random() * ((max - min) + 1)) + min;        
       
        if(generatedPosition < 50)
            currentDigitY = LOW_Y;
        else if(generatedPosition >= 50)
            currentDigitY = HIGH_Y;
    }
    
    /**
     * Metoda aktualizuje zebraną cyfrę
     * Sprwadza, czy daną cyfrę można zebrać tylko raz
     */
    private void addCode(){
        if(canEnter){
            code.add(generatedDigit);            
            codeToString += code.get(code.size()-1);
            codeDisplay.setText(codeToString);
                        
            if(code.size() == 12){                                              // enough binary code length = go to decoding section (stage2)
                currentDigitX = START_X;                                        // set digit posiotion on right of the window frame
                MainFrame.currentStage = GameStage.stage2;
                MainFrame.currentStage.changeFlag(true);
                
                codeToSend = codeToString;
                clear();                                                        // reseting parametrs when player returns from stage2                
                decodeFrame = new DecodingSection(codeToSend, points, level, nickname);                                
                points += decodeFrame.getBonusPoints();
                this.setPoints(points);                
            }                  
        }        
    }
    
    /**
     * Metoda aktualizuje liczbę dostepnych żyć
     */
    private void takeLife(){
        if(canEnter){
            lives--;
            heart.setImg(lives);
        }        
    }
    
    /**
     * Metoda aktualizuje wartość punktów i wyświetla nową wartość
     */
    public void setPoints(int pts){
        this.pointsDisplay.setText(Integer.toString(pts));
    }
    
    /**
     * Metoda, która ustawia szybkość poruszania się cyfry w danej turze w zależności od wciśniętego przycisku (easy, medium, hard)
     * @param vel przesłana szybkość z jaką będzie się poruszać cyfra
     */
    public void setVelocity(int vel){
        this.velocityX = vel;        
    }
    
    /**
     * Metoda która jest cyklicznie wywoływana
     * sprawdza czy doszło do zderzenia bohatera z cyfrą i w zależności od tego dodaje punkty, zabiera życie i generuje nową wartośc oraz pozycję cyfry
     * @param e nie używane
     */
    public void actionPerformed(ActionEvent e){                                
        if(MainFrame.currentStage.equals(GameStage.stage1)){
            Area areaA = new Area(character.getBounds());
            Area areaB = new Area(this.getBounds());

            if(areaA.intersects(areaB.getBounds2D())){                          // checking collision between digit and character
                if(generatedDigit >= 0 && generatedDigit <= 1){                    
                    this.points += 10;
                    setPoints(this.points);
                    
                    addCode();
                    canEnter = false;                                           // ONLY ONCE PLAYER CAN ENTER CURRENT DIGIT
                    currentDigitX = START_X;
                    setLocation(currentDigitX, currentDigitY);                    
                    generatePosition();                                         // new parameters for the next digit
                    generateDigit();                                            
                    new PlaySound("collect");                                   // sound of colleting digit
                }
                else if(generatedDigit >= 2 && generatedDigit <= 9){
                    if(lives > 1){
                        takeLife();
                        canEnter = false;                                       // ONLY ONCE PLAYER CAN ENTER CURRENT DIGIT
                        currentDigitX = START_X;
                        setLocation(currentDigitX, currentDigitY);                                                                                
                        generatePosition();
                        generateDigit();
                        
                        if(points > 0)
                            points -= 10;
                    }
                    else{
                        clear();
                        currentDigitX = START_X;
                        MainFrame.currentStage = GameStage.menu;
                        MainFrame.currentStage.changeFlag(true);
                        new GameOver(nickname, points);
                        points = 0;
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
            if(generatedDigit >= 0 && generatedDigit <= 1 && this.character.getLocation().x - 2 == currentDigitX + digitWidth && canEnter){
                if(lives > 1)                                                    
                    takeLife();
                else{
                    currentDigitX = START_X;
                    clear();
                    MainFrame.currentStage = GameStage.menu;
                    MainFrame.currentStage.changeFlag(true);
                    
                    // internal frame with overall score from current game                    
                    new GameOver(nickname, points);
                    points = 0;
                }
            }
        }
    }
    
    /**
     * resetowanie wartości parametrów po zakończonej turze
     */
    public void clear(){
        code.clear();
        codeToString = "";
        codeDisplay.setText("");
        pointsDisplay.setText("0");
        lives = MAX_LIVES;
        heart.setImg(lives);
    }
}