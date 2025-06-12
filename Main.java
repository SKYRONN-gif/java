import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner tr = new Scanner (System.in);
		Gerenciador gerenciador = new Gerenciador();
		
		int opcao = 0;
		do {
			menu();
			try {
				opcao = Integer.parseInt(tr.nextLine());
				switch (opcao) {
				case 1 -> gerenciador.cadastrar(tr);
				case 2 -> gerenciador.excluir(tr);
				case 3 -> gerenciador.alterar(tr); 
				case 4 -> gerenciador.buscarProduto(tr);
				case 5 -> gerenciador.menos(tr);
				case 6 -> gerenciador.mais(tr);
				case 7 -> gerenciador.relatorio();
				case 8 -> {
					gerenciador.salvar();
					System.out.println("Saindo...");
				} default -> System.out.println("Opção invalida. ");
				}
			} catch (NumberFormatException a) {
		        System.out.println("Erro. Digite um numero.");
		      }
		} while (opcao != 8);
	}
		public static void menu() {
			System.out.println("==MENU==");
		    System.out.println("1 - Cadastrar produto");
		    System.out.println("2 - Excluir produto");
		    System.out.println("3 - Alterar produto");
		    System.out.println("4 - Buscar produto por nome");
		    System.out.println("5 - Saida de estoque");
		    System.out.println("6 - Add de estoque");
		    System.out.println("7 - Relatorio");
		    System.out.println("8 - Sair");
		    System.out.print("Escolha uma opcao: ");
		}
	
}
