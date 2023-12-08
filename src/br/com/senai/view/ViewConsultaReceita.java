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

import br.com.senai.core.domain.Receita;
import br.com.senai.core.service.ReceitaService;
import br.com.senai.view.componentes.table.ReceitaTableModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ListSelectionModel;

public class ViewConsultaReceita extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtFiltro;
	
	private ReceitaService receitaService;
	private JTable tableReceita;

	public ViewConsultaReceita() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewConsultaReceita.class.getResource("/br/com/senai/imagens/receita.png")));
		this.receitaService = new ReceitaService();
		ReceitaTableModel model = new ReceitaTableModel(new ArrayList<Receita>());
		this.tableReceita = new JTable(model);
		tableReceita.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setResizable(false);
		setTitle("Gerenciar Receita - Listagem");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 530, 420);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		tableReceita.getTableHeader().setReorderingAllowed(false);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(new Color(192, 192, 192));
		btnAdicionar.setIcon(new ImageIcon(ViewConsultaReceita.class.getResource("/br/com/senai/imagens/adicionar2.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewCadastroDeReceita view = new ViewCadastroDeReceita();
				view.setVisible(true);
				dispose();
				
			}
		});
		btnAdicionar.setBounds(393, 13, 109, 26);
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
		btnListar.setIcon(new ImageIcon(ViewConsultaReceita.class.getResource("/br/com/senai/imagens/consultar.png")));
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String consulta = edtFiltro.getText();
					List<Receita> receitas = receitaService.listarPor(consulta);
					ReceitaTableModel model = new ReceitaTableModel(receitas);
					tableReceita.setModel(model);
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
				
			}
		});
		btnListar.setBounds(321, 75, 81, 26);
		contentPane.add(btnListar);
		
		JLabel lblNewLabel_1 = new JLabel("Resultados");
		lblNewLabel_1.setBounds(12, 110, 64, 16);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane spResultado = new JScrollPane(tableReceita);
		spResultado.setBounds(12, 142, 492, 185);
		contentPane.add(spResultado);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBackground(new Color(192, 192, 192));
		btnRemover.setIcon(new ImageIcon(ViewConsultaReceita.class.getResource("/br/com/senai/imagens/remover.png")));
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int linhaSelecionada = tableReceita.getSelectedRow();

				ReceitaTableModel model = (ReceitaTableModel) tableReceita.getModel();
				
				if (linhaSelecionada >= 0 && !model.isVazio() && !model.isLinhaInvalida(linhaSelecionada)) {
					
					int op = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente remover?", 
							"Remoção", JOptionPane.YES_NO_OPTION);
					
					if (op == 0) {
						
						Receita receitaSelecionada = model.getPor(linhaSelecionada);
						
						try {
							model.removerPor(linhaSelecionada);
							receitaService.removerPor(receitaSelecionada.getId());
							tableReceita.updateUI();
							JOptionPane.showMessageDialog(contentPane, "Receita excluída com sucesso");								
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(contentPane, ex.getMessage());
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
		btnEditar.setIcon(new ImageIcon(ViewConsultaReceita.class.getResource("/br/com/senai/imagens/editar1.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int linhaSelecionada = tableReceita.getSelectedRow();
				
				ReceitaTableModel model = (ReceitaTableModel) tableReceita.getModel();

				if (linhaSelecionada >= 0 && !model.isVazio() && !model.isLinhaInvalida(linhaSelecionada)) {
					
					Receita receitaSelecionada = model.getPor(linhaSelecionada);
					
					ViewCadastroDeReceita view = new ViewCadastroDeReceita();
					view.setReceita(receitaSelecionada);
					view.setVisible(true);
					dispose();
					
				} else {
					JOptionPane.showMessageDialog(contentPane, "Seleciona um registro na tabela para edição");
				}
				
			}
		});
		btnEditar.setBounds(298, 343, 88, 27);
		contentPane.add(btnEditar);
		setLocationRelativeTo(null);
	}
}
