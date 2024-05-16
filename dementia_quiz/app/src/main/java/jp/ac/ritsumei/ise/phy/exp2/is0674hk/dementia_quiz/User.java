package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

public class User {
        private int id;
        private float per;
        private String email;

        public User(int id, float per) {
            this.id = id;
            this.per = per;

        }

        //idを取得するメソッド
        public int getId() {
            return id;
        }

        //nameを取得するメソッド
        public float getPer() {
            return per;
        }

        //emailを取得するメソッド
        public String getEmail() {
            return email;
        }

        //idを更新するメソッド
        public void setId(int id) {
            this.id = id;
        }

        //setName(),setEmail()
    }


