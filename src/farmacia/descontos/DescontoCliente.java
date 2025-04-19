package farmacia.descontos;

import farmacia.interfaces.Desconto;
import farmacia.modelos.Venda;

public class DescontoCliente implements Desconto {
    private static final double PERCENTUAL_FIXO = 5.0;
    
    @Override
    public void aplicar(Venda venda, double percentual) {
        // Ignoramos o percentual passado e aplicamos o desconto fixo para clientes
        venda.aplicarDesconto(PERCENTUAL_FIXO);
    }
}
