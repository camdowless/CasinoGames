package game;
import java.util.ArrayList;
import java.util.Random;

class Roulette {
    private ArrayList<roulettePocket> wheel = new ArrayList<>();
    private int bet;

    private boolean isAWin;
    private int winnings;
    Roulette(){
        initTable();
    }

    private void initTable(){
        wheel.add(new roulettePocket(-1, "green")); //This is the double-zero pocket, the number gets changed in the roulettePocket constructor
        wheel.add(new roulettePocket(0, "green"));

         /*in ranges 1-10 & 19-28, odd numbers are red and even numbers are black
           after the green, pockets start at index 2
           pocket 00 at index 0
           pocket 0 at index 1
           pocket 1 at index 2
           pocket 2 at index 3
           etc*/
        for(int i = 1; i <= 10; i++){
            if(i % 2 == 0){
                wheel.add(new roulettePocket(i, "black"));
            } else {
                wheel.add(new roulettePocket(i, "red"));
            }
        }
        for(int i = 19; i <= 28; i++){
            if(i % 2 == 0){
                wheel.add(new roulettePocket(i, "black"));
            } else {
                wheel.add(new roulettePocket(i, "red"));
            }
        }

        //in ranges 11-18 & 29-36, odd numbers are black and even numbers are red
        for(int i = 11; i <= 18; i++){
            if(i % 2 == 0){
                wheel.add(new roulettePocket(i, "red"));
            } else {
                wheel.add(new roulettePocket(i, "black"));
            }
        }
        for(int i = 29; i <= 36; i++){
            if(i % 2 == 0){
                wheel.add(new roulettePocket(i, "red"));
            } else {
                wheel.add(new roulettePocket(i, "black"));
            }
        }
    }

    private roulettePocket spin(){
        Random rand = new Random();
        int randomPocketIndex = rand.nextInt(37);
        return wheel.get(randomPocketIndex);
    }

    String play(String playerChoice, int playerBet){
        this.bet = playerBet;
        //playerChoice is a color as a String,
        //"red", "black", or "green"
        //returns amount of winnings, 0 if player loses, playerBet * 2 if player wins
        roulettePocket result = spin();

        if(compare(playerChoice, result)){
            //System.out.println("You win!");
            win(playerChoice);
            return result.getPocketID();
        } else{
            //System.out.println("You lose!");
            lose();
            return result.getPocketID();
        }
    }

    private void win(String playerChoice){
        isAWin = true;
        if(playerChoice.equals("green")){
            winnings = bet * 10;
        } else {
            winnings = bet * 2;
        }
    }

    private void lose(){
        isAWin = false;
        winnings = 0;
    }


    boolean isAWin() {
        return isAWin;
    }

    int getBet(){
        return bet;
    }

    int getWinnings(){
        return winnings;
    }

    boolean compare(String choice, roulettePocket result){
        //System.out.printf("Pocket number: %d\nColor: %s\n", result.getNum(), result.getColor());
        return choice.equals(result.getColor());
    }

    ArrayList<roulettePocket> getWheel(){
        return wheel;
    }
}