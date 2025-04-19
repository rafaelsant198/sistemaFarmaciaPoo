package farmacia.modelos;

import java.time.LocalDate;

public class Medicamento {
    private String nome;
    private int quantidade;
    private double preco;
    private LocalDate dataValidade;
    private String id;
    
    public Medicamento(String nome, int quantidade, double preco, LocalDate dataValidade, String id) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dataValidade = dataValidade;
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public LocalDate getDataValidade() {
        return dataValidade;
    }
    
    public String getId() {
        return id;
    }
    
    public boolean estaExpirado() {
        return LocalDate.now().isAfter(dataValidade);
    }
    
    public boolean temEstoqueSuficiente(int quantidadeDesejada) {
        return quantidade >= quantidadeDesejada;
    }
    
    public void diminuirEstoque(int quantidade) {
        this.quantidade -= quantidade;
    }
    
    @Override
    public String toString() {
        return "Medicamento: Nome=" + nome + 
               ", Preço=" + preco + 
               ", Quantidade=" + quantidade + 
               ", ID=" + id + 
               ", Data de Validade=" + dataValidade;
    }
}
