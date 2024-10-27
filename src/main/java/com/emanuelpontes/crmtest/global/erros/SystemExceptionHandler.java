package com.emanuelpontes.crmtest.global.erros;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SystemExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource messageSource;
	
	//Campos nulos obrigatorios
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<SystemError> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	//Recurso não encontrado
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handlerEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<SystemError> erros = Arrays.asList(new SystemError(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	
	//Usado para emitir qualquer erro de validação
	@ExceptionHandler({ AplicationException.class })
	public ResponseEntity<Object> sistemaException(AplicationException ex) {
		String mensagemUsuario = ex.getMessage();
		String mensagemDesenvolvedor = ex.toString();
		List<SystemError> erros = Arrays.asList(new SystemError(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({ ResourceNotFoundException.class })
	public ResponseEntity<Object> handlerResourceNotFoundException(ResourceNotFoundException ex) {
		String mensagemUsuario = ex.getMessage();
		String mensagemDesenvolvedor = ex.toString();
		List<SystemError> erros = Arrays.asList(new SystemError(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	//@ExceptionHandler({ MaxUploadSizeExceededException.class })
	public ResponseEntity<Object> handlerMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
		String mensagemUsuario = "Tamanho do arquivo excede o tamanho máximo permitido de 100MB";
		String mensagemDesenvolvedor = ex.toString();
		List<SystemError> erros = Arrays.asList(new SystemError(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	//Criar uma lista de erro
	private List<SystemError> criarListaDeErros(BindingResult bindingResult) {
		List<SystemError> erros = new ArrayList<>();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new SystemError(mensagemUsuario, mensagemDesenvolvedor));
		}
			
		return erros;
	}
	
}