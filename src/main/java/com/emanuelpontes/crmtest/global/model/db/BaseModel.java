package com.emanuelpontes.crmtest.global.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
@Data
@MappedSuperclass
public class BaseModel<ID extends Serializable> implements IBaseModel, Serializable {

	private static final long serialVersionUID = -6146873645822923891L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(nullable = false, updatable = false)
	protected LocalDateTime createdAt;

	@Column
	@LastModifiedDate
	protected LocalDateTime updatedAt;

	@SuppressWarnings("static-access")
	public BaseModel() {
		this.createdAt = LocalDateTime.now();
	}
}
