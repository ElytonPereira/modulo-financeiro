package br.com.senai.view;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.LogDeAcessos;
import br.com.senai.core.domain.Usuario;
import br.com.senai.core.service.LogDeAcessosService;
import br.com.senai.core.service.UsuarioService;
import br.com.senai.view.componentes.table.LogDeAcessosTableModel;
import java.awt.Color;

public class ViewLogDeAcessos extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTable tableLogs;
	private JComboBox<Usuario> cbUsuario;
	private UsuarioService usuariosService;
	private LogDeAcessosService logsAcessosService;

	public void carregarComboUsuario() {
		
		List<Usuario> usuarios = usuariosService.listarUsuarios();
		for(Usuario u : usuarios) {
			this.cbUsuario.addItem(u);
			
		}
		
	}

	public ViewLogDeAcessos() {
		setTitle("Logs de Acessos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewLogDeAcessos.class.getResource("/br/com/senai/imagens/usuarioLogs.png")));
		setResizable(false);
		LogDeAcessosTableModel model = new LogDeAcessosTableModel(new ArrayList<LogDeAcessos>());
		this.tableLogs = new JTable(model);
		this.usuariosService = new UsuarioService();
		this.logsAcessosService = new LogDeAcessosService();
		tableLogs.setEnabled(false);
		tableLogs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 490, 410);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		tableLogs.getTableHeader().setReorderingAllowed(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 55, 33, 17);
		contentPane.add(lblNewLabel);
		
		cbUsuario = new JComboBox<Usuario>();
		cbUsuario.setBackground(new Color(192, 192, 192));
		cbUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String usuario = cbUsuario.getSelectedItem().toString();
					
					List<LogDeAcessos> logs = logsAcessosService.listarAcessosServices(usuario);
					LogDeAcessosTableModel model = new LogDeAcessosTableModel(logs);
					tableLogs.setModel(model);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
				}
				
			}
		});
		cbUsuario.setBounds(10, 86, 414, 22);
		contentPane.add(cbUsuario);
		
		JScrollPane spAcessos = new JScrollPane(tableLogs);
		spAcessos.setBounds(10, 192, 454, 168);
		contentPane.add(spAcessos);
		
		JLabel lblAcessos = new JLabel("Acessos");
		lblAcessos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAcessos.setBounds(10, 164, 48, 17);
		contentPane.add(lblAcessos);
		this.carregarComboUsuario();
	}
}
