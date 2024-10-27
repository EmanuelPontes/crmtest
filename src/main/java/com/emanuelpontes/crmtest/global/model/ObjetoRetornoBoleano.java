package com.emanuelpontes.crmtest.global.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonSerialize
public class ObjetoRetornoBoleano implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean resultado;

	public ObjetoRetornoBoleano(Boolean resultado) {
		this.resultado = resultado;
	}

	public Boolean getResultado() {
		return resultado;
	}

	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}
}
