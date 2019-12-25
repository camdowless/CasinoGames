package game;

import java.util.ArrayList;

class PokerHand {
    ArrayList<Card> cards = new ArrayList<>();
    PokerHand(ArrayList<Card> hand, ArrayList<Card> flop, Card turn, Card river){
        for(Card c : hand){
            cards.add(c);
        }
        for(Card c : flop){
            cards.add(c);
        }
        cards.add(turn);
        cards.add(river);
    }

    
}
