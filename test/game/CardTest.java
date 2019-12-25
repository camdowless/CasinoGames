package game;

import org.junit.Assert;
import org.junit.Test;

public class CardTest {
    @Test
    public void testCardImage(){
        Card card = new Card("CLUBS", 5, "5");
        Assert.assertEquals("src\\resources\\5_CLUBS.png","src\\resources\\" + card.getRank() + "_" + card.getSuit() + ".png");
    }

}
