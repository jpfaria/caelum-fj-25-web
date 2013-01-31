package br.com.caelum.financas.install;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.TagDAO;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.Tag;
import br.com.caelum.financas.modelo.TipoMovimentacao;

public class MovimentacaoBuilder {
	
	private final int maximaQuantidadeDeTags;
	private final EntityManager em;
	
	private final String[] nomesDeTags = 
		new String[] {"restaurante", "família", "trabalho", "transporte", "entretenimento", "alimentação"};
	
	private List<Tag> tags;
	private Conta conta;

	public MovimentacaoBuilder(EntityManager em, int maximaQuantidadeDeTags) {
		this.maximaQuantidadeDeTags = maximaQuantidadeDeTags;
		this.em = em;
		criaContaManaged();
		criaEPersisteTags();
	}
	
	private void criaContaManaged() {
		conta = new Conta();
		conta.setTitular("Eu");
		conta.setAgencia("111");
		conta.setNumero("222");
		conta.setBanco("BB");
		em.persist(conta);
	}
	
	private void criaEPersisteTags() {
		TagDAO tagDAO = new TagDAO(em);
		
		tags = new ArrayList<Tag>();
		
		for (String nome : nomesDeTags) {
			tags.add(tagDAO.adicionaOuBuscaTagComNome(nome));
		}
	}
	
	public Movimentacao criaMovimentacao() {
		Random random = new Random();
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DATE, random.nextInt(15));
		
		Movimentacao nova = new Movimentacao();
		
		nova.setConta(conta);
		nova.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		nova.setValor(new BigDecimal(random.nextInt(1000)));
		nova.setData(data);
		
		return nova;
	}
	
	public List<Tag> geraListaAleatoriaDeTags() {
		Random random = new Random();
		
		Set<Tag> tagsAleatorias = new HashSet<Tag>();
		
		int maximaQuantidadePossivelDeTags = random.nextInt(maximaQuantidadeDeTags)+1;
		
		for (int j = 0; j<= maximaQuantidadePossivelDeTags; j++) {
			tagsAleatorias.add(tags.get(random.nextInt(tags.size())));
		}
		return new ArrayList<Tag>(tagsAleatorias);
	}
}
