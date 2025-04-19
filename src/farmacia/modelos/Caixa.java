package farmacia.modelos;

public class Caixa extends Funcionario {
    
    public Caixa(int funcionarioId, String nome) {
        super(funcionarioId, nome);
    }
    
    public double processarPagamento(double valor) {
        // Lógica para processar pagamento poderia envolver integração com sistema de pagamento
        // Simplificando para o exercício, apenas retornamos o valor recebido
        return valor;
    }
    
    @Override
    public String toString() {
        return "Caixa [ID=" + getFuncionarioId() + ", Nome=" + getNome() + "]";
    }
}
