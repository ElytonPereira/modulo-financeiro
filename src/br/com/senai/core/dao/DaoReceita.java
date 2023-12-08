package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.Receita;

public interface DaoReceita {
	
	public void inserir(Receita receita);
	
	public void alterar(Receita receita);
	
	public void excluirPor(int id);
	
	public List<Receita> listarPor(String nomeCompleto);
	
	public List<Receita> listar();
	
}
