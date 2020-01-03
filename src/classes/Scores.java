package classes;

/**
 * Klasa która tworzy pojedynczy połączenie pseudonimu i wyniku
 * @author Sebastian Urwan
 */
public class Scores{
   
    /** pole przechowujące nazwę pseudonimu */
    public String nickname;
    
    /** pole przechowujące wynik gracza */
    public int score;    
    
    /**
     * Konstuktor ustawia wartości pól przesłanymi parametrami
     * @param nickname pseudonim przekazany do zapisania
     * @param score wynik przekazany do zapisania
     */
    public Scores(String nickname, int score){
        this.nickname = nickname;
        this.score = score;
    }
    
    /**     
     * @return metoda zwraca łańcuch znaków pseudonimu i wyniku
     */
    public String toString() { 
        return this.nickname + " " + Integer.toString(this.score) ;
    }
}
