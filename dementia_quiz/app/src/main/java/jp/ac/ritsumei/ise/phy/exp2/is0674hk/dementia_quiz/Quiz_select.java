package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

public class Quiz_select {

    private int id;
    private int select_diff;

    public Quiz_select(int id , int select_diff){
        this.id = id;
        this.select_diff = select_diff;
    }

    public int getSelect_diff(){return this.select_diff;}
}
