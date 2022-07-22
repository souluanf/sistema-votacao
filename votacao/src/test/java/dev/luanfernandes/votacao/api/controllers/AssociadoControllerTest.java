package dev.luanfernandes.votacao.api.controllers;

import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.dto.AssociadoDTO;
import dev.luanfernandes.votacao.domain.entity.Associado;
import dev.luanfernandes.votacao.domain.utils.JsonUtil;
import dev.luanfernandes.votacao.infrastructure.implementation.AssociadoServiceImpl;
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

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AssociadoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AssociadoServiceImpl associadoServiceImpl;

    private static Associado criaAssociado() {
        return criaAssociado(1L, "87511191029", "Associado 1");
    }

    private static Associado criaAssociadoDois() {
        return criaAssociado(2L, "78673472083", "Associado 2");
    }

    private static Associado criaAssociado(Long id, String CPF, String nome) {
        Associado associado = new Associado();
        associado.setId(id);
        associado.setCpf(CPF);
        associado.setNome(nome);
        return associado;
    }

    @Test
    @DisplayName("Verifica obter associado")
    void verificaObterAssociado() throws Exception {
        Associado associadoEsperado = criaAssociado();
        when(this.associadoServiceImpl.obterPorId(Mockito.anyLong())).thenReturn(associadoEsperado);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/associados/" + 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(AssociadoDTO.from(associadoEsperado))));
    }

    @Test
    @DisplayName("Verifica NotFoundException obter associado")
    void verificaNotFoundExceptionObterAssociado() throws Exception {
        when(this.associadoServiceImpl.obterPorId(Mockito.anyLong())).thenThrow(new NotFoundException(""));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/associados/" + 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Verifica obter todos associados")
    void verificaObterTodosAssociados() throws Exception {
        Associado associado1 = criaAssociado();
        Associado associado2 = criaAssociadoDois();
        List<Associado> listaEsperada = asList(associado1, associado2);
        when(this.associadoServiceImpl.obterTodos())
                .thenReturn(listaEsperada);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/associados")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        JsonUtil.toJson(listaEsperada.stream().map(AssociadoDTO::from).collect(Collectors.toList()))));
    }
}
