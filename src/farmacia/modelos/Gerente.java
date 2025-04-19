package farmacia.modelos;

import farmacia.interfaces.Desconto;
import farmacia.descontos.DescontoGerente;

public class Gerente extends Caixa {
    private Desconto estrategiaDesconto;
    
    public Gerente(int funcionarioId, String nome) {
        super(funcionarioId, nome);
        this.estrategiaDesconto = new DescontoGerente();
    }
    
    public void aplicarDesconto(Venda venda, double percentualDesconto) {
        estrategiaDesconto.aplicar(venda, percentualDesconto);
    }
    
    @Override
    public String toString() {
        return "Gerente [ID=" + getFuncionarioId() + ", Nome=" + getNome() + "]";
    }
}
