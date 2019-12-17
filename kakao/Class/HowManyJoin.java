package kr.ac.kw.kakao.Class;

// 가입한 사람 총 숫자 관리
public class HowManyJoin {
    private static HowManyJoin howManyJoin= null;
    private  int count;
    private  HowManyJoin(){}

    public static HowManyJoin getInstance(){
        if(howManyJoin == null)
            howManyJoin = new HowManyJoin();

        return howManyJoin;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
