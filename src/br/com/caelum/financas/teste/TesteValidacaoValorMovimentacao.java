package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;


import br.com.caelum.financas.infra.ValidatorUtil;
import br.com.caelum.financas.modelo.Movimentacao;

public class TesteValidacaoValorMovimentacao {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		Movimentacao m = new Movimentacao();
		m.setValor(BigDecimal.ZERO);
		
		Validator validator = new ValidatorUtil().getValidator();
		Set<ConstraintViolation<Movimentacao>> erros = validator.validate(m);
		
		for (ConstraintViolation<Movimentacao> erro : erros) {
			System.out.println(erro.getPropertyPath() + " - " + erro.getMessage());
		}

	}

}
