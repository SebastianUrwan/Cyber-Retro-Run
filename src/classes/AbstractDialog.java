package classes;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class AbstractDialog extends JDialog{
    
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JButton button = new JButton();
    private JTextField textField = new JTextField();
    
    public AbstractDialog(){
        super();
    }
    
    private void setFont(){
        
    }
    
    private void addLabel(String textToSet){
        
    }
    
    private void addTextField(){
        
    }    
    
    private void addButton(String textToSet){
        
    }
    
    private void buttonMouseClicked(){
        
    }                                     

    private void buttonMouseEntered(){
        
    }                                      

    private void buttonMouseExited(){
        
    }       
}