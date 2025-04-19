package farmacia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import farmacia.excecoes.CPFInvalidoException;
import farmacia.excecoes.EstoqueInsuficienteException;
import farmacia.modelos.Caixa;
import farmacia.modelos.Cliente;
import farmacia.modelos.Gerente;
import farmacia.modelos.Medicamento;
import farmacia.modelos.Venda;
import farmacia.repositorios.RepositorioMedicamentos;

public class Aplicacao {
    private static Scanner scanner = new Scanner(System.in);
    private static RepositorioMedicamentos repositorioMedicamentos = new RepositorioMedicamentos();
    
    public static void main(String[] args) {
        inicializarDados();
        
        int opcao = 0;
        do {
            exibirMenu();
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    listarMedicamentos();
                    break;
                case 2:
                    adicionarMedicamento();
                    break;
                case 3:
                    realizarVenda();
                    break;
                case 4:
                    realizarVendaComDesconto();
                    break;
                case 0:
                    System.out.println("Sistema finalizado. Obrigado!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close();
    }
    
    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE FARMÁCIA ===");
        System.out.println("1. Listar Medicamentos");
        System.out.println("2. Adicionar Medicamento");
        System.out.println("3. Realizar Venda (Caixa)");
        System.out.println("4. Realizar Venda com Desconto (Gerente)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void inicializarDados() {
        // Adicionar alguns medicamentos iniciais
        repositorioMedicamentos.adicionarMedicamento(new Medicamento("Paracetamol", 100, 10.50, LocalDate.of(2025, 12, 31), "12345"));
        repositorioMedicamentos.adicionarMedicamento(new Medicamento("Aspirina", 150, 8.75, LocalDate.of(2024, 10, 15), "67890"));
        repositorioMedicamentos.adicionarMedicamento(new Medicamento("Dipirona", 80, 5.25, LocalDate.of(2026, 5, 20), "54321"));
        repositorioMedicamentos.adicionarMedicamento(new Medicamento("Amoxicilina", 50, 25.00, LocalDate.of(2024, 8, 10), "98765"));
    }
    
    private static void listarMedicamentos() {
        System.out.println("\n=== MEDICAMENTOS DISPONÍVEIS ===");
        for (Medicamento medicamento : repositorioMedicamentos.listarTodos()) {
            System.out.println(medicamento);
        }
    }
    
    private static void adicionarMedicamento() {
        System.out.println("\n=== ADICIONAR MEDICAMENTO ===");
        
        System.out.print("Nome do medicamento: ");
        String nome = scanner.nextLine();
        
        System.out.print("Quantidade: ");
        int quantidade = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Preço: ");
        double preco = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Data de validade (AAAA-MM-DD): ");
        LocalDate dataValidade = LocalDate.parse(scanner.nextLine());
        
        System.out.print("ID do medicamento: ");
        String id = scanner.nextLine();
        
        Medicamento medicamento = new Medicamento(nome, quantidade, preco, dataValidade, id);
        repositorioMedicamentos.adicionarMedicamento(medicamento);
        
        System.out.println("Medicamento adicionado com sucesso!");
    }
    
    private static void realizarVenda() {
        System.out.println("\n=== REALIZAR VENDA ===");
        
        Caixa caixa = new Caixa(1, "João");
        Venda venda = new Venda(1, LocalDateTime.now(), caixa, null);
        
        boolean continuarVenda = true;
        while (continuarVenda) {
            listarMedicamentos();
            
            System.out.print("Digite o nome do medicamento (ou 'fim' para encerrar): ");
            String nomeMedicamento = scanner.nextLine();
            
            if (nomeMedicamento.equalsIgnoreCase("fim")) {
                continuarVenda = false;
                continue;
            }
            
            Medicamento medicamento = repositorioMedicamentos.buscarPorNome(nomeMedicamento);
            if (medicamento == null) {
                System.out.println("Medicamento não encontrado!");
                continue;
            }
            
            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());
            
            try {
                venda.adicionarItem(medicamento, quantidade, repositorioMedicamentos);
                System.out.println("Item adicionado à venda!");
            } catch (EstoqueInsuficienteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        
        System.out.println("\n=== RESUMO DA VENDA ===");
        venda.imprimirDetalhes();
        
        System.out.print("\nConfirmar venda (S/N)? ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            venda.finalizar(repositorioMedicamentos);
            System.out.println("Venda realizada com sucesso!");
        } else {
            System.out.println("Venda cancelada!");
        }
    }
    
    private static void realizarVendaComDesconto() {
        System.out.println("\n=== REALIZAR VENDA COM DESCONTO (GERENTE) ===");
        
        Gerente gerente = new Gerente(2, "Maria");
        
        System.out.print("O cliente possui cadastro? (S/N): ");
        String temCadastro = scanner.nextLine();
        
        Cliente cliente = null;
        if (temCadastro.equalsIgnoreCase("S")) {
            System.out.print("Nome do cliente: ");
            String nomeCliente = scanner.nextLine();
            
            System.out.print("CPF do cliente: ");
            String cpf = scanner.nextLine();
            
            try {
                cliente = new Cliente(1, nomeCliente, cpf);
            } catch (CPFInvalidoException e) {
                System.out.println("Erro: " + e.getMessage());
                return;
            }
        }
        
        Venda venda = new Venda(2, LocalDateTime.now(), gerente, cliente);
        
        boolean continuarVenda = true;
        while (continuarVenda) {
            listarMedicamentos();
            
            System.out.print("Digite o nome do medicamento (ou 'fim' para encerrar): ");
            String nomeMedicamento = scanner.nextLine();
            
            if (nomeMedicamento.equalsIgnoreCase("fim")) {
                continuarVenda = false;
                continue;
            }
            
            Medicamento medicamento = repositorioMedicamentos.buscarPorNome(nomeMedicamento);
            if (medicamento == null) {
                System.out.println("Medicamento não encontrado!");
                continue;
            }
            
            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());
            
            try {
                venda.adicionarItem(medicamento, quantidade, repositorioMedicamentos);
                System.out.println("Item adicionado à venda!");
            } catch (EstoqueInsuficienteException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        
        System.out.println("\n=== RESUMO DA VENDA ===");
        venda.imprimirDetalhes();
        
        if (cliente != null) {
            System.out.println("Desconto para cliente cadastrado: 5%");
            cliente.aplicarDesconto(venda);
            System.out.println("Valor após desconto do cliente: R$" + venda.getValorTotal());
        }
        
        System.out.print("Aplicar desconto adicional de gerente? (S/N): ");
        String aplicarDesconto = scanner.nextLine();
        
        if (aplicarDesconto.equalsIgnoreCase("S")) {
            System.out.print("Percentual de desconto (0-20%): ");
            double percentualDesconto = Double.parseDouble(scanner.nextLine());
            
            if (percentualDesconto >= 0 && percentualDesconto <= 20) {
                gerente.aplicarDesconto(venda, percentualDesconto);
                System.out.println("Desconto de gerente aplicado: " + percentualDesconto + "%");
                System.out.println("Valor final após todos os descontos: R$" + venda.getValorTotal());
            } else {
                System.out.println("Percentual de desconto inválido!");
            }
        }
        
        System.out.print("\nConfirmar venda (S/N)? ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            venda.finalizar(repositorioMedicamentos);
            System.out.println("Venda realizada com sucesso!");
        } else {
            System.out.println("Venda cancelada!");
        }
    }
}
