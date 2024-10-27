package com.emanuelpontes.crmtest.global.servico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.emanuelpontes.crmtest.global.erros.AplicationException;
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
		// validate o objeto a ser incluido
		validate(entity);
		// faz algum tratamento antes de persistir no banco
		entity = prePersistency(entity);
		// inclui objeto na base e retorna o resultado
		E result = repository.save(entity);
		triggerEvent(result);
		return result;
	}

	@Transactional
	public List<E> saveBatch(List<E> entitys) {

		// validate os objetos a ser incluido
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
		// valida objeto a ser atualizado
		validate(entity);
		// faz algum tratamento antes de persistir no banco
		entity = prePersistency(entity);
		E _entity = repository.findById((C)entity.getId()).orElse(null);
		if (_entity == null) {
			throw new AplicationException("Object not found");
		}
		E result = repository.save(entity);
		triggerEvent(result);
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

	public Object pesquisa(Optional<String> valor, Optional<Integer> limite) {
		throw new AplicationException("Unsupported method");
	}

	public Object pesquisa(Optional<String> valor, Optional<String> codigo, Optional<String> descricao,
			Optional<Integer> limite) {
		throw new AplicationException("Unsupported method");
	}

	public Object pesquisa(Optional<String> valor, Optional<String> codigo, Optional<String> identificador,
			Optional<String> descricao, Optional<Integer> limite) {
		throw new AplicationException("Unsupported method");
	}

	@Transactional
	public Boolean delete(C id) {
		// valida objeto a ser removido
		validateToDelete(repository.findById(id).orElse(null));
		repository.findById(id).ifPresent(e -> repository.deleteById(id));
		// executa métod para remover objeto da base de dados
		// retorna verdadeiro se ñ ocorreu nenhum erro
		return true;
	}
	
	
	@Transactional
	public Boolean alteraStatusParaRemovido(C id) {
		return true;
	}
	
	@Transactional
	public BigDecimal total() {
		
		Long count = this.repository.count();
		
		return BigDecimal.valueOf(count);
		
	}

	@Transactional
	public Boolean deleteBatch(List<E> entitys) {

		// validate os objetos a ser incluido
		for (E entity : entitys) {
			validateToDelete(entity);
		}

		repository.deleteAll(entitys);

		return true;
	}
}
