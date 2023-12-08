package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.senai.core.dao.DaoFornecedor;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Fornecedor;

public class DaoPostgresqlFornecedor implements DaoFornecedor{

	private final String SELECT = "SELECT id, nome_fantasia FROM fornecedores order by nome_fantasia";

	private Connection conexao;
	
	public DaoPostgresqlFornecedor() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	public Fornecedor extrairDo(ResultSet rs) {
		
		try {
			int id = rs.getInt("id");
			String nome_fantasia = rs.getString("nome_fantasia");
			return new Fornecedor(id, nome_fantasia);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair os fornecedores. Motivo: " + e.getMessage());
		}
		
	}
	
	@Override
	public ArrayList<Fornecedor> listarFornecedor() {
		ArrayList<Fornecedor> listaFornecedores = new ArrayList<Fornecedor>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				listaFornecedores.add(extrairDo(rs));
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar os fornecedores. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
			
		}
		return listaFornecedores;
	}
	

	
	
}
