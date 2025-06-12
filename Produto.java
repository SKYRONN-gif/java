public class Produto {
    private String nome;
    private double preco;
    private int quantidade;

    public Produto (String nome, double preco, int quantidade) {
      this.nome = nome;
      this.preco = preco;
      this.quantidade = quantidade;
    }
    
    public String getNome() {return nome; }
    public double getPreco() {return preco; }
    public int getQuant() {return quantidade; }
    
    public void setNome(String nome) {this.nome=nome; }
    public void setPreco(double preco) {this.preco=preco; }
    public void setQuant(int quantidade) {this.quantidade=quantidade; }
    
    public double calcularT() {
    	return preco * quantidade;
    }
    
    @Override
    public String toString() {
    	return String.format(
    			"Nome: %s\nPreço: %.2f\nQuantidade: %d\nTotal: %.2f", nome, preco, quantidade, calcularT()
    	);
    }
    
  } 
