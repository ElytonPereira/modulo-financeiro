package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoCentroDeCusto;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.CentroDeCusto;

public class DaoPostgresqlCentroDeCusto implements DaoCentroDeCusto{

	
private final String INSERT = "INSERT INTO centros_custos (descricao) VALUES (?)";
	
	private final String UPDATE = "UPDATE centros_custos SET descricao = ? WHERE id =?";
	
	private final String DELETE = "DELETE FROM centros_custos where id = ?";
	
	private final String SELECT = "SELECT cc.id, cc.descricao FROM centros_custos cc ORDER BY cc.descricao";

	private final String SELECT_BY_DESCRICAO = "SELECT cc.id, cc.descricao FROM centros_custos As cc WHERE Upper(cc.descricao) LIKE Upper(?)";
	
	private final String SELECT_ID_EXISTENTE = "SELECT count(despesas.id_centro_custo) as qtde FROM despesas WHERE despesas.id_centro_custo = ? ";
	
	private Connection conexao;
	
	public DaoPostgresqlCentroDeCusto() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	@Override
	public List<CentroDeCusto> listarCentroDeCusto() {
		List<CentroDeCusto> listarCentros= new ArrayList<CentroDeCusto>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT);			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				listarCentros.add(extrairDo(rs));
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar os centros de custo. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
			
		}
		return listarCentros;
	}

	private CentroDeCusto extrairDo(ResultSet rs) {
		
		try {
			int id = rs.getInt("id");
			String descricao = rs.getString("descricao");
			return new CentroDeCusto(id, descricao);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair o centro de custo. Motivo: " + e.getMessage());
		}
		
	}

	public void inserir(CentroDeCusto centroDeCusto) {
			PreparedStatement ps = null;
		
		try {
			
			ps = conexao.prepareStatement(INSERT);
			ps.setString(1, centroDeCusto.getDescricao());
			ps.execute();
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao inserir o centro de custo. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}		
	}

	@Override
	public void editar(CentroDeCusto centroDeCusto) {
		PreparedStatement ps = null;
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			ps = conexao.prepareStatement(UPDATE);
			
			ps.setString(1, centroDeCusto.getDescricao());
			ps.setInt(2, centroDeCusto.getId());
			
			boolean isAlteracaoOk = ps.executeUpdate() == 1;
			if (isAlteracaoOk) {
				this.conexao.commit();
			} else {
				this.conexao.rollback();				
			}
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);		
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na alteração do centro de custo. Motivo: " + e.getMessage());
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
			throw new RuntimeException("Ocorreu um erro na exclução do centro de custo. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			
		}
		
	}

	@Override
	public List<CentroDeCusto> listarPor(String descricao) {
		
		List<CentroDeCusto> Centros = new ArrayList<CentroDeCusto>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT_BY_DESCRICAO);
			ps.setString(1, descricao);
			
			rs= ps.executeQuery();
			while (rs.next()) {
				Centros.add(extrairDo(rs));				
			}
			return Centros;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar os centros de custo. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
	
	}
	
	@Override
	public boolean validarRemocao(int id) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT_ID_EXISTENTE);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			boolean isValidadoOK = false;
			if (rs.next()) {
				isValidadoOK = rs.getInt("qtde") > 0;
			}
			
			return isValidadoOK;
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na validação do id para remoção. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
	}
	
}
