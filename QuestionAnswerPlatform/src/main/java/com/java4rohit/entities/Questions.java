package com.java4rohit.entities;

public class Questions {
	
	String QuestionText;
	int CompanyId ;
	String SubtopicId ;
	int UserID ;
	
	public Questions(String questionText, int companyId, String subtopicId, int userID) {
		super();
		QuestionText = questionText;
		CompanyId = companyId;
		SubtopicId = subtopicId;
		UserID = userID;
	}

	public String getQuestionText() {
		return QuestionText;
	}

	public void setQuestionText(String questionText) {
		QuestionText = questionText;
	}

	public int getCompanyId() {
		return CompanyId;
	}

	public void setCompanyId(int companyId) {
		CompanyId = companyId;
	}

	public String getSubtopicId() {
		return SubtopicId;
	}

	public void setSubtopicId(String subtopicId) {
		SubtopicId = subtopicId;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	@Override
	public String toString() {
		return "Questions [QuestionText=" + QuestionText + ", CompanyId=" + CompanyId + ", SubtopicId=" + SubtopicId
				+ ", UserID=" + UserID + "]";
	}
	
	
	

}
