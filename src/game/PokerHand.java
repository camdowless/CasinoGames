package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class PokerHand {
    private ArrayList<Card> cards = new ArrayList<>();
    private int handScore;
    private String handType;
    /*
    10 - Royal Flush
     9 - Straight Flush
     8 - 4 of a kind
     7 - Full house
     6 - Flush
     5 - Straight
     4 - Three of a kind
     3 - Two pair
     2 - Pair
     1 - High card
     */

    PokerHand(ArrayList<Card> hand, ArrayList<Card> flop, Card turn, Card river){
        cards.addAll(hand);
        cards.addAll(flop);
        cards.add(turn);
        cards.add(river);
        calcScore();
    }

    PokerHand(){
        //used for testing
        sortHand();
    }

    private void calcScore(){
        sortHand();
        if(checkRoyalFlush()){
            handScore = 10;
            handType = "Royal Flush";
        }
    }


    boolean checkRoyalFlush(){
        sortHand();
        //TODO: Remove sortHand()
        int index= 0;
        String suit;
        for(Card c : cards){
            if (c.getValue() == 10){
                suit = c.getSuit();
                /*
                    -cards.get(index + 1) is the next card in the sorted hand
                    -checks if it is a jack, value 11
                    -and if the suit is the same as the 10
                 */
                if(cards.get(index + 1).getValue() == 11 & cards.get(index + 1).getSuit().equals(suit)){                //jack
                    if(cards.get(index + 2).getValue() == 12 & cards.get(index + 2).getSuit().equals(suit)){            //queen
                        if(cards.get(index + 3).getValue() == 13 & cards.get(index + 3).getSuit().equals(suit)){        //king
                            if(cards.get(index + 4).getValue() == 14 & cards.get(index + 4).getSuit().equals(suit)){    //ace
                                return true;
                            }
                        }
                    }
                }
            }
            index++;
        }
        return false;
    }

    boolean checkStraightFlush(){
        if(checkStraight() & checkFlush()){
            return true;
        }
        return false;
    }

    boolean checkStraight(){
        sortHand();
        //TODO: Remove sorthand()
        for(int i = 0; i < 3; i++){
            int pairs = 0;
            Card current = cards.get(i);
            /*If anybody reads this, I am sorry its awful
            Only need to loop through the first 3 cards, because a Straight requires 5
            For every 1 of 3 cards, checks if value+1 = the next card's value, or a pair
            Since it is possible to have up to 2 pairs and still have a straight, we check for that at the end
            */
            if(current.getValue() + 1 == cards.get(i + 1).getValue() | isPair(current, cards.get(i + 1))){
                if(isPair(current, cards.get(i + 1))) {
                    pairs++;
                }
                if(cards.get(i + 1).getValue() + 1 == cards.get(i + 2).getValue() | isPair(cards.get(i + 1), cards.get(i + 2))){
                    if(isPair(cards.get(i + 1), cards.get(i + 2))){
                        pairs++;
                    }
                    if(cards.get(i + 2).getValue() + 1 == cards.get(i + 3).getValue() | isPair(cards.get(i + 2), cards.get(i + 3))){
                        if(isPair(cards.get(i + 2), cards.get(i + 3))){
                            pairs++;
                        }
                        if(cards.get(i + 3).getValue() + 1 == cards.get(i + 4).getValue() | isPair(cards.get(i + 3), cards.get(i + 4))){
                            if(isPair(cards.get(i + 1), cards.get(i + 2))){
                                pairs++;
                            }
                            if(pairs <= 2){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean isPair(Card c1, Card c2){
        return c1.getValue() == c2.getValue();
    }

    boolean checkFlush(){
        int heartCount = 0;
        int diamondCount = 0;
        int clubCount = 0;
        int spadeCount = 0;
        for(Card c : cards){
            switch(c.getSuit()){
                case "HEARTS":
                    heartCount++;
                    break;
                case "DIAMONDS":
                    diamondCount++;
                    break;
                case "CLUBS":
                    clubCount++;
                    break;
                case "SPADES":
                    spadeCount++;
                    break;
            }
        }
        if(heartCount >= 5 | diamondCount >= 5 | clubCount >= 5 | spadeCount >= 5){
            return true;
        }
        return false;
    }

    void sortHand(){
        cards.sort(Card::compareTo);
    }

    void printCards(){
        for(Card c : cards){
            System.out.printf("%s of %s\n", c.getRank(), c.getSuit());
        }
    }

    void setCards(ArrayList<Card> hand){
        //used for testing
        cards = hand;
    }
}
