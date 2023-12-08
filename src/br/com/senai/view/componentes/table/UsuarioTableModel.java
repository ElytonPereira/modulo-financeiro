package br.com.senai.view.componentes.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.Usuario;

public class UsuarioTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private List<Usuario> usuarios;
	
	public UsuarioTableModel(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@Override
	public int getRowCount() {
		return usuarios.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Usuario usuarioEscolhida = usuarios.get(rowIndex);
		
		if (columnIndex == 0) {
			return usuarioEscolhida.getLogin();
		}
		
		throw new IllegalArgumentException("Índice inválido");
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		
		if (columnIndex == 0) {
			return "Login";
		}
		
		throw new IllegalArgumentException("Índice inválido");
		
	}
	
	public Usuario getPor(int rowIndex) {
		return usuarios.get(rowIndex);
	}
	
	public boolean isVazio() {
		return usuarios.isEmpty();
	}
	
	public boolean isLinhaInvalida(int id) {
		return id >= usuarios.size();
	}
	
}
