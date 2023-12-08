package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoCliente;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Cliente;

public class DaoPostgresqlCliente implements DaoCliente{
	
	private final String SELECT = "SELECT c.id, c.nome_completo FROM clientes AS c ORDER BY c.nome_completo";
	
	private Connection conexao;
	
	public DaoPostgresqlCliente() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	@Override
	public List<Cliente> listarClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT);			
			rs = ps.executeQuery();			
			while (rs.next()) {			
				clientes.add(extrairDo(rs));
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na listagem das receitas. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
			
		}
		return clientes;
	}
	
	private Cliente extrairDo(ResultSet rs) {
		
		try {
			int id = rs.getInt("id");
			String nomeCompleto = rs.getString("nome_completo");
			return new Cliente(id, nomeCompleto);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na extração das receitas. Motivo: " + e.getMessage());
		}
		
	}
	
}
