package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;

public class TesteCacheSegundoNivel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JPAUtil jutil = new JPAUtil();
		
		EntityManager primeiraEM = jutil.getEntityManager();
		
		primeiraEM.getTransaction().begin();
		
		Conta primeiraConta = primeiraEM.find(Conta.class, 3);
		
		primeiraEM.getTransaction().commit();
		primeiraEM.close();
		
		jutil.tiraDoCache(Conta.class, 3);
		
		
		EntityManager segundaEM = new JPAUtil().getEntityManager();
		
		Conta segundaConta = segundaEM.find(Conta.class, 3);
		
		segundaEM.close();
		
		System.out.println("Titular da primeira conta: " + primeiraConta.getTitular());

		System.out.println("Titular da primeira conta: " + segundaConta.getTitular());
		

	}

}
