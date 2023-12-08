package br.com.senai.core.domain;

public class Usuario {
	
	private String login;
	private String senha;
	private String confirmacaoSenha;
	private Ativo ativo;
	
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public Usuario(String login, String senha, Ativo ativo) {
		this.login = login;
		this.senha = senha;
		this.ativo = ativo;
	}

	public Usuario(String login, String senha, String confirmacaoSenha, Ativo ativo) {
		this.login = login;
		this.senha = senha;
		this.confirmacaoSenha = confirmacaoSenha;
		this.ativo = ativo;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return login;
	}
	
	
	
}
