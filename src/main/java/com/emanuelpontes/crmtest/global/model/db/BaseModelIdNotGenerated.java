package com.emanuelpontes.crmtest.global.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;



import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseModelIdNotGenerated<ID extends Serializable> implements IBaseModel, Serializable {

	private static final long serialVersionUID = 6773259619089672743L;

	@Id
	protected Long id;

//	@Column(nullable = false, updatable = false)
//	protected LocalDateTime criacao;
//
//	@Column
//	@LastModifiedDate
//	protected LocalDateTime edicao;

	@SuppressWarnings("static-access")
	public BaseModelIdNotGenerated() {
//		this.criacao = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}
//
//	public LocalDateTime getCriacao() {
//		return criacao;
//	}
//
//	public void setCriacao(LocalDateTime criacao) {
//		this.criacao = criacao;
//	}
//
//	public LocalDateTime getEdicao() {
//		return edicao;
//	}
//
//	public void setEdicao(LocalDateTime edicao) {
//		this.edicao = edicao;
//	}

	public void setId(Long id) {
		this.id = id;
	}

}
