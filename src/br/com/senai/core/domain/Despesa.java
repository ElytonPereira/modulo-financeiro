package br.com.senai.core.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Despesa {

	private int id;
	private String descricao;
	private LocalDate dataDeLancamento;
	private double valor;
	private Fornecedor fornecedor;
	private CentroDeCusto centroDeCusto;
	
	public Despesa(String descricao, LocalDate dataDeLancamento, double valor, Fornecedor fornecedor,
			CentroDeCusto centroDeCusto) {
		this.descricao = descricao;
		this.dataDeLancamento = dataDeLancamento;
		this.valor = valor;
		this.fornecedor = fornecedor;
		this.centroDeCusto = centroDeCusto;
	}
	
	public Despesa(int id, String descricao, LocalDate dataDeLancamento, double valor, Fornecedor fornecedor,
			CentroDeCusto centroDeCusto) {
		this(descricao, dataDeLancamento, valor, fornecedor, centroDeCusto);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getDataDeLancamento() {
		return dataDeLancamento;
	}
	public void setDataDeLancamento(LocalDate dataDeLancamento) {
		this.dataDeLancamento = dataDeLancamento;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public CentroDeCusto getCentroDeCusto() {
		return centroDeCusto;
	}
	public void setCentroDeCusto(CentroDeCusto centroDeCusto) {
		this.centroDeCusto = centroDeCusto;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Despesa other = (Despesa) obj;
		return id == other.id;
	}
	
	public boolean isPersistido() {
		return getId() > 0;
	}
	
}
