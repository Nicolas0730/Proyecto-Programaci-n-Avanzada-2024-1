package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.model.Ciudad;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Ubicacion;
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
    Ubicacion ubicacion = new Ubicacion(245,245);

    @Test
    public void registrarTest() throws Exception {

        List<String> negociosFav = new ArrayList<>();
        List<String> registroBusqueda = new ArrayList<>();


        //Se crea un objeto de tipo RegistroClienteDTO
        RegistroUsuarioDTO registroUsuarioDTO = new RegistroUsuarioDTO(
                "Juan",
                "micorreo@gmail.com",
                "Mipassword1@",
                "juanakdmfwmfofo",
                "juanito123",
                Ciudad.ARMENIA,
                ubicacion,
                registroBusqueda,
                negociosFav
                );

        //Se registra el cliente
        String codigo = usuarioServicio.registrarUsuario(registroUsuarioDTO);

        //Se verifica que el codigo no sea nulo, es decir, que se haya registrado el cliente
        Assertions.assertNotNull(codigo);

    }

    @Test
    public void actualizarTest() throws Exception{

        ActualizarUsuarioDTO actualizarUsuarioDTO = new ActualizarUsuarioDTO(
                "Cliente1",
                "Andres",
                "andres1@gmail.com",
                "urlfotoperfil",
                Ciudad.PEREIRA,
                ubicacion
        );

        usuarioServicio.actualizarUsuario(actualizarUsuarioDTO);
        DetalleUsuarioDTO detalleUsuarioDTO = usuarioServicio.obtenerUsuario("Cliente1");
        Assertions.assertNotNull("urlFotoperfil",detalleUsuarioDTO.fotoPerfil());

    }

    @Test
    public void eliminarTest() throws Exception{

        usuarioServicio.eliminarUsuario("Cliente2");

        Assertions.assertThrows(Exception.class, () -> {
            usuarioServicio.obtenerUsuario("Cliente2");
        });

    }

}
