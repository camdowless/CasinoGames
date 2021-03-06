package game;

import java.util.*;

class PokerHand {
    private ArrayList<Card> cards7 = new ArrayList<>();
    private int handScore;
    int full_house;
    private String handType;
    private String highCardRank;
    private int highCardValue;
    private ArrayList<Card> hand;
    private ArrayList<Card> flop;
    private Card turn;
    private Card river;
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
        full_house = 0;
        this.hand = hand;
        this.flop = flop;
        this.turn = turn;
        this.river = river;

        cards7.addAll(hand);
        cards7.addAll(flop);
        cards7.add(turn);
        cards7.add(river);
        highCardRank = findHighCardRank(cards7);
        highCardValue = findHighCardValue(cards7);
        calcScore(cards7);
    }

    PokerHand(){
        full_house = 0;
        sortHand(cards7);
    }

    void calcScore(ArrayList<Card> cards){
        printCards();
        sortHand(cards);
         handScore = 1;
         handType = String.format("High card %s", highCardRank);
         if(checkPair(cards)){
             full_house++;
             handScore = 2;
             handType = "Pair";
         } if(checkTwoPair(cards)){
             handScore = 3;
             handType = "Two Pair";
         } if(checkThreeOfAKind(cards)){
             full_house++;
             handScore = 4;
             handType = "Three of a Kind";
         } if(checkStraight(cards)){
             handScore = 5;
             handType = "Straight";
         } if(checkFlush(cards)){
             handScore = 6;
             handType = "Flush";
         } if(checkFullHouse()){
             handScore = 7;
             handType = "Full House";
         } if(checkFourOfAKind(cards)){
             handScore = 8;
             handType = "Four of a Kind";
         } if(checkStraightFlush(cards)){
             handScore = 9;
             handType = "Straight Flush";
         } if(checkRoyalFlush(cards)){
             handScore = 10;
             handType = "Royal Flush";
         }
     }

    void calc2Score(ArrayList<Card> cards){
        int pocketScore[][] = new int[13][13];
        pocketScore[0][0] = 85; //AA
        pocketScore[0][1] = 66; //AK
        pocketScore[0][2] = 65; //AQ
        pocketScore[0][3] = 65; //AJ
        pocketScore[0][4] = 64; //AT
        pocketScore[0][5] = 62; //A9
        pocketScore[0][6] = 61; //A8
        pocketScore[0][7] = 60; //A7
        pocketScore[0][8] = 59; //A6
        pocketScore[0][9] = 60; //A5
        pocketScore[0][10] = 59; //A4
        pocketScore[0][11] = 58; //A3
        pocketScore[0][12] = 57; //A2

        pocketScore[1][0] = 68; //KA
        pocketScore[1][1] = 83; //KK
        pocketScore[1][2] = 62; //KQ
        pocketScore[1][3] = 62; //KJ
        pocketScore[1][4] = 61; //KT
        pocketScore[1][5] = 59; //K9
        pocketScore[1][6] = 58; //K8
        pocketScore[1][7] = 57; //K7
        pocketScore[1][8] = 56; //K6
        pocketScore[1][9] = 55; //K5
        pocketScore[1][10] = 54; //K4
        pocketScore[1][11] = 54; //K3
        pocketScore[1][12] = 53; //K2

        pocketScore[2][0] = 00; //QA
        pocketScore[2][1] = 83; //QK
        pocketScore[2][2] = 62; //QQ
        pocketScore[2][3] = 62; //QJ
        pocketScore[2][4] = 61; //QT
        pocketScore[2][5] = 59; //Q9
        pocketScore[2][6] = 58; //Q8
        pocketScore[2][7] = 57; //Q7
        pocketScore[2][8] = 56; //Q6
        pocketScore[2][9] = 55; //Q5
        pocketScore[2][10] = 54; //Q4
        pocketScore[2][11] = 54; //Q3
        pocketScore[2][12] = 53; //Q2
     }

    boolean checkFullHouse() {
         return full_house == 2;
     }

    boolean checkFourOfAKind(ArrayList<Card> cards) {
        return ofAKind(4, cards);
    }

    boolean checkThreeOfAKind(ArrayList<Card> cards){
        return ofAKind(3, cards);
    }

    boolean checkTwoPair(ArrayList<Card> cards){
        findPairCount(cards);
        return findPairCount(cards) >= 2;
     }

    boolean checkPair(ArrayList<Card> cards){
        findPairCount(cards);
         return findPairCount(cards) == 1;
     }

    private boolean checkPairFH(ArrayList<Card> cards){
        //There is a bug with the game not being able to find Full Houses
        //And I think this will fix it
        int pc = findPairCount(cards);
        return pc == 1 | pc == 2 | pc == 3;
    }

    private String findHighCardRank(ArrayList<Card> cardList){
        Card high = new Card("", 0, "0");
        for(Card c : cardList){
            if(c.getValue() > high.getValue()){
                high = c;
            }
        }
        highCardValue = high.getValue();
        return high.getRank();
     }

    int findHighCardValue(ArrayList<Card> cardList){
        Card high = new Card("", 0, "0");
        for(Card c : cardList){
            if(c.getValue() > high.getValue()){
                high = c;
            }
        }
        return high.getValue();
    }

    boolean checkRoyalFlush(ArrayList<Card> cards){
        sortHand(cards);
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

    private boolean checkStraightFlush(ArrayList<Card> cards){
         return checkStraight(cards) & checkFlush(cards);
     }

    boolean checkStraight(ArrayList<Card> cards){
        sortHand(cards);
         Set<Integer> set = new HashSet<>(cards.size());
        cards.removeIf(p -> !set.add(p.getValue()));
        try{
            for(int i = 0; i < 3; i++){
                Card current = cards.get(i);
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

    boolean checkFlush(ArrayList<Card> cards){
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

    boolean ofAKind(int amount, ArrayList<Card> cards){
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

    int findPairCount(ArrayList<Card> cards){
        int pairCount = 0;
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
        return pairCount;
    }

    void sortHand(ArrayList<Card> cards){
        cards.sort(Card::compareTo);
    }

    int getHighCardValue(){
        //Used in Poker.compareHands()
        return highCardValue;
    }

    void printCards(){
        for(Card c : cards7){
            System.out.printf("%s of %s\n", c.getRank(), c.getSuit());
        }
    }

    void setCards(ArrayList<Card> hand){
        //used for testing
        cards7 = hand;
    }

    int getHandScore(){
        return handScore;
    }

    String getHandType(){
        return handType;
    }
}
