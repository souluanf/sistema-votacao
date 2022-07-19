package dev.luanfernandes.votacao.infrastructure.implementation;


import dev.luanfernandes.votacao.api.exceptions.DateTimeException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.ResultadoSessao;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import dev.luanfernandes.votacao.domain.records.SessaoVotacao;
import dev.luanfernandes.votacao.domain.service.PublisherService;
import dev.luanfernandes.votacao.domain.service.SessaoService;
import dev.luanfernandes.votacao.infrastructure.repository.SessaoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
public class SessaoServiceImpl implements SessaoService {

	private SessaoRepository sessaoRepository;
	private PautaServiceImpl pautaServiceImpl;
	private PublisherService publisherService;

	@Override
	public Sessao abrir(Sessao sessao) throws DateTimeException, NotFoundException {
		if (!this.pautaServiceImpl.verificaExistePautaPorId(sessao.getPauta().getId())) {
			throw new NotFoundException("Pauta inexistente");
		}
		if (!this.verificaSessaoComDataHoraMaiorIgualAtual(sessao)) {
			throw new DateTimeException("Data/Hora início deve ser maior ou igual a atual");
		}
		sessao.setDataHoraFechamento(Optional.ofNullable(sessao.getDataHoraFechamento())
				.orElse(sessao.getDataHoraInicio().plusMinutes(1)));
		if (!this.verificaSessaoComDiferencaMinimaUmMinuto(sessao)) {
			throw new DateTimeException("Data/Hora inicio e fechamento deve ter uma diferença de no mínimo 1 minuto");
		}
		return this.sessaoRepository.save(sessao);
	}

	@Override
	public boolean verificaSessaoComDataHoraMaiorIgualAtual(Sessao sessao) {
		return sessao.getDataHoraInicio().isBefore(OffsetDateTime.now());
	}

	@Override
	public boolean verificaSessaoComDiferencaMinimaUmMinuto(Sessao sessao) {
		return sessao.getDataHoraFechamento().isBefore(sessao.getDataHoraInicio())
				|| Duration.between(sessao.getDataHoraInicio(), sessao.getDataHoraFechamento()).toMinutes() >= 1;
	}

	@Override
	public Sessao obterPorId(Long id) throws NotFoundException {
		return this.sessaoRepository.findById(id).orElseThrow(() -> new NotFoundException("Sessão inexistente"));
	}

	@Override
	public List<Sessao> obterTodos() {
		return this.sessaoRepository.findAll();
	}

	@Override
	public ResultadoSessao obterResultadoPorIdSessao(Long idSessao) throws NotFoundException {
		Sessao sessao = this.sessaoRepository.findById(idSessao)
				.orElseThrow(() -> new NotFoundException("Sessão inexistente"));


		List<Object[]> resultado = this.sessaoRepository.obterResultadoPorIdSessao(idSessao);
		Integer totalSim = Optional.ofNullable(resultado.get(0)[0]).map(i -> Integer.valueOf(i.toString())).orElse(0);
		Integer totalNao = Optional.ofNullable(resultado.get(0)[1]).map(i -> Integer.valueOf(i.toString())).orElse(0);
		var resultadoSessao = new ResultadoSessao(sessao.getId(), sessao.isAberta(), totalSim, totalNao);
		notificar(resultadoSessao,sessao);
		return resultadoSessao;
	}

	public void notificar(ResultadoSessao resultadoSessao, Sessao sessao){
		var sessaoVotacao = SessaoVotacao.newBuilder()
				.setPauta(sessao.getPauta().getNome())
				.setSessao(resultadoSessao.getIdSessao())
				.setHoraInicio(String.valueOf(sessao.getDataHoraInicio().toInstant().getEpochSecond()))
				.setHoraFim(String.valueOf(sessao.getDataHoraFechamento().toInstant().getEpochSecond()))
				.setVotoSim(resultadoSessao.getTotalSim())
				.setVotoNao(resultadoSessao.getTotalNao())
				.build();
		log.info("AQUI");
		publisherService.sessaoFinalizada(sessaoVotacao);
	}
}