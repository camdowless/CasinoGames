package game;

import java.util.*;
import java.util.stream.Collectors;

class PokerHand {
    private ArrayList<Card> cards = new ArrayList<>();
    private int handScore;
    private String handType;
    private int pairCount;
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
        pairCount = 0;
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

     int calcScore(){
        //TODO: Fix trickle down bug
        sortHand();
        if(checkRoyalFlush()){
            handScore = 10;
            handType = "Royal Flush";
            return 1;
        } else if(checkStraightFlush()){
            handScore = 9;
            handType = "Straight Flush";
            return 1;
        } else if(checkFourOfAKind()){
            handScore = 8;
            handType = "Four of a Kind";
            return 1;
        } else if(checkFullHouse()){
            handScore = 7;
            handType = "Full House";
            return 1;
        } else if(checkFlush()){
            handScore = 6;
            handType = "Flush";
            return 1;
        } else if(checkStraight()){
            handScore = 5;
            handType = "Straight";
            return 1;
        } else if(checkThreeOfAKind()){
            handScore = 4;
            handType = "Three of a Kind";
            return 1;
        } else if(checkTwoPair()){
            handScore = 3;
            handType = "Two Pair";
            return 1;
        } else if(checkPair()){
            handScore = 2;
            handType = "Pair";
            return 1;
        } else {
            handScore = 1;
            handType = String.format("High card %s", getHighCard());
            return 1;
        }
    }

     boolean checkFullHouse() {
        if(checkThreeOfAKind() & checkPair()){
            return true;
        }
        return false;
    }

     boolean checkFourOfAKind() {
        return ofAKind(4);
    }

     boolean checkThreeOfAKind(){
        return ofAKind(3);
    }

     boolean checkTwoPair(){
        findPairCount();
        if(pairCount >= 2){
            return true;
        }
        return false;
    }

     boolean checkPair(){
        findPairCount();
        if(pairCount == 1){
            return true;
        }
        return false;
    }

     String getHighCard(){
        Card high = new Card("", 0, "0");
        for(Card c : cards){
            if(c.getValue() > high.getValue()){
                high = c;
            }
        }
        return high.getRank();
     }

     boolean checkRoyalFlush(){
        sortHand();
        int index= 0;
        String suit;
        try{
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
        }catch(IndexOutOfBoundsException e){
            return false;
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
        ArrayList<Card> copy = cards;
        Set<Integer> set = new HashSet<>(copy.size());
        copy.removeIf(p -> !set.add(p.getValue()));
        try{
            for(int i = 0; i < 3; i++){
                Card current = copy.get(i);
                if(current.getValue() + 1 == cards.get(i + 1).getValue()){
                    if(cards.get(i + 1).getValue() + 1 == cards.get(i + 2).getValue()){
                        if(cards.get(i + 2).getValue() + 1 == cards.get(i + 3).getValue()){
                            if(cards.get(i + 3).getValue() + 1 == cards.get(i + 4).getValue()){
                                return true;
                            }
                        }
                    }
                }
            }
        } catch(IndexOutOfBoundsException e){
            return false;
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

     boolean ofAKind(int amount){
        HashMap<String, Integer> pairs = new HashMap<>();
        for(Card c : cards){
            if(!pairs.containsKey(c.getRank())){
                pairs.put(c.getRank(), 1);
            } else{
                int val = pairs.get(c.getRank());
                pairs.replace(c.getRank(), val + 1);
            }
        }
        Iterator it = pairs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int value = (int)pair.getValue();
            if(value == amount){
                return true;
            }
            it.remove();
        }
        return false;
    }

     void findPairCount(){
        HashMap<String, Integer> pairs = new HashMap<>();
        for(Card c : cards){
            if(!pairs.containsKey(c.getRank())){
                pairs.put(c.getRank(), 1);
            } else{
                int val = pairs.get(c.getRank());
                pairs.replace(c.getRank(), val + 1);
            }
        }
        Iterator it = pairs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int value = (int)pair.getValue();
            if(value == 2){
                pairCount++;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
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

    int getHandScore(){
        return handScore;
    }

    String getHandType(){
        return handType;
    }
}
