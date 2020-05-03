package game;

import java.util.ArrayList;

class Player {
    private String name;
    private Integer money;
    private ArrayList<Card> hand;

    Player(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    String get_name(){
        return name;
    }
    Integer get_money() {
        return money;
    }
}