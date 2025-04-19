package farmacia.descontos;

import farmacia.interfaces.Desconto;
import farmacia.modelos.Venda;

public class DescontoGerente implements Desconto {
    private static final double PERCENTUAL_MAXIMO = 20.0;
    
    @Override
    public void aplicar(Venda venda, double percentual) {
        // Gerentes podem aplicar descontos de até 20%
        if (percentual > 0 && percentual <= PERCENTUAL_MAXIMO) {
            venda.aplicarDesconto(percentual);
        }
    }
}
