package br.com.caelum.financas.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

//import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import br.com.caelum.financas.validator.PossuiNumeroEAgencia;

@Entity
@PossuiNumeroEAgencia
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conta {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Version
	private Integer versao;
	
	//@NotNull
	@NotBlank
	private String titular;
	private String agencia;
	private String numero;
	private String banco;
	
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@OneToMany(mappedBy="conta")
	private List<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();

	public List<Movimentacao> getMovimentacoes() {
		return Collections.unmodifiableList(movimentacoes);
	}

	public void addMovimentacao(Movimentacao m) {
		m.setConta(this);
		this.movimentacoes.add(m);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}
	
	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}



}
