/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 * Klasa odpowiedzialna za sterowanie wartością aktualnego stanu aplikacji
 * @author Sebastian Urwan
 */
public enum GameStage {   
    
    /** menu i podmenu*/
    menu, 
    
    /** etap zręcznościowy - sterowanie postacią */
    stage1,
    
    /** etap logiczny - dekodowanie */
    stage2,
        
    /** stan po przegraniu rozgrywki*/
    over;
    
    /** flaga oznaczająca możliwość zmiany etapu na inny */
    public boolean canChange = false;
    
    /**
     * Ustawia wartość logiczną flagi odpowiedzialnej za aktualizację wyglądu interfejsu w każdym z etapów
     * @param flag jest odebranym parametrem i opisuje wartość pola obiektu klasy GameStage
     */
    public void changeFlag(boolean flag){        
        canChange = flag;
    }      
}