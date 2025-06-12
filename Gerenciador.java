import java.io.*;
import java.util.*;

public class Gerenciador {
	private final List<Produto> produtos = new ArrayList<>();
	
	 public List<Produto> getProdutos() {
	        return produtos;
	    }
	
	public void cadastrar (Scanner tr) {
		System.out.println("Nome do produto (ou 'Cancelar' para sair.):");
		String nome = tr.nextLine().trim();
		
		if (nome.equalsIgnoreCase("Cancelar") || nome.isEmpty() ) {
			System.out.println("Cancelado. ");
			return;
		}
		
		double preco = lerPreco (tr, "Preço (R$): ");
		int quantidade = lerQuantidade (tr, "Quantidade: ");
		
		produtos.add(new Produto(nome,preco,quantidade));
		System.out.println("Cadastro feito com sucesso. ");
	}
	
	public void excluir (Scanner tr) {
		if (produtos.isEmpty()) {
		      System.out.println("Nenhum produto cadastrado.");
		      return;
		    }
		
		System.out.println("Digite o nome do produto para excluir:");
	    String nome = tr.nextLine().trim();
		
	    Optional<Produto> produto = buscar(nome);
	    
	    if (produto.isPresent()) {
	    	produtos.remove(produto.get());
	    	System.out.println("Produto excluido.");
	    } else {
	    	System.out.println("Produto não encontrado. ");
	    }
	    
	}
	
	public void alterar (Scanner tr) {
		if (produtos.isEmpty()) {
			System.out.println("Nenhum produto cadastrado.");
			return;
		}
		
		System.out.println("Nome do produto para alterar: ");
		String nome = tr.nextLine().trim();
		Optional<Produto> resultado = buscar(nome);
		
		if (resultado.isPresent()) {
			Produto pa = resultado.get();
			System.out.println("Novo nome: ");
			pa.setNome(tr.nextLine().trim());
			pa.setPreco(lerPreco(tr, "Novo preco: "));
			pa.setQuant(lerQuantidade(tr, "Nova quantidade: "));
		} else { 
			System.out.println("Produto não encontrado. ");
		}
	}
	
	public void buscarProduto (Scanner tr) {
		System.out.println("Digite o nome do produto que esta buscando:");
		String nome = tr.nextLine().trim();
		
		Optional<Produto> resultado = buscar(nome);
		
		if (resultado.isPresent()) {
			Produto p = resultado.get();
			System.out.printf("Produto encontrado: %s - Preço: %.2f - Quantidade: %d%n", p.getNome(), p.getPreco(), p.getQuant());
		} else {
			System.out.println("Produto não encontrado.");
		}
	}
	
	public void menos (Scanner tr) {
		System.out.println("==SAIDA DE ESTOQUE==");
		System.out.println("Qual item deseja retirar? ");
		String nome = tr.nextLine().trim();
		
		Optional<Produto> resultado = buscar(nome);
		
		if (resultado.isPresent()) {
			Produto pa = resultado.get();
			
			System.out.println("Quantos itens deseja retirar? ");
			int itensRetirados = tr.nextInt();
			tr.nextLine();
			if (itensRetirados > 0) {
				if(itensRetirados <= pa.getQuant()) {
					pa.setQuant(pa.getQuant() - itensRetirados);
					System.out.println("Itens retirados com sucesso.");
				} else {
					System.out.println("A quantidade deve ser maior que o estoque.");
				}
			} else {
				System.out.println("Erro. Deve ser maior que zero. ");
			}
		} else {
			System.out.println("Produto não encontrado.");
		}
	}
	
	public void mais (Scanner tr) {
		System.out.println("==ADIÇÃO DE ESTOQUE==");
		System.out.println("Qual item deseja add? ");
		String nome = tr.nextLine().trim();
		
		Optional<Produto> resultado = buscar(nome);
		
		if (resultado.isPresent()) {
			Produto pa = resultado.get();
			
			System.out.println("Quantos itens deseja add? ");
			int itensAdd = tr.nextInt();
			tr.nextLine();
			if (itensAdd > 0) {
				pa.setQuant(itensAdd + pa.getQuant());
				System.out.println("Add com sucesso.");
			} else {
				System.out.println("Erro. deve ser maior que zero.");
			}
		} else {
			System.out.println("Produto não encontrado.");
		}
	}
	
	public void relatorio () {
		if (produtos.isEmpty()) {
	        System.out.println("Nenhum produto cadastrado.");
	        return;
	      }
		double totalGeral = 0;
		Produto maiscaro = Collections.max(produtos, Comparator.comparingDouble(Produto::getPreco));
		Produto maisbarato = Collections.min(produtos, Comparator.comparingDouble(Produto::getPreco));
		
		System.out.println("\n ==RELATÓRIO== ");
		
		for (Produto p : produtos) {
			System.out.println("\n" + p);
			totalGeral += p.calcularT(); 
		}
		
		System.out.printf("\nTotal geral: %.2f", totalGeral);
		System.out.printf("\nMais caro: %s (R$%.2f)", maiscaro.getNome(), maiscaro.getPreco());
		System.out.printf("\nMais barato: %s (R$%.2f)\n", maisbarato.getNome(), maisbarato.getPreco());
		
	}
	
	private Optional<Produto> buscar (String nome) {
			return produtos.stream()
					.filter(pa -> pa.getNome().equalsIgnoreCase(nome))
					.findFirst();
	}
	
	private double lerPreco (Scanner tr, String mensagem) {
		while (true) {
			System.out.println(mensagem);
			if (tr.hasNextDouble()) {
		        double preco = tr.nextDouble();
		        tr.nextLine();
		        if (preco > 0) return preco; 
		          System.out.println("O preco deve ser maior que zero. ");
		      } else {
		        System.out.println("Erro. Digite um numero valido. ");
		        tr.nextLine();
		      }
		}
	}
	
	private int lerQuantidade (Scanner tr, String mensagem) {
		while (true) {
			System.out.println(mensagem);
			if (tr.hasNextInt()) {
		        int preco = tr.nextInt();
		        tr.nextLine();
		        if (preco > 0) return preco; 
		          System.out.println("O numero deve ser maior que zero. ");
		      } else {
		        System.out.println("Erro. Digite um numero valido. ");
		        tr.nextLine();
		      }
		}
	}
	
	public void salvar() {
	    try (BufferedWriter salve = new BufferedWriter(
	    		new OutputStreamWriter(new FileOutputStream("Lista.txt"), java.nio.charset.StandardCharsets.UTF_8))) {
	      salve.write(String.format("%-15s %-10s %-10s%n","Nome","Preco","Quantidade"));
	      for (Produto p : produtos) {
	        salve.write(String.format("%-15s %-10.2f %-10d%n", p.getNome(), p.getPreco(), p.getQuant()));
	      }
	      System.out.println("Produtos salvos com sucesso.");
	    } catch (IOException e) {
	      System.out.println("Erro ao salvar: " + e.getMessage());
	    }
	  }
	
}
