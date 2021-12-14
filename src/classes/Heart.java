package classes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Klasa, której zadaniem jest wyświetlanie aktualnej liczby serc na podstawie otrzymego parametru
 * @author Sebastian Urwan
 */
public class Heart extends JLabel{

    /** wczytana grafika z sercami */
    BufferedImage img = ImageIO.read(MainFrame.class.getResourceAsStream("graphics/hearts2.png"));
    
    /** pozycja X lokalizacji etykiety */
    final public int labelX = 283;
    
    /** pozycja Y lokalizacji etykiety */
    final public int labelY = 430;
    
    /** szerokość fragmentu obrazu z sercami */
    final public int labelWidth = 74;
    
    /** wysokość fragmentu obrazu z sercami */
    final public int labelHeight = 50;
    
    /**
     * Konstruktor wywołujący obiekt JLabel i ustawiający jego grafikę 
     * @throws IOException obsługa wyjątku błędnego wczytania grafiki
     */
    public Heart() throws IOException{
        super();
        this.setOpaque(false);
        setImg(3);
    }
    
    /**
     * Metoda ustawia położenie fragmentu grafiki w zależności od aktualnej liczby żyć
     * @param life przesyła aktualną liczbę żyć
     */
    public void setImg(int life){
        this.setIcon(new ImageIcon(img.getSubimage(0, (life*50) - 50, labelWidth, labelHeight)));
    }
}
