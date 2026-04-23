package com.project.Mesa.Validation.validators.campanha;

import java.util.List;

import org.springframework.stereotype.Component;

import com.project.Mesa.Model.Campanha;
import com.project.Mesa.Service.exception.ValidationException;
import com.project.Mesa.Validation.BaseValidation;

@Component
public class CampanhaValidator {
 	private final List<BaseValidation<Campanha>> validators;
 	
	public CampanhaValidator( List<BaseValidation<Campanha>> validators) {
		this.validators = validators;
	}
	
	public void validaCamposdeCampanha(Campanha campanha) {
	if(campanha == null)
		throw new ValidationException("Dados de campanhas necessário");
		validators.forEach(v -> v.validar(campanha));	
	}
}
