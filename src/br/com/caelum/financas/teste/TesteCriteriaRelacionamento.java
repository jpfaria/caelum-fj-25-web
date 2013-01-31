package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.MovimentacaoDAO;
import br.com.caelum.financas.infra.JPAUtil;

public class TesteCriteriaRelacionamento {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		MovimentacaoDAO dao = new MovimentacaoDAO(em);
		BigDecimal soma = dao.somaMovimentacoesDoTitular("%joao%");
		System.out.println(soma);

	}

}
