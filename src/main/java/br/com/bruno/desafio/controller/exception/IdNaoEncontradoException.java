package br.com.bruno.desafio.controller.exception;

import java.util.NoSuchElementException;

public class IdNaoEncontradoException extends NoSuchElementException{

	private static final long serialVersionUID = 1L;

	public IdNaoEncontradoException(String message) {
		super(message);
	}
	
}
