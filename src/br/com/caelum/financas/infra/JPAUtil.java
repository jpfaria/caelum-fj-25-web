package br.com.caelum.financas.infra;

import java.io.Serializable;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory entityManagerFactory;
	
	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("controlefinancas");
	}
	
	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	public void tiraDoCache(Class<?> entityClass, Serializable id) {
		Cache cache = entityManagerFactory.getCache();
		cache.evict(entityClass, id);
	}
	
}
