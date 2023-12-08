package br.com.senai.view.componentes.table;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.Receita;

public class ReceitaTableModel extends AbstractTableModel{	

	private static final long serialVersionUID = 1L;
	
	private List<Receita> receitas;
	
	private  Locale localeBr = new Locale("pt", "BR");
	private NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBr);
	
	public ReceitaTableModel(List<Receita> receitas) {
		this.receitas = receitas;
	}
	
	@Override
	public int getRowCount() {
		return this.receitas.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Receita receitaEscolhida = receitas.get(rowIndex);
		
		if (columnIndex == 0) {
			return receitaEscolhida.getId();
		} else if (columnIndex == 1) {
			return receitaEscolhida.getCliente().getNome();
		} else if (columnIndex == 2) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dateFormatada = receitaEscolhida.getDataDeLancamento().format(dtf);
			return dateFormatada;
		} else if (columnIndex == 3) {
			return dinheiro.format(receitaEscolhida.getValor());
		}
		
		throw new IllegalArgumentException("Índice inválido");
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		
		if (columnIndex == 0) {
			return "Id";
		} else if (columnIndex == 1) {
			return "Cliente";
		} else if (columnIndex == 2) {
			return "Data";
		} else if (columnIndex == 3) {
			return "Valor (R$)";
		}
		
		throw new IllegalArgumentException("Índice inválido");
		
	}
	
	public Receita getPor(int rowIndex) {
		return receitas.get(rowIndex);
	}
	
	public void removerPor(int rowIndex) {
		this.receitas.remove(rowIndex);
	}
	
	public boolean isVazio() {
		return receitas.isEmpty();
	}
	
	public boolean isLinhaInvalida(int id) {
		return id >= receitas.size();
	}
	
}
