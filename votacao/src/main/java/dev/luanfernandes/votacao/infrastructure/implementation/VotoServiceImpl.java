package dev.luanfernandes.votacao.infrastructure.implementation;

import dev.luanfernandes.votacao.api.exceptions.ConflictException;
import dev.luanfernandes.votacao.api.exceptions.DateTimeException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.Associado;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import dev.luanfernandes.votacao.domain.entity.Voto;
import dev.luanfernandes.votacao.domain.service.UserService;
import dev.luanfernandes.votacao.domain.service.VotoService;
import dev.luanfernandes.votacao.infrastructure.repository.VotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static dev.luanfernandes.votacao.domain.utils.StringUtil.removeMask;


@Service
@AllArgsConstructor
public class VotoServiceImpl implements VotoService {

	private VotoRepository votoRepository;
	private SessaoServiceImpl sessaoServiceImpl;
	private AssociadoServiceImpl associadoServiceImpl;
	private UserService userService;

	@Override
	public Voto votar(Long idSessao, String cpf, Boolean valor) throws NotFoundException, DateTimeException, ConflictException{
		Sessao sessao = this.sessaoServiceImpl.obterPorId(idSessao);
		if (!sessao.isAberta()) {
			throw new DateTimeException("Sessão não está aberta");
		}
		cpf = removeMask(cpf);
		Associado associado = this.associadoServiceImpl.obterPorCPF(cpf).orElseThrow(() -> new NotFoundException("Associado inexistente"));
		if (this.verificaExistePorIdSessaoIdAssociado(sessao.getId(), associado.getId())) {
			throw new ConflictException("O associado já votou");
		}

		Voto voto = new Voto();
		voto.setSessao(sessao);
		voto.setAssociado(associado);
		voto.setValor(valor);
		return this.votoRepository.save(voto);
	}

	@Override
	public boolean verificaExistePorIdSessaoIdAssociado(Long idSessao, Long idAssociado) {
		return this.votoRepository.existsVotoByIdSessaoAndIdAssociado(idSessao, idAssociado);
	}
}