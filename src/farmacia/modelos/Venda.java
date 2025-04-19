package farmacia.modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import farmacia.excecoes.EstoqueInsuficienteException;
import farmacia.repositorios.RepositorioMedicamentos;

public class Venda {
    private int vendaId;
    private Map<Medicamento, Integer> itensVendidos; // Medicamento e quantidade
    private double valorTotal;
    private LocalDateTime dataHora;
    private Funcionario funcionario;
    private Cliente cliente;
    private boolean finalizada;
    
    public Venda(int vendaId, LocalDateTime dataHora, Funcionario funcionario, Cliente cliente) {
        this.vendaId = vendaId;
        this.itensVendidos = new HashMap<>();
        this.valorTotal = 0.0;
        this.dataHora = dataHora;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.finalizada = false;
    }
    
    public void adicionarItem(Medicamento medicamento, int quantidade, RepositorioMedicamentos repositorio) 
            throws EstoqueInsuficienteException {
        // Verificar se o medicamento existe no repositório e tem estoque suficiente
        Medicamento medicamentoNoEstoque = repositorio.buscarPorNome(medicamento.getNome());
        
        if (medicamentoNoEstoque == null) {
            throw new EstoqueInsuficienteException("Medicamento não encontrado no estoque: " + medicamento.getNome());
        }
        
        if (!medicamentoNoEstoque.temEstoqueSuficiente(quantidade)) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para " + medicamento.getNome() + 
                    ". Disponível: " + medicamentoNoEstoque.getQuantidade() + ", Solicitado: " + quantidade);
        }
        
        // Adicionar item à venda
        if (itensVendidos.containsKey(medicamentoNoEstoque)) {
            // Se o medicamento já está na venda, soma a quantidade
            int quantidadeExistente = itensVendidos.get(medicamentoNoEstoque);
            itensVendidos.put(medicamentoNoEstoque, quantidadeExistente + quantidade);
        } else {
            // Se é um novo medicamento na venda
            itensVendidos.put(medicamentoNoEstoque, quantidade);
        }
        
        // Recalcular o valor total
        calcularTotal();
    }
    
    public void calcularTotal() {
        valorTotal = 0.0;
        for (Map.Entry<Medicamento, Integer> item : itensVendidos.entrySet()) {
            Medicamento medicamento = item.getKey();
            int quantidade = item.getValue();
            valorTotal += medicamento.getPreco() * quantidade;
        }
    }
    
    public void aplicarDesconto(double percentual) {
        if (percentual > 0 && percentual <= 100) {
            double valorDesconto = valorTotal * (percentual / 100.0);
            valorTotal -= valorDesconto;
        }
    }
    
    public double getValorTotal() {
        return valorTotal;
    }
    
    public int getVendaId() {
        return vendaId;
    }
    
    public Map<Medicamento, Integer> getItensVendidos() {
        return itensVendidos;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public boolean isFinalizada() {
        return finalizada;
    }
    
    public void finalizar(RepositorioMedicamentos repositorio) {
        if (!finalizada) {
            // Atualizar estoque para cada medicamento vendido
            for (Map.Entry<Medicamento, Integer> item : itensVendidos.entrySet()) {
                Medicamento medicamento = item.getKey();
                int quantidade = item.getValue();
                
                Medicamento medicamentoNoEstoque = repositorio.buscarPorNome(medicamento.getNome());
                medicamentoNoEstoque.diminuirEstoque(quantidade);
            }
            
            // Marcar como finalizada
            finalizada = true;
        }
    }
    
    public void imprimirDetalhes() {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        System.out.println("Venda #" + vendaId);
        System.out.println("Data/Hora: " + dataHora.format(formatoData));
        System.out.println("Funcionário: " + funcionario.getNome());
        
        if (cliente != null) {
            System.out.println("Cliente: " + cliente.getNome() + " (CPF: " + cliente.getCpf() + ")");
        } else {
            System.out.println("Cliente: Não identificado");
        }
        
        System.out.println("\nItens:");
        for (Map.Entry<Medicamento, Integer> item : itensVendidos.entrySet()) {
            Medicamento medicamento = item.getKey();
            int quantidade = item.getValue();
            double subtotal = medicamento.getPreco() * quantidade;
            
            System.out.printf("- %s x%d: R$ %.2f (Subtotal: R$ %.2f)\n", 
                medicamento.getNome(), quantidade, medicamento.getPreco(), subtotal);
        }
        
        System.out.printf("\nValor Total: R$ %.2f\n", valorTotal);
    }
}
