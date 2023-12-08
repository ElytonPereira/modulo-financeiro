package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoCentroDeCusto;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.CentroDeCusto;

public class CentroDeCustoService {

	private DaoCentroDeCusto dao;
	
	public CentroDeCustoService() {
		this.dao = FactoryDao.getInstance().getDaoCentroDeCusto();
	}
	
	public void salvar(CentroDeCusto centroDeCusto) {
		
		this.validar(centroDeCusto);
		
		boolean isPersistido = centroDeCusto.getId() > 0;
		
		if(isPersistido) {
			this.dao.editar(centroDeCusto);
		}else {
			this.dao.inserir(centroDeCusto);
		}
		
	}
	
	public void validar(CentroDeCusto centroDeCusto) {
		
		if(centroDeCusto != null) {
			
			boolean isDescricaoInvalida = centroDeCusto.getDescricao() == null 
							|| centroDeCusto.getDescricao().isBlank();
					
			
			if (isDescricaoInvalida) {
				throw new IllegalArgumentException("A descrição é obrigatória");
			}
		
		} else {
			
			throw new IllegalArgumentException("O centro de custo é obrigatório");
		}
		
	}
	
	public void removerPor(int idCentro) {
		
		if (idCentro > 0) {
			
			boolean isRemocaoInvalida = this.dao.validarRemocao(idCentro);
			if (isRemocaoInvalida) {
				throw new IllegalArgumentException("Não é possível excluir um centro de custo vinculado a uma despesa");
			}
			
			this.dao.excluirPor(idCentro);
			
		} else {
			throw new IllegalArgumentException("O id para remoção deve ser maior que zero");			
		}
		
	}
		
	public List<CentroDeCusto> listarPor(String descricao){
		
		if (descricao != null && !descricao.isBlank()) {
			String filtro = "%" + descricao + "%";
			return dao.listarPor(filtro);
			
		} else {			
			throw new IllegalArgumentException("O filtro é obrigatório");
		}		
	}
	
	
	public List<CentroDeCusto> listarCentroDeCustos(){
		return dao.listarCentroDeCusto();
	}
	
}
