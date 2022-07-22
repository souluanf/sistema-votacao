package dev.luanfernandes.votacao.infrastructure.implementation;

import dev.luanfernandes.votacao.api.exceptions.ConflictException;
import dev.luanfernandes.votacao.api.exceptions.DateTimeException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.api.exceptions.SessaoException;
import dev.luanfernandes.votacao.domain.entity.Associado;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import dev.luanfernandes.votacao.domain.entity.Voto;
import dev.luanfernandes.votacao.domain.service.UserService;
import dev.luanfernandes.votacao.infrastructure.repository.VotoRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class VotoServiceImplTest {

    @Mock
    private VotoRepository votoRepository;
    @Mock
    private SessaoServiceImpl sessaoServiceImpl;
    @Mock
    private AssociadoServiceImpl associadoServiceImpl;
    @Mock
    private UserService userService;
    @Mock
    private Sessao sessao;
    @InjectMocks
    @Spy
    private VotoServiceImpl votoServiceImpl;

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
    @DisplayName("Verifica votar")
    void votar() throws NotFoundException, DateTimeException, ConflictException {
        Voto voto = criaVoto();
        doReturn(true).when(this.sessao).isAberta();
        doReturn(voto.getSessao()).when(this.sessaoServiceImpl).obterPorId(Mockito.anyLong());
        doReturn(Optional.of(voto.getAssociado())).when(this.associadoServiceImpl).obterPorCPF(Mockito.anyString());
        doReturn(true).when(this.userService).consultaCpf(Mockito.anyString());
        doReturn(false).when(this.votoRepository).existsVotoByIdSessaoAndIdAssociado(Mockito.anyLong(),
                Mockito.anyLong());
        doReturn(SerializationUtils.clone(voto)).when(this.votoRepository).save(Mockito.any(Voto.class));
        Voto votoNovo = this.votoServiceImpl.votar(voto.getSessao().getId(), voto.getAssociado().getCpf(), voto.getValor());
        assertNotNull(votoNovo);
    }

    @Test
    @DisplayName("Verifica votar com sessao fechada")
    void votarSessaoFechada() throws NotFoundException {
        Voto voto = criaVoto();
        OffsetDateTime currentDateTime = OffsetDateTime.now();
        voto.getSessao().setDataHoraInicio(currentDateTime.minusHours(2));
        voto.getSessao().setDataHoraFechamento(currentDateTime.minusHours(1));
        doReturn(false).when(this.sessao).isAberta();
        doReturn(voto.getSessao()).when(this.sessaoServiceImpl).obterPorId(Mockito.anyLong());
        doReturn(Optional.of(voto.getAssociado())).when(this.associadoServiceImpl).obterPorCPF(Mockito.anyString());
        doReturn(true).when(this.userService).consultaCpf(Mockito.anyString());
        assertThrows(SessaoException.class,
                () -> this.votoServiceImpl.votar(voto.getSessao().getId(), voto.getAssociado().getCpf(), voto.getValor()),
                "Sessão não está aberta");
    }

    @Test
    @DisplayName("Verifica votar com associado inexistente")
    void votarAssociadoInexistente() throws NotFoundException {
        Voto voto = criaVoto();
        doReturn(true).when(this.sessao).isAberta();
        doReturn(voto.getSessao()).when(this.sessaoServiceImpl).obterPorId(Mockito.anyLong());
        doReturn(Optional.empty()).when(this.associadoServiceImpl).obterPorCPF(Mockito.anyString());
        assertThrows(NotFoundException.class,
                () -> this.votoServiceImpl.votar(voto.getSessao().getId(), voto.getAssociado().getCpf(), voto.getValor()),
                "Associado inexistente");
    }

    @Test
    @DisplayName("Verifica votar com associado que ja votou")
    void votarAssociadoJaVotou() throws NotFoundException {
        Voto voto = criaVoto();
        doReturn(true).when(this.sessao).isAberta();
        doReturn(voto.getSessao()).when(this.sessaoServiceImpl).obterPorId(Mockito.anyLong());
        doReturn(Optional.of(voto.getAssociado())).when(this.associadoServiceImpl).obterPorCPF(Mockito.anyString());
        doReturn(true).when(this.userService).consultaCpf(Mockito.anyString());
        doReturn(true).when(this.votoRepository).existsVotoByIdSessaoAndIdAssociado(Mockito.anyLong(),
                Mockito.anyLong());
        Assertions.assertThrows(ConflictException.class,
                () -> this.votoServiceImpl.votar(voto.getSessao().getId(), voto.getAssociado().getCpf(), voto.getValor()),
                "O associado já votou");
    }

    @Test
    @DisplayName("Verifica voto existente por id sessão e id associado")
    void verificaExistePorIdSessaoIdAssociado() {
        doReturn(true).when(this.votoRepository).existsVotoByIdSessaoAndIdAssociado(Mockito.anyLong(),
                Mockito.anyLong());
        assertTrue(this.votoServiceImpl.verificaExistePorIdSessaoIdAssociado(1L, 1L));
    }

    @Test
    @DisplayName("Verifica voto nao existente por id sessão e id associado")
    void verificaNaoExistePorIdSessaoIdAssociado() {
        doReturn(false).when(this.votoRepository).existsVotoByIdSessaoAndIdAssociado(Mockito.anyLong(),
                Mockito.anyLong());
        assertFalse(this.votoServiceImpl.verificaExistePorIdSessaoIdAssociado(1L, 1L));
    }
}