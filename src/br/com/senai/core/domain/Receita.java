package br.com.senai.core.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Receita {
	
	private int id;
	private String descricao;
	private LocalDate dataDeLancamento;
	private double valor;
	private Cliente cliente;
	
	public Receita(String descricao, LocalDate dataDeLancamento, double valor, Cliente cliente) {
		this.descricao = descricao;
		this.dataDeLancamento = dataDeLancamento;
		this.valor = valor;
		this.cliente = cliente;
	}
	
	public Receita(int id, String descricao, LocalDate dataDeLancamento, double valor, Cliente cliente) {
		this(descricao, dataDeLancamento, valor, cliente);
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
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		Receita other = (Receita) obj;
		return id == other.id;
	}
	
	public boolean isPersistido() {
		return getId() > 0;
	}
	
}
