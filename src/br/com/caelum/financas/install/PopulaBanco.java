package br.com.caelum.financas.install;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.Tag;

public class PopulaBanco {
	private static final int MAX_QUANTIDADE_TAGS_POR_MOVIMENTACAO = 3;

	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		MovimentacaoBuilder builder = new MovimentacaoBuilder(em, MAX_QUANTIDADE_TAGS_POR_MOVIMENTACAO);
		
		for (int i = 0; i< 50; i++) {
			Movimentacao m = builder.criaMovimentacao();
			
			List<Tag> tagsAleatorias = builder.geraListaAleatoriaDeTags();
			
			m.setTags(tagsAleatorias);
			
			em.persist(m);
		}
		
		em.getTransaction().commit();
		em.close();
		System.exit(0);
	}
}
