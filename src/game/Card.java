package game;
class Card {

    private String suit;
    private Integer value;
    private String rank;
    private boolean flipped;

    Card(String suit, Integer value, String rank){
        this.suit=suit;
        this.value = value;
        this.rank = rank;
        flipped = false;
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


    String getColor() {
        if (suit.equals("HEARTS")||suit.equals("DIAMONDS")){
            return "red";
        }
        return "black";
    }

    int getNumber() {
        switch (rank) {
            case "A":
                return 1;
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
        }
        return Integer.parseInt(rank);
    }


    String getImage() {
        return "src\\resources\\" + rank + "_" + suit + ".png";
    }
}