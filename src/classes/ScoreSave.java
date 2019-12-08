package classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ScoreSave {
    
    public File scoreFile = new File("data.txt");    
    int scoreToSave;    
    
    void save(String nickname, String score){
        try{
            if(scoreFile.exists()){
                BufferedWriter output = new BufferedWriter(new FileWriter("src/retrorun/data.txt", true));
                                
                for(int i = nickname.length(); i < 9; i++){
                    nickname += " ";
                }
                                
                output.append(nickname + "\n" + score + "\n");
                output.close();                
            }
            else{
                scoreFile.createNewFile();
            }            
        }
        catch(Exception e){
            System.out.println("IO error");
        }
    }
}