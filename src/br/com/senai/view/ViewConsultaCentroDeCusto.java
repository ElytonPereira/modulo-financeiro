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

import br.com.senai.core.domain.CentroDeCusto;
import br.com.senai.core.service.CentroDeCustoService;
import br.com.senai.view.componentes.table.CentroDeCustoTableModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;

public class ViewConsultaCentroDeCusto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtFiltro;
	
	private CentroDeCustoService service;
	private JTable tableCentroDeCusto;
	
	public ViewConsultaCentroDeCusto() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewConsultaCentroDeCusto.class.getResource("/br/com/senai/imagens/centronovo.png")));
		this.service = new CentroDeCustoService();
		CentroDeCustoTableModel model = new CentroDeCustoTableModel(new ArrayList<CentroDeCusto>());
		this.tableCentroDeCusto = new JTable(model);
		tableCentroDeCusto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setResizable(false);
		setTitle("Gerenciar Centro de Custo - Listagem");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 470, 420);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		tableCentroDeCusto.getTableHeader().setReorderingAllowed(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(new Color(192, 192, 192));
		btnAdicionar.setIcon(new ImageIcon(ViewConsultaCentroDeCusto.class.getResource("/br/com/senai/imagens/adicionar2.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewCadastroCentroDeCusto view = new ViewCadastroCentroDeCusto();
				view.setVisible(true);
				dispose();
			}
		});
		btnAdicionar.setBounds(333, 12, 109, 26);
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
		btnListar.setIcon(new ImageIcon(ViewConsultaCentroDeCusto.class.getResource("/br/com/senai/imagens/consultar.png")));
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String consulta = edtFiltro.getText();
					List<CentroDeCusto> centroResultado = service.listarPor(consulta);
					CentroDeCustoTableModel model = new CentroDeCustoTableModel(centroResultado);
					tableCentroDeCusto.setModel(model);
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
		
		JScrollPane spCentroDeCusto = new JScrollPane(tableCentroDeCusto);
		spCentroDeCusto.setBounds(12, 142, 433, 185);
		contentPane.add(spCentroDeCusto);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBackground(new Color(192, 192, 192));
		btnRemover.setIcon(new ImageIcon(ViewConsultaCentroDeCusto.class.getResource("/br/com/senai/imagens/remover.png")));
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
				
					int linhaSelecionada = tableCentroDeCusto.getSelectedRow();
				
					CentroDeCustoTableModel model = (CentroDeCustoTableModel) tableCentroDeCusto.getModel();
				
					if (linhaSelecionada >= 0 && !model.isVazio() && !model.isLinhaInvalida(linhaSelecionada)) {
						int op = JOptionPane.showConfirmDialog(contentPane, 
								"Deseja realmente remover?", 
								"Remoção", JOptionPane.YES_NO_OPTION);
						
						if (op == 0) {
							
							CentroDeCusto CentroSelecionado = model.getPor(linhaSelecionada);
	
							service.removerPor(CentroSelecionado.getId());
							model.removerPor(linhaSelecionada);
							tableCentroDeCusto.updateUI();
							JOptionPane.showMessageDialog(contentPane, "Centro de custo excluido com sucesso");
						}		
					} else {
						JOptionPane.showMessageDialog(contentPane, "Selecione um registro na tabela para remoção");
					}				
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
				}
				
			}
		});
		btnRemover.setBounds(336, 343, 106, 27);
		contentPane.add(btnRemover);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(192, 192, 192));
		btnEditar.setIcon(new ImageIcon(ViewConsultaCentroDeCusto.class.getResource("/br/com/senai/imagens/editar1.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int linhaSelecionada = tableCentroDeCusto.getSelectedRow();
				
				CentroDeCustoTableModel model = (CentroDeCustoTableModel) tableCentroDeCusto.getModel();

				if (linhaSelecionada >= 0 && !model.isVazio() && !model.isLinhaInvalida(linhaSelecionada)) {
					CentroDeCusto centroSelecionado = model.getPor(linhaSelecionada);
					ViewCadastroCentroDeCusto view = new ViewCadastroCentroDeCusto();
					view.alterarCentro(centroSelecionado);
					view.setVisible(true);
					dispose();					
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um registro na tabela para edição");					
				}
				
			}
		});
		btnEditar.setBounds(238, 343, 88, 27);
		contentPane.add(btnEditar);
		setLocationRelativeTo(null);
	}
}
