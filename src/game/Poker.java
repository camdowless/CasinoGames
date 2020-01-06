package game;
import java.util.ArrayList;

class Poker {
    private Deck deck = new Deck();
    private Pot pot;
    private ArrayList<Card> flopCards = new ArrayList<>();
    private Card turnCard;
    private Card riverCard;
    private ArrayList<Card> playerCards = new ArrayList<>();
    private ArrayList<Card> opponentCards = new ArrayList<>();
    private PokerHand playerHand;
    private PokerHand oppHand;
    private int playerBet;
    private int opponentBet;
    private boolean didPlayerFold;
    private int opponentRaise;
    private int playerRaise;
    private int playerMoney;
    private int opponentMoney;
    private boolean fold = false;
    private int gamePhase = 0;
    private boolean didPlayerRaise;
    private boolean didCPURaise;
    private String gameOutcome;
    /*
    0 is the start, first bets
    1 is the cards being dealt
    2 is player's choice after deal
    3 is opponent's choice after deal
    4 is the flop
    5 is player's choice after flop
    6 is opponent's choice after flop
    7 is the turn
    8 is player's choice after turn
    9 is opponent's choice after turn
    10 is the river
    11 is player's choice after river
    12 is opponent's choice after river
    13 is comparison
     */

    Poker() {
        this.pot = new Pot();
        playerMoney = 500;
        opponentMoney = 500;
    }

    void play(){
        playerCards.clear();
        opponentCards.clear();
        flopCards.clear();
        turnCard = null;
        riverCard = null;
        didPlayerFold = false;
        fold = false;
        gamePhase = 0;
        Ante();
    }

    private void Ante(){
        gamePhase++;
        System.out.println("Phase: " + gamePhase);
        /*Scanner input = new Scanner(System.in);
        System.out.println("Enter bet");
        playerBet = input.nextInt(); */
    }

    ArrayList<ArrayList<Card>> deal(){
        gamePhase++;
        System.out.println("Phase: " + gamePhase);
        deck.checkLowDeck();
        Card p1 = Deck.draw();
        Card p2 = Deck.draw();
        playerCards.add(p1);
        playerCards.add(p2);
        Card o1 = Deck.draw();
        Card o2 = Deck.draw();
        opponentCards.add(o1);
        opponentCards.add(o2);
        ArrayList<ArrayList<Card>> cards = new ArrayList<>();
        cards.add(playerCards);

        /* System.out.println("p1: " + p1.getImage());
        System.out.println("p2: " + p2.getImage());
        System.out.println("o1: " + o1.getImage());
        System.out.println("o2: " + o2.getImage());
        p1 is cards.get(0).get(0)
        p2 is cards.get(0).get(1)
        o1 is cards.get(1).get(0)
        o2 is cards.get(1).get(1) */

        cards.add(opponentCards);
        return cards;
    }

     boolean playerCheck() throws InterruptedException {
        gamePhase++;
        System.out.println("Phase: " + gamePhase);
        switch(cpuCheckFoldOrRaise()){
            case 0:
                opponentFold();
                break;
            case 1:
                gamePhase++;
                System.out.println("Phase: " + gamePhase);
                return false;
            case 2:
                gamePhase++;
                System.out.println("Phase: " + gamePhase);
                return true;
        }
        return false;
    }

    boolean playerRaise() throws InterruptedException {
        didPlayerRaise = true;
        gamePhase++;
        System.out.println("Phase: " + gamePhase);
        switch(cpuCallFoldOrRaise()){
            case 0:
                opponentFold();
                break;
            case 1:
                gamePhase++;
                System.out.println("Phase: " + gamePhase);
                return false;
            case 2:
                gamePhase++;
                System.out.println("Phase: " + gamePhase);
                opponentRaise = 10;
                return true;
        }
        return false;
    }

    private int cpuCheckFoldOrRaise(){
        cpuWait();
        /*
        0: Fold
        1: Check
        2: Raise
         */
        return 1;
    }
    private int cpuCallFoldOrRaise(){
        cpuWait();
        /*
        0: Fold
        1: Call
        2: Raise
         */
        return 1;
    }

    void cpuWait(){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    ArrayList<Card> flop() {
        gamePhase++;
        System.out.println("Phase: " + gamePhase);
        for(int i = 0; i < 3; i++){
            flopCards.add(Deck.draw());
        }
        return flopCards;
    }

    Card turn() {
        gamePhase++;
        System.out.println("Phase: " + gamePhase);
        turnCard = Deck.draw();
        return turnCard;
    }

    Card river() {
        gamePhase++;
        System.out.println("Phase: " + gamePhase);
        riverCard = Deck.draw();
        return riverCard;
    }

    void compareHands(){
        playerHand = new PokerHand(playerCards, flopCards, turnCard, riverCard);
        oppHand = new PokerHand(opponentCards, flopCards,turnCard,riverCard);
        System.out.printf("Player hand: %s    Score: %d\n", playerHand.getHandType(), playerHand.getHandScore());
        System.out.printf("Opponent hand: %s    Score: %d\n", oppHand.getHandType(), oppHand.getHandScore());
        if(playerHand.getHandScore() > oppHand.getHandScore()){
            playerWin();
        } else if(playerHand.getHandScore() < oppHand.getHandScore()){
            opponentWin();
        } else {
            //Player hand score == Opponent hand score, so we can use playerHand for the switch
            int playerHighCard = playerHand.getHighCardValue();
            int oppHighCard = oppHand.getHighCardValue();
            if(playerHighCard > oppHighCard){
                playerWin();
            } else if(playerHighCard < oppHighCard){
                opponentWin();
            } else {
                //TODO: If highcards are equal, get second high card, if they are equal, split the pot
                System.out.println("TIE");
                playerWin();
            }
        }
    }

    void playerFold(){
        didPlayerFold = true;
        gameOutcome = "\nPlayer folds\n";
        opponentWin();
    }

    private void opponentFold(){
        fold = true;
        gameOutcome = "\nOpponent folds\n";
        playerWin();
    }

    private void playerWin(){
        if(!fold) {
            gameOutcome = String.format("\nPlayer wins with a %s\n", playerHand.getHandType());
        }
        playerMoney += getPotTotal();
        pot.resetPot();
    }

    private void opponentWin(){
        if(!didPlayerFold){
            gameOutcome = String.format("\nOpponent wins with a %s\n", oppHand.getHandType());
        }
        opponentMoney += getPotTotal();
        pot.resetPot();
    }

    boolean didPlayerRaise(){
        return didPlayerRaise;
    }

    void setDidPlayerRaise(boolean b){
        didPlayerRaise = b;
    }

    int getPhase(){
        return gamePhase;
    }

    int getPlayerMoney(){
        return playerMoney;
    }

    int getOpponentMoney(){
        return opponentMoney;
    }

    ArrayList<Card> getOpponentCards(){
        return opponentCards;
    }

    int getPlayerBet(){
        return playerBet;
    }

    void setPlayerBet(int bet){
        playerBet = bet;
        opponentBet = playerBet; // change this later
        playerMoney -= bet;
        opponentMoney -= bet;
        pot.addToPot(opponentBet + playerBet);
    }

    String getGameOutcome(){
        return gameOutcome;
    }

    int getOpponentBet(){
        return opponentBet;
    }

    int getPotTotal(){
        return pot.getPotTotal();
    }
}