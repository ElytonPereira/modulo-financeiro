package br.com.senai.view.componentes.table;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.LogDeAcessos;

public class LogDeAcessosTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private List<LogDeAcessos> logs;
	
	public LogDeAcessosTableModel(List<LogDeAcessos> list) {
		this.logs = list;		
	}

	@Override
	public int getRowCount() {
		return logs.size();
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
			return "Horário";
		} 
		throw new IllegalArgumentException("Indice invalido");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LogDeAcessos linhaLogDeAcessos = logs.get(rowIndex);
		
		if (columnIndex == 0) {
			return linhaLogDeAcessos.getId();
		} else if (columnIndex == 1) {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm ");
			String dateFormatada = linhaLogDeAcessos.getDataHora().format(dtf);
			return dateFormatada;			
		}
		throw new IllegalArgumentException("Indice invalido");
		
	}
	
	
	
	
}
