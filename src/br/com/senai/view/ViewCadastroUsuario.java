package br.com.senai.view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Ativo;
import br.com.senai.core.domain.Usuario;
import br.com.senai.core.service.UsuarioService;

public class ViewCadastroUsuario extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtLogin;
	private JPasswordField edtSenha;
	private JPasswordField edtConfirmacaoDaSenha;
	JCheckBox chkAtivo;
	
	private Usuario usuario;
	private UsuarioService usuarioService;
	
	public void setUsuario(Usuario usuario) {
		
		this.usuario = usuario;
		
		edtLogin.setText(usuario.getLogin());
		edtLogin.setEnabled(false);
		edtSenha.setText(null);
		edtConfirmacaoDaSenha.setText(usuario.getConfirmacaoSenha());
		chkAtivo.setSelected(usuario.getAtivo() == Ativo.S);
		
	}
	
	public ViewCadastroUsuario() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewCadastroUsuario.class.getResource("/br/com/senai/imagens/usuario.png")));
		this.usuarioService = new UsuarioService();
		setTitle("Gerenciar Usu\u00E1rios - Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 280);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnConsulta = new JButton("Consulta");
		btnConsulta.setBackground(new Color(192, 192, 192));
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaUsuario view = new ViewConsultaUsuario();
				view.setVisible(true);
				dispose();
				
			}
		});
		btnConsulta.setBounds(374, 12, 98, 26);
		contentPane.add(btnConsulta);
		
		edtLogin = new JTextField();
		edtLogin.setBackground(new Color(192, 192, 192));
		edtLogin.setBounds(12, 50, 460, 20);
		contentPane.add(edtLogin);
		edtLogin.setColumns(10);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(12, 22, 55, 16);
		contentPane.add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(12, 82, 36, 16);
		contentPane.add(lblSenha);
		
		JLabel lblConfirmaoDaSenha = new JLabel("Confirma\u00E7\u00E3o da Senha");
		lblConfirmaoDaSenha.setBounds(12, 142, 129, 16);
		contentPane.add(lblConfirmaoDaSenha);
		
		chkAtivo = new JCheckBox("Ativo");
		chkAtivo.setBackground(new Color(192, 192, 192));
		chkAtivo.setBounds(12, 198, 112, 24);
		contentPane.add(chkAtivo);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(new Color(192, 192, 192));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String login = edtLogin.getText();
					String senha = edtSenha.getText();
					String confirmacaoSenha= edtConfirmacaoDaSenha.getText();
					Ativo ativo = chkAtivo.isSelected() ? Ativo.S : Ativo.N;
					
					if (usuario == null) {
						
						usuario = new Usuario(login, senha, confirmacaoSenha, ativo);
						usuarioService.salvar(usuario);
						JOptionPane.showMessageDialog(contentPane, "Usuário inserido com sucesso");
						
						edtLogin.setText("");
						edtSenha.setText("");
						edtConfirmacaoDaSenha.setText("");
						chkAtivo.setSelected(false);
						
						usuario = null;
						
					} else {
						
						usuario.setLogin(login);
						usuario.setSenha(senha);
						usuario.setConfirmacaoSenha(confirmacaoSenha);
						usuario.setAtivo(ativo);
						usuarioService.salvar(usuario);
						
						JOptionPane.showMessageDialog(contentPane, "Usuário alterado com sucesso");
						
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
					if (!usuarioService.isPersistido(usuario.getLogin())) {
						usuario = null;						
					}
				}
				
			}
		});
		btnSalvar.setBounds(374, 202, 98, 26);
		contentPane.add(btnSalvar);
		
		edtSenha = new JPasswordField();
		edtSenha.setBackground(new Color(192, 192, 192));
		edtSenha.setBounds(12, 110, 460, 20);
		contentPane.add(edtSenha);
		
		edtConfirmacaoDaSenha = new JPasswordField();
		edtConfirmacaoDaSenha.setBackground(new Color(192, 192, 192));
		edtConfirmacaoDaSenha.setBounds(12, 170, 460, 20);
		contentPane.add(edtConfirmacaoDaSenha);
	}
}
