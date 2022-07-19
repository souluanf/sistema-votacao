package dev.luanfernandes.votacao.infrastructure.implementation;


import dev.luanfernandes.votacao.api.exceptions.ConflictException;
import dev.luanfernandes.votacao.api.exceptions.NotFoundException;
import dev.luanfernandes.votacao.domain.entity.Associado;
import dev.luanfernandes.votacao.infrastructure.repository.AssociadoRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AssociadoServiceImplTest {

    @Mock
    private AssociadoRepository associadoRepository;
    @InjectMocks
    @Spy
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
    @DisplayName("Verifica atualizar associado com CPF já cadastrado para outro usuario")
    void verificaAtualizarAssociadoCPFCadastradoOutroUsuario() throws ConflictException {
        Associado associado = criaAssociado();
        doReturn(true).when(this.associadoServiceImpl).verificaExistePorId(Mockito.anyLong());
        doReturn(Optional.of(associado)).when(this.associadoRepository).findByCpf(Mockito.anyString());
        Associado associadoNovo = criaAssociadoDois();
        associadoNovo.setCpf(associado.getCpf());
    }

    @Test
    @DisplayName("Verifica atualizar CPF do associado")
    void verificaAtualizarCPFDoAssociado() throws ConflictException, NotFoundException {
        Associado associado = criaAssociado();
        String cpf = "15016460019";
        Associado associadoAtualizado = SerializationUtils.clone(associado);
        associadoAtualizado.setCpf(cpf);
        doReturn(true).when(this.associadoServiceImpl).verificaExistePorId(Mockito.anyLong());
        doReturn(Optional.of(associado)).when(this.associadoRepository).findByCpf(Mockito.anyString());
        doReturn(associadoAtualizado).when(this.associadoRepository).save(Mockito.any(Associado.class));
    }

    @Test
    @DisplayName("Verifica deletar associado")
    void deletarAssociado() throws NotFoundException {
        Long id = 1L;
        doReturn(true).when(this.associadoServiceImpl).verificaExistePorId(Mockito.anyLong());
        this.associadoServiceImpl.deletar(id);
        verify(this.associadoRepository).deleteById(id);
    }

    @Test
    @DisplayName("Verifica deletar associado inexistente")
    void deletarAssociadoInexistente() throws NotFoundException {
        doReturn(false).when(this.associadoServiceImpl).verificaExistePorId(Mockito.anyLong());
        assertThrows(NotFoundException.class, () -> this.associadoServiceImpl.deletar(1L),
                "Associado inexistente");
    }

    @Test
    @DisplayName("Verifica obter associado por id")
    void obterAssociadoPorId() throws NotFoundException {
        Associado associado = criaAssociado();
        doReturn(Optional.of(SerializationUtils.clone(associado))).when(this.associadoRepository)
                .findById(Mockito.anyLong());
        Associado associadoRetornado = this.associadoServiceImpl.obterPorId(1L);
        assertNotNull(associadoRetornado);
        assertTrue(new ReflectionEquals(associadoRetornado).matches(associado));
    }

    @Test
    @DisplayName("Verifica obter associado por id inexistente")
    void obterAssociadoPorIdInexistente() throws NotFoundException {
        doReturn(Optional.empty()).when(this.associadoRepository).findById(Mockito.anyLong());
        assertThrows(NotFoundException.class, () -> this.associadoServiceImpl.obterPorId(1L),
                "Associado inexistente");
    }

    @Test
    @DisplayName("Verifica obter todos")
    void obterTodos() {
        List<Associado> associados = asList(criaAssociado(), criaAssociadoDois());
        doReturn(associados).when(this.associadoRepository)
                .findAll();
        List<Associado> associadosRetornados = this.associadoServiceImpl.obterTodos();
        assertEquals(associados.size(), associadosRetornados.size());
    }

    @Test
    @DisplayName("Verifica obter associado por CPF")
    void obterAssociadoPorCPF() {
        Associado associado = criaAssociado();
        doReturn(Optional.of(SerializationUtils.clone(associado))).when(this.associadoRepository)
                .findByCpf(Mockito.anyString());
        Optional<Associado> optionalAssociado = this.associadoServiceImpl.obterPorCPF("03492767141");
        assertNotNull(optionalAssociado);
        assertTrue(optionalAssociado.isPresent());
        Associado associadoRetornado = optionalAssociado.get();
        assertTrue(new ReflectionEquals(associadoRetornado).matches(associado));
    }

    @Test
    @DisplayName("Verifica obter associado por CPF inexistente")
    void obterAssociadoPorCPFInexistente() {
        doReturn(Optional.empty()).when(this.associadoRepository).findByCpf(Mockito.anyString());
        Optional<Associado> optionalAssociado = this.associadoServiceImpl.obterPorCPF("03492767141");
        assertTrue(optionalAssociado.isEmpty());
    }

    @Test
    @DisplayName("Verifica se associado existe por id")
    void verificaExistePorId() {
        doReturn(true).when(this.associadoRepository).existsById(Mockito.anyLong());
        assertTrue(this.associadoServiceImpl.verificaExistePorId(1L));
    }
}