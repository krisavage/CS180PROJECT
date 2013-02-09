package com.ucr.scottytalk;

import java.util.ArrayList;
import java.util.List;
 
import com.stackmob.sdk.model.StackMobUser;
 
public class User extends StackMobUser {
 
    //private List<TaskList> taskLists;
    private String email;
    private String Username;
    private String phonenumber;
 
    protected User(String username, String password) {
        super(User.class, username, password);
        Username = username;
    }
 
 /*
    public String getTaskLists() {
        return taskLists;
    }
 
    public void setTasks(List<TaskList> taskLists) {
        this.taskLists = taskLists;
    }
    */
}