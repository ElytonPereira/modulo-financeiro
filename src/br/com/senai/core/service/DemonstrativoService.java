package br.com.senai.core.service;

import br.com.senai.core.domain.Despesa;
import br.com.senai.core.domain.Receita;

public class DemonstrativoService {
	
	private DespesaService despesaService;
	private ReceitaService receitaService;
	
	public DemonstrativoService() {
		this.despesaService = new DespesaService();
		this.receitaService = new ReceitaService();
	}
	
	public double calcularTotalDespesa() {
		
		double totalDespesa = 0;
		
		for (Despesa despesa : despesaService.listarPor("%%")) {
			totalDespesa += despesa.getValor();
		}
		
		return totalDespesa;
		
	}

	public double calcularTotalReceita() {
		
		double totalReceita = 0;
		
		for (Receita receita : receitaService.listarPor("%%")) {
			totalReceita += receita.getValor();
		}
		
		return totalReceita;
		
	}
	
	public double calcularResultado(double receita, double despesa) {
		
		double resultado = receita - despesa;
		
		return resultado;
		
	}
	
}
