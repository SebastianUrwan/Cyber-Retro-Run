package classes;
import java.util.Comparator;

public class SortData implements Comparator<Scores>{
    
    public int compare(Scores a, Scores b){
        return b.score - a.score;
    } 
}