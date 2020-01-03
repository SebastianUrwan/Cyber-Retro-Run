package classes;
import java.util.Comparator;

/**
 * Klasa, której zadaniem jest porównywanie przesłanych wyników
 * @author Sebastian Urwan
 */
public class SortData implements Comparator<Scores>{
    
    /**     
     * @param a pierwszy wynik
     * @param b drugi wynik
     * @return metoda zwraca wartość porównania obu wartości
     */
    public int compare(Scores a, Scores b){
        return b.score - a.score;
    } 
}