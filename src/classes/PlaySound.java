package classes;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PlaySound {
    InputStream input;        
    ClassLoader resource = this.getClass().getClassLoader();
    URL path;
    
    public PlaySound(String name){
        try{ 
            path = this.getClass().getResource("../graphics/" + name + ".wav");
            input = new FileInputStream(path.getFile());
            AudioStream audio = new AudioStream(input);
            AudioPlayer.player.start(audio);

        }catch(Exception e){
            e.printStackTrace();
        }    
    }
}