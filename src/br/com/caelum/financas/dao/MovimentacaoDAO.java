package br.com.caelum.financas.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import br.com.caelum.financas.infra.JPAUtil;
//import br.com.caelum.financas.infra.LuceneFullTextQueryUtil;
import br.com.caelum.financas.infra.search.ElementoDaBusca;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.modelo.ValorPorMesEAno;

public class MovimentacaoDAO {

	private final DAO<Movimentacao> dao;
	private final EntityManager em;

	public MovimentacaoDAO(EntityManager em) {
		this.em = em;
		dao = new DAO<Movimentacao>(em, Movimentacao.class);
	}

	public void adiciona(Movimentacao t) {
		dao.adiciona(t);
	}

	public Movimentacao busca(Integer id) {
		return dao.busca(id);
	}

	public List<Movimentacao> lista() {
		return dao.lista();
	}

	public List<Movimentacao> listaTodasMovimentacoes(Conta conta) {

		EntityManager em = new JPAUtil().getEntityManager();

		String jpql = "select m from Movimentacao m "
				+ "where m.conta = :conta order by m.valor desc";

		Query q = em.createQuery(jpql);
		q.setParameter("conta", conta);

		return q.getResultList();

	}

	public List<Movimentacao> listaPorValorETipo(BigDecimal valor,
			TipoMovimentacao tipo) {

		EntityManager em = new JPAUtil().getEntityManager();

		String jpql = "select m from Movimentacao m "
				+ "where m.valor <= :valor and m.tipoMovimentacao = :tipo";

		Query q = em.createQuery(jpql);
		
		q.setParameter("valor", valor);
		q.setParameter("tipo", tipo);
		
		q.setHint("org.hibernate.cacheable", "true");

		return q.getResultList();

	}

	public BigDecimal calculaTotalMovimentado(Conta conta, TipoMovimentacao tipo) {

		String jpql = "select sum(m.valor) from Movimentacao m where m.conta = :conta and m.tipoMovimentacao = :tipo";

		TypedQuery<BigDecimal> q = em.createQuery(jpql, BigDecimal.class);

		q.setParameter("conta", conta);
		q.setParameter("tipo", tipo);

		return q.getSingleResult();

	}

	public List<Movimentacao> buscaTodasMovimentacoesDaConta(String titular) {

		String jpql = "select m from Movimentacao m where m.conta.titular like :titular";
		TypedQuery<Movimentacao> q = this.em.createQuery(jpql,
				Movimentacao.class);

		q.setParameter("titular", "%" + titular + "%");

		return q.getResultList();

	}

	public List<ValorPorMesEAno> listaMesesComMovimentacoes(Conta conta,
			TipoMovimentacao tipo) {

		String jpql = "select new br.com.caelum.financas.modelo.ValorPorMesEAno(month(m.data), year(m.data), sum(m.valor)) from Movimentacao m where m.conta = :conta and m.tipoMovimentacao = :tipo group by year(m.data)||month(m.data) order by sum(m.valor) desc";
		Query q = this.em.createQuery(jpql);

		q.setParameter("conta", conta);
		q.setParameter("tipo", tipo);

		List<ValorPorMesEAno> r = q.getResultList();

		return r;
	}

	public void remove(Movimentacao t) {
		dao.remove(t);
	}

	public List<Movimentacao> todasComCriteria() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Movimentacao> criteria = builder
				.createQuery(Movimentacao.class);
		criteria.from(Movimentacao.class);

		return em.createQuery(criteria).getResultList();
	}

	public BigDecimal somaMovimentacoesDoTitular(String titular) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteria = builder
				.createQuery(BigDecimal.class);

		Root<Movimentacao> root = criteria.from(Movimentacao.class);

		criteria.select(builder.sum(root.<BigDecimal> get("valor")));

		criteria.where(builder.like(
				root.<Conta> get("conta").<String> get("titular"), titular));

		return this.em.createQuery(criteria).getSingleResult();

	}

	public List<Movimentacao> pesquisa(Conta conta,
			TipoMovimentacao tipoMovimentacao, Integer mes) {

		CriteriaBuilder builder = this.em.getCriteriaBuilder();

		CriteriaQuery<Movimentacao> criteria = builder
				.createQuery(Movimentacao.class);

		Root<Movimentacao> root = criteria.from(Movimentacao.class);

		Predicate conjunction = builder.conjunction();

		if (conta.getId() == null) {

			conjunction = builder.and(conjunction,
					builder.equal(root.<Conta> get("conta"), conta));

		}

		if (mes != null && mes != 0) {

			Expression<Integer> expression = builder.function("month",
					Integer.class, root.<Calendar> get("data"));

			conjunction = builder.and(conjunction,
					builder.equal(expression, mes));
		}

		if (tipoMovimentacao != null) {

			conjunction = builder.and(conjunction, builder.equal(
					root.<TipoMovimentacao> get("tipoMovimentacao"),
					tipoMovimentacao));

		}

		criteria.where(conjunction);

		return this.em.createQuery(criteria).getResultList();
	}

	public List<Movimentacao> buscaMovimentacoesBaseadoNasTags(String texto) {

		/*
		return new
		LuceneFullTextQueryUtil<BrazilianAnalyzer, Movimentacao>(em).getResultList(texto,"tags.nome");
		*/
		
		FullTextEntityManager fullTextEntityManager = Search
				.getFullTextEntityManager(em);

		QueryParser parser = new QueryParser(Version.LUCENE_29, "tags.nome",
				new BrazilianAnalyzer(Version.LUCENE_29));

		try {

			org.apache.lucene.search.Query query = parser.parse(texto);

			FullTextQuery textQuery = fullTextEntityManager
					.createFullTextQuery(query, Movimentacao.class);

			return textQuery.getResultList();

		} catch (ParseException e) {

			throw new IllegalArgumentException();

		}

	}

	public List<Movimentacao> buscaAvancada(ElementoDaBusca elemento) {

		FullTextEntityManager fullTextEM = Search.getFullTextEntityManager(em);

		QueryBuilder queryBuilder = fullTextEM.getSearchFactory()
				.buildQueryBuilder().forEntity(Movimentacao.class).get();

		org.apache.lucene.search.Query query = queryBuilder
				.keyword()
				.fuzzy()
				.withThreshold(elemento.getSemelhanca().getValor())
				.boostedTo(elemento.getMultiplicador())
				.onField("tags.nome")
				.matching(elemento.getTexto()).createQuery();
		
		Query jpaQuery = fullTextEM.createFullTextQuery(query, Movimentacao.class);
		
		return jpaQuery.getResultList();
			
	}

}