package com.project.Mesa.Validation.validators;

import org.springframework.stereotype.Component;

import com.project.Mesa.Service.exception.ValidationException;

@Component
public class NomeValidator  {

	  private static final String LETRAS = "^[A-Za-zÀ-ÿ ]+$";
	    
	    public String validarNome(String nome, int min, int max) {
	        if (nome == null || nome.isBlank()) {
	            throw new ValidationException( " Nome é obrigatório");
	        }
	        
	        String nomeTrimado = nome.trim();
	        
	        if (nomeTrimado.length() < min || nomeTrimado.length() > max) {
	            throw new ValidationException(
	                String.format("Nome inválido: deve conter entre %d e %d caracteres", min, max)
	            );
	        }
	        
	        if (!nomeTrimado.matches(LETRAS)) {
	            throw new ValidationException( " inválido: use apenas letras");
	        }
	        
	        return nome;
	    }

}
