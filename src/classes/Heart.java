package classes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Heart extends JLabel{

    BufferedImage img = ImageIO.read(MainFrame.class.getResourceAsStream("../graphics/hearts2.png"));
    final public int labelX = 283;
    final public int labelY = 430;
    final public int labelWidth = 74;
    final public int labelHeight = 50;
    
    public Heart() throws IOException{
        super();
        this.setOpaque(false);
        setImg(3);
    }
    
    public void setImg(int life){
        this.setIcon(new ImageIcon(img.getSubimage(0, (life*50) - 50, labelWidth, labelHeight)));
    }
}