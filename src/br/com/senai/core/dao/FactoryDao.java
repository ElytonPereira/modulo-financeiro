package br.com.senai.core.dao;

import br.com.senai.core.dao.postgresql.DaoPostgresqlCliente;
import br.com.senai.core.dao.postgresql.DaoPostgresReceita;
import br.com.senai.core.dao.postgresql.DaoPostgresqlCentroDeCusto;
import br.com.senai.core.dao.postgresql.DaoPostgresqlDespesa;
import br.com.senai.core.dao.postgresql.DaoPostgresqlFornecedor;
import br.com.senai.core.dao.postgresql.DaoPostgresqlLogDeAcessos;
import br.com.senai.core.dao.postgresql.DaoPostgresqlUsuario;

public class FactoryDao {

	private static FactoryDao instance;
	
	public FactoryDao() {
	
	}
	
	public DaoDespesa getDaoDespesa() {
		
		return new DaoPostgresqlDespesa();
	}
	
	public DaoFornecedor getDaoFornecdor() {
		
		return new DaoPostgresqlFornecedor();
	}
	
	public DaoCentroDeCusto getDaoCentroDeCusto() {
		
		return new DaoPostgresqlCentroDeCusto();
	}
	
	public DaoReceita getDaoReceita() {
		
		return new DaoPostgresReceita();
		
	}
	
	public DaoCliente getDaoCliente() {
		
		return new DaoPostgresqlCliente();
		
	}
	
	public DaoUsuario getDaoUsuario() {
		
		return new DaoPostgresqlUsuario();
		
	}
	
	public DaoLogDeAcessos getDaoLogDeAcessos() {
		
		return new DaoPostgresqlLogDeAcessos();
		
	}
	
	public static FactoryDao getInstance() {
		
		if (instance == null) {
			instance = new FactoryDao();
		}
		return instance;
	}
	
}
