package game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Deck {

    private static ArrayList<Card> cards;
    private List<Integer> valueList = Arrays.asList(14,2,3,4,5,6,7,8,9,10,11,12,13);
    private List<String> rankList = Arrays.asList("A","2","3","4","5","6","7","8","9","10","J","Q","K");

    Deck(){
        cards = new ArrayList<>();
        init();
    }

    private void init(){
        for(int num = 0; num < 13; num++) {
            cards.add(new Card("CLUBS",valueList.get(num), rankList.get(num)));
            cards.add(new Card("DIAMONDS",valueList.get(num), rankList.get(num)));
            cards.add(new Card("HEARTS",valueList.get(num), rankList.get(num)));
            cards.add(new Card("SPADES",valueList.get(num), rankList.get(num)));
        }
        shuffle();
    }

    private void shuffle(){
        Collections.shuffle(cards);
    }

    static Card draw(){
        return cards.remove(0);
    }

    //only used for testing
    public ArrayList<Card> getDeck(){
        return cards;
    }
}