package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoReceita;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Receita;

public class ReceitaService {
	
	private DaoReceita dao;
	
	public ReceitaService() {
		this.dao = FactoryDao.getInstance().getDaoReceita();
	}
	
	public void salvar(Receita receita) {
		this.validar(receita);
		
		if (receita.isPersistido()) {
			this.dao.alterar(receita);
		} else {
			this.dao.inserir(receita);
		}
	}
	
	public void validar(Receita receita) {
		if (receita != null) {
			
			boolean isDescricaoInvalida = receita.getDescricao() == null
					|| receita.getDescricao().isBlank();
			if (isDescricaoInvalida) {
				throw new IllegalArgumentException("A descrição é obrigatória");
			}
			
			boolean isDataDeLancamentoInvalida = receita.getDataDeLancamento() == null
					|| receita.getDataDeLancamento().equals("  /  /    ");
			if (isDataDeLancamentoInvalida) {
				throw new IllegalArgumentException("A data de lançamento é obrigatória e deve estar no formato DD/MM/AAAA");
			}
			
			boolean isValorInvalido = receita.getValor() < 0;
			if (isValorInvalido) {
				throw new IllegalArgumentException("O valor deve ser positivo");
			}
			
			boolean isClienteInvalido = receita.getCliente() == null;
			if (isClienteInvalido) {
				throw new IllegalArgumentException("O cliente é obrigatório");
			}
			
		} else {
			throw new NullPointerException("A receita é obrigatória");
		}
	}

	public void removerPor(int id) {
		if (id > 0) {
			this.dao.excluirPor(id);
		} else {
			throw new IllegalArgumentException("O id para remoção deve ser maior que zero.");
		}
	}
	
	public List<Receita> listarPor(String nomeCliente) {
		if (nomeCliente != null && !nomeCliente.isBlank()) {
			String filtro = "%" + nomeCliente + "%";
			return this.dao.listarPor(filtro);
		} else {
			throw new IllegalArgumentException("O filtro para a listagem é obrigatório");
		}
	}
	
	public List<Receita> listar() {
		return this.dao.listar();
	}

}
