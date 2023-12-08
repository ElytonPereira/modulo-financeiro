package br.com.senai.view.componentes.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.CentroDeCusto;

public class CentroDeCustoTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
		
	private List<CentroDeCusto> centrosDeCustos;	
	
	public CentroDeCustoTableModel(List<CentroDeCusto> list) {
		this.centrosDeCustos = list;
	}
	
	public CentroDeCusto getPor(int rowIndex) {
		
		return centrosDeCustos.get(rowIndex);
		
	}
	
	public void removerPor(int rowIndex) {
		
		this.centrosDeCustos.remove(rowIndex);
		
	}
	
	
	@Override
	public int getRowCount() {
		return centrosDeCustos.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Código";
		} else if (columnIndex == 1) {
			return "Descrição";
		} 
		throw new IllegalArgumentException("Indice invalido");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		CentroDeCusto linhaCentroDeCusto = centrosDeCustos.get(rowIndex);
		
		if (columnIndex == 0) {
			return linhaCentroDeCusto.getId();
		} else if (columnIndex == 1 ) {
			return linhaCentroDeCusto.getDescricao();		
		} 
		throw new IllegalArgumentException("Indice invalido");
	}
	
	public boolean isVazio() {
		return centrosDeCustos.isEmpty();
	}
	
	public boolean isLinhaInvalida(int id) {
		return id >= centrosDeCustos.size();
	}

}
