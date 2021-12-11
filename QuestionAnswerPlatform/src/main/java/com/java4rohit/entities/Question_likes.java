package com.java4rohit.entities;

public class Question_likes {
	
	
	int QuestionID;
	int UserID;
	
	
	
	public Question_likes(int questionID, int userID) {
		super();
		QuestionID = questionID; 
		UserID = userID;
	}
	public int getQuestionID() {
		return QuestionID;
	}
	public void setQuestionID(int questionID) {
		QuestionID = questionID;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	
	

}
