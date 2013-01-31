package br.com.caelum.financas.teste;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;


import br.com.caelum.financas.infra.ValidatorUtil;
import br.com.caelum.financas.modelo.Conta;

public class TesteValidacaoContaPossuiNumeroEAgencia {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Conta conta = new Conta();
		//conta.setTitular(null);
		conta.setAgencia("uma agencia qualquer");
		
		Validator validator = new ValidatorUtil().getValidator();
		Set<ConstraintViolation<Conta>> erros = validator.validate(conta);
		
		for (ConstraintViolation<Conta> erro : erros) {
			System.out.println(erro.getPropertyPath() + " - " + erro.getMessage());
		}

	}

}
