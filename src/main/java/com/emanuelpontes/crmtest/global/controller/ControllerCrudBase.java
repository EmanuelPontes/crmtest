package com.emanuelpontes.crmtest.global.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.emanuelpontes.crmtest.global.model.db.IBaseModel;
import com.emanuelpontes.crmtest.global.servico.IServiceCrudBase;

import jakarta.servlet.http.HttpServletRequest;

/*
 	@param <S> Classe do serviço da classe a ser controlada.
	@param <E> Classe da entidade a ser manipulada.
	@param <C> Classe do tipo de dado do id da classe a ser manipulada.
*/
public abstract class ControllerCrudBase<S extends IServiceCrudBase<E, C>, E extends IBaseModel, C> {

	protected Boolean semAutorizacao = false;

	@Autowired
	protected S service;

	// Create
	@PostMapping
	public ResponseEntity<?> create(@RequestBody E entidade, HttpServletRequest request) {

		E novo = service.create(entidade);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entidade.getId())
				.toUri();

		ResponseEntity<?> response = ResponseEntity.created(uri).body(novo);
		return response;
	}

	// Update
	@PutMapping
	public ResponseEntity<?> update(@RequestBody E entidade, HttpServletRequest request) {
		ResponseEntity<?> response = ResponseEntity.ok(service.edit(entidade));
		return response;
	}

	// Delete
	@DeleteMapping("/{id}")
	public void delete(@PathVariable C id, HttpServletRequest request) {
		service.delete(id);
	}

	// Buscar por id
	@GetMapping("/{id}")
	public ResponseEntity<?> searchById(@PathVariable C id, HttpServletRequest request) {

		E entidade = service.searchById(id);
	if (entidade == null) {
			return ResponseEntity.notFound().build();
		}
		ResponseEntity<?> response = ResponseEntity.ok(entidade);
		return response;
	}

	// Listar todos
	@GetMapping
	public ResponseEntity<?> list(HttpServletRequest request) {
		ResponseEntity<?> response = ResponseEntity.ok(service.list());
		return response;
	}

	// Create batch
	@PostMapping("/batch")
	public ResponseEntity<?> saveBatch(@RequestBody List<E> entidades, HttpServletRequest request) {

		entidades = service.saveBatch(entidades);

		ResponseEntity<?> response = ResponseEntity.ok(entidades);
		return response;
	}

	// Delete batch
	@PostMapping("/delete/batch")
	public void deleteBatch(@RequestBody List<E> entidades, HttpServletRequest request) {
		service.deleteBatch(entidades);
	}
	  
}
