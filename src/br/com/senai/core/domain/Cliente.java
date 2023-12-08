package br.com.senai.core.domain;

import java.util.Objects;

public class Cliente {
	
	private int id;
	private String nome;
	
	public Cliente(String nome) {
		this.nome = nome;
	}
	
	public Cliente(int id, String nome) {
		this(nome);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
		Cliente other = (Cliente) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return nome;
	}
	
}
