VETOR COM NOME E NOTA

import java.util.Scanner;
import java.util.ArrayList;

class Alunos{
    String nome;
    int nota;
            
        public Alunos(String nome, int nota){
            this.nome = nome;
            this.nota = nota;
        }
}

public class Main
{
	public static void main(String[] args) {
	    Scanner kkk = new Scanner(System.in);
	    ArrayList<Alunos> total = new ArrayList<>();
	    
		for (int i = 0; i < 3; i ++){
		    System.out.printf("Digite nome: ");    
		    String nome = kkk.nextLine();
		    
		    System.out.printf("Digite a sua nota: ");
		    int nota = kkk.nextInt();
		    kkk.nextLine();
		    
		    total.add(new Alunos(nome, nota));
		}
		
		
		for (Alunos alunos : total) {
		    System.out.printf("Nomes: %s | Nota: %d \n", alunos.nome, alunos.nota);
		}
	
	}
}
