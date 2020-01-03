package classes;

import java.io.*;

/**
 * Klasa, której zadaniem jest zapisywanie wyników pojedynczej rozgrywki (nickname wraz z uzyskanym wynikiem)
 * @author Sebastian Urwan
 */
public class ScoreSave {
    
    /** plik z wynikami graczy */
    public File scoreFile = new File("data.txt");    
    
    /** wynik z ostatniej rozgrywki, który będzie zapisany do pliku */
    private int scoreToSave;    
    
    /**
     * Zapis danych na końcu pliku
     * @param nickname pseudonim przekazany do zapisania
     * @param score wynik przekazany do zapisania
     */
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