package dev.luanfernandes.votacao.infrastructure.implementation;

import dev.luanfernandes.votacao.api.exceptions.ConflictException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.api.exceptions.ValidationException;
import dev.luanfernandes.votacao.domain.entity.Associado;
import dev.luanfernandes.votacao.domain.service.AssociadoService;
import dev.luanfernandes.votacao.domain.utils.DocumentValidator;
import dev.luanfernandes.votacao.domain.utils.StringUtil;
import dev.luanfernandes.votacao.infrastructure.repository.AssociadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AssociadoServiceImpl implements AssociadoService {
	private AssociadoRepository repository;

	@Override
	public Associado criar(Associado associado) {
		associado.setCpf(StringUtil.removeMask(associado.getCpf()));
		if (!DocumentValidator.isValidCpf(associado.getCpf())){
			throw new ValidationException("Cpf inválido");
		}
		Optional<Associado> associadoCadastrado = this.repository.findByCpf(associado.getCpf());
		if (associadoCadastrado.isPresent()) {
			throw new ConflictException("CPF já cadastrado");
		}
		return this.repository.save(associado);
	}

	@Override
	public void deletar(Long id)  {
		if (!this.verificaExistePorId(id)) {
			throw new NotFoundException("Associado inexistente");
		}
		this.repository.deleteById(id);
	}

	@Override
	public Associado obterPorId(Long id)   {
		return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Associado inexistente"));

	}

	@Override
	public List<Associado> obterTodos() {
		return this.repository.findAll();
	}

	@Override
	public Optional<Associado> obterPorCPF(String cpf) {
		cpf = StringUtil.removeMask(cpf);
		if (!DocumentValidator.isValidCpf(cpf)){
			throw new ValidationException("Cpf inválido");
		}
		return this.repository.findByCpf(cpf);
	}

	@Override
	public boolean verificaExistePorId(Long id) {
		return this.repository.existsById(id);
	}
}