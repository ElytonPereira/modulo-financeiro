
package br.com.senai.view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.senai.core.domain.CentroDeCusto;
import br.com.senai.core.domain.Despesa;
import br.com.senai.core.domain.Fornecedor;
import br.com.senai.core.service.CentroDeCustoService;
import br.com.senai.core.service.DespesaService;
import br.com.senai.core.service.FornecedorService;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;

public class ViewCadastroDeDespesa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtValor;
	private JComboBox<CentroDeCusto> cbCentroDeCusto;
	private JComboBox<Fornecedor> cbFornecedor;
	private JFormattedTextField ftfDataDeLancamento;
	private JTextArea taDescricao;
	
	private DespesaService service;
	private Despesa despesa;
	private FornecedorService fornecedorService;
	private CentroDeCustoService centroDeCustoService;

	public void alterarDespesa(Despesa despesa) {
		
		this.despesa = despesa;
		this.edtValor.setText(String.valueOf(despesa.getValor()));
		
		this.cbFornecedor.setSelectedItem(despesa.getFornecedor());
		this.cbCentroDeCusto.setSelectedItem(despesa.getCentroDeCusto());
		this.taDescricao.setText(despesa.getDescricao());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dateFormatada = despesa.getDataDeLancamento().format(dtf);
		this.ftfDataDeLancamento.setText(dateFormatada);
				
		}
	
	public void carregarComboFornecedor() {
		List<Fornecedor> fornecedores = fornecedorService.listarFornecedor();
		for(Fornecedor f : fornecedores ) {
			this.cbFornecedor.addItem(f);			
		}		
	}
	
	public void carregarComboCentroDeCustoService() {
		List<CentroDeCusto> centros = centroDeCustoService.listarCentroDeCustos();
		for (CentroDeCusto cdc : centros) {
			cbCentroDeCusto.addItem(cdc);
		}
		
	}
	
	public ViewCadastroDeDespesa() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewCadastroDeDespesa.class.getResource("/br/com/senai/imagens/despesa.png")));
		setResizable(false);		
		this.service = new DespesaService();
		this.centroDeCustoService = new CentroDeCustoService();
		this.fornecedorService = new FornecedorService();
		setTitle("Gerenciar Despesa - Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 407);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Fornecedor");
		lblNewLabel.setBounds(10, 45, 65, 16);
		contentPane.add(lblNewLabel);
		
		cbCentroDeCusto = new JComboBox<CentroDeCusto>();		
		cbCentroDeCusto.setBackground(new Color(192, 192, 192));
		cbCentroDeCusto.setBounds(10, 128, 220, 22);
		contentPane.add(cbCentroDeCusto);
		
		cbFornecedor = new JComboBox<Fornecedor>();		
		cbFornecedor.setBackground(new Color(192, 192, 192));
		cbFornecedor.setBounds(10, 70, 463, 22);
		contentPane.add(cbFornecedor);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBackground(new Color(192, 192, 192));
		btnConsultar.setIcon(new ImageIcon(ViewCadastroDeDespesa.class.getResource("/br/com/senai/imagens/consultar.png")));
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaDespesa view = new ViewConsultaDespesa();
				view.setVisible(true);
				dispose();
			}
		});
		btnConsultar.setBounds(431, 12, 103, 26);
		contentPane.add(btnConsultar);
		
		JLabel lblNewLabel_1 = new JLabel("Centro de Custo");
		lblNewLabel_1.setBounds(10, 103, 91, 16);
		contentPane.add(lblNewLabel_1);		
		
		JLabel lblNewLabel_2 = new JLabel("Data de Lan\u00E7amento");
		lblNewLabel_2.setBounds(239, 103, 131, 16);
		contentPane.add(lblNewLabel_2);
		
		ftfDataDeLancamento = new JFormattedTextField();
		ftfDataDeLancamento.setBackground(new Color(192, 192, 192));
		ftfDataDeLancamento.setBounds(240, 129, 111, 22);
		contentPane.add(ftfDataDeLancamento);
		try {
			MaskFormatter mascara = new MaskFormatter("##/##/####");
			mascara.install(ftfDataDeLancamento);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		JLabel lblNewLabel_3 = new JLabel("Valor(R$)");
		lblNewLabel_3.setBounds(361, 103, 53, 16);
		contentPane.add(lblNewLabel_3);
		
		edtValor = new JTextField();
		edtValor.setBackground(new Color(192, 192, 192));
		edtValor.setBounds(361, 129, 138, 20);
		contentPane.add(edtValor);
		edtValor.setColumns(10);
		
		taDescricao = new JTextArea();
		taDescricao.setBackground(new Color(192, 192, 192));
		taDescricao.setWrapStyleWord(true);
		taDescricao.setLineWrap(true);
		taDescricao.setBounds(10, 186, 524, 135);
		contentPane.add(taDescricao);
		
		JLabel lblNewLabel_4 = new JLabel("Descri\u00E7\u00E3o");
		lblNewLabel_4.setBounds(10, 161, 61, 14);
		contentPane.add(lblNewLabel_4);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(new Color(192, 192, 192));
		btnSalvar.setIcon(new ImageIcon(ViewCadastroDeDespesa.class.getResource("/br/com/senai/imagens/salvar.png")));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					Fornecedor fornecedor = (Fornecedor) cbFornecedor.getSelectedItem();
					CentroDeCusto centroDeCusto = (CentroDeCusto) cbCentroDeCusto.getSelectedItem();
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate dataLancto = LocalDate.from(dtf.parse(ftfDataDeLancamento.getText()));
					double valor = Double.parseDouble(edtValor.getText());
					String descricao = taDescricao.getText();
					
					if (despesa == null) {
						
						despesa = new Despesa(descricao, dataLancto, valor, fornecedor, centroDeCusto);
						service.salvar(despesa);
						JOptionPane.showMessageDialog(contentPane, "Despesa inserida com sucesso");
						
						edtValor.setText("");
						ftfDataDeLancamento.setText("");
						cbFornecedor.setSelectedIndex(0);
						cbCentroDeCusto.setSelectedIndex(0);
						taDescricao.setText("");
						
						despesa = null;

					} else {
						
						despesa.setFornecedor(fornecedor);
						despesa.setCentroDeCusto(centroDeCusto);
						despesa.setDataDeLancamento(dataLancto);
						despesa.setValor(valor);
						despesa.setDescricao(descricao);
						service.salvar(despesa);
						JOptionPane.showMessageDialog(contentPane, "Despesa alterada com sucesso");
						
					}													
										
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(contentPane, "O valor é obrigatório e deve ser um número");
				} catch (DateTimeParseException dtpe) {
					JOptionPane.showMessageDialog(contentPane, "A data de lançamento é obrigatória e deve estar no formato DD/MM/AAAA");
				} catch (ClassCastException cce) {					
					JOptionPane.showMessageDialog(contentPane, "Preencha as informações para salvar");
				} catch (Exception ex) {					
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
					if (!despesa.isPersistido()) {
						despesa = null;						
					}
				}
				
			}
		});
		btnSalvar.setBounds(450, 334, 84, 26);
		contentPane.add(btnSalvar);
		this.carregarComboCentroDeCustoService();
		this.carregarComboFornecedor();
	}	
}
