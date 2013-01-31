package br.com.caelum.financas.modelo;

//import java.util.ArrayList;
//import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
//import javax.persistence.ManyToMany;

@Entity
public class Tag {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Field(index=Index.TOKENIZED)
	private String nome;
	
	//@ManyToMany(mappedBy="movimentacao")
	//private List<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/*
	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}
	*/

	@Override
	// TODO Auto-generated method stub
	public String toString() {	
		return this.getNome();
	}

	
}
