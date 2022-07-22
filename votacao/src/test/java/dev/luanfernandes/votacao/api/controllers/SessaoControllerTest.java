package dev.luanfernandes.votacao.api.controllers;

import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.dto.ResultadoSessaoDTO;
import dev.luanfernandes.votacao.domain.dto.SessaoDTO;
import dev.luanfernandes.votacao.domain.entity.Pauta;
import dev.luanfernandes.votacao.domain.entity.ResultadoSessao;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import dev.luanfernandes.votacao.domain.utils.JsonUtil;
import dev.luanfernandes.votacao.infrastructure.implementation.SessaoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SessaoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SessaoServiceImpl sessaoServiceImpl;

    private static Sessao criaSessao(Long id, String descricao, OffsetDateTime dataHoraInicio, OffsetDateTime dataHoraFinal) {
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
    @DisplayName("Verifica criar sessao sem id da pauta")
    void verificaCriarSessaoSemIdPauta() throws Exception {
        Sessao sessaoSalvar = criaSessao();
        sessaoSalvar.setPauta(null);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(SessaoDTO.from(sessaoSalvar))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verifica criar sessao sem descricao")
    void verificaCriarSessaoSemNome() throws Exception {
        Sessao sessaoSalvar = criaSessao();
        sessaoSalvar.setDescricao(null);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(SessaoDTO.from(sessaoSalvar))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verifica criar sessao sem data/hora inicio")
    void verificaCriarSessaoSemDataHoraInicio() throws Exception {
        Sessao sessaoSalvar = criaSessao();
        sessaoSalvar.setDataHoraInicio(null);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(SessaoDTO.from(sessaoSalvar))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verifica NotFoundException ao criar sessao")
    void verificaNotFoundExceptionCriarSessao() throws Exception {
        Sessao sessaoSalvar = criaSessao();
        when(this.sessaoServiceImpl.abrir(Mockito.any(Sessao.class))).thenThrow(new NotFoundException(""));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(SessaoDTO.from(sessaoSalvar))))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Verifica obter sessao")
    void verificaObterSessao() throws Exception {
        Sessao sessaoEsperada = criaSessao();
        when(this.sessaoServiceImpl.obterPorId(Mockito.anyLong())).thenReturn(sessaoEsperada);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/sessoes/" + 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(SessaoDTO.from(sessaoEsperada))));
    }

    @Test
    @DisplayName("Verifica obter sessao inexistente")
    void verificaObterSessaoInexistente() throws Exception {
        when(this.sessaoServiceImpl.obterPorId(Mockito.anyLong())).thenThrow(new NotFoundException(""));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/sessoes/" + 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Verifica obter todas sessoes")
    void verificaObterTodosSessoes() throws Exception {
        Sessao sessao1 = criaSessao();
        Sessao sessao2 = criaSessaoDois();
        List<Sessao> listaEsperada = asList(sessao1, sessao2);
        when(this.sessaoServiceImpl.obterTodos())
                .thenReturn(listaEsperada);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/sessoes")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        JsonUtil.toJson(listaEsperada.stream().map(SessaoDTO::from).collect(Collectors.toList()))));
    }

    @Test
    @DisplayName("Verifica obter resultado da sessao")
    void verificaObterResultadoSessao() throws Exception {
        ResultadoSessao resultadoSessao = new ResultadoSessao(1L, true, 5, 10);
        when(this.sessaoServiceImpl.obterResultadoPorIdSessao(Mockito.anyLong())).thenReturn(resultadoSessao);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/sessoes/" + 1 + "/resultado")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(JsonUtil.toJson(ResultadoSessaoDTO.from(resultadoSessao))));
    }

    @Test
    @DisplayName("Verifica NotFoundException ao obter resultado da sessao")
    void verificaNotFoundExceptionObterResultadoSessao() throws Exception {
        when(this.sessaoServiceImpl.obterResultadoPorIdSessao(Mockito.anyLong())).thenThrow(new NotFoundException(""));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/sessoes/" + 1 + "/resultado")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}