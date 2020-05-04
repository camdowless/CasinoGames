package game;
import java.util.ArrayList;
class BlackJack {

    private Deck deck = new Deck();
    private int playerTotal = 0;
    private int dealerTotal = 0;
    private ArrayList<Card> playerCards = new ArrayList<>();
    private ArrayList<Card> dealerCards = new ArrayList<>();


    private Card dealerSecondCard;

    boolean over21(){
        return playerTotal > 21;
    }

    boolean isDealerTurn(){
        return dealerTotal < 17;
    }

    void newGame(){
        playerTotal = 0;
        dealerTotal = 0;
        playerCards.clear();
        dealerCards.clear();
        start();
    }

    Card hit(){
        Card newCard = deck.draw();
        playerTotal += newCard.getValue();
        System.out.printf("New total: %d\n", playerTotal);
        return newCard;
    }

    boolean dealerBust(){
        return dealerTotal > 21;
    }

    Card dealerHit(){
        Card newDealerCard = deck.draw();
        dealerTotal += newDealerCard.getValue();
        System.out.println("New dealer Total: " + dealerTotal);
        return newDealerCard;
    }

    int compareDealerAndPlayer(){
        if(dealerTotal > playerTotal){
            return 0;
        } else if (playerTotal > dealerTotal){
            return 1;
        } else {
            return 2;
        }
    }


    void start(){
        playerCards.add(deck.draw());
        playerCards.add(deck.draw());
        playerTotal = playerCards.get(0).getValue() + playerCards.get(1).getValue();

        dealerCards.add(deck.draw());
        dealerCards.add(deck.draw());
        dealerTotal = dealerCards.get(0).getValue() + dealerCards.get(1).getValue();

        dealerSecondCard = dealerCards.get(1);
        System.out.println("Your total: " + playerTotal);
        System.out.println("Dealer total: " + dealerTotal);
    }

    ArrayList<Card> getPlayerCards(){
        return playerCards;
    }

    ArrayList<Card> getDealerCards(){
        return dealerCards;
    }

    boolean blackjack() {
        return playerTotal == 21;
    }

    Card getDealerSecondCard(){
        return dealerSecondCard;
    }


    //methods only used for testing:::

    int getPlayerTotal(){
        return playerTotal;
    }

    void setPlayerCards(ArrayList<Card> playerCards){
        this.playerCards = playerCards;
        this.playerTotal = playerCards.get(0).getValue() + playerCards.get(1).getValue();
    }

    int getDealerTotal(){
        return dealerTotal;
    }

    void setDealerCards(ArrayList<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }


}
