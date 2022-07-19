package dev.luanfernandes.votacao.infrastructure.implementation;


import dev.luanfernandes.votacao.api.exceptions.DateTimeException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.Pauta;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import dev.luanfernandes.votacao.infrastructure.repository.SessaoRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;
    @Mock
    private PautaServiceImpl pautaServiceImpl;
    @InjectMocks
    @Spy
    private SessaoServiceImpl sessaoServiceImpl;

    private static Sessao criaSessao(Long id, String descricao, OffsetDateTime dataHoraInicio,
                                     OffsetDateTime dataHoraFinal) {
        Sessao sessao = new Sessao();
        sessao.setId(id);
        sessao.setDescricao(descricao);
        sessao.setDataHoraInicio(dataHoraInicio);
        sessao.setDataHoraFechamento(dataHoraFinal);
        Pauta pauta = new Pauta();
        pauta.setId(id);
        pauta.setNome("Pauta");
        sessao.setPauta(pauta);
        return sessao;
    }

    private Sessao criaSessao() {
        OffsetDateTime now = OffsetDateTime.now();
        return criaSessao(1L, "Sessão 1", now, now.plusHours(1));
    }

    private Sessao criaSessaoDois() {
        OffsetDateTime now = OffsetDateTime.now();
        return criaSessao(2L, "Sessão 2", now, now.plusHours(1));
    }

    @Test
    @DisplayName("Verifica abrir sessão")
    void abrirSessao() throws DateTimeException, NotFoundException {
        Sessao sessao = criaSessao();
        when(this.pautaServiceImpl.verificaExistePautaPorId(Mockito.anyLong())).thenReturn(true);
        doReturn(true).when(this.sessaoServiceImpl).verificaSessaoComDataHoraMaiorIgualAtual(Mockito.any(Sessao.class));
        doReturn(true).when(this.sessaoServiceImpl).verificaSessaoComDiferencaMinimaUmMinuto(Mockito.any(Sessao.class));
        when(this.sessaoRepository.save(Mockito.any(Sessao.class))).thenReturn(sessao);
        Sessao sessaoNova = SerializationUtils.clone(sessao);
        sessaoNova.setId(null);
        sessaoNova = this.sessaoServiceImpl.abrir(sessaoNova);
        assertNotNull(sessaoNova);
        assertTrue(new ReflectionEquals(sessaoNova).matches(sessao));
    }

    @Test
    @DisplayName("Verifica abrir sessão com data/hora fechamento default")
    void abrirSessaoComDataHoraFechamentoDefault() throws DateTimeException, NotFoundException {
        Sessao sessao = criaSessao();
        sessao.setDataHoraFechamento(sessao.getDataHoraInicio().plusMinutes(1));
        when(this.pautaServiceImpl.verificaExistePautaPorId(Mockito.anyLong())).thenReturn(true);
        doReturn(true).when(this.sessaoServiceImpl).verificaSessaoComDataHoraMaiorIgualAtual(Mockito.any(Sessao.class));
        doReturn(true).when(this.sessaoServiceImpl).verificaSessaoComDiferencaMinimaUmMinuto(Mockito.any(Sessao.class));
        when(this.sessaoRepository.save(Mockito.any(Sessao.class))).thenReturn(sessao);
        Sessao sessaoNova = SerializationUtils.clone(sessao);
        sessaoNova.setId(null);
        sessaoNova.setDataHoraFechamento(null);
        sessaoNova = this.sessaoServiceImpl.abrir(sessaoNova);
        assertNotNull(sessaoNova);
        assertTrue(new ReflectionEquals(sessaoNova, "dataHoraFechamento").matches(sessao));
        assertEquals(sessaoNova.getDataHoraFechamento(), sessao.getDataHoraFechamento());
    }

    @Test
    @DisplayName("Verifica abrir sessão com pauta inexistente")
    void abrirSessaoComPautaInexistente() throws DateTimeException {
        Sessao sessao = criaSessao();
        when(this.pautaServiceImpl.verificaExistePautaPorId(Mockito.anyLong())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> this.sessaoServiceImpl.abrir(sessao), "Pauta inexistente");
    }

    @Test
    @DisplayName("Verifica abrir sessão com data/hora inicio anterior a atual")
    void abrirSessaoComDataHoraInicioAnteriorAtual() throws DateTimeException {
        Sessao sessao = criaSessao();
        when(this.pautaServiceImpl.verificaExistePautaPorId(Mockito.anyLong())).thenReturn(true);
        doReturn(false).when(this.sessaoServiceImpl).verificaSessaoComDataHoraMaiorIgualAtual(Mockito.any(Sessao.class));
        sessao.setDataHoraInicio(sessao.getDataHoraInicio().minusHours(1));
        assertThrows(DateTimeException.class, () -> this.sessaoServiceImpl.abrir(sessao),
                "Data/Hora início deve ser maior ou igual a atual");
    }

    @Test
    @DisplayName("Verifica abrir sessão com data/hora inicio e fechamento com diferença de no mínimo 1 minuto")
    void abrirSessaoComDataHoraInicioFechamentoDiferencaMinimaUmMinuto() throws DateTimeException {
        Sessao sessao = criaSessao();
        when(this.pautaServiceImpl.verificaExistePautaPorId(Mockito.anyLong())).thenReturn(true);
        doReturn(true).when(this.sessaoServiceImpl).verificaSessaoComDataHoraMaiorIgualAtual(Mockito.any(Sessao.class));
        doReturn(false).when(this.sessaoServiceImpl).verificaSessaoComDiferencaMinimaUmMinuto(Mockito.any(Sessao.class));
        assertThrows(DateTimeException.class, () -> this.sessaoServiceImpl.abrir(sessao),
                "Data/Hora inicio e fechamento deve ter uma diferença de no mínimo 1 minuto");
    }

    @Test
    @DisplayName("Verifica obter todas sessões")
    void obterTodos() throws NotFoundException {
        List<Sessao> sessoes = asList(criaSessao(), criaSessaoDois());
        doReturn(sessoes).when(this.sessaoRepository).findAll();
        List<Sessao> sessaosRetornadas = this.sessaoServiceImpl.obterTodos();
        assertEquals(sessoes.size(), sessaosRetornadas.size());
    }


    @Test
    @DisplayName("Verifica obter sessao por id inexistente")
    void obterSessaoPorIdInexistente() throws NotFoundException {
        when(this.sessaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> this.sessaoServiceImpl.obterPorId(1L),
                "Sessao inexistente");
    }
}