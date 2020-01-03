package classes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

/**
 * Główna klasa aplikacji
 * zarządza i steruje wywołaniami obiektów
 * @author Sebastian Urwan
 */
public final class MainFrame extends javax.swing.JFrame {
    
    /** obiekt odpowiedzialny za odczyt danych z pliku */
    public ScoreRead sr;
    
    /** obiekt poruszającej się cyfry */
    public Digits digit;        
    
    /** grafika z klatkami animacji biegu i kucania bohatera */
    BufferedImage characterImg;
    
    /** krotność czasu opóźnienia zegara; aktualizcja pozycji pierwszej warstwy budynków */
    private final int LAYER1_REFRESH_TIME       = 20;
    
    /** krotność czasu opóźnienia zegara; aktualizcja pozycji środkowej warstwy budynków */
    private final int LAYER2_REFRESH_TIME       = 45;
    
    /** krotność czasu opóźnienia zegara; aktualizcja pozycji ostatniej warstwy budynków */
    private final int LAYER3_REFRESH_TIME       = 100;
    
    /** krotność czasu opóźnienia zegara; aktualizcja klatki animacji bohatera */
    private final int CHARACTER_REFRESH_TIME    = 11;
    
    /** krotność czasu opóźnienia zegara; aktualizcja parametrów skoku */
    private final int JUMP_REFRESH_TIME         = 1;              
    
    /** wartość początkowa od której będą przemieszczały się budynki */
    private final int LAYERS_START_X            = 640;    
    
    /** szybkość gry */
    private final int GAME_SPEED                = 2;
    
    /** wartość siły grawitacji */
    private double gravity                      = 1.5; 
        
    /** aktualna pozycja pierwszej warstwy budunków */
    public int currLayer1Pos                    = 0;
    
    /** aktualna pozycja środkowej warstwy budunków */
    public int currLayer2Pos                    = 0;
    
    /** aktualna pozycja ostatniej warstwy budunków */
    public int currLayer3Pos                    = 0;
    
    /** aktualna pozycja X obrazu odczytująca klatkę animacji */
    public int charXPos                         = 0;
    
    /** aktualna pozycja Y obrazu odczytująca klatkę animacji */
    public int charYPos                         = 0;
       
    /** liczba powrótrzeń cykli zegara (REFRESH_TIME) wykonanych przez pierwszą warstwę budynków */
    public int repeaterCounterLayer1            = 1;
    
    /** liczba powrótrzeń cykli zegara (REFRESH_TIME) wykonanych przez środkową warstwę budynków */
    public int repeaterCounterLayer2            = 1;
    
    /** liczba powrótrzeń cykli zegara (REFRESH_TIME) wykonanych przez ostatnią warstwę budynków */
    public int repeaterCounterLayer3            = 1;
    
    /** liczba powrótrzeń cykli zegara (REFRESH_TIME) wykonanych przez przetworzenie animacji bogatera */
    public int repeaterCounterChar              = 1;
    
    /** liczba powrótrzeń cykli zegara (REFRESH_TIME) wykonanych przez pojedynczą aktualizację parametrów skoku */
    public int repeaterCounterJump              = 1;        
    
    /** pole przechowujące czas trwania aplikacji */
    public long gameTime                        = 0;
    
    /** switch przełączający pierwszą część warstwy (pierwsza warstwa budynków) na drugą */
    public boolean swLayer1                     = false;
    
    /** switch przełączający pierwszą część warstwy (środkowa warstwa budynków) na drugą */
    public boolean swLayer2                     = false;
    
    /** switch przełączający pierwszą część warstwy (ostatnia warstwa budynków) na drugą */
    public boolean swLayer3                     = false;        
       
    /** flaga oznaczająca możliwość wykonania ślizgu */
    private boolean canSlide                    = false;
    
    /** flaga oznaczająca kontakt bohatera z podłożem */
    private boolean grounded                    = true;
    
    /** flaga oznaczająca kontakt bohatera z podłożem */
    private final double minJumpHeight          = 354.0;
    
    /** maksymalna wysokość podczas wykonywania skoku */
    private final double maxJumpHeight          = 260.0;
            
    /** aktualna wysokość skoku */
    private double jumpHeight                   = 354.0;
    
    /** nickname wyświetlany podczas uruchomienia aplikacji */
    public String nickname                      = "player1";   
    
    /** tryb wyliczeniowy służący do opisu aktualnego stanu bohatera */
    public enum Animation {
	run, jump, fall, slide;
    }
    
    /** steruje możliwością modyfikacji parametrów bohatera w zależności od przyjętego stanu */
    public Animation animator                   = Animation.run;
    
    /** tryb wyliczeniowy służący do opisu aktualnego stanu bohatera */
    public static GameStage currentStage;
        
    /** pierwsza część pierwszej warstwy poruszających się budynków - aktywna na start */
    private final JLabel buildings1Layer1;
    
    /** druga część pierwszej warstwy poruszających się budynków - nieaktywna na start */
    private final JLabel buildings1Layer2;
    
    /** pierwsza część drugiej warstwy poruszających się budynków - aktywna na start */
    private final JLabel buildings2Layer1;
    
    /** druga część drugiej warstwy poruszających się budynków - nieaktywna na start */
    private final JLabel buildings2Layer2;
    
    /** pierwsza część trzeciej warstwy poruszających się budynków - aktywna na start */
    private final JLabel buildings3Layer1;
    
    /** drugiej część trzeciej warstwy poruszających się budynków - nieaktywna na start */
    private final JLabel buildings3Layer2;
    
    /** pierwsza część warstwy poruszającej się wody - aktywna na start */
    private final JLabel waterDynamicLayer1;
    
    /** druga część warstwy poruszającej się wody - nieaktywna na start */
    private final JLabel waterDynamicLayer2;
    
    /** pierwsza część pierwszej warstwy poruszających się budynków - aktywna na start */
    private final JLabel waterStaticLayer1;
    
    /** pierwsza część pierwszej warstwy poruszających się budynków - aktywna na start */
    private final JLabel waterStaticLayer2;
    
    /** pierwsza część pierwszej warstwy poruszających się budynków - aktywna na start */
    private final JLabel background;
    
    /**
     * Kontruktor wywołuje i ustawia parametry wszystkich poruszających się elementów 
     * Wywołuje obiekt klasy Digit     
     */
    public MainFrame(){
        initComponents();        
        try {    
            characterImg = ImageIO.read(MainFrame.class.getResourceAsStream("../graphics/spriteSheet.png"));
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        currentStage = GameStage.menu;        
        MainFrame.currentStage.changeFlag(true);
        
        // setting font
        setUpFont();

        // creating all jLabel
        buildings1Layer1        = new javax.swing.JLabel();
        buildings1Layer2        = new javax.swing.JLabel();
        buildings2Layer1        = new javax.swing.JLabel();
        buildings2Layer2        = new javax.swing.JLabel();
        buildings3Layer1        = new javax.swing.JLabel();
        buildings3Layer2        = new javax.swing.JLabel();
        waterDynamicLayer1      = new javax.swing.JLabel();
        waterDynamicLayer2      = new javax.swing.JLabel();
        waterStaticLayer1       = new javax.swing.JLabel();
        waterStaticLayer2       = new javax.swing.JLabel();
        background              = new javax.swing.JLabel();

        // setting jLabels' icons
        buildings1Layer1.setIcon(new ImageIcon(getClass().getResource("../graphics/buildings1.png")));
        buildings1Layer2.setIcon(new ImageIcon(getClass().getResource("../graphics/buildings1.png")));
        buildings2Layer1.setIcon(new ImageIcon(getClass().getResource("../graphics/buildings2.png")));
        buildings2Layer2.setIcon(new ImageIcon(getClass().getResource("../graphics/buildings2.png")));
        buildings3Layer1.setIcon(new ImageIcon(getClass().getResource("../graphics/buildings3.png")));
        buildings3Layer2.setIcon(new ImageIcon(getClass().getResource("../graphics/buildings3.png")));
        waterDynamicLayer1.setIcon(new ImageIcon(getClass().getResource("../graphics/waterDynamic.png")));
        waterDynamicLayer2.setIcon(new ImageIcon(getClass().getResource("../graphics/waterDynamic.png")));
        waterStaticLayer1.setIcon(new ImageIcon(getClass().getResource("../graphics/waterStatic2.png")));
        waterStaticLayer2.setIcon(new ImageIcon(getClass().getResource("../graphics/waterStatic1.png")));
        background.setIcon(new ImageIcon(getClass().getResource("../graphics/bg2.png")));
        
        // add jLabels to jPanel
        jPanel1.add(waterStaticLayer1, new AbsoluteConstraints(0, 330, 640, 100));        
        jPanel1.add(waterDynamicLayer1, new AbsoluteConstraints(640, 0, 640, 100));        
        jPanel1.add(waterDynamicLayer2, new AbsoluteConstraints(640, 0, 640, 100));        
        jPanel1.add(waterStaticLayer2, new AbsoluteConstraints(0, 330, 640, 100));        
        jPanel1.add(buildings1Layer1, new AbsoluteConstraints(640, 0, 640, 330));
        jPanel1.add(buildings1Layer2, new AbsoluteConstraints(640, 0, 640, 330));
        jPanel1.add(buildings2Layer1, new AbsoluteConstraints(640, 0, 640, 330));
        jPanel1.add(buildings2Layer2, new AbsoluteConstraints(640, 0, 640, 330));
        jPanel1.add(buildings3Layer1, new AbsoluteConstraints(640, 0, 640, 330));        
        jPanel1.add(buildings3Layer2, new AbsoluteConstraints(640, 0, 640, 330));                                
        jPanel1.add(background, new AbsoluteConstraints(0, 0, 640, 480));        
                
        animation();        
        
        // creating digit
        try{            
            digit = new Digits(jPanel1, codeLabel, pointsLabel, character, nickname);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
        
    /**
     * Metoda, która jest odświeżana z częstotliwością 1/GAME_SPEED
     * Odpowiada za koordynację i sterowania poruszającymi się warstwami
     */
    public void animation(){
        Thread t;
        t = new Thread(){                
            @Override
            public void run(){
                while(true){
                    
                    try{
                        sleep(GAME_SPEED);                       
                    } catch(InterruptedException e){}
                    
                    gameTime++;                        
                        
                    //--- ANIMATOR -------------------------------                                                     
                    if(gameTime/repeaterCounterChar == CHARACTER_REFRESH_TIME){
                        if(animator == Animation.run){
                            if(charXPos >= 48 && charYPos >= characterImg.getHeight() - 76){
                                charXPos = 0;
                                charYPos = 0;
                            }
                            else if(charXPos < characterImg.getWidth() - 48){
                                charXPos += 48;
                            }                            
                            else{
                                charXPos = 0;
                                charYPos += 76;
                            }                                
                        }
                        else if(animator == Animation.slide){                                    
                            charYPos = characterImg.getHeight() - 76;

                            if(!canSlide){                                                                    
                                charXPos = 97;
                                canSlide = true;
                            }
                            else{
                                charXPos = 145;
                                canSlide = false;
                            }
                        }
                        else if(animator == Animation.jump){
                            charXPos = 48;
                            charYPos = 152;
                        }

                        repeaterCounterChar++;
                    }

                    //------------------------------------------------------
                    //------ JUMP
                    //------------------------------------------------------
                    if(gameTime/repeaterCounterJump == JUMP_REFRESH_TIME){
                        repeaterCounterJump++;

                        if(animator == Animation.jump){
                            // jumping frame 
                            charXPos = 48;
                            charYPos = 152;

                            if(character.getLocation().y > maxJumpHeight){          // is grounded?
                                jumpHeight -= gravity;
                                gravity -= 0.012;                    
                                character.setLocation(character.getLocation().x, (int)jumpHeight);
                            }                                
                            else if(character.getLocation().y <= maxJumpHeight){    // is jumping?
                                animator = Animation.fall;                                    
                                jumpHeight = maxJumpHeight;
                                gravity = 0.0;
                            }
                        }
                        else if(animator == Animation.fall){                                
                            if(character.getLocation().y < minJumpHeight){          // is falling?
                                jumpHeight += gravity;
                                gravity += 0.012;                                
                                character.setLocation(character.getLocation().x, (int)jumpHeight);
                            }                                                                
                            else if(character.getLocation().y >= minJumpHeight){    // is grounded?
                                animator = Animation.run;
                                character.setLocation(character.getLocation().x, (int)minJumpHeight);
                                jumpHeight = 354.0;
                                gravity = 1.5;
                                grounded = true;
                            }
                        }
                    }

                    //------------------------------------------------------
                    //------ LAYER 1 BG + CHECK STAGE
                    //------------------------------------------------------                        
                    if(gameTime/repeaterCounterLayer1 == LAYER1_REFRESH_TIME){
                        repeaterCounterLayer1++;
                        currLayer1Pos++;

                        //--------------------------------------------------
                        //-- cykliczne sprawdzanie czy można zmienić 
                        //-- widoczność elementów interfejsu graficznego
                        if(currentStage.canChange){                                
                            checkStage();                                
                        }

                        if(currLayer1Pos <= LAYERS_START_X){
                            if(!swLayer1){
                                buildings1Layer1.setLocation(-currLayer1Pos, 0);
                                buildings1Layer2.setLocation(-currLayer1Pos + 640, 0);
                                waterDynamicLayer1.setLocation(-currLayer1Pos, 330);
                                waterDynamicLayer2.setLocation(-currLayer1Pos + 640, 330); 
                            }
                            else{
                                buildings1Layer1.setLocation(-currLayer1Pos + 640, 0);
                                buildings1Layer2.setLocation(-currLayer1Pos, 0);
                                waterDynamicLayer1.setLocation(-currLayer1Pos + 640, 330);
                                waterDynamicLayer2.setLocation(-currLayer1Pos, 330);                           
                            }
                        }
                        else{ // swaping currently moving layers
                            currLayer1Pos = 0;
                            swLayer1 = !swLayer1;
                        }
                    }

                    //------------------------------------------------------
                    //------ LAYER 2 BG
                    //------------------------------------------------------
                    if(gameTime/repeaterCounterLayer2 == LAYER2_REFRESH_TIME){
                        repeaterCounterLayer2++;
                        currLayer2Pos++;

                        if(currLayer2Pos <= LAYERS_START_X){
                            if(!swLayer2){
                                buildings2Layer1.setLocation(-currLayer2Pos, 0);
                                buildings2Layer2.setLocation(-currLayer2Pos + 640, 0);                                     
                            }
                            else {
                                buildings2Layer1.setLocation(-currLayer2Pos + 640, 0);
                                buildings2Layer2.setLocation(-currLayer2Pos, 0);                           
                            }
                        }
                        else{ // swaping currently moving layer
                            currLayer2Pos = 0;                            
                            swLayer2 = !swLayer2;
                        }
                    }

                    //------------------------------------------------------
                    //------ LAYER 3 BG
                    //------------------------------------------------------
                    if(gameTime/repeaterCounterLayer3 == LAYER3_REFRESH_TIME){
                        repeaterCounterLayer3++;
                        currLayer3Pos++;


                        if(currLayer3Pos <= LAYERS_START_X){
                            if(!swLayer3){
                                buildings3Layer1.setLocation(-currLayer3Pos, 0);
                                buildings3Layer2.setLocation(-currLayer3Pos + 640, 0); 
                            }
                            else {
                                buildings3Layer1.setLocation(-currLayer3Pos + 640, 0);
                                buildings3Layer2.setLocation(-currLayer3Pos, 0);                           
                            }
                        }
                        else{ // swaping currently moving layer
                            currLayer3Pos = 0;                            
                            swLayer3 = !swLayer3;
                        }
                    }

                    // setting next frame of character movement animation
                    character.setIcon(new ImageIcon(characterImg.getSubimage(charXPos, charYPos, 44, 76)));
                }
            }
        };
        t.start();
    }
        
    /**
    * Metoda ustawia czcionkę dla wszystkich JObiektów
    */        
    private void setUpFont(){
        try {
            URL url = getClass().getResource("../graphics/kongtext.ttf");            
            Font retroFont = Font.createFont(Font.TRUETYPE_FONT, new File(url.getPath())).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(retroFont);
            startButton.setFont(retroFont);
            nickButton.setFont(retroFont);
            highScoreButton.setFont(retroFont);
            exitButton.setFont(retroFont);
            easyButton.setFont(retroFont);
            normalButton.setFont(retroFont);
            hardButton.setFont(retroFont);
            backButton.setFont(retroFont);
            pointsLabel.setFont(retroFont);            
            codeLabel.setFont(retroFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
    }    
    
    /**
    * ustawianie widoczność dla przycisków na różnych etapach gry
    */
    public void checkStage(){             
        if(currentStage.equals(GameStage.menu)){
            startButton.setVisible(true);
            nickButton.setVisible(true);
            highScoreButton.setVisible(true);
            exitButton.setVisible(true);
            easyButton.setVisible(false);
            normalButton.setVisible(false);
            hardButton.setVisible(false);
            backButton.setVisible(false);
            menuBackground.setVisible(true);
            pointsLabel.setVisible(false);
            codeLabel.setVisible(false);
        }else if(currentStage.equals(GameStage.stage1) || currentStage.equals(GameStage.stage2)){
            startButton.setVisible(false);
            nickButton.setVisible(false);
            highScoreButton.setVisible(false);
            exitButton.setVisible(false);
            easyButton.setVisible(false);
            normalButton.setVisible(false);
            hardButton.setVisible(false);
            backButton.setVisible(false);
            menuBackground.setVisible(false);
                    
            if(currentStage.equals(GameStage.stage1)){
                pointsLabel.setVisible(true);
                codeLabel.setVisible(true);
            }
            else if (currentStage.equals(GameStage.stage2)){
                pointsLabel.setVisible(false);
                codeLabel.setVisible(false);
            }
        }
        
        currentStage.canChange = false;
    }
       
    //--------------------------------------------------------------------------
    //--- Wygenerowany kod
    //--------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        nickButton = new javax.swing.JButton();
        highScoreButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        easyButton = new javax.swing.JButton();
        normalButton = new javax.swing.JButton();
        hardButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        menuBackground = new javax.swing.JLabel();
        viniete = new javax.swing.JLabel();
        character = new javax.swing.JLabel();
        gif = new javax.swing.JLabel();
        pointsLabel = new javax.swing.JTextField();
        codeLabel = new javax.swing.JTextField();
        ground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(640, 480));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        startButton.setForeground(new java.awt.Color(245, 245, 245));
        startButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        startButton.setText("start");
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        startButton.setFocusable(false);
        startButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startButton.setMaximumSize(new java.awt.Dimension(150, 30));
        startButton.setMinimumSize(new java.awt.Dimension(150, 30));
        startButton.setPreferredSize(new java.awt.Dimension(150, 30));
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButtonMouseExited(evt);
            }
        });
        jPanel1.add(startButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        nickButton.setForeground(new java.awt.Color(245, 245, 245));
        nickButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        nickButton.setText("player1");
        nickButton.setBorderPainted(false);
        nickButton.setContentAreaFilled(false);
        nickButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nickButton.setFocusable(false);
        nickButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nickButton.setMaximumSize(new java.awt.Dimension(150, 30));
        nickButton.setMinimumSize(new java.awt.Dimension(150, 30));
        nickButton.setPreferredSize(new java.awt.Dimension(150, 30));
        nickButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nickButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nickButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nickButtonMouseExited(evt);
            }
        });
        jPanel1.add(nickButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        highScoreButton.setForeground(new java.awt.Color(245, 245, 245));
        highScoreButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        highScoreButton.setText("scores");
        highScoreButton.setBorderPainted(false);
        highScoreButton.setContentAreaFilled(false);
        highScoreButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        highScoreButton.setFocusable(false);
        highScoreButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        highScoreButton.setMaximumSize(new java.awt.Dimension(150, 30));
        highScoreButton.setMinimumSize(new java.awt.Dimension(150, 30));
        highScoreButton.setPreferredSize(new java.awt.Dimension(150, 30));
        highScoreButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                highScoreButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                highScoreButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                highScoreButtonMouseExited(evt);
            }
        });
        jPanel1.add(highScoreButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        exitButton.setForeground(new java.awt.Color(245, 245, 245));
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        exitButton.setText("exit");
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.setFocusable(false);
        exitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exitButton.setMaximumSize(new java.awt.Dimension(150, 30));
        exitButton.setMinimumSize(new java.awt.Dimension(150, 30));
        exitButton.setPreferredSize(new java.awt.Dimension(150, 30));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButtonMouseExited(evt);
            }
        });
        jPanel1.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, -1, -1));

        easyButton.setForeground(new java.awt.Color(245, 245, 245));
        easyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        easyButton.setText("easy");
        easyButton.setBorderPainted(false);
        easyButton.setContentAreaFilled(false);
        easyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        easyButton.setFocusable(false);
        easyButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        easyButton.setMaximumSize(new java.awt.Dimension(150, 30));
        easyButton.setMinimumSize(new java.awt.Dimension(150, 30));
        easyButton.setPreferredSize(new java.awt.Dimension(150, 30));
        easyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                easyButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                easyButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                easyButtonMouseExited(evt);
            }
        });
        jPanel1.add(easyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        normalButton.setForeground(new java.awt.Color(245, 245, 245));
        normalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        normalButton.setText("normal");
        normalButton.setBorderPainted(false);
        normalButton.setContentAreaFilled(false);
        normalButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        normalButton.setFocusable(false);
        normalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        normalButton.setMaximumSize(new java.awt.Dimension(150, 30));
        normalButton.setMinimumSize(new java.awt.Dimension(150, 30));
        normalButton.setPreferredSize(new java.awt.Dimension(150, 30));
        normalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                normalButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                normalButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                normalButtonMouseExited(evt);
            }
        });
        jPanel1.add(normalButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        hardButton.setForeground(new java.awt.Color(245, 245, 245));
        hardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        hardButton.setText("hard");
        hardButton.setBorderPainted(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hardButton.setFocusable(false);
        hardButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hardButton.setMaximumSize(new java.awt.Dimension(150, 30));
        hardButton.setMinimumSize(new java.awt.Dimension(150, 30));
        hardButton.setPreferredSize(new java.awt.Dimension(150, 30));
        hardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hardButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hardButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hardButtonMouseExited(evt);
            }
        });
        jPanel1.add(hardButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        backButton.setForeground(new java.awt.Color(245, 245, 245));
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        backButton.setText("back");
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.setFocusable(false);
        backButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backButton.setMaximumSize(new java.awt.Dimension(150, 30));
        backButton.setMinimumSize(new java.awt.Dimension(150, 30));
        backButton.setPreferredSize(new java.awt.Dimension(150, 30));
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButtonMouseExited(evt);
            }
        });
        jPanel1.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, -1, -1));

        menuBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/CRR_logo.png"))); // NOI18N
        jPanel1.add(menuBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 480));

        viniete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/viniet.png"))); // NOI18N
        jPanel1.add(viniete, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        character.setPreferredSize(new java.awt.Dimension(44, 76));
        jPanel1.add(character, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, -1, -1));

        gif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/vhs.gif"))); // NOI18N
        jPanel1.add(gif, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pointsLabel.setBackground(new java.awt.Color(0, 0, 0));
        pointsLabel.setForeground(new java.awt.Color(245, 245, 245));
        pointsLabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pointsLabel.setBorder(null);
        pointsLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pointsLabel.setFocusable(false);
        pointsLabel.setOpaque(false);
        jPanel1.add(pointsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 430, 283, 50));

        codeLabel.setBackground(new java.awt.Color(0, 0, 0));
        codeLabel.setForeground(new java.awt.Color(245, 245, 245));
        codeLabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codeLabel.setBorder(null);
        codeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        codeLabel.setFocusable(false);
        codeLabel.setOpaque(false);
        jPanel1.add(codeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 283, 50));

        ground.setBackground(new java.awt.Color(0, 0, 0));
        ground.setOpaque(true);
        jPanel1.add(ground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 640, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    //--------------------------------------------------------------------------
    //--- Przyciski
    //--------------------------------------------------------------------------
    
    /**
     * Ustawia widoczność przycisków po wściśnieciu przycisku
     * @param evt nie używany
     */
    private void startButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseClicked
        startButton.setVisible(false);
        nickButton.setVisible(false);
        highScoreButton.setVisible(false);
        exitButton.setVisible(false);
        easyButton.setVisible(true);
        normalButton.setVisible(true);
        hardButton.setVisible(true);
        backButton.setVisible(true);
    }//GEN-LAST:event_startButtonMouseClicked

    /**
     * Najechanie na przycisk wywoła metodę do zmiany parametrów przycisku
     * @param evt 
     */
    private void startButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseEntered
        onMouseEnter(startButton);
    }//GEN-LAST:event_startButtonMouseEntered

    /**
     * Obsługa wyjścia myszy z pola przycisku
     * @param evt nie używany
     */
    private void startButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseExited
        onMouseExit(startButton);
    }//GEN-LAST:event_startButtonMouseExited

    /**
     * Kliknięcie przycisku nickButton powoduje wywołanie okna zmiany pseudonimu
     * @param evt nie używany
     */
    private void nickButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nickButtonMouseClicked
        // creating internal frame - typing nickname                
        new Nickname(nickname, nickButton);        
    }//GEN-LAST:event_nickButtonMouseClicked

    /**
     * Najechanie na przycisk wywoła metodę do zmiany parametrów przycisku
     * @param evt nie używany
     */
    private void nickButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nickButtonMouseEntered
        onMouseEnter(nickButton);
    }//GEN-LAST:event_nickButtonMouseEntered

    /**
     * Obsługa wyjścia myszy z pola przycisku
     * @param evt nie używany
     */
    private void nickButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nickButtonMouseExited
        onMouseExit(nickButton);
    }//GEN-LAST:event_nickButtonMouseExited

    /**
     * Kliknięcie przycisku otworzy dodatkowe okno wyświetlające listę 20-stu najlepszych wyników
     * @param evt nie używany
     */
    private void highScoreButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highScoreButtonMouseClicked
        // creating internal frame - displaying best 20 scores
        try {
            new ScoreRead();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_highScoreButtonMouseClicked
    
    /**
     * Najechanie na przycisk wywoła metodę do zmiany parametrów przycisku
     * @param evt nie używany
     */    
    private void highScoreButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highScoreButtonMouseEntered
        onMouseEnter(highScoreButton);
    }//GEN-LAST:event_highScoreButtonMouseEntered

    /**
     * Obsługa wyjścia myszy z pola przycisku
     * @param evt nie używany
     */
    private void highScoreButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highScoreButtonMouseExited
        onMouseExit(highScoreButton);
    }//GEN-LAST:event_highScoreButtonMouseExited

    /**
     * Kliknięcie przycisku spowoduje wyjście z aplikacji
     * @param evt nie używany
     */
    private void exitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitButtonMouseClicked

    /**
     * Najechanie na przycisk wywoła metodę do zmiany parametrów przycisku
     * @param evt nie używany
     */
    private void exitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseEntered
        onMouseEnter(exitButton);
    }//GEN-LAST:event_exitButtonMouseEntered

    /**
     * Obsługa wyjścia myszy z pola przycisku
     * @param evt nie używany
     */
    private void exitButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseExited
        onMouseExit(exitButton);
    }//GEN-LAST:event_exitButtonMouseExited
    
    //--------------------------------------------------------------------------
    //--- Wybór poziomu
    //--------------------------------------------------------------------------
    
    /**
     * Aktualizacja stanu aplikacji oraz ustawienie poziomu trudności na łatwy
     * @param evt nie używany
     */
    private void easyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_easyButtonMouseClicked
        currentStage = GameStage.stage1;        
        currentStage.changeFlag(true);        
        digit.setVelocity(2);
    }//GEN-LAST:event_easyButtonMouseClicked

    /**
     * Najechanie na przycisk wywoła metodę do zmiany parametrów przycisku
     * @param evt nie używany
     */
    private void easyButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_easyButtonMouseEntered
        onMouseEnter(easyButton);
    }//GEN-LAST:event_easyButtonMouseEntered

    /**
     * Obsługa wyjścia myszy z pola przycisku
     * @param evt nie używany
     */
    private void easyButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_easyButtonMouseExited
        onMouseExit(easyButton);
    }//GEN-LAST:event_easyButtonMouseExited

    /**
     * Aktualizacja stanu aplikacji oraz ustawienie poziomu trudności na średni
     * @param evt nie używany
     */
    private void normalButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_normalButtonMouseClicked
        currentStage = GameStage.stage1;        
        currentStage.changeFlag(true);
        digit.setVelocity(3);
    }//GEN-LAST:event_normalButtonMouseClicked

    /**
     * Najechanie na przycisk wywoła metodę do zmiany parametrów przycisku
     * @param evt nie używany
     */
    private void normalButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_normalButtonMouseEntered
        onMouseEnter(normalButton);
    }//GEN-LAST:event_normalButtonMouseEntered

    /**
     * Obsługa wyjścia myszy z pola przycisku
     * @param evt nie używany
     */
    private void normalButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_normalButtonMouseExited
        onMouseExit(normalButton);
    }//GEN-LAST:event_normalButtonMouseExited

    /**
     * Aktualizacja stanu aplikacji oraz ustawienie poziomu trudności na trudny
     * @param evt nie używany
     */
    private void hardButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hardButtonMouseClicked
        currentStage = GameStage.stage1;
        currentStage.changeFlag(true);
        digit.setVelocity(4);
    }//GEN-LAST:event_hardButtonMouseClicked

    /**
     * Najechanie na przycisk wywoła metodę do zmiany parametrów przycisku
     * @param evt nie używany
     */
    private void hardButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hardButtonMouseEntered
        onMouseEnter(hardButton);
    }//GEN-LAST:event_hardButtonMouseEntered

    /**
     * Obsługa wyjścia myszy z pola przycisku
     * @param evt nie używany
     */
    private void hardButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hardButtonMouseExited
        onMouseExit(hardButton);
    }//GEN-LAST:event_hardButtonMouseExited

    /**
     * Kliknięcie przycisku back spowoduje przeniesienie do głównego menu gry
     * @param evt nie używany
     */
    private void backButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseClicked
        startButton.setVisible(true);
        nickButton.setVisible(true);
        highScoreButton.setVisible(true);
        exitButton.setVisible(true);
        easyButton.setVisible(false);
        normalButton.setVisible(false);
        hardButton.setVisible(false);
        backButton.setVisible(false);
    }//GEN-LAST:event_backButtonMouseClicked

    /**
     * Najechanie na przycisk wywoła metodę do zmiany parametrów przycisku
     * @param evt nie używany
     */
    private void backButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseEntered
        onMouseEnter(backButton);
    }//GEN-LAST:event_backButtonMouseEntered

    /**
     * Obsługa wyjścia myszy z pola przycisku
     * @param evt nie używany
     */
    private void backButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseExited
        onMouseExit(backButton);
    }//GEN-LAST:event_backButtonMouseExited

    //--------------------------------------------------------------------------
    //--- Klawisz na formie
    //--------------------------------------------------------------------------
    
    /**
     * Obsługa klikniętych klawiszy ze sprawdzaniem warunków danego trybu rozgrywki
     * @param evt zbiera informacje o stanach klawiszy
     */
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int x = evt.getKeyCode();
                
        if(x == KeyEvent.VK_Q){
            System.exit(0);
        }
        
        if(currentStage.equals(GameStage.stage1) || currentStage.equals(GameStage.stage2)){
            
            // back to menu from current game stage
            if(x == KeyEvent.VK_ESCAPE){
                codeLabel.setText("");
                digit.clear();
                currentStage = GameStage.menu;
                checkStage();                
                digit.velocityX = 0;
                digit.points = 0;
            }
                        
            if(animator == Animation.run){                
                // if up arrow (jumping) key is pressed 
                if(x == KeyEvent.VK_UP){                    
                    animator = Animation.jump;
                    grounded = false;
                    new PlaySound("jump");
                }
                // if down arrow (crouching) key is pressed 
                else if(x == KeyEvent.VK_DOWN && grounded){                    
                    animator = Animation.slide;
                    character.setLocation(character.getLocation().x, 374);
                }
            }
        }
    }//GEN-LAST:event_formKeyPressed

    /**
     * Obsługa puszczonego klawisza
     * @param evt zbiera informacje o stanach klawiszy
     */
    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        int x = evt.getKeyCode();
    
        if(animator != Animation.run){
            
            // back from crouching 
            if(x == KeyEvent.VK_DOWN && grounded){
                animator = Animation.run;
                character.setLocation(character.getLocation().x, 354);
            }
        }
    }//GEN-LAST:event_formKeyReleased
    
    //--------------------------------------------------------------------------
    //--- Tekstury przycisków
    //--------------------------------------------------------------------------
    
    /**
     * Metoda zmieniająca parametry przycisku po najechaniu myszką
     * @param btn przesłany JButton, którego opcje będą zmieniać
     * 
     */
    private void onMouseEnter(JButton btn){
        btn.setForeground(new java.awt.Color(255, 143, 159));
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("../graphics/btn2.png")));
    }

    /**
     * Metoda zmieniająca parametry przycisku po wyjściu myszy z jego obszaru
     * @param btn przesłany JButton, którego opcje będą zmieniać
     */
    private void onMouseExit(JButton btn){
        btn.setForeground(new java.awt.Color(245, 245, 245));
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("../graphics/btn.png")));
    }
    
    /**
     * Główna metoda aplikacji
     * odpowiada za wywołanie początkowe okna
     * @param args parametry podane w tablicy są wywoływane z metodą main     
     */
    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {            
            public void run() {                                                
                new MainFrame().setVisible(true);                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton backButton;
    public javax.swing.JLabel character;
    public javax.swing.JTextField codeLabel;
    public javax.swing.JButton easyButton;
    public javax.swing.JButton exitButton;
    private javax.swing.JLabel gif;
    private javax.swing.JLabel ground;
    public javax.swing.JButton hardButton;
    public javax.swing.JButton highScoreButton;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JLabel menuBackground;
    public javax.swing.JButton nickButton;
    public javax.swing.JButton normalButton;
    public javax.swing.JTextField pointsLabel;
    public javax.swing.JButton startButton;
    private javax.swing.JLabel viniete;
    // End of variables declaration//GEN-END:variables
}