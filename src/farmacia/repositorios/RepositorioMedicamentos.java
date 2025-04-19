package farmacia.repositorios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import farmacia.modelos.Medicamento;

public class RepositorioMedicamentos {
    private Map<String, Medicamento> medicamentosPorNome;
    
    public RepositorioMedicamentos() {
        this.medicamentosPorNome = new HashMap<>();
    }
    
    public void adicionarMedicamento(Medicamento medicamento) {
        medicamentosPorNome.put(medicamento.getNome(), medicamento);
    }
    
    public Medicamento buscarPorNome(String nome) {
        return medicamentosPorNome.get(nome);
    }
    
    public Medicamento buscarPorId(String id) {
        for (Medicamento medicamento : medicamentosPorNome.values()) {
            if (medicamento.getId().equals(id)) {
                return medicamento;
            }
        }
        return null;
    }
    
    public Collection<Medicamento> listarTodos() {
        return medicamentosPorNome.values();
    }
    
    public List<Medicamento> listarExpirados() {
        List<Medicamento> expirados = new ArrayList<>();
        
        for (Medicamento medicamento : medicamentosPorNome.values()) {
            if (medicamento.estaExpirado()) {
                expirados.add(medicamento);
            }
        }
        
        return expirados;
    }
    
    public void removerMedicamento(String nome) {
        medicamentosPorNome.remove(nome);
    }
    
    public void atualizarEstoque(String nome, int novaQuantidade) {
        Medicamento medicamento = buscarPorNome(nome);
        if (medicamento != null) {
            medicamento.setQuantidade(novaQuantidade);
        }
    }
}
