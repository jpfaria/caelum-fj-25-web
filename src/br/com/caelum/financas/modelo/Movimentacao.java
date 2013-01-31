package br.com.caelum.financas.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Indexed // Indexa Movimentacao
public class Movimentacao {
	@Id
	@GeneratedValue
	private Integer id;
	
	//@Field(index=Index.TOKENIZER)
	private String descricao;
	
	private Calendar data;
	
	@DecimalMin("0.01")
	//@DecimalMin(value="0.01", message="O valor só pode ser igual ou acima de 1 centavo")
	private BigDecimal valor;

	@Valid // Força que Movimento carregue as validações de Conta
	@ManyToOne(fetch=FetchType.LAZY) // Forca nao carregar dinamicamente conta
	private Conta conta;

	@Enumerated(EnumType.STRING)
	private TipoMovimentacao tipoMovimentacao;

	@ManyToMany
	@LazyCollection(LazyCollectionOption.EXTRA)
	@IndexedEmbedded
	private List<Tag> tags = new ArrayList<Tag>();

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
