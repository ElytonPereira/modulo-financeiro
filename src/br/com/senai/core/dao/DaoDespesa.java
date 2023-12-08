package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.Despesa;

public interface DaoDespesa {

	public void inserir(Despesa despesa);
	
	public void alterar(Despesa despesa);
	
	public void excluirPor(int id);
	
	public List<Despesa> listarPor(String nomeFantasia);
	
	public List<Despesa> listar();
	
}
