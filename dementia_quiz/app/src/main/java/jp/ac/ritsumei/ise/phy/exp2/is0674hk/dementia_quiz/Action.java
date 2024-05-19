package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

public class Action {

    private int id;
    private String name;
    private int difficulty;

    public Action(int id,String name,int difficulty){
        this.id=id;
        this.name=name;
        this.difficulty=difficulty;
    }

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public int getDifficulty(){return this.difficulty;}
}
