package br.com.caelum.financas.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE) // Só poderá utilizar esta validação em uma classe.
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PossuiNumeroEAgenciaValidator.class)
public @interface PossuiNumeroEAgencia {

	String message() default "{br.com.caelum.financas.validator.PossuiNumeroEAgencia.message}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}
