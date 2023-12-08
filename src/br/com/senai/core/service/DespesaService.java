package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoDespesa;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Despesa;

public class DespesaService {
	
	private DaoDespesa dao;
	
	public DespesaService() {
		this.dao = FactoryDao.getInstance().getDaoDespesa();		
	}
	
	public void salvar(Despesa despesa) {
		this.validar(despesa);
		
		boolean isPersistido = despesa.getId() > 0;
		
		if (isPersistido) {
			this.dao.alterar(despesa);
		} else {
			this.dao.inserir(despesa);
		}		
	}

	private void validar(Despesa despesa) {
		
		if(despesa != null) {
			
			boolean isDescricaoInvalida = despesa.getDescricao() == null || despesa.getDescricao().isBlank();
			
			if (isDescricaoInvalida) {
				throw new IllegalArgumentException("A descrição é obrigatória");
			}
			
			boolean isDataLancamentoInvalida = despesa.getDataDeLancamento() == null
					|| despesa.getDataDeLancamento().equals("  /  /    ");
			
			if (isDataLancamentoInvalida) {
				throw new IllegalArgumentException("A data de lançamento é obrigatória e deve estar no formato DD/MM/AAAA");
			}
			
			boolean isValorInvalido = despesa.getValor() < 0;
			
			if (isValorInvalido) {
				throw new IllegalArgumentException("O valor deve ser positivo");
			}
			
			boolean isFornecedorInvalido = despesa.getFornecedor() == null;
			
			if (isFornecedorInvalido) {
				throw new IllegalArgumentException("O fornecedor é obrigatório");
			}
			
			boolean isCentroDeCustoInvalido = despesa.getCentroDeCusto() == null;
			
			if (isCentroDeCustoInvalido) {
				throw new IllegalArgumentException("O centro de custo é obrigatório");
			}
			
			
		} else {
			throw new IllegalArgumentException("A despesa é obrigatória");
		}
		
	}
	
	public void removerPor(int idDaDespesa) {
		if (idDaDespesa > 0) {
			this.dao.excluirPor(idDaDespesa);
			} else {				
				throw new IllegalArgumentException("O id para remoção deve ser maior que zero");
			}		
	}	
	
	public List<Despesa> listarPor(String nomeFantasia){
		
		if (nomeFantasia != null && !nomeFantasia.isBlank()) {
			String filtro = "%" + nomeFantasia + "%";
			return dao.listarPor(filtro);
			
		} else {			
			throw new IllegalArgumentException("O filtro é obrigatório");
		}		
	}
	
	public List<Despesa> listar() {
		return this.dao.listar();
	}
	
}
