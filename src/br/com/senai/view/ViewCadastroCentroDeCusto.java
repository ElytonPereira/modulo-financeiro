package br.com.senai.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.CentroDeCusto;
import br.com.senai.core.service.CentroDeCustoService;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;

public class ViewCadastroCentroDeCusto extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtDescricao;
	
	private CentroDeCustoService service;
	private CentroDeCusto centroDeCusto;
	
	public void alterarCentro(CentroDeCusto centroDeCusto) {
		
		this.centroDeCusto = centroDeCusto;
		this.edtDescricao.setText(centroDeCusto.getDescricao());			
		}

	
	public ViewCadastroCentroDeCusto() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewCadastroCentroDeCusto.class.getResource("/br/com/senai/imagens/centronovo.png")));
		this.service = new CentroDeCustoService();
		setTitle("Gerenciar Centro de Custo - Cadastro");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 215);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBackground(new Color(192, 192, 192));
		btnConsultar.setIcon(new ImageIcon(ViewCadastroCentroDeCusto.class.getResource("/br/com/senai/imagens/consultar.png")));
		btnConsultar.setBounds(334, 12, 103, 26);
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaCentroDeCusto view = new ViewConsultaCentroDeCusto();
				view.setVisible(true);
 				dispose();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnConsultar);
		
		JLabel lblNewLabel = new JLabel("Descri\u00E7\u00E3o");
		lblNewLabel.setBounds(12, 50, 63, 16);
		contentPane.add(lblNewLabel);
		
		edtDescricao = new JTextField();
		edtDescricao.setBackground(new Color(192, 192, 192));
		edtDescricao.setBounds(12, 78, 404, 20);
		contentPane.add(edtDescricao);
		edtDescricao.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(new Color(192, 192, 192));
		btnSalvar.setIcon(new ImageIcon(ViewCadastroCentroDeCusto.class.getResource("/br/com/senai/imagens/salvar.png")));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					String descricao = edtDescricao.getText();
					
					if(centroDeCusto == null) {
						
						centroDeCusto = new CentroDeCusto(descricao);	
						service.salvar(centroDeCusto);
						JOptionPane.showMessageDialog(contentPane, "Centro de Custo inserido com sucesso");
						
						edtDescricao.setText("");
						
						centroDeCusto = null;
						
					}else {
						
						centroDeCusto.setDescricao(descricao);
						service.salvar(centroDeCusto);
						JOptionPane.showMessageDialog(contentPane, "Centro de Custo alterado com sucesso");						
						
					}
					
					
				} catch (Exception ex) {					
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
					if (!centroDeCusto.isPersistido()) {
						centroDeCusto = null;						
					}
				}
			}
		});
		btnSalvar.setBounds(353, 137, 84, 26);
		contentPane.add(btnSalvar);
	}
}
