package br.com.caelum.financas.mb;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.ContaDAO;
import br.com.caelum.financas.infra.JPAUtil;
import br.com.caelum.financas.modelo.Conta;

@ViewScoped
@ManagedBean
public class ContasBean {
	private Conta conta = new Conta();
	private List<Conta> contas;

	public void grava() {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		ContaDAO dao = new ContaDAO(em);
		
		if (conta.getId() == null) {
			dao.adiciona(conta);
		} else {
			dao.altera(conta);
		}
		
		em.getTransaction().commit();
		
		em.close();
		
		limpaFormularioDoJSF();
	}

	public void remove() {
		
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		ContaDAO dao = new ContaDAO(em);
		Conta contaParaRemover = dao.busca(this.conta.getId());
		
		dao.remove(contaParaRemover);
		
		contas = dao.lista();
		
		em.getTransaction().commit();
		em.close();
		
		limpaFormularioDoJSF();
		
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<Conta> getContas() {
		
		if ( contas == null ) {
			EntityManager em = new JPAUtil().getEntityManager();
			ContaDAO dao = new ContaDAO(em);
			contas = dao.lista();
			em.close();
		}
		
		return contas;
	}

	/**
	 * Esse método apenas limpa o formulário da forma com que o JSF espera.
	 * Invoque-o no momento em que precisar do formulário vazio.
	 */
	private void limpaFormularioDoJSF() {
		this.conta = new Conta();
	}
}
