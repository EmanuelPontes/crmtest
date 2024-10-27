package com.emanuelpontes.crmtest.global.model;

import java.io.Serializable;

public class ObjetoStringJson implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String resultado;

	public ObjetoStringJson(String resultado) {
		this.resultado = resultado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
}
