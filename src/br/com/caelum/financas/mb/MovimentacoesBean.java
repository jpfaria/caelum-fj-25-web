package br.com.caelum.financas.mb;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.ContaDAO;
import br.com.caelum.financas.dao.MovimentacaoDAO;
import br.com.caelum.financas.dao.TagDAO;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.Tag;
import br.com.caelum.financas.modelo.TipoMovimentacao;

@ManagedBean
public class MovimentacoesBean {
	
	private List<Movimentacao> movimentacoes;
	private Movimentacao movimentacao = new Movimentacao();
	private Integer contaId;
	private String tags;
	@ManagedProperty(name="em",value="#{requestScope.em}")
	private EntityManager em;
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public void grava() {
		
		
		ContaDAO contaDao = new ContaDAO(em);
		MovimentacaoDAO movimentacaoDao = new MovimentacaoDAO(em);
		
		Conta contaRelacionada = contaDao.busca(contaId);
		
		this.movimentacao.setConta(contaRelacionada);
		
		movimentacaoDao.adiciona(this.movimentacao);
		
		gravaEAssociaAsTags(em);
		
		this.movimentacoes = movimentacaoDao.lista();
		
		limpaFormularioDoJSF();
	}
	

	public void remove() {
		
		MovimentacaoDAO dao = new MovimentacaoDAO(em);
		Movimentacao movimentacaoParaRemover = dao.busca(this.movimentacao.getId());
		
		dao.remove(movimentacaoParaRemover);
		
		movimentacoes = dao.lista();
		
		limpaFormularioDoJSF();
	}

	public List<Movimentacao> getMovimentacoes() {
		if ( movimentacoes == null ) {
			MovimentacaoDAO dao = new MovimentacaoDAO(em);
			movimentacoes = dao.lista();
		}
		
		return movimentacoes;
	}
	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	

	public Movimentacao getMovimentacao() {
		if(movimentacao.getData()==null) {
			movimentacao.setData(Calendar.getInstance());
		}
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public Integer getContaId() {
		return contaId;
	}

	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}
	
	public TipoMovimentacao[] getTiposDeMovimentacao() {
		return TipoMovimentacao.values();
	}
	
	private void gravaEAssociaAsTags(EntityManager em) {
		String[] nomesDasTags = this.tags.split(" ");
		TagDAO tagDAO = new TagDAO(em);
		
		for (String nome : nomesDasTags) {
			
			Tag tag = tagDAO.adicionaOuBuscaTagComNome(nome);
			movimentacao.getTags().add(tag);
			
		}
	}

	/**
	 * Esse método apenas limpa o formulário da forma com que o JSF espera.
	 * Invoque-o no momento em que precisar do formulário vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.movimentacao = new Movimentacao();
		this.tags = null;
	}

	
}
