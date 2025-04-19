package farmacia.modelos;

import farmacia.excecoes.CPFInvalidoException;
import farmacia.interfaces.Desconto;
import farmacia.descontos.DescontoCliente;

public class Cliente {
    private int clienteId;
    private String nome;
    private String cpf;
    private Desconto estrategiaDesconto;
    
    public Cliente(int clienteId, String nome, String cpf) throws CPFInvalidoException {
        this.clienteId = clienteId;
        this.nome = nome;
        
        if (validarCPF(cpf)) {
            this.cpf = cpf;
        } else {
            throw new CPFInvalidoException("CPF inválido: " + cpf);
        }
        
        this.estrategiaDesconto = new DescontoCliente();
    }
    
    private boolean validarCPF(String cpf) {
        // Remover caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // Verificar se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verificar se todos os dígitos são iguais (caso inválido)
        boolean todosDigitosIguais = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosDigitosIguais = false;
                break;
            }
        }
        
        if (todosDigitosIguais) {
            return false;
        }
        
        // Para simplificar, não estamos implementando a validação completa do algoritmo de CPF
        // Em um sistema real, seria necessário implementar o algoritmo completo de validação
        
        return true;
    }
    
    public int getClienteId() {
        return clienteId;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void aplicarDesconto(Venda venda) {
        // Desconto fixo para clientes cadastrados (5%)
        estrategiaDesconto.aplicar(venda, 5.0);
    }
    
    @Override
    public String toString() {
        return "Cliente [ID=" + clienteId + ", Nome=" + nome + ", CPF=" + cpf + "]";
    }
}
