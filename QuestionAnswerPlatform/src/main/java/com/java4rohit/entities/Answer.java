package com.java4rohit.entities;

public class Answer {
	int QuestionID;
	String AnswerText;
	int UserID;
	
	public Answer(int questionID, String answerText, int userID) {
		super();
		QuestionID = questionID;
		AnswerText = answerText;
		UserID = userID;
	}

	public int getQuestionID() {
		return QuestionID;
	}

	public void setQuestionID(int questionID) {
		QuestionID = questionID;
	}

	public String getAnswerText() {
		return AnswerText;
	}

	public void setAnswerText(String answerText) {
		AnswerText = answerText;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	@Override
	public String toString() {
		return "Answer [QuestionID=" + QuestionID + ", AnswerText=" + AnswerText + ", UserID=" + UserID + "]";
	}
	
	
	
	

}
