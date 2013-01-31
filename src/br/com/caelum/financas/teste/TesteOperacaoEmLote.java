package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;

public class TesteOperacaoEmLote {

	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		Conta conta = em.find(Conta.class, 3);
		
		String nomeDoBancoASerAlterado = conta.getBanco();
		
		String jpql = "UPDATE Conta c SET c.banco = :novoNome WHERE c.banco = :antigoNome";
		
		Query query = em.createQuery(jpql);
		
		query.setParameter("antigoNome", nomeDoBancoASerAlterado);
		query.setParameter("novoNome", "Novo nome");
		
		int contasAlteradas = query.executeUpdate();
		
		System.out.println("Quantidade de contas alteradas: " + contasAlteradas);
		System.out.println("Nome antigo: " + nomeDoBancoASerAlterado);
		
		em.detach(conta);
		
		Conta contaAlterada = em.find(Conta.class, 3);
		System.out.println("Novo nome: " + contaAlterada.getBanco());
		
		em.getTransaction().commit();
		em.close();

		
		
		
	}
	
}
