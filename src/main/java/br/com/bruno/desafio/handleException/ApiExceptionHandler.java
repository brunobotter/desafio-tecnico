package br.com.bruno.desafio.handleException;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.bruno.desafio.controller.exception.IdNaoEncontradoException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		var campos = new ArrayList<Problema.Campo>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			campos.add(new Problema.Campo(nome, mensagem));
		}
		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setErro("Um ou mais campos estão invalidos." + " Faça o preenchimento correto!");
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	@ExceptionHandler(IdNaoEncontradoException.class)
	public ResponseEntity<Object> idNaoEncontrada(IdNaoEncontradoException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problema = recebeProblema(status.value(), ex.getMessage());
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	private Problema recebeProblema(Integer status, String message) {
		var problema = new Problema();
		problema.setStatus(status);
		problema.setErro(message);
		problema.setDataHora(OffsetDateTime.now());
		return problema;
	}
}
