package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.model.Ciudad;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Usuario;
import co.edu.uniquindio.unilocal.servicios.implementacion.UsuarioServicioImpl;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UsuarioServicioTest {


    @Autowired
    private UsuarioServicio usuarioServicio;

    @Test
    public void registrarTest() throws Exception {

        List<String> negociosFav = new ArrayList<>();

        //Se crea un objeto de tipo RegistroClienteDTO
        RegistroUsuarioDTO registroUsuarioDTO = new RegistroUsuarioDTO(
                "Juan",
                "micorreo@gmail.com",
                "mipassword",
                "juanakdmfwmfofo",
                "juanito123",
                Ciudad.ARMENIA,
                "Quimbaya, Mz 1 #12-12",
                negociosFav
                );

        //Se registra el cliente
        String codigo = usuarioServicio.registrarUsuario(registroUsuarioDTO);

        //Se verifica que el codigo no sea nulo, es decir, que se haya registrado el cliente
        Assertions.assertNotNull(codigo);

    }

    @Test
    public void actualizarTest() throws Exception{

        Usuario usuario = new Usuario();

        ActualizarUsuarioDTO actualizarUsuarioDTO = new ActualizarUsuarioDTO(
                usuario.getId(),
                "Andres",
                "andres@gmail.com",
                "urlfotoperfil",
                Ciudad.PEREIRA,
                "Carrera 9 # 9-98"
        );

        usuarioServicio.actualizarUsuario(actualizarUsuarioDTO);
        Assertions.assertNotNull(usuario.getId());

    }

    @Test
    public void eliminarTest() throws Exception{

        Usuario usuario = new Usuario();

        usuario.setEstadoRegistro(EstadoRegistro.INACTIVO);

        usuarioServicio.eliminarUsuario(usuario.getId());
        Assertions.assertNotNull(usuario.getId());

    }

    @Test
    public void obtenerTest() throws Exception{



    }

}
