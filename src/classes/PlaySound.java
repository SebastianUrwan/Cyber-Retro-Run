package classes;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * Klasa, której zadaniem jest odtworzenie dźwięków w grze
 * @author Sebastian Urwan
 */
public class PlaySound {
    
    private final int BUFFER_SIZE = 128000;
    
    /**
     * Metoda, która odtwarza dźwięki skoku bohatera oraz zebranej cyfry
     * @param name parametr przesyłający nazwę odtwarzanego dźwięku
     */
    public PlaySound(String name){
        URL path = this.getClass().getResource("graphics/" + name + ".wav");

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(path);
            AudioFormat audioFormat = audioStream.getFormat();
        
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);

            sourceLine.start();

            int nBytesRead = 0;
            byte[] abData = new byte[BUFFER_SIZE];
            while (nBytesRead != -1) {
                try {
                    nBytesRead = audioStream.read(abData, 0, abData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nBytesRead >= 0) {
                    int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
                }
            }

            sourceLine.drain();
            sourceLine.close();            
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
