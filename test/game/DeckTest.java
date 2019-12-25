package game;

import org.junit.Assert;
import org.junit.Test;

public class DeckTest {
    Deck deck = new Deck();
    @Test
    public void testDeckSize(){

        Assert.assertEquals(52, deck.getDeck().size());
        for(Card c : deck.getDeck()){
            System.out.println(c.getImage());
        }
    }

    @Test
    public void testDeckDraw(){
        Card c = null;
        c = deck.draw();
        Assert.assertNotNull(c);
        System.out.println(c.getImage());
    }
}
