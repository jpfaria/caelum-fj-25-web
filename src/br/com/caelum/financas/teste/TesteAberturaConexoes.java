package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;

public class TesteAberturaConexoes {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		
		
		for (int i = 1; i <= 30; i++) {
		
			EntityManager em = new JPAUtil().getEntityManager();
			em.getTransaction().begin();
			System.out.println("criado EntityManager numero " + i);
			
		}
		
		Thread.sleep(30 * 1000);
		

	}

}
