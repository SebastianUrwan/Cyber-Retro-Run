package classes;

import java.io.*;
import java.net.URL;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Klasa, której zadaniem jest odtworzenie dźwięków w grze
 * @author Sebastian Urwan
 */
public class PlaySound {
    
    /** plik dźwiękowy */
    InputStream input;        
    
    /** ścieżka do pliku dźwiękowego */
    URL path;
    
    /**
     * Metoda, która odtwarza dźwięki skoku bohatera oraz zebranej cyfry
     * @param name parametr przesyłający nazwę odtwarzanego dźwięku
     */
    public PlaySound(String name){
        try{ 
            path = this.getClass().getResource("../graphics/" + name + ".wav");
            input = new FileInputStream(path.getFile());
            AudioStream audio = new AudioStream(input);
            AudioPlayer.player.start(audio);
        }catch(IOException e){}    
    }
}