package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.LogDeAcessos;
import br.com.senai.core.domain.Usuario;

public interface DaoLogDeAcessos {

	public List<LogDeAcessos> listarPor(String login);

	public void inserir(Usuario usuario);
	
}
