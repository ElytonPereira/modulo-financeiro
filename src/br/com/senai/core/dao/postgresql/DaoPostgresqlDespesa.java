package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoDespesa;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.CentroDeCusto;
import br.com.senai.core.domain.Despesa;
import br.com.senai.core.domain.Fornecedor;

public class DaoPostgresqlDespesa implements DaoDespesa{

	private final String INSERT = "INSERT INTO despesas (descricao, data_lancto, valor, id_fornecedor, id_centro_custo) VALUES (?,?,?,?,?)";
	
	private final String UPDATE = "UPDATE despesas SET descricao = ?,  data_lancto = ?, valor = ?, id_fornecedor = ?, id_centro_custo = ? where id = ?";
	
	private final String DELETE = "DELETE FROM despesas where id = ?";
	
	private final String SELECT = "SELECT d.id, d.descricao, d.data_lancto, d.valor, "
			+ "d.id_fornecedor, d.id_centro_custo, f.id as id_fornecedor, f.nome_fantasia, cc.id as id_centro_de_custo, cc.descricao "
			+ "FROM despesas  d left join fornecedores f on d.id_fornecedor = f.id "
			+ "left join centros_custos cc on d.id_centro_custo = cc.id "
			+ "ORDER BY id";
	
	private final String SELECT_BY_NOME_FANTASIA = "SELECT d.id, d.descricao, d.data_lancto, d.valor, "
			+ "d.id_fornecedor, d.id_centro_custo, f.id as id_fornecedor, f.nome_fantasia, cc.id as id_centro_de_custo, cc.descricao "
			+ "FROM despesas  d left join fornecedores f on d.id_fornecedor = f.id "
			+ "left join centros_custos cc on d.id_centro_custo = cc.id   "
			+ "WHERE Upper(f.nome_fantasia) LIKE Upper(?)";
	
	
	
	private Connection conexao;
	
	public DaoPostgresqlDespesa() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	@Override
	public void inserir(Despesa despesa) {
		PreparedStatement ps = null;
		
		try {
			
			ps = conexao.prepareStatement(INSERT);
			ps.setString(1, despesa.getDescricao());
			ps.setDate(2, Date.valueOf(despesa.getDataDeLancamento()));
			ps.setDouble(3, despesa.getValor());
			ps.setInt(4, despesa.getFornecedor().getId());
			ps.setInt(5, despesa.getCentroDeCusto().getId());
			ps.execute();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao inserir a despesa. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}		
	}

	@Override
	public void alterar(Despesa despesa) {
		PreparedStatement ps = null;
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			ps = conexao.prepareStatement(UPDATE);
			
			ps.setString(1, despesa.getDescricao());
			ps.setDate(2, Date.valueOf(despesa.getDataDeLancamento()));
			ps.setDouble(3, despesa.getValor());
			ps.setInt(4, despesa.getFornecedor().getId());
			ps.setInt(5, despesa.getCentroDeCusto().getId());
			ps.setInt(6, despesa.getId());
			
			boolean isAlteracaoOk = ps.executeUpdate() == 1;
			if (isAlteracaoOk) {
				this.conexao.commit();
			} else {
				this.conexao.rollback();				
			}
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);		
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na alteração da despesa. Motivo: " + e.getMessage());
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
			throw new RuntimeException("Ocorreu um erro na exclução da despesa. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);			
		}		
	}
	
	public Despesa extrairDo(ResultSet rs) {
		
		try {
			int idDespesa = rs.getInt("id");
			String descricaoDespesa = rs.getString("descricao");
			LocalDate dataLancto = rs.getDate("data_lancto").toLocalDate();
			double valor = rs.getDouble("valor");
			
			
			int idFornecedor = rs.getInt("id_fornecedor");
			String nomeFantasia = rs.getString("nome_fantasia");
			Fornecedor fornecedor = new Fornecedor(idFornecedor, nomeFantasia);
			
			int idCentroDeCusto = rs.getInt("id_centro_de_custo");
			String descricaoCentroDeCusto = rs.getString("descricao");
			CentroDeCusto centroDeCusto = new CentroDeCusto(idCentroDeCusto, descricaoCentroDeCusto);
			
			return new Despesa(idDespesa, descricaoDespesa, dataLancto, valor, fornecedor, centroDeCusto);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair a despesa. Motivo: " + e.getMessage());
		}
	}	

	@Override
	public List<Despesa> listarPor(String nomeFantasia) {
		List<Despesa> despesas = new ArrayList<Despesa>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT_BY_NOME_FANTASIA);
			ps.setString(1, nomeFantasia);
			
			rs= ps.executeQuery();
			while (rs.next()) {
				despesas.add(extrairDo(rs));				
			}
			return despesas;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar as despesas. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}		
	}
	
	@Override
	public List<Despesa> listar() {
		List<Despesa> despesas = new ArrayList<Despesa>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				despesas.add(extrairDo(rs));				
			}
			return despesas;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar as despesas. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}		
	}

}
