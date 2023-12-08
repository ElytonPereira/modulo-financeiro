package br.com.senai.core.domain;

import java.util.List;

public class Demonstrativo {
	
	private List<Despesa> despesas;
	private List<Receita> receitas;
	
	public Demonstrativo(List<Despesa> despesas, List<Receita> receitas) {
		this.despesas = despesas;
		this.receitas = receitas;
	}
	
	public List<Despesa> getDespesas() {
		return despesas;
	}
	public void setDespesas(List<Despesa> despesas) {
		this.despesas = despesas;
	}
	public List<Receita> getReceitas() {
		return receitas;
	}
	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}
	
}
