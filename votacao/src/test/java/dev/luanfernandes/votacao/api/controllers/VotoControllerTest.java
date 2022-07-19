package dev.luanfernandes.votacao.api.controllers;


import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.dto.VotoDTO;
import dev.luanfernandes.votacao.domain.entity.Associado;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import dev.luanfernandes.votacao.domain.entity.Voto;
import dev.luanfernandes.votacao.infrastructure.implementation.VotoServiceImpl;
import dev.luanfernandes.votacao.domain.utils.JsonUtil;
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

import java.time.OffsetDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VotoServiceImpl votoServiceImpl;

    private VotoDTO criaVotoDTO(Voto voto) {
        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setCpf(voto.getAssociado().getCpf());
        votoDTO.setIdSessao(voto.getSessao().getId());
        votoDTO.setValor(voto.getValor());
        return votoDTO;
    }

    private Voto criaVoto(Associado associado, Sessao sessao) {
        Voto voto = new Voto();
        voto.setId(1L);
        voto.setAssociado(associado);
        voto.setSessao(sessao);
        voto.setValor(false);
        return voto;
    }

    private Voto criaVoto() {
        return criaVoto(criaAssociado(), criaSessao());
    }

    private Associado criaAssociado() {
        Associado associado = new Associado();
        associado.setId(1L);
        associado.setCpf("87511191029");
        associado.setNome("Associado 1");
        return associado;
    }

    private static Sessao criaSessao() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setDescricao("Sessão 1");
        OffsetDateTime now = OffsetDateTime.now();
        sessao.setDataHoraInicio(now);
        sessao.setDataHoraFechamento(now.plusHours(1));
        return sessao;
    }

    @Test
    @DisplayName("Verifica votar com sucesso")
    void verificaVotarComSucesso() throws Exception {
        Voto votoSalvar = criaVoto();
        when(this.votoServiceImpl.votar(Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(votoSalvar);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/votos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(criaVotoDTO(votoSalvar))))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verifica votar sem id da sessão")
    void verificaVotarSemIdSessao() throws Exception {
        Voto votoSalvar = criaVoto();
        votoSalvar.getSessao().setId(null);
        when(this.votoServiceImpl.votar(Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(votoSalvar);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/votos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(criaVotoDTO(votoSalvar))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verifica votar sem CPF")
    void verificaVotarSemCPF() throws Exception {
        Voto votoSalvar = criaVoto();
        votoSalvar.getAssociado().setCpf(null);
        when(this.votoServiceImpl.votar(Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(votoSalvar);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/votos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(criaVotoDTO(votoSalvar))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verifica votar sem valor")
    void verificaVotarSemValor() throws Exception {
        Voto votoSalvar = criaVoto();
        votoSalvar.setValor(null);
        when(this.votoServiceImpl.votar(Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(votoSalvar);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/votos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(criaVotoDTO(votoSalvar))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verifica NotFoundException ao votar")
    void verificaNotFoundExceptionVotar() throws Exception {
        Voto votoSalvar = criaVoto();
        when(this.votoServiceImpl.votar(Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
                .thenThrow(new NotFoundException(""));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/votos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(criaVotoDTO(votoSalvar))))
                .andExpect(status().isNotFound());
    }


}