package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

public class Act_select {
    private int select_diff;
    private int id;
    public Act_select(int id , int select_diff){
        this.id = id;
        this.select_diff = select_diff;
    }
    public int getSelect_diff(){return this.select_diff;}
}
