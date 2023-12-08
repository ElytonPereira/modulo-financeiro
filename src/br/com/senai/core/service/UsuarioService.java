package br.com.senai.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoUsuario;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Usuario;

public class UsuarioService {
	
	private DaoUsuario dao;
	
	public UsuarioService( ) {
		dao = FactoryDao.getInstance().getDaoUsuario();
	}
	
	public void salvar(Usuario usuario) {
		this.validar(usuario);
		
		if (isPersistido(usuario.getLogin())) {
			this.dao.alterar(usuario);
		} else {
			this.dao.inserir(usuario);
		}
	}
	
	public void validar(Usuario usuario) {
		
		if (usuario != null) {
			
			boolean isLoginInvalido = usuario.getLogin() == null
					|| usuario.getLogin().isBlank();
			if (isLoginInvalido) {
				throw new IllegalArgumentException("O login � obrigat�rio");
			}
			
			boolean isSenhaInvalida = usuario.getSenha() == null
					|| usuario.getSenha().isBlank()
					|| !usuario.getSenha().equals(usuario.getConfirmacaoSenha());
			if (isSenhaInvalida) {
				throw new IllegalArgumentException("A senha � obrigat�ria e deve ser igual � confirma��o da senha");
			}
			
			boolean isConfirmacaoSenhaInvalida = usuario.getConfirmacaoSenha() == null
					|| usuario.getConfirmacaoSenha().isBlank()
					|| !usuario.getConfirmacaoSenha().equals(usuario.getSenha());
			if (isConfirmacaoSenhaInvalida) {
				throw new IllegalArgumentException("A confirma��o da senha � obrigat�ria e deve ser igual � senha");
			}
			
			
			
		} else {
			throw new NullPointerException("O usu�rio � obrigat�rio");
		}
		
	}
	
	public List<Usuario> listarPor(String login) {
		if (login != null && !login.isBlank()) {
			String filtro = "%" + login + "%";
			return this.dao.listarPor(filtro);
		} else {
			throw new IllegalArgumentException("O filtro para a listagem � obrigat�rio");
		}
	}
	
	public boolean isPersistido(String login) {
		return dao.buscarPor(login) != null;
	}
	
	public ArrayList<Usuario> listarUsuarios(){
		return this.dao.listarUsuarios();
	}
	
	public void logar(Usuario usuario) {
		
		if (usuario != null) {
			
			Usuario usuarioBanco = dao.buscarPor(usuario.getLogin());
			
			boolean isLoginInvalido = usuario.getLogin() == null
					|| usuario.getLogin().isBlank();
			if (isLoginInvalido) {
				throw new IllegalArgumentException("O login � obrigat�rio");
			}
			
			boolean isSenhaInvalida = usuario.getSenha() == null
					|| usuario.getSenha().isBlank();
			if (isSenhaInvalida) {
				throw new IllegalArgumentException("A senha � obrigat�ria");
			}
			
			if (usuarioBanco == null 
					|| !usuario.getSenha().equals(usuarioBanco.getSenha())
					|| !usuario.getLogin().equals(usuarioBanco.getLogin())) {
				
				throw new RuntimeException("Login ou senha inv�lidos");
			}
			
		} else {
			throw new NullPointerException("O usu�rio n�o pode ser nulo");
		}
		
	}
	
}
