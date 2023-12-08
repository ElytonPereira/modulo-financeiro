package br.com.senai.view;

import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Despesa;
import br.com.senai.core.domain.Receita;
import br.com.senai.core.service.DemonstrativoService;
import br.com.senai.core.service.DespesaService;
import br.com.senai.core.service.ReceitaService;
import br.com.senai.view.componentes.table.DespesaTableModel;
import br.com.senai.view.componentes.table.ReceitaTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;
import java.text.NumberFormat;

public class ViewDemonstrativo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableDespesa;
	private JTable tableReceita;
	
	private ReceitaService receitaService;
	private DespesaService despesaService;
	private DemonstrativoService demonstrativoService;
	
	private Locale localeBr = new Locale("pt", "BR");
	private NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBr);
	
	public ViewDemonstrativo() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewDemonstrativo.class.getResource("/br/com/senai/imagens/demonstrativo.png")));
		setResizable(false);
		
		this.receitaService = new ReceitaService();
		this.despesaService = new DespesaService();
		this.demonstrativoService = new DemonstrativoService();
		setTitle("Demonstrativo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		List<Receita> receitas = receitaService.listar();
		ReceitaTableModel receitaModel = new ReceitaTableModel(receitas);
		this.tableReceita = new JTable(receitaModel);
		tableReceita.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableReceita.setEnabled(false);
		JScrollPane spReceitas = new JScrollPane(tableReceita);
		spReceitas.setEnabled(false);
		spReceitas.setBounds(10, 38, 369, 450);
		contentPane.add(spReceitas);
		tableReceita.getTableHeader().setReorderingAllowed(false);
		
		List<Despesa> despesasResultado = despesaService.listar();
		DespesaTableModel despesaModel = new DespesaTableModel(despesasResultado);
		this.tableDespesa = new JTable(despesaModel);
		tableDespesa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDespesa.setEnabled(false);
		JScrollPane spDespesas = new JScrollPane(tableDespesa);
		spDespesas.setEnabled(false);
		spDespesas.setBounds(405, 38, 369, 450);
		contentPane.add(spDespesas);
		tableDespesa.getTableHeader().setReorderingAllowed(false);
		
		JLabel lblTabelaReceitas = new JLabel("Tabela de Receitas");
		lblTabelaReceitas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTabelaReceitas.setBounds(10, 11, 369, 16);
		contentPane.add(lblTabelaReceitas);
		
		JLabel lblTabelaDespesas = new JLabel("Tabela de Despesas");
		lblTabelaDespesas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTabelaDespesas.setBounds(405, 11, 369, 16);
		contentPane.add(lblTabelaDespesas);
		
		double totalReceita = demonstrativoService.calcularTotalReceita();
		JLabel lblTotalReceitas = new JLabel("Total receitas: " + dinheiro.format(totalReceita));
		lblTotalReceitas.setBounds(10, 499, 369, 16);
		contentPane.add(lblTotalReceitas);
		
		double totalDespesa = demonstrativoService.calcularTotalDespesa();
		JLabel lblTotalDespesas = new JLabel("Total despesas: " + dinheiro.format(totalDespesa));
		lblTotalDespesas.setBounds(405, 499, 369, 16);
		contentPane.add(lblTotalDespesas);
		
		double resultado = demonstrativoService.calcularResultado(totalReceita, totalDespesa);
		JLabel lblResultado = new JLabel("Resultado: " + dinheiro.format(resultado));
		if (resultado < 0) {
			lblResultado.setForeground(new Color(255, 0, 0));
		} else {
			lblResultado.setForeground(new Color(0, 255, 0));
		}
		lblResultado.setBounds(10, 534, 369, 16);
		contentPane.add(lblResultado);
		setLocationRelativeTo(null);
	}
}
