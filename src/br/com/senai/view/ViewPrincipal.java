package br.com.senai.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public ViewPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewPrincipal.class.getResource("/br/com/senai/imagens/financeiro.png")));
		setResizable(false);
		setTitle("M\u00F3dulo de Gest\u00E3o Financeira");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 450);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(240, 240, 240));
		setJMenuBar(menuBar);
		
		JMenu menuCadastros = new JMenu("Cadastros");
		menuCadastros.setBackground(new Color(240, 240, 240));
		menuBar.add(menuCadastros);
		
		JMenuItem opCentrosDeCusto = new JMenuItem("Centros de Custo");
		opCentrosDeCusto.setIcon(new ImageIcon(ViewPrincipal.class.getResource("/br/com/senai/imagens/centronovo.png")));
		opCentrosDeCusto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaCentroDeCusto view = new ViewConsultaCentroDeCusto();
				view.setVisible(true);
				
			}
		});
		menuCadastros.add(opCentrosDeCusto);
		
		JMenu menuLancamentos = new JMenu("Lan\u00E7amentos");
		menuBar.add(menuLancamentos);
		
		JMenuItem opReceitas = new JMenuItem("Receitas");
		opReceitas.setIcon(new ImageIcon(ViewPrincipal.class.getResource("/br/com/senai/imagens/receita.png")));
		opReceitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaReceita view = new ViewConsultaReceita();
				view.setVisible(true);
				
			}
		});
		menuLancamentos.add(opReceitas);
		
		JMenuItem opDespesas = new JMenuItem("Despesas");
		opDespesas.setIcon(new ImageIcon(ViewPrincipal.class.getResource("/br/com/senai/imagens/despesa.png")));
		opDespesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaDespesa view = new ViewConsultaDespesa();
				view.setVisible(true);
				
			}
		});
		menuLancamentos.add(opDespesas);
		
		JMenuItem opDemonstrativo = new JMenuItem("Demonstrativo");
		opDemonstrativo.setIcon(new ImageIcon(ViewPrincipal.class.getResource("/br/com/senai/imagens/demonstrativo.png")));
		opDemonstrativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewDemonstrativo view = new ViewDemonstrativo();
				view.setVisible(true);
			}
		});
		menuLancamentos.add(opDemonstrativo);
		
		JMenu mnSair = new JMenu("Sair");
		mnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		
		JMenu mnSistema = new JMenu("Sistema");
		menuBar.add(mnSistema);
		
		JMenuItem opUsuarios = new JMenuItem("Usu\u00E1rios");
		opUsuarios.setIcon(new ImageIcon(ViewPrincipal.class.getResource("/br/com/senai/imagens/usuario.png")));
		opUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewConsultaUsuario view = new ViewConsultaUsuario();
				view.setVisible(true);
			}
		});
		mnSistema.add(opUsuarios);
		
		JMenuItem opLogDeAcessos = new JMenuItem("Log de acessos");
		opLogDeAcessos.setIcon(new ImageIcon(ViewPrincipal.class.getResource("/br/com/senai/imagens/usuarioLogs.png")));
		opLogDeAcessos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewLogDeAcessos view = new ViewLogDeAcessos();
				view.setVisible(true);
				
			}
		});
		mnSistema.add(opLogDeAcessos);
		menuBar.add(mnSair);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTelaMeio = new JLabel("");
		lblTelaMeio.setIcon(new ImageIcon(ViewPrincipal.class.getResource("/br/com/senai/imagens/planoDeFundo.png")));
		lblTelaMeio.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelaMeio.setBounds(0, 0, 726, 389);
		contentPane.add(lblTelaMeio);
		setLocationRelativeTo(null);
	}
}
