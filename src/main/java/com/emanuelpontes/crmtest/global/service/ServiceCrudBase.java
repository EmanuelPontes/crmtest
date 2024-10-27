package com.emanuelpontes.crmtest.global.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.emanuelpontes.crmtest.global.errors.ApplicationException;
import com.emanuelpontes.crmtest.global.model.db.IBaseModel;



/**
 * Abstract class for CRUD operations
 * 
 * @author Emanuel Pontes
 * @param <T> Repo class
 * @param <E> Entity
 * @param <C> Id class
 */
public abstract class ServiceCrudBase<T extends JpaRepository<E, C>, E extends IBaseModel, C extends Serializable>
		implements IServiceCrudBase<E, C> {

	
	@Autowired
	public T repository;


	@Transactional(readOnly = true)
	public E searchById(C id) {
		// busca o objeto na base de dados e retorna o resultado
		E entity = repository.findById(id).orElse(null);
		return entity;
	}

	@Transactional(readOnly = true)
	public List<E> list() {
		// busca todos os objetos na base de dados e retorna o resultado
		List<E> list = repository.findAll();
		return list;
	}

	@Transactional
	public E create(E entity) {
		validate(entity);
		entity = prePersistency(entity);
		E result = repository.save(entity);
		triggerChangeEvent(result);
		return result;
	}

	@Transactional
	public List<E> saveBatch(List<E> entitys) {

		for (E entity : entitys) {
			validate(entity);
		}

		validateBatch(entitys);

		entitys = repository.saveAll(entitys);
		return entitys;
	}

	@Transactional(readOnly = true)
	public void validate(E entity) {

	}

	@Transactional
	public void validateBatch(List<E> entitys) {

	}
	@Transactional
	public E edit(E entity) {
		validate(entity);
		entity = prePersistency(entity);
		E _entity = repository.findById((C)entity.getId()).orElse(null);
		if (_entity == null) {
			throw new ApplicationException("Object not found");
		}
		E result = repository.save(entity);
		triggerChangeEvent(result);
		return result;
	}

	public PageRequest getPage(Optional<Integer> size) {
		return getPage(Optional.empty(), size);
	}

	public PageRequest getPage(Optional<Integer> page, Optional<Integer> size) {
		Integer _page = 0;
		Integer _size = 10;
		if (size.isPresent() && size.get() > 0)
			_size = size.get();
		if (page.isPresent() && page.get() > 0)
			_page = page.get();
		return getPage(_page, _size);
	}

	public PageRequest getPage(Integer size) {
		return getPage(0, size);
	}

	@SuppressWarnings("deprecation")
	public PageRequest getPage(Integer page, Integer size) {
		PageRequest pageRequest = PageRequest.of(0, 10);

		if (size != null && size > 0)
			pageRequest = PageRequest.of(0, size);

		return pageRequest;
	}

	@Transactional
	public Boolean delete(C id) {
		// valida objeto a ser removido
		validateToDelete(repository.findById(id).orElse(null));
		repository.findById(id).ifPresent(e -> repository.deleteById(id));
		// executa métod para remover objeto da base de dados
		// retorna verdadeiro se ñ ocorreu nenhum erro
		triggerDeleteEvent(id);
		return true;
	}
	
	@Transactional
	public BigDecimal total() {
		
		Long count = this.repository.count();
		
		return BigDecimal.valueOf(count);
		
	}

	@Transactional
	public Boolean deleteBatch(List<E> entitys) {

		for (E entity : entitys) {
			validateToDelete(entity);
		}

		repository.deleteAll(entitys);

		return true;
	}
}
