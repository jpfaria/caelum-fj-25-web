package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.Tag;

public class TesteRecuperaTagsAssociadasAUmaMovimentacao {
	
	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		Movimentacao movimentacao = em.find(Movimentacao.class, 1);
		
		for (Tag tag : movimentacao.getTags()) {
			System.out.println(tag.getNome());
		}
		
		em.close();
		
	}

}
