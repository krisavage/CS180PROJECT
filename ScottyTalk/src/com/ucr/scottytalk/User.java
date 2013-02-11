package com.ucr.scottytalk;
 
import com.stackmob.sdk.model.StackMobUser;
 
public class User extends StackMobUser {
	String user;
    protected User(String username, String password) {
        super(User.class, username, password);
        user = username;
    }
    
    public String getUser() {
        return user;}

}