package com.emanuelpontes.crmtest.global.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.emanuelpontes.crmtest.global.model.db.IBaseModel;

/**
 * 
 * @author Emanuel Pontes
 *
 * @param <E> Entity
 * @param <C> Id type
 */
public interface IServiceCrudBase<E extends IBaseModel, C> {

	E create(E entity) ;
	List<E> saveBatch(List<E> entitys) ;
	E edit(E entity) ;
	Boolean delete(C id) ;
	Boolean deleteBatch(List<E> entitys) ;
	void validate(E entity) ;
	void validateBatch(List<E> entitys) ;
	void validateToDelete(E entity) ;
	E prePersistency(E entity);
	Object list() ;
	E searchById(C id) ;
	void triggerChangeEvent(E entity);
	void triggerDeleteEvent(C id);
}
