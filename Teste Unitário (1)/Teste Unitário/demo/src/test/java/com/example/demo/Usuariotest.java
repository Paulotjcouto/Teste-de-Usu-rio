    package com.example.demo;

    import java.util.Arrays;
    import java.util.List;
    import java.util.Optional;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import static org.mockito.Mockito.doNothing;
    import static org.mockito.Mockito.times;
    import static org.mockito.Mockito.verify;
    import static org.mockito.Mockito.when;
    import org.mockito.MockitoAnnotations;
    import org.springframework.http.ResponseEntity;

    import com.example.demo.Model.Usuario;
    import com.example.demo.Service.UsuarioService;
    import com.example.demo.controller.Usuariocontroller;

    public class Usuariotest {

        @InjectMocks
        private Usuariocontroller controller;

        @Mock
        private UsuarioService service;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testListarTodos() {
            List<Usuario> lista = Arrays.asList(
                    new Usuario(1L, "João", "joao@example.com"),
                    new Usuario(2L, "Maria", "maria@example.com")
            );

            when(service.listarTodos()).thenReturn(lista);

            ResponseEntity<List<Usuario>> response = controller.listarTodos();

            assertEquals(2, response.getBody().size());
            verify(service, times(1)).listarTodos();
        }

        @Test
        void testBuscarPorIdExistente() {
            Long id = 1L;
            Usuario usuario = new Usuario(id, "Carlos", "carlos@example.com");

            when(service.buscarPorId(id)).thenReturn(Optional.of(usuario));

            ResponseEntity<Usuario> response = controller.buscarPorId(id);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(usuario, response.getBody());
            verify(service, times(1)).buscarPorId(id);
        }

        @Test
        void testBuscarPorIdNaoExistente() {
            Long id = 99L;

            when(service.buscarPorId(id)).thenReturn(Optional.empty());

            ResponseEntity<Usuario> response = controller.buscarPorId(id);

            assertEquals(404, response.getStatusCodeValue());
            verify(service, times(1)).buscarPorId(id);
        }

        @Test
        void testSalvar() {
            Usuario usuario = new Usuario(null, "Ana", "ana@example.com");
            Usuario salvo = new Usuario(1L, "Ana", "ana@example.com");

            when(service.salvar(usuario)).thenReturn(salvo);

            ResponseEntity<Usuario> response = controller.salvar(usuario);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals(salvo, response.getBody());
            verify(service, times(1)).salvar(usuario);
        }

        @Test
        void testDeletar() {
            Long id = 1L;

            doNothing().when(service).deletar(id);

            ResponseEntity<Void> response = controller.deletar(id);

            assertEquals(204, response.getStatusCodeValue());
            verify(service, times(1)).deletar(id);
        }
    }
