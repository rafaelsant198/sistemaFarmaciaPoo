package farmacia.modelos;

public class Funcionario {
    private int funcionarioId;
    private String nome;
    
    public Funcionario(int funcionarioId, String nome) {
        this.funcionarioId = funcionarioId;
        this.nome = nome;
    }
    
    public int getFuncionarioId() {
        return funcionarioId;
    }
    
    public String getNome() {
        return nome;
    }
    
    @Override
    public String toString() {
        return "Funcionario [ID=" + funcionarioId + ", Nome=" + nome + "]";
    }
}
