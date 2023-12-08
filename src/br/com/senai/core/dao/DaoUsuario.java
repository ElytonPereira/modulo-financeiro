package br.com.senai.core.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.domain.Usuario;

public interface DaoUsuario {
	
	public void inserir(Usuario usuario);
	
	public void alterar(Usuario usuario);
	
	public List<Usuario> listarPor(String login);
	
	public Usuario buscarPor(String login);
	
	public ArrayList<Usuario> listarUsuarios();
	
}
