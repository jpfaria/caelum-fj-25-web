package br.com.caelum.financas.infra;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorUtil {
	
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	
	public Validator getValidator() {
		return factory.getValidator();
	}

}
