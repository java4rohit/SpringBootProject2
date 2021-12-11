package com.java4rohit.entities;

public class Answer_likes {
	
	int AnswerID;
	int UserID;
	
	public Answer_likes(int answerID, int userID) {
		super();
		AnswerID = answerID;
		UserID = userID;
	}
	public int getAnswerID() {
		return AnswerID;
	}
	public void setAnswerID(int answerID) {
		AnswerID = answerID;
	}
	
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	@Override
	public String toString() {
		return "Answer_likes [AnswerID=" + AnswerID + ", UserID=" + UserID + "]";
	}
	
	
	

}
