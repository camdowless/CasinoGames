package game;

public class Pot {
    private int total;

    Pot(){
        total = 0;
    }

    public void addToPot(int bet){
        total += bet;
    }

    public void resetPot(){
        total = 0;
    }

    public int getPotTotal(){
        return total;
    }
}
