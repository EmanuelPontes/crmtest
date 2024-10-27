package com.emanuelpontes.crmtest.global.errors;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String mensagem) {
		super(mensagem);
	}

	public ApplicationException(Exception e) {
		super(e);
	}
}
