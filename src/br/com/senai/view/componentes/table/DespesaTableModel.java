package br.com.senai.view.componentes.table;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.Despesa;

public class DespesaTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	private List<Despesa> despesas;
	
	private  Locale localeBr = new Locale("pt", "BR");
	private NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBr);
	
	public DespesaTableModel(List<Despesa> despesas) {
		this.despesas = despesas;
	}
	
	public Despesa getPor(int rowIndex) {
		
		return despesas.get(rowIndex);
		
	}
	
	public void removerPor(int rowIndex) {
		
		this.despesas.remove(rowIndex);
		
	}
	
	@Override
	public int getRowCount() {
		return despesas.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Código";
		} else if (columnIndex == 1) {
			return "Fornecedor";
		} else if (columnIndex == 2) {
			return "Data";
		} else if (columnIndex == 3) {
			return "Valor (R$)";
		}
		throw new IllegalArgumentException("Indice invalido");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Despesa despesaLinha = despesas.get(rowIndex);
		
		if (columnIndex == 0) {
			return despesaLinha.getId();
		} else if (columnIndex == 1 ) {
			return despesaLinha.getFornecedor().getNomeFantasia();			
		} else if (columnIndex == 2) {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dateFormatada = despesaLinha.getDataDeLancamento().format(dtf);
			return dateFormatada;
		} else if (columnIndex == 3) {
			return dinheiro.format(despesaLinha.getValor());					
		}
		
		throw new IllegalArgumentException("Indice invalido");
	}

	public boolean isVazio() {
		return despesas.isEmpty();
	}
	
	public boolean isLinhaInvalida(int id) {
		return id >= despesas.size();
	}
	
}
