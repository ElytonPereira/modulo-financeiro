package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoLogDeAcessos;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.LogDeAcessos;
import br.com.senai.core.domain.Usuario;

public class LogDeAcessosService {

	private DaoLogDeAcessos dao;
	
	public LogDeAcessosService() {
		this.dao = FactoryDao.getInstance().getDaoLogDeAcessos();
		
	}
	
	public void inserir(Usuario usuario) {
		
		this.dao.inserir(usuario);
		
	}
	
	public List<LogDeAcessos> listarAcessosServices(String login){
		
		if (login != null && !login.isBlank()) {
			return dao.listarPor(login);
		} else {			
			throw new IllegalArgumentException("O filtro é obrigatório");
		}
	}
	
	
}
