package br.com.caelum.financas.mb;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.MovimentacaoDAO;
import br.com.caelum.financas.infra.search.ElementoDaBusca;
import br.com.caelum.financas.infra.search.Presenca;
import br.com.caelum.financas.infra.search.Semelhanca;
import br.com.caelum.financas.modelo.Movimentacao;

@ManagedBean
public class BuscaTextualDSLBean {
	@ManagedProperty(value = "#{elementosEscolhidosBean}")
	private ElementosEscolhidosBean escolhidos;

	@ManagedProperty(name = "em", value = "#{requestScope.em}")
	private EntityManager em;

	private ElementoDaBusca elemento = new ElementoDaBusca();
	
	private List<Movimentacao> movimentacoes;
	
	public void buscaUsandoDSL() {
		MovimentacaoDAO dao = new MovimentacaoDAO(em);
		movimentacoes = dao.buscaAvancada(this.elemento);
	}

	private void limpaMovimentacoes() {
		movimentacoes = new ArrayList<Movimentacao>();
	}

	public void limpaBusca() {
		this.escolhidos.limpa();
		limpaMovimentacoes();
	}

	public void desfazUltima() {
		this.escolhidos.desfazUltima();
		limpaMovimentacoes();
	}

	public void adicionaNaBusca() {
		limpaMovimentacoes();
		this.escolhidos.adiciona(elemento);
		this.elemento = new ElementoDaBusca();
	}
	
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public void setEscolhidos(ElementosEscolhidosBean apoio) {
		this.escolhidos = apoio;
	}

	public Presenca[] getTiposDePresenca() {
		return Presenca.values();
	}

	public Semelhanca[] getGrausDeSemelhanca() {
		return Semelhanca.values();
	}

	public ElementoDaBusca getElemento() {
		return elemento;
	}
}
