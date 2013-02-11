package com.ucr.scottytalk;

import com.stackmob.sdk.model.StackMobModel;

public class Profile extends StackMobModel {
	private String name;
	private String phoneNum;
	private String username;

	
	public Profile(String name, String pnum, String UserName) {
		super(Profile.class);
		this.name = name;
		phoneNum = pnum;
		username = UserName;
	}
	
    public String getName() {
        return name;}
    public String getPhoneNumber() {
        return phoneNum;}
    public String getUserName() {
        return username;}
}