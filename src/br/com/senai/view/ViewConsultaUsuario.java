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

import br.com.senai.core.domain.Usuario;
import br.com.senai.core.service.UsuarioService;
import br.com.senai.view.componentes.table.UsuarioTableModel;
import java.awt.Color;
import java.awt.Toolkit;

public class ViewConsultaUsuario extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtFiltro;
	private JTable tableUsuarios;
	
	private UsuarioService usuarioService;

	/**
	 * Create the frame.
	 */
	public ViewConsultaUsuario() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewConsultaUsuario.class.getResource("/br/com/senai/imagens/usuario.png")));
		setResizable(false);
		setTitle("Gerenciar Usu\u00E1rios - Consulta");
		this.usuarioService = new UsuarioService();
		UsuarioTableModel model = new UsuarioTableModel(new ArrayList<Usuario>());
		this.tableUsuarios = new JTable(model);
		this.tableUsuarios.getTableHeader().setReorderingAllowed(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 405);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(new Color(192, 192, 192));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewCadastroUsuario view = new ViewCadastroUsuario();
				view.setVisible(true);
				dispose();
				
			}
		});
		btnAdicionar.setBounds(373, 11, 98, 26);
		contentPane.add(btnAdicionar);
		
		edtFiltro = new JTextField();
		edtFiltro.setBackground(new Color(192, 192, 192));
		edtFiltro.setBounds(10, 75, 351, 26);
		contentPane.add(edtFiltro);
		edtFiltro.setColumns(10);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(10, 48, 55, 16);
		contentPane.add(lblFiltro);
		
		JButton btnListar = new JButton("Listar");
		btnListar.setBackground(new Color(192, 192, 192));
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String consulta = edtFiltro.getText();
					List<Usuario> usuarios = usuarioService.listarPor(consulta);
					UsuarioTableModel model = new UsuarioTableModel(usuarios);
					tableUsuarios.setModel(model);
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
				
			}
		});
		btnListar.setBounds(373, 75, 98, 26);
		contentPane.add(btnListar);
		
		JLabel lblResultados = new JLabel("Resultados");
		lblResultados.setBounds(10, 113, 64, 16);
		contentPane.add(lblResultados);
		
		JScrollPane spUsuarios = new JScrollPane(tableUsuarios);
		spUsuarios.setBounds(10, 141, 462, 176);
		contentPane.add(spUsuarios);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(192, 192, 192));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int linhaSelecionada = tableUsuarios.getSelectedRow();
				
				UsuarioTableModel model = (UsuarioTableModel) tableUsuarios.getModel();
				
				if (linhaSelecionada >= 0 && !model.isVazio() && !model.isLinhaInvalida(linhaSelecionada)) {
					
					Usuario usuarioSelecionado = model.getPor(linhaSelecionada);
					
					ViewCadastroUsuario view = new ViewCadastroUsuario();
					view.setUsuario(usuarioSelecionado);
					view.setVisible(true);
					dispose();
					
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um registro na tabela para edição");
				}
				
			}
		});
		btnEditar.setBounds(373, 329, 98, 26);
		contentPane.add(btnEditar);
	}
}
