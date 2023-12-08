package br.com.senai.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Despesa;
import br.com.senai.core.service.DespesaService;
import br.com.senai.view.componentes.table.DespesaTableModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ListSelectionModel;

public class ViewConsultaDespesa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtFiltro;
	
	private DespesaService service;
	private JTable tableDespesa;	

	public ViewConsultaDespesa() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewConsultaDespesa.class.getResource("/br/com/senai/imagens/despesa.png")));
		this.service = new DespesaService();
		DespesaTableModel model = new DespesaTableModel(new ArrayList<Despesa>());
		this.tableDespesa = new JTable(model);
		tableDespesa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setResizable(false);
		setTitle("Gerenciar Despesa - Listagem");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 530, 420);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		tableDespesa.getTableHeader().setReorderingAllowed(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(new Color(192, 192, 192));
		btnAdicionar.setIcon(new ImageIcon(ViewConsultaDespesa.class.getResource("/br/com/senai/imagens/adicionar2.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewCadastroDeDespesa view = new ViewCadastroDeDespesa();
				view.setVisible(true);
				dispose();
			}
		});
		btnAdicionar.setBounds(393, 11, 109, 26);
		contentPane.add(btnAdicionar);
		
		JLabel lblNewLabel = new JLabel("Filtro");
		lblNewLabel.setBounds(12, 50, 55, 16);
		contentPane.add(lblNewLabel);
		
		edtFiltro = new JTextField();
		edtFiltro.setBackground(new Color(192, 192, 192));
		edtFiltro.setBounds(12, 78, 297, 20);
		contentPane.add(edtFiltro);
		edtFiltro.setColumns(10);
		
		JButton btnListar = new JButton("Listar");
		btnListar.setBackground(new Color(192, 192, 192));
		btnListar.setIcon(new ImageIcon(ViewConsultaDespesa.class.getResource("/br/com/senai/imagens/consultar.png")));
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String consulta = edtFiltro.getText();
					List<Despesa> despesasResultado = service.listarPor(consulta);
					DespesaTableModel model = new DespesaTableModel(despesasResultado);
					tableDespesa.setModel(model);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
				}								
			}
		});
		btnListar.setBounds(321, 75, 81, 26);
		contentPane.add(btnListar);
		
		JLabel lblNewLabel_1 = new JLabel("Resultados");
		lblNewLabel_1.setBounds(12, 110, 64, 16);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane spDespesas = new JScrollPane(tableDespesa);
		spDespesas.setBounds(12, 142, 492, 185);
		contentPane.add(spDespesas);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBackground(new Color(192, 192, 192));
		btnRemover.setIcon(new ImageIcon(ViewConsultaDespesa.class.getResource("/br/com/senai/imagens/remover.png")));
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int linhaSelecionada = tableDespesa.getSelectedRow();

				DespesaTableModel model = (DespesaTableModel) tableDespesa.getModel();
				
				if (linhaSelecionada >= 0 && !model.isVazio() && !model.isLinhaInvalida(linhaSelecionada)) {
					int op = JOptionPane.showConfirmDialog(contentPane, 
							"Deseja realmente remover?", 
							"Remoção", JOptionPane.YES_NO_OPTION);
					
					if (op == 0) {
						Despesa despesaSelecionada = model.getPor(linhaSelecionada);
						
						try {
							model.removerPor(linhaSelecionada);
							service.removerPor(despesaSelecionada.getId());
							tableDespesa.updateUI();
							JOptionPane.showMessageDialog(contentPane, "Despesa excluida com sucesso");
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(contentPane, e2.getMessage());
						}
					} 		
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um registro na tabela para remoção");
				}				
			}
		});
		btnRemover.setBounds(396, 343, 106, 27);
		contentPane.add(btnRemover);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(192, 192, 192));
		btnEditar.setIcon(new ImageIcon(ViewConsultaDespesa.class.getResource("/br/com/senai/imagens/editar1.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int linhaSelecionada = tableDespesa.getSelectedRow();
				
				DespesaTableModel model = (DespesaTableModel) tableDespesa.getModel();

				if (linhaSelecionada >= 0  && !model.isVazio() && !model.isLinhaInvalida(linhaSelecionada)) {
					Despesa despesaSelecionada = model.getPor(linhaSelecionada);
					ViewCadastroDeDespesa view = new ViewCadastroDeDespesa();
					view.alterarDespesa(despesaSelecionada);
					view.setVisible(true);
					dispose();					
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um registro na tabela para edição");					
				}
				
			}
		});
		btnEditar.setBounds(298, 343, 88, 27);
		contentPane.add(btnEditar);
		setLocationRelativeTo(null);
	}
}
