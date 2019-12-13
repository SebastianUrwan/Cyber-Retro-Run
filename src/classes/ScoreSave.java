package classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ScoreSave {
    
    public File scoreFile = new File("data.txt");    
    int scoreToSave;    
    
    void save(String nickname, String score){
        try{
            if(!scoreFile.exists())
                scoreFile.createNewFile();            

            BufferedWriter output = new BufferedWriter(new FileWriter("data.txt", true));

            for(int i = nickname.length(); i < 9; i++){                         // when nickname has less char than 9 - add space
                nickname += " ";
            }

            output.append(nickname + "\n" + score + "\n");                      // add prepared string to the file
            output.close();                            
        }
        catch(Exception e){
            System.out.println("IO error");
        }
    }
}