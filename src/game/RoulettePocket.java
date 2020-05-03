package game;
class roulettePocket {

    private int num;
    private String color;
    private String pocketID;

    roulettePocket(int num, String color){
        this.num = num;
        this.color = color;
        if(num == -1){
            pocketID = "green_00";
        } else {
            pocketID = String.format("%s_%d", color, num);
        }
    }

    int getNum(){
        return num;
    }
    String getColor(){
        return color;
    }
    String getPocketID(){return pocketID;}
}