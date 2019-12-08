package classes;

public class Scores{
   
    String nickname;
    int score;    
    
    public Scores(String _nickname, int _score){        
        this.nickname = _nickname;
        this.score = _score;
    }
    
    public String toString() { 
        return this.nickname + " " + Integer.toString(this.score) ;
    }
}
