package game;
class Card{

    private String suit;
    private Integer value;
    private String rank;

    Card(String suit, Integer value, String rank) {
        this.suit=suit;
        this.value = value;
        this.rank = rank;
    }

    int getValue() {
        return this.value;
    }

    String getSuit() {
        return suit;
    }

    String getRank() {
        return this.rank;
    }

    String getName(){
        return rank + "_" + suit;
    }

    String getImage() {
        return "src\\resources\\" + rank + "_" + suit + ".png";
    }

    int compareTo(Card c) {
        return value - c.getValue();
    }
}