package br.com.senai.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Usuario;
import br.com.senai.core.service.LogDeAcessosService;
import br.com.senai.core.service.UsuarioService;
import java.awt.Toolkit;

public class ViewLogin extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtLogin;
	private JPasswordField pfSenha;
	
	private Usuario usuario;
	private UsuarioService usuarioService;
	private LogDeAcessosService logDeAcessosService;
	
	/**
	 * Create the frame.
	 */
	public ViewLogin() {
		setTitle("Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewLogin.class.getResource("/br/com/senai/imagens/usuario.png")));
		setResizable(false);
		usuarioService = new UsuarioService();
		logDeAcessosService = new LogDeAcessosService();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblUsuario = new JLabel("Usu\u00E1rio");
		lblUsuario.setBounds(10, 11, 46, 14);
		contentPane.add(lblUsuario);
		
		edtLogin = new JTextField();
		edtLogin.setBackground(new Color(192, 192, 192));
		edtLogin.setBounds(10, 36, 314, 20);
		contentPane.add(edtLogin);
		edtLogin.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(10, 67, 46, 14);
		contentPane.add(lblSenha);
		
		pfSenha = new JPasswordField();
		pfSenha.setBackground(new Color(192, 192, 192));
		pfSenha.setBounds(10, 92, 314, 20);
		contentPane.add(pfSenha);
		
		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					usuario = new Usuario(edtLogin.getText(), pfSenha.getText());
					
					usuarioService.logar(usuario);
					logDeAcessosService.inserir(usuario);;
					
					ViewPrincipal view = new ViewPrincipal();
					view.setVisible(true);
					dispose();
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
				
				
			}
		});
		btnLogar.setBackground(new Color(192, 192, 192));
		btnLogar.setBounds(123, 127, 89, 23);
		contentPane.add(btnLogar);
	}
}
