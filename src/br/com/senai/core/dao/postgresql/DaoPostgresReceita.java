package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoReceita;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Cliente;
import br.com.senai.core.domain.Receita;

public class DaoPostgresReceita implements DaoReceita{

	private final String INSERT = "INSERT INTO receitas (descricao, data_lancto, valor, id_cliente) VALUES (?,?,?,?)";
	
	private final String UPDATE = "UPDATE receitas SET descricao = ?,  data_lancto = ?, valor = ?, id_cliente = ? "
			+ "WHERE id = ?";
	
	private final String DELETE = "DELETE FROM receitas "
			+ "WHERE id = ?";
	
	private final String SELECT = "SELECT r.id, r.descricao, r.data_lancto, r.valor, r.id_cliente, c.id AS id_cliente, c.nome_completo "
			+ "FROM receitas as r, clientes as c "
			+ "WHERE c.id = r.id_cliente "
			+ "ORDER BY id";
	
	private final String SELECT_BY_NOME_CLIENTE= "SELECT r.id, r.descricao, r.data_lancto, r.valor, r.id_cliente, c.id AS id_cliente, c.nome_completo "
			+ "FROM receitas as r, clientes as c "
			+ "WHERE c.id = r.id_cliente "
			+ "AND Upper(c.nome_completo) LIKE Upper(?)";
	
	private Connection conexao;
	
	public DaoPostgresReceita() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	@Override
	public void inserir(Receita receita) {
		
		PreparedStatement ps = null;
		
		try {
			ps = conexao.prepareStatement(INSERT);
			
			ps.setString(1, receita.getDescricao());
			ps.setDate(2, Date.valueOf(receita.getDataDeLancamento()));
			ps.setDouble(3, receita.getValor());
			ps.setInt(4, receita.getCliente().getId());
			
			ps.execute();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na inserção da receita. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}		
	}

	@Override
	public void alterar(Receita receita) {
		
		PreparedStatement ps = null;
		
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			
			ps = conexao.prepareStatement(UPDATE);
			
			ps.setString(1, receita.getDescricao());
			ps.setDate(2, Date.valueOf(receita.getDataDeLancamento()));
			ps.setDouble(3, receita.getValor());
			ps.setInt(4, receita.getCliente().getId());
			ps.setInt(5, receita.getId());
			
			boolean isAlteracaoOk = ps.executeUpdate() == 1;
			if (isAlteracaoOk) {
				this.conexao.commit();
			} else {
				this.conexao.rollback();				
			}
			
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);		
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na alteração da receita. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);			
		}
		
	}

	@Override
	public void excluirPor(int id) {
		
		PreparedStatement ps = null;
		
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			
			ps = conexao.prepareStatement(DELETE);
			
			ps.setInt(1, id);
			
			boolean isExclusaoOk = ps.executeUpdate() == 1;
			if (isExclusaoOk) {
				this.conexao.commit();
			} else {
				this.conexao.rollback();
			}
			
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na exclusão da receita. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			
		}
		
	}
	
	@Override
	public List<Receita> listarPor(String nomeCompleto) {
		
		List<Receita> receitas = new ArrayList<Receita>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT_BY_NOME_CLIENTE);
			
			ps.setString(1, nomeCompleto);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				receitas.add(extrairDo(rs));				
			}
			
			return receitas;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar as receitas. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		
	}
	
	@Override
	public List<Receita> listar() {
		
		List<Receita> receitas = new ArrayList<Receita>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				receitas.add(extrairDo(rs));				
			}
			
			return receitas;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar as receitas. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		
	}

	public Receita extrairDo(ResultSet rs) {
		
		try {
			int id = rs.getInt("id");
			String descricao = rs.getString("descricao");
			LocalDate dataDeLancamento = rs.getDate("data_lancto").toLocalDate();
			double valor = rs.getDouble("valor");
			
			int idCliente = rs.getInt("id_cliente");
			String nomeCliente = rs.getString("nome_completo");
			Cliente cliente = new Cliente(idCliente, nomeCliente);
			
			return new Receita(id, descricao, dataDeLancamento, valor, cliente);
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair a receita. Motivo: " + e.getMessage());
		}
	}
	
}

