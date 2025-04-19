package farmacia.interfaces;

import farmacia.modelos.Venda;

public interface Desconto {
    void aplicar(Venda venda, double percentual);
}
