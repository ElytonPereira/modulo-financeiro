package br.com.senai.core.domain;

import java.util.Objects;

public class Fornecedor {
	
	private int id;
	private String nomeFantasia;
	
	public Fornecedor(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public Fornecedor(int id, String nomeFantasia) {
		this(nomeFantasia);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
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
		Fornecedor other = (Fornecedor) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return nomeFantasia;
	}
	
}
