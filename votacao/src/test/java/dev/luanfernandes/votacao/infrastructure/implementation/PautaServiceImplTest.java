package dev.luanfernandes.votacao.infrastructure.implementation;


import dev.luanfernandes.votacao.api.exceptions.NotDeleteException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.Pauta;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import dev.luanfernandes.votacao.infrastructure.repository.PautaRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PautaServiceImplTest {

	@Mock
	private PautaRepository pautaRepository;
	@InjectMocks
	private PautaServiceImpl pautaServiceImpl;

	private static Pauta criaPauta(Long id, String nome) {
		Pauta pauta = new Pauta();
		pauta.setId(id);
		pauta.setNome(nome);
		pauta.setSessoes(new ArrayList<>());
		return pauta;
	}

	private static Pauta criaPauta() {
		return criaPauta(1L, "Pauta 1");
	}

	private static Pauta criaPautaDois() {
		return criaPauta(2L, "Pauta 2");
	}

	private static Pauta criaPautaComSessao() {
		Pauta pauta = criaPauta();
		pauta.setSessoes(new ArrayList<>());
		Sessao sessao = new Sessao();
		sessao.setId(1L);
		sessao.setDescricao("Sessão 1");
		sessao.setPauta(pauta);
		OffsetDateTime now = OffsetDateTime.now();
		sessao.setDataHoraInicio(now);
		sessao.setDataHoraFechamento(now.plusHours(1));
		pauta.getSessoes().add(sessao);
		return pauta;
	}

	@Test
	@DisplayName("Verifica criar pauta")
	void verificaPautaCriadaComSucesso() {
		Pauta pauta = criaPauta();
		when(this.pautaRepository.save(Mockito.any(Pauta.class))).thenReturn(pauta);
		Pauta pautaNova = SerializationUtils.clone(pauta);
		pautaNova.setId(null);
		pautaNova = this.pautaServiceImpl.criar(pautaNova);
		assertNotNull(pautaNova);
		assertTrue(new ReflectionEquals(pautaNova).matches(pauta));
	}

	@Test
	@DisplayName("Verifica atualizar pauta")
	void verificaAtualizarPauta() {
		Pauta pauta = criaPauta();
		when(this.pautaRepository.save(Mockito.any(Pauta.class))).thenReturn(pauta);
		Pauta pautaNovo = this.pautaServiceImpl.criar(SerializationUtils.clone(pauta));
		assertNotNull(pautaNovo);
		assertTrue(new ReflectionEquals(pautaNovo).matches(pauta));
	}

	@Test
	@DisplayName("Verifica atualizar pauta inexistente")
	void verificaAtualizarPautaInexistente() {
		when(this.pautaRepository.existsById(Mockito.anyLong())).thenReturn(false);
		assertThrows(NotFoundException.class, () -> this.pautaServiceImpl.atualizar(criaPauta()), "Pauta inexistente");
	}

	@Test
	@DisplayName("Verifica deletar pauta")
	void deletarPauta() throws NotFoundException, NotDeleteException {
		Long id = 1L;
		Pauta pauta = criaPauta();
		when(this.pautaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(pauta));
		this.pautaServiceImpl.deletar(id);
		verify(this.pautaRepository).findById(id);
		verify(this.pautaRepository).deleteById(id);
	}

	@Test
	@DisplayName("Verifica deletar pauta inexistente")
	void deletarPautaInexistente() {
		when(this.pautaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> this.pautaServiceImpl.deletar(1L), "Pauta inexistente");
	}

	@Test
	@DisplayName("Verifica deletar pauta com sessão")
	void deletarPautaComSessao() throws NotFoundException, NotDeleteException {
		Pauta pautaComSessao = criaPautaComSessao();
		when(this.pautaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(pautaComSessao));
		assertThrows(NotDeleteException.class, () -> this.pautaServiceImpl.deletar(pautaComSessao.getId()),
				"Não é possível deletar a pauta pois existe sessões para a mesma");
	}

	@Test
	@DisplayName("Verifica obter todas pautas")
	void obterTodos() throws NotFoundException {
		List<Pauta> pautas = asList(criaPauta(), criaPautaDois());
		when(this.pautaRepository.findAll()).thenReturn(pautas);
		List<Pauta> pautasRetornadas = this.pautaServiceImpl.obterTodos();
		assertEquals(pautas.size(), pautasRetornadas.size());
	}

	@Test
	@DisplayName("Verifica obter pauta por id")
	void obterPautaPorId() throws NotFoundException {
		Pauta pauta = criaPauta();
		when(this.pautaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(SerializationUtils.clone(pauta)));
		Pauta pautaRetornada = this.pautaServiceImpl.obterPorId(pauta.getId());
		assertNotNull(pautaRetornada);
		assertTrue(new ReflectionEquals(pautaRetornada).matches(pauta));
	}

	@Test
	@DisplayName("Verifica obter pauta por id inexistente")
	void obterPautaPorIdInexistente() {
		when(this.pautaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> this.pautaServiceImpl.obterPorId(1L), "Pauta inexistente");
	}
}