package dev.luanfernandes.votacao.infrastructure.implementation;

import dev.luanfernandes.votacao.api.exceptions.ConflictException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.api.exceptions.SessaoException;
import dev.luanfernandes.votacao.api.exceptions.ValidationException;
import dev.luanfernandes.votacao.domain.entity.Associado;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import dev.luanfernandes.votacao.domain.entity.Voto;
import dev.luanfernandes.votacao.domain.service.UserInfoService;
import dev.luanfernandes.votacao.domain.service.VotoService;
import dev.luanfernandes.votacao.domain.utils.DocumentValidator;
import dev.luanfernandes.votacao.infrastructure.repository.VotoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.luanfernandes.votacao.domain.utils.StringUtil.removeMask;


@Slf4j
@Service
@AllArgsConstructor
public class VotoServiceImpl implements VotoService {

	private VotoRepository votoRepository;
	private SessaoServiceImpl sessaoServiceImpl;
	private AssociadoServiceImpl associadoServiceImpl;
	private UserInfoService userInfoService;

	@Override
	public Voto votar(Long idSessao, String cpf, Boolean valor){
		cpf = removeMask(cpf);
		if (!DocumentValidator.isValidCpf(cpf)){
			throw new ValidationException("Cpf inválido");
		}
		Sessao sessao = this.sessaoServiceImpl.obterPorId(idSessao);
		if (!sessao.isAberta()) {
			throw new SessaoException("Sessão não está aberta");
		}
		Associado associado = this.associadoServiceImpl.obterPorCPF(cpf).orElseThrow(() -> new NotFoundException("Associado inexistente"));
		if (this.verificaExistePorIdSessaoIdAssociado(sessao.getId(), associado.getId())) {
			throw new ConflictException("O associado já votou");
		}
		userInfoService.enableToVote(cpf);
		return this.votoRepository.save(new Voto(null,sessao,associado,valor));
	}

	@Override
	public boolean verificaExistePorIdSessaoIdAssociado(Long idSessao, Long idAssociado) {
		return this.votoRepository.existsVotoByIdSessaoAndIdAssociado(idSessao, idAssociado);
	}
}