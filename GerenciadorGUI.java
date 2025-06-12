import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GerenciadorGUI extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private DefaultTableModel Tabela;
	private JTable Produtos;
	
	private Gerenciador gerenciador = new Gerenciador();
	
	private JTextField campoN = new JTextField(15);
	private JTextField campoP = new JTextField(10);
	private JTextField campoQ = new JTextField(5);
	private JTextField campoB = new JTextField(15);
	private JButton botaoCadastro = new JButton("Cadastrar");
	private JButton botaoExcluir = new JButton("Excluir");
	private JButton botaoAlterar = new JButton ("Alterar");
	private JButton botaoBuscar = new JButton ("Buscar");
	
	public GerenciadorGUI() {
		super("Gerenciador tester");
		
		JPanel busca = new JPanel();
		busca.setLayout(new FlowLayout(FlowLayout.LEFT));
		busca.add(new JLabel("Buscar por nome"));
		busca.add(campoB);
		busca.add(botaoBuscar);
		
		JPanel painel = new JPanel();
		painel.setLayout(new GridLayout(4,2,5,5));
		
		painel.add(new JLabel("Nome:"));
		painel.add(campoN);
		
		painel.add(new JLabel("Preço(R$):"));
		painel.add(campoP);
		
		painel.add(new JLabel("Quantidade:"));
		painel.add(campoQ);
		
		painel.add(botaoCadastro);
		painel.add(botaoExcluir);
		
		JPanel painelAlterar = new JPanel();
		painelAlterar.setLayout(new FlowLayout(FlowLayout.LEFT));
		painelAlterar.add(botaoAlterar);
		
		Tabela = new DefaultTableModel(new Object[] {"Nome", "Preço", "Quantidade"}, 0);
		Produtos = new JTable(Tabela);
		JScrollPane scroll = new JScrollPane(Produtos);
		scroll.setPreferredSize(new Dimension(600, 150));
		
		Produtos.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int i = Produtos.getSelectedRow();
				if (i != -1) {
					campoN.setText(Tabela.getValueAt(i, 0).toString());
					campoP.setText(Tabela.getValueAt(i, 1).toString());
					campoQ.setText(Tabela.getValueAt(i, 2).toString());
				}
			}
		});
		
		JPanel principal = new JPanel (new BorderLayout(10, 10));
		principal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JPanel topo = new JPanel(new BorderLayout());
		topo.add(busca, BorderLayout.NORTH);
		topo.add(painel, BorderLayout.CENTER);

		principal.add(topo, BorderLayout.NORTH);
		
		principal.add(scroll, BorderLayout.CENTER);
		principal.add(painelAlterar, BorderLayout.SOUTH);
		
		add(principal);
		
		botaoCadastro.addActionListener(e -> cadastrar());
		botaoExcluir.addActionListener(e -> excluir());
		botaoAlterar.addActionListener(e -> alterar());
		botaoBuscar.addActionListener(e -> buscar());
		
		campoN.addActionListener(e -> campoP.requestFocus());
		campoP.addActionListener(e -> campoQ.requestFocus());
		campoQ.addActionListener(e -> botaoCadastro.doClick());
		campoB.addActionListener(e -> buscar());
		
		campoN.requestFocus();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,400);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void cadastrar() {
		try {
			String nome = campoN.getText().trim();
			double preco = Double.parseDouble(campoP.getText().trim());
			int quantidade = Integer.parseInt(campoQ.getText().trim());
			
			if (preco <= 0 || quantidade <= 0 || nome.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Valor invalido.");
				return;
			} 
			
			gerenciador.getProdutos().add(new Produto(nome, preco, quantidade)); 
			JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso.");
			
			campoN.setText("");
			campoP.setText("");
			campoQ.setText("");
			
			atualizar();
			
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Valores invalidos.");
		}
		campoN.requestFocus();
	}
	
	private void excluir() {
		int selecionado = Produtos.getSelectedRow();
		
		if (selecionado == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um produto para excluir");
			return;
		}
		
		gerenciador.getProdutos().remove(selecionado);
		atualizar();
		campoN.requestFocus();
	}
	
	public void alterar() {
		int selecionado = Produtos.getSelectedRow();
		
		if(selecionado == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um produto para alterar. ");
			return;
		}
		try {
			String nome = campoN.getText().trim();
			double preco = Double.parseDouble(campoP.getText().trim());
			int quantidade = Integer.parseInt(campoQ.getText().trim());
			
			if (preco <= 0 || quantidade <= 0 || nome.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Valor invalido.");
				return;
			} 
			
			Produto p = gerenciador.getProdutos().get(selecionado);
			p.setNome(nome);
			p.setPreco(preco);
			p.setQuant(quantidade);

			JOptionPane.showMessageDialog(this, "Produto alterado com sucesso.");
			
			campoN.setText("");
			campoP.setText("");
			campoQ.setText("");
			
			atualizar();
			
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Valores invalidos.");
		}
		campoN.requestFocus();
	}
	
	public void buscar() {
		String termo = campoB.getText().trim();
		if (termo.isEmpty()) {
			atualizar();
			return;
		}
		
		Tabela.setRowCount(0);
		
		for (Produto p : gerenciador.getProdutos()) {
			if (p.getNome().contains(termo)) {
				Tabela.addRow(new Object[] {p.getNome(), p.getPreco(), p.getQuant()});
			}
		}
		campoN.requestFocus();
	}
	
	private void atualizar() {
		Tabela.setRowCount(0);
		
		for (Produto p : gerenciador.getProdutos()) {
			Tabela.addRow(new Object[] {p.getNome(), p.getPreco(), p.getQuant()});
		}
	}
}
