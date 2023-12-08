package br.com.senai.core.dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoLogDeAcessos;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.LogDeAcessos;
import br.com.senai.core.domain.Usuario;

public class DaoPostgresqlLogDeAcessos implements DaoLogDeAcessos {
	
	private final String INSERT = "INSERT INTO logs_acessos (data_hora, login) "
			+ "VALUES (?, ?)";
	
	private final String SELECT_BY_LOGIN = "SELECT la.id, la.data_hora, la.login, u.senha, u.login " 
			+ "FROM logs_acessos la "
			+ "inner join usuarios u "
			+ "on la.login = u.login "
			+ "WHERE Upper(la.login) LIKE Upper(?) ORDER BY la.id";
	
	private Connection conexao;
	
	public DaoPostgresqlLogDeAcessos() {
		this.conexao = ManagerDb.getInstance().getConexao();
		
	}
	
	@Override
	public void inserir(Usuario usuario) {
		
		PreparedStatement ps = null;
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());  
		
		Timestamp t = new Timestamp(timestamp.getTime());
				
		try {
			
			ps = conexao.prepareStatement(INSERT);
			
			ps.setTimestamp(1, t);
			ps.setString(2, usuario.getLogin());
			
			ps.execute();
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na inserção do usuário. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}
		
	}
	
	@Override
	public List<LogDeAcessos> listarPor(String login) {
		List<LogDeAcessos> logs = new ArrayList<LogDeAcessos>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conexao.prepareStatement(SELECT_BY_LOGIN);
			ps.setString(1, login);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				logs.add(extrairDo(rs));
				
			}
			return logs;
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar os logs de acessos. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);			
		}
	}
	
	public LogDeAcessos extrairDo(ResultSet rs) {
		
		try {
			int idLog = rs.getInt("id");
			LocalDateTime dataHora = rs.getTimestamp("data_hora").toLocalDateTime();
			
			String login = rs.getString("login");
			String senha = rs.getString("senha");
			Usuario usuario = new Usuario(login, senha);
			
			return new LogDeAcessos(idLog, dataHora, usuario);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair a Logs De Acessos. Motivo: " + e.getMessage());
		}
		
	}
	

}
