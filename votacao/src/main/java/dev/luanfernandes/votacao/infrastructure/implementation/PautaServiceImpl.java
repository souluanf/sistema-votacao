package dev.luanfernandes.votacao.infrastructure.implementation;


import dev.luanfernandes.votacao.api.exceptions.ConflictException;
import dev.luanfernandes.votacao.api.exceptions.NotDeleteException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.Pauta;
import dev.luanfernandes.votacao.domain.service.PautaService;
import dev.luanfernandes.votacao.infrastructure.repository.PautaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class PautaServiceImpl implements PautaService {
    private static final String PAUTA_INEXISTENTE = "Pauta inexistente";
    private final PautaRepository repository;
    @Override
    public Pauta criar(Pauta pauta) {
        var res = repository.findByNomeIgnoreCase(pauta.getNome());
        if (res.isPresent()){
            throw new ConflictException("Pauta já cadastrada");
        }
        return this.repository.save(pauta);
    }

    @Override
    public void atualizar(Pauta pauta) {
        if (!this.repository.existsById(pauta.getId())) {
            throw new NotFoundException(PAUTA_INEXISTENTE);
        }
        this.repository.save(pauta);
    }

    @Override
    public void deletar(Long id) throws NotFoundException, NotDeleteException {
        Pauta pauta = this.repository.findById(id).orElseThrow(() -> new NotFoundException(PAUTA_INEXISTENTE));
        if (!pauta.getSessoes().isEmpty()) {
            throw new NotDeleteException("Não é possível deletar a pauta pois existe sessões para a mesma");
        }
        this.repository.deleteById(id);
    }

    @Override
    public List<Pauta> obterTodos() {
        return this.repository.findAll();
    }

    @Override
    public Pauta obterPorId(Long id) throws NotFoundException {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(PAUTA_INEXISTENTE));
    }

    @Override
    public boolean verificaExistePautaPorId(Long id) {
        return this.repository.existsById(id);
    }
}