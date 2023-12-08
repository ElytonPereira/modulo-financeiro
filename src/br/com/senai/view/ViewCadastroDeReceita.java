
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

import br.com.senai.core.domain.Cliente;
import br.com.senai.core.domain.Receita;
import br.com.senai.core.service.ClienteService;
import br.com.senai.core.service.ReceitaService;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;

public class ViewCadastroDeReceita extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtValor;
	private JTextArea taDescricao;
	private JFormattedTextField ftfDataDeLancamento;
	private JComboBox<Cliente> cbCliente;
	
	private Receita receita;
	private ReceitaService receitaService;
	private ClienteService clienteService;
	
	public void setReceita(Receita receita) {
		this.receita = receita;
		this.taDescricao.setText(receita.getDescricao());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataDeLancamentoFormatada = receita.getDataDeLancamento().format(dtf);
		this.ftfDataDeLancamento.setText(dataDeLancamentoFormatada);
		
		this.edtValor.setText(String.valueOf(receita.getValor()));
		this.cbCliente.setSelectedItem(receita.getCliente());
		
	}
	
	public void carregarComboClientes() {
		List<Cliente> clientes = clienteService.listarClientes();
		for (Cliente c : clientes) {
			this.cbCliente.addItem(c);
		}
	}

	public ViewCadastroDeReceita() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewCadastroDeReceita.class.getResource("/br/com/senai/imagens/receita.png")));
		this.receitaService = new ReceitaService();
		this.clienteService = new ClienteService();
		setTitle("Gerenciar Receita - Cadastro");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 370);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(229, 229, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBackground(new Color(192, 192, 192));
		btnConsultar.setIcon(new ImageIcon(ViewCadastroDeReceita.class.getResource("/br/com/senai/imagens/consultar.png")));
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaReceita view = new ViewConsultaReceita();
				view.setVisible(true);
				dispose();
			}
		});
		btnConsultar.setBounds(429, 13, 103, 26);
		contentPane.add(btnConsultar);
		
		JLabel lblNewLabel_1 = new JLabel("Cliente");
		lblNewLabel_1.setBounds(10, 61, 91, 16);
		contentPane.add(lblNewLabel_1);
		
		cbCliente = new JComboBox<Cliente>();
		cbCliente.setBackground(new Color(192, 192, 192));
		cbCliente.setBounds(10, 86, 220, 22);
		contentPane.add(cbCliente);
		
		JLabel lblNewLabel_2 = new JLabel("Data de Lançamento");
		lblNewLabel_2.setBounds(239, 61, 112, 16);
		contentPane.add(lblNewLabel_2);
		
		ftfDataDeLancamento = new JFormattedTextField();
		ftfDataDeLancamento.setBackground(new Color(192, 192, 192));
		ftfDataDeLancamento.setBounds(240, 87, 111, 22);
		contentPane.add(ftfDataDeLancamento);
		try {
			MaskFormatter mascara = new MaskFormatter("##/##/####");
			mascara.install(ftfDataDeLancamento);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JLabel lblNewLabel_3 = new JLabel("Valor(R$)");
		lblNewLabel_3.setBounds(361, 61, 53, 16);
		contentPane.add(lblNewLabel_3);
		
		edtValor = new JTextField();
		edtValor.setBackground(new Color(192, 192, 192));
		edtValor.setBounds(361, 87, 138, 20);
		contentPane.add(edtValor);
		edtValor.setColumns(10);
		
		taDescricao = new JTextArea();
		taDescricao.setWrapStyleWord(true);
		taDescricao.setLineWrap(true);
		taDescricao.setBackground(new Color(192, 192, 192));
		taDescricao.setBounds(10, 144, 524, 135);
		contentPane.add(taDescricao);
		
		JLabel lblNewLabel_4 = new JLabel("Descrição");
		lblNewLabel_4.setBounds(10, 119, 58, 16);
		contentPane.add(lblNewLabel_4);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(new Color(192, 192, 192));
		btnSalvar.setIcon(new ImageIcon(ViewCadastroDeReceita.class.getResource("/br/com/senai/imagens/salvar.png")));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					String descricao = taDescricao.getText();
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate dataDeLancamento = LocalDate.from(dtf.parse(ftfDataDeLancamento.getText()));
					
					double valor = 0;
					
					valor = Double.parseDouble(edtValor.getText());						
					
					Cliente cliente = (Cliente) cbCliente.getSelectedItem();
					
					if (receita == null) {
						
						receita = new Receita(descricao, dataDeLancamento, valor, cliente);
						receitaService.salvar(receita);
						
						JOptionPane.showMessageDialog(contentPane, "Receita inserida com sucesso");
						
						edtValor.setText("");
						ftfDataDeLancamento.setText("");
						taDescricao.setText("");
						cbCliente.setSelectedIndex(0);
						
						receita = null;
						
					} else {
						
						receita.setDescricao(descricao);
						receita.setDataDeLancamento(dataDeLancamento);
						receita.setValor(valor);
						receita.setCliente(cliente);
						receitaService.salvar(receita);
						
						JOptionPane.showMessageDialog(contentPane, "Receita alterada com sucesso");
						
					}					
					
				}catch (DateTimeParseException dtpe) {
					JOptionPane.showMessageDialog(contentPane, "A data de lançamento é obrigatória e deve estar no formato DD/MM/AAAA");	
				}catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(contentPane, "O valor é obrigatório e deve ser um número");
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
					if (!receita.isPersistido()) {
						receita = null;						
					}
				}
			}
		});
		btnSalvar.setBounds(448, 292, 84, 26);
		contentPane.add(btnSalvar);
		this.carregarComboClientes();
	}
}
