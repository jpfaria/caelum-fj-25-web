package br.com.caelum.financas.infra;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

/*
public class LuceneFullTextQueryUtil<T extends Analyzer, M> {

	
	private final EntityManager em;
	private Version matchVersion = Version.LUCENE_29;

	public LuceneFullTextQueryUtil(EntityManager em) {

		this.em = em;
		
	}

	public LuceneFullTextQueryUtil(EntityManager em, Version matchVersion) {
		
		this(em);
		this.matchVersion = matchVersion;
		
	}

	public List<M> getResultList(String search, String field) {

		//Object<T> analyzer = T.getInstance();
		
		FullTextEntityManager fullTextEntityManager = Search
				.getFullTextEntityManager(em);
		
		QueryParser parser = new QueryParser(this.matchVersion, field, this.getAnalyzer());

		org.apache.lucene.search.Query query = parser.parse(search);
		
		FullTextQuery textQuery = fullTextEntityManager.createFullTextQuery(query, Class<M>);
		
		return textQuery.getResultList();

	}
	
	public Analyzer getAnalyzer() {
		
		Type type = LuceneFullTextQueryUtil.class.getGenericSuperclass();
		
		ParameterizedType paramType = (ParameterizedType)type;
                tClass = (Class<T>) paramType.getActualTypeArguments()[0];

                field = tClass.newInstance();
        }
        
        
		return analyzer;
		
	}

	
}
*/