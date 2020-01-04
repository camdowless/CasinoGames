package game;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class beforeShowdownHandTest {
    /*
        These tests are for:
        -2 card hands that the players are dealt
        -5 card hands with pocket cards & the flop
        -6 card hands with pocket cards, flop, and turn
     */
    @Test
    public void test2CardPair(){
        PokerHand pair = new PokerHand();
        PokerHand notPair = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("DIAMONDS", 7, "7"));
        cards.add(new Card("CLUBS", 7, "7"));
        pair.setCards(cards);
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("DIAMONDS", 7, "7"));
        cards2.add(new Card("CLUBS", 13, "K"));
        notPair.setCards(cards2);

        pair.calc2Score(cards);
        Assert.assertTrue(pair.checkPair(cards));
        Assert.assertEquals(2, pair.getHandScore());
        Assert.assertFalse(notPair.checkPair(cards2));
    }

    @Test
    public void test2CardHigh(){
        PokerHand high = new PokerHand();
        ArrayList<Card>cards = new ArrayList<>();
        cards.add(new Card("DIAMONDS", 7, "7"));
        cards.add(new Card("CLUBS", 13, "K"));
        high.setCards(cards);
        Assert.assertEquals(13, high.findHighCardValue(cards));
    }

    //TODO: Write 5 and 6 card hand tests. All hands are possible
}
