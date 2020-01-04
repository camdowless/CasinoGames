package game;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class HandTest {
    /*
        These tests are for 7 card hands that include
        -2 player cards
        -3 flop cards
        -1 turn
        -1 river
     */
    @Test
    public void testSort() {
        PokerHand hand = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("HEARTS", 11, "J"));
        cards.add(new Card("HEARTS", 10, "10"));
        cards.add(new Card("HEARTS", 14, "A"));
        cards.add(new Card("HEARTS", 12, "Q"));
        cards.add(new Card("HEARTS", 13, "K"));
        cards.add(new Card("SPADES", 3, "3"));
        cards.add(new Card("CLUBS", 9, "9"));
        hand.setCards(cards);
        hand.printCards();
        System.out.println();
        hand.sortHand(cards);
        hand.printCards();
    }

    @Test
    public void testRoyalFlush() {
        PokerHand royal = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("HEARTS", 11, "J"));
        cards.add(new Card("HEARTS", 10, "10"));
        cards.add(new Card("HEARTS", 14, "A"));
        cards.add(new Card("HEARTS", 12, "Q"));
        cards.add(new Card("HEARTS", 13, "K"));
        cards.add(new Card("SPADES", 3, "3"));
        cards.add(new Card("CLUBS", 9, "9"));
        royal.setCards(cards);

        PokerHand notRoyal = new PokerHand();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("CLUBS", 11, "J"));
        cards2.add(new Card("CLUBS", 10, "10"));
        cards2.add(new Card("HEARTS", 14, "A"));
        cards2.add(new Card("SPADES", 10, "J"));
        cards2.add(new Card("HEARTS", 13, "K"));
        cards2.add(new Card("SPADES", 3, "3"));
        cards2.add(new Card("CLUBS", 9, "9"));
        notRoyal.setCards(cards2);

        Assert.assertTrue(royal.checkRoyalFlush(cards));
        Assert.assertFalse(notRoyal.checkRoyalFlush(cards2));
    }

    @Test
    public void testFLush() {
        PokerHand flush = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("SPADES", 11, "J"));
        cards.add(new Card("DIAMONDS", 10, "10"));
        cards.add(new Card("DIAMONDS", 13, "K"));
        cards.add(new Card("DIAMONDS", 2, "2"));
        cards.add(new Card("DIAMONDS", 8, "8"));
        cards.add(new Card("CLUBS", 8, "8"));
        cards.add(new Card("DIAMONDS", 9, "9"));
        flush.setCards(cards);

        PokerHand notFlush = new PokerHand();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("CLUBS", 11, "J"));
        cards2.add(new Card("CLUBS", 10, "10"));
        cards2.add(new Card("HEARTS", 14, "A"));
        cards2.add(new Card("SPADES", 10, "J"));
        cards2.add(new Card("HEARTS", 13, "K"));
        cards2.add(new Card("SPADES", 3, "3"));
        cards2.add(new Card("CLUBS", 9, "9"));
        notFlush.setCards(cards2);

        Assert.assertTrue(flush.checkFlush(cards));
        Assert.assertFalse(notFlush.checkFlush(cards2));
    }

    @Test
    public void testStraight() {
        PokerHand straight = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("CLUBS", 2, "2"));
        cards.add(new Card("HEARTS", 3, "3"));
        cards.add(new Card("DIAMONDS", 6, "6"));
        cards.add(new Card("HEARTS", 5, "5"));
        cards.add(new Card("CLUBS", 4, "4"));
        cards.add(new Card("SPADES", 3, "3"));
        cards.add(new Card("CLUBS", 9, "9"));
        straight.setCards(cards);

        PokerHand notStraight = new PokerHand();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("CLUBS", 2, "2"));
        cards2.add(new Card("CLUBS", 3, "3"));
        cards2.add(new Card("HEARTS", 3, "3"));
        cards2.add(new Card("SPADES", 10, "J"));
        cards2.add(new Card("HEARTS", 13, "K"));
        cards2.add(new Card("SPADES", 4, "4"));
        cards2.add(new Card("CLUBS", 5, "5"));
        notStraight.setCards(cards2);

        Assert.assertTrue(straight.checkStraight(cards));
        Assert.assertFalse(notStraight.checkStraight(cards2));
    }

    @Test
    public void testPair() {
        PokerHand pair = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("CLUBS", 2, "2"));
        cards.add(new Card("HEARTS", 3, "3"));
        cards.add(new Card("DIAMONDS", 6, "6"));
        cards.add(new Card("HEARTS", 5, "5"));
        cards.add(new Card("CLUBS", 4, "4"));
        cards.add(new Card("SPADES", 3, "3"));
        cards.add(new Card("CLUBS", 9, "9"));
        pair.setCards(cards);

        PokerHand notPair = new PokerHand();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("CLUBS", 2, "2"));
        cards2.add(new Card("CLUBS", 5, "5"));
        cards2.add(new Card("HEARTS", 9, "9"));
        cards2.add(new Card("SPADES", 10, "J"));
        cards2.add(new Card("HEARTS", 13, "K"));
        cards2.add(new Card("SPADES", 3, "3"));
        cards2.add(new Card("CLUBS", 8, "8"));
        notPair.setCards(cards2);

        Assert.assertTrue(pair.checkPair(cards));
        Assert.assertFalse(notPair.checkPair(cards2));
    }

    @Test
    public void testTwoPair() {
        PokerHand twoPair = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("CLUBS", 11, "J"));
        cards.add(new Card("HEARTS", 7, "7"));
        cards.add(new Card("DIAMONDS", 6, "6"));
        cards.add(new Card("HEARTS", 11, "J"));
        cards.add(new Card("CLUBS", 7, "7"));
        cards.add(new Card("SPADES", 3, "3"));
        cards.add(new Card("CLUBS", 9, "9"));
        twoPair.setCards(cards);

        PokerHand notTwoPair = new PokerHand();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("CLUBS", 14, "A"));
        cards2.add(new Card("CLUBS", 5, "5"));
        cards2.add(new Card("HEARTS", 14, "A"));
        cards2.add(new Card("SPADES", 11, "J"));
        cards2.add(new Card("HEARTS", 11, "J"));
        cards2.add(new Card("SPADES", 3, "3"));
        cards2.add(new Card("CLUBS", 8, "8"));
        notTwoPair.setCards(cards2);

        Assert.assertTrue(twoPair.checkTwoPair(cards));
        Assert.assertTrue(notTwoPair.checkTwoPair(cards2));
    }

    @Test
    public void testThreeOfAKind() {
        PokerHand threeOfAKind = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("CLUBS", 2, "2"));
        cards.add(new Card("HEARTS", 3, "3"));
        cards.add(new Card("DIAMONDS", 6, "6"));
        cards.add(new Card("HEARTS", 5, "5"));
        cards.add(new Card("CLUBS", 3, "3"));
        cards.add(new Card("SPADES", 3, "3"));
        cards.add(new Card("CLUBS", 9, "9"));
        threeOfAKind.setCards(cards);

        PokerHand notThreeOfAKind = new PokerHand();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("CLUBS", 2, "2"));
        cards2.add(new Card("CLUBS", 5, "5"));
        cards2.add(new Card("HEARTS", 9, "9"));
        cards2.add(new Card("SPADES", 10, "J"));
        cards2.add(new Card("HEARTS", 13, "K"));
        cards2.add(new Card("SPADES", 3, "3"));
        cards2.add(new Card("CLUBS", 8, "8"));
        notThreeOfAKind.setCards(cards2);

        Assert.assertTrue(threeOfAKind.checkThreeOfAKind(cards));
        Assert.assertFalse(notThreeOfAKind.checkThreeOfAKind(cards2));
    }

    @Test
    public void testFourOfAKind() {
        PokerHand fourOfAKind = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("CLUBS", 2, "2"));
        cards.add(new Card("HEARTS", 3, "3"));
        cards.add(new Card("DIAMONDS", 6, "6"));
        cards.add(new Card("DIAMONDS", 3, "3"));
        cards.add(new Card("CLUBS", 3, "3"));
        cards.add(new Card("SPADES", 3, "3"));
        cards.add(new Card("CLUBS", 9, "9"));
        fourOfAKind.setCards(cards);

        PokerHand notFourOfAKind = new PokerHand();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("CLUBS", 2, "2"));
        cards2.add(new Card("HEARTS", 3, "3"));
        cards2.add(new Card("DIAMONDS", 6, "6"));
        cards2.add(new Card("HEARTS", 5, "5"));
        cards2.add(new Card("CLUBS", 3, "3"));
        cards2.add(new Card("SPADES", 3, "3"));
        cards2.add(new Card("CLUBS", 9, "9"));
        notFourOfAKind.setCards(cards2);

        Assert.assertTrue(fourOfAKind.checkFourOfAKind(cards));
        Assert.assertFalse(notFourOfAKind.checkFourOfAKind(cards2));
    }

    @Test
    public void testFullHouse() {
        PokerHand fullHouse = new PokerHand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("SPADES", 11, "J"));
        cards.add(new Card("HEARTS", 9, "9"));
        cards.add(new Card("DIAMONDS", 14, "A"));
        cards.add(new Card("HEARTS", 14, "A"));
        cards.add(new Card("CLUBS", 9, "9"));
        cards.add(new Card("SPADES", 7, "7"));
        cards.add(new Card("DIAMONDS", 9, "9"));
        fullHouse.setCards(cards);

        PokerHand notFullHouse = new PokerHand();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card("CLUBS", 2, "2"));
        cards2.add(new Card("CLUBS", 5, "5"));
        cards2.add(new Card("HEARTS", 9, "9"));
        cards2.add(new Card("SPADES", 10, "J"));
        cards2.add(new Card("HEARTS", 13, "K"));
        cards2.add(new Card("SPADES", 3, "3"));
        cards2.add(new Card("CLUBS", 8, "8"));
        notFullHouse.setCards(cards2);


        Assert.assertTrue(fullHouse.checkFullHouse(cards));
        Assert.assertFalse(notFullHouse.checkFullHouse(cards2));
    }

}