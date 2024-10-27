package com.emanuelpontes.crmtest.global.errors;

public class SystemError {
	
	private String messageUser;
	private String messageDeveloper;
	
	public SystemError(String messageUser, String messageDeveloper) {
		this.messageUser = messageUser;
		this.messageDeveloper = messageDeveloper;
	}

	public String getMessageUser() {
		return messageUser;
	}

	public String getMessageDeveloper() {
		return messageDeveloper;
	}
}
