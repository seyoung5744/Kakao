package kr.ac.kw.kakao.Class;

import java.util.ArrayList;

public class User {
  //  ArrayList<User> userArrayList = new ArrayList<>();
    String email, name;
    boolean ownSelf;


    public void setUser(String email, String name){
        this.email = email;
        this.name = name;
    }

    public void setOwnSelf(boolean ownSelf){
        this.ownSelf =ownSelf;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public boolean isOwnSelf() {
        return ownSelf;
    }
//    public void addUser(User user){
//        userArrayList.add(user);
//    }
//
//    public int usersSize(){
//        return userArrayList.size();
//    }
//
//    public ArrayList getUserList(){
//        return userArrayList;
//    }
}
