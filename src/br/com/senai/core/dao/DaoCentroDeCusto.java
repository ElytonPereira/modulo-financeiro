package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.CentroDeCusto;

public interface DaoCentroDeCusto {

public void inserir(CentroDeCusto centroDeCusto);
	
	public void editar(CentroDeCusto centroDeCusto);
	
	public void excluirPor(int id);
	
	public List<CentroDeCusto> listarPor(String descricao);
	
	public List<CentroDeCusto> listarCentroDeCusto();
	
	public boolean validarRemocao(int id);
	
}
