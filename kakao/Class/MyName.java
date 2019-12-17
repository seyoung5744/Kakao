package kr.ac.kw.kakao.Class;

public class MyName {
    private String name = "";

    private static MyName myNameClass = null;

    public static MyName getInstance(){
        if(myNameClass == null)
            myNameClass = new MyName();
        return myNameClass;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
