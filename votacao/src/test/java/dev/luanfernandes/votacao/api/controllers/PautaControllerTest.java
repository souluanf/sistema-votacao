package dev.luanfernandes.votacao.api.controllers;

import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.dto.PautaDTO;
import dev.luanfernandes.votacao.domain.entity.Pauta;
import dev.luanfernandes.votacao.infrastructure.implementation.PautaServiceImpl;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
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

    @Test
    @DisplayName("Verifica criar pauta sem nome")
    void verificaCriarPautaSemNome() throws Exception {
        Pauta pautaSalvar = criaPauta();
        pautaSalvar.setNome(null);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/pautas")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.toJson(PautaDTO.from(pautaSalvar))))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Verifica obter pauta")
    void verificaObterPauta() throws Exception {
        Pauta pautaEsperada = criaPauta();
        when(this.pautaServiceImpl.obterPorId(Mockito.anyLong())).thenReturn(pautaEsperada);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/pautas/" + 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(PautaDTO.from(pautaEsperada))));
    }

    @Test
    @DisplayName("Verifica NotFoundException ao obter pauta")
    void verificaNotFoundExceptionObterPauta() throws Exception {
        when(this.pautaServiceImpl.obterPorId(Mockito.anyLong())).thenThrow(new NotFoundException(""));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/pautas/" + 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Verifica obter todas pautas")
    void verificaObterTodosPautas() throws Exception {
        Pauta pauta1 = criaPauta();
        Pauta pauta2 = criaPautaDois();
        List<Pauta> listaEsperada = asList(pauta1, pauta2);
        when(this.pautaServiceImpl.obterTodos())
                .thenReturn(listaEsperada);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/pautas")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        JsonUtil.toJson(listaEsperada.stream().map(PautaDTO::from).collect(Collectors.toList()))));
    }
}