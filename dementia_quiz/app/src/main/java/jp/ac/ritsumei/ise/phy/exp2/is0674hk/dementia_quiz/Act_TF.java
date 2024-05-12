package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

public class Act_TF {
    public Act_TF(int quiz_id,boolean cor){
        this.quiz_id=quiz_id;
        this.cor=cor;
    }
    private int quiz_id;
    private boolean cor;

    public int getQuiz_id() {
        return quiz_id;
    }

    public boolean isCor() {
        return cor;
    }
}
