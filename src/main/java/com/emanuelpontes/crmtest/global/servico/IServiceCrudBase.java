package com.emanuelpontes.crmtest.global.servico;

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
	Object pesquisa(Optional<String> valor, Optional<Integer> limite) ;
	Object pesquisa(Optional<String> valor, Optional<String> codigo, Optional<String> descricao, Optional<Integer> limite) ;
	Object pesquisa(Optional<String> valor, Optional<String> codigo, Optional<String> identificador, Optional<String> descricao, Optional<Integer> limite) ;
	E searchById(C id) ;
	void triggerEvent(E entity);
}
