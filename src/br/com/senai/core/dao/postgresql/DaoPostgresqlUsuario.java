package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoUsuario;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Ativo;
import br.com.senai.core.domain.Usuario;

public class DaoPostgresqlUsuario implements DaoUsuario{
	
	private final String INSERT = "INSERT INTO usuarios (login, senha, ativo) "
			+ "VALUES (?, ?, ?)";
	
	private final String UPDATE = "UPDATE usuarios SET senha = ?, ativo = ? "
			+ "WHERE login = ?";
	
	private final String SELECT_BY_LOGIN = "SELECT u.login, u.senha, u.ativo "
			+ "FROM usuarios AS u "
			+ "WHERE Upper(u.login) LIKE Upper(?)";
	
	private final String SELECT = "SELECT login, senha, ativo from usuarios ORDER BY login";
	
	private Connection conexao;
	
	public DaoPostgresqlUsuario() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	@Override
	public void inserir(Usuario usuario) {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conexao.prepareStatement(INSERT);
			
			ps.setString(1, usuario.getLogin());
			ps.setString(2, usuario.getSenha());
			ps.setString(3, usuario.getAtivo().name());
			
			ps.execute();
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na inserção do usuário. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}
		
	}

	@Override
	public void alterar(Usuario usuario) {
		
		PreparedStatement ps = null;
		
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			
			ps = conexao.prepareStatement(UPDATE);
			
			ps.setString(1, usuario.getSenha());
			ps.setString(2, usuario.getAtivo().name());
			ps.setString(3, usuario.getLogin());
			
			boolean isAlteracaoOk = ps.executeUpdate() == 1;
			if (isAlteracaoOk) {
				this.conexao.commit();
			} else {
				this.conexao.rollback();
			}
			
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na alteração do usuário. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}
		
	}

	@Override
	public List<Usuario> listarPor(String login) {
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT_BY_LOGIN);
			
			ps.setString(1, login);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				usuarios.add(extrairDo(rs));				
			}
			
			return usuarios;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar os usuários. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		
	}
	
	@Override
	public Usuario buscarPor(String login) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = conexao.prepareStatement(SELECT_BY_LOGIN);
			ps.setString(1, login);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				return extrairDo(rs);
			}
			
			return null;
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na busca pelo usuário. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		
	}
	
	public Usuario extrairDo(ResultSet rs) {
		
		try {
			String login = rs.getString("login");
			String senha = rs.getString("senha");
			Ativo ativo = Ativo.valueOf(rs.getString("ativo"));
			
			return new Usuario(login, senha, ativo);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair o usuário. Motivo: " + e.getMessage());
		}
	}
	
	@Override
	public ArrayList<Usuario> listarUsuarios() {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				listaUsuarios.add(extrairDo(rs));				
			}
			
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar os Usuarios. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);			
		}
		
		return listaUsuarios;
	}
	
}
