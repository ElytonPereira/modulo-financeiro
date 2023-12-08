package br.com.senai.core.service;

import java.util.ArrayList;

import br.com.senai.core.dao.DaoFornecedor;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Fornecedor;

public class FornecedorService {

	private DaoFornecedor dao;
	
	public FornecedorService() {
		this.dao = FactoryDao.getInstance().getDaoFornecdor();
	}
	
	public ArrayList<Fornecedor> listarFornecedor() {
		
		return dao.listarFornecedor();
	}
	
}
