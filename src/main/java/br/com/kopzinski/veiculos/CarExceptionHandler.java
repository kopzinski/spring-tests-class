package br.com.kopzinski.veiculos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CarExceptionHandler {
	
	@ExceptionHandler(CarException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCarException() {
    }
}
