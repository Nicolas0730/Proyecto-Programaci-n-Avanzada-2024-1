package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.model.*;
import co.edu.uniquindio.unilocal.servicios.implementacion.UsuarioServicioImpl;
import co.edu.uniquindio.unilocal.servicios.interfaces.NegocioServicio;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UsuarioServicioTest {


    @Autowired
    private UsuarioServicio usuarioServicio;
    Ubicacion ubicacion = new Ubicacion(245,245);

    @Autowired
    private NegocioServicio negocioServicio;

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
                "661b22d9b6d1751c2ab2a94c",
                "Andres",
                "andres1@gmail.com",
                "urlfotoperfil",
                Ciudad.PEREIRA,
                ubicacion,
                EstadoRegistro.ACTIVO
        );

        usuarioServicio.actualizarUsuario(actualizarUsuarioDTO);
        DetalleUsuarioDTO detalleUsuarioDTO = usuarioServicio.obtenerUsuario("Cliente1");
        Assertions.assertNotNull("urlFotoperfil",detalleUsuarioDTO.fotoPerfil());

    }

    @Test
    public void eliminarTest() throws Exception{

        usuarioServicio.eliminarUsuario("Usuario1");

        Assertions.assertThrows(Exception.class, () -> {
            usuarioServicio.obtenerUsuario("Usuario1");
        });

    }

    @Test
    public void obtenerTest() throws Exception{

        DetalleUsuarioDTO detalleUsuarioDTO = usuarioServicio.obtenerUsuario("Usuario1");

        Assertions.assertNotNull(detalleUsuarioDTO.id());

    }

    @Test
    public void obtenerBorradoTest() throws Exception{

        Assertions.assertThrows(Exception.class, () -> {
            usuarioServicio.obtenerUsuario("Usuario2");
        });

    }

    @Test
    public void recuperarContraseniaTest() throws Exception{

        Assertions.assertNotNull(usuarioServicio.recuperarContrasenia("Usuario1"));

    }

    @Test
    public void agregarNegecioFavTest() throws Exception{

        usuarioServicio.agregarNegocioFavorito("Usuario1","Negocio1");

    }

    @Test
    public void eliminarNegocioFavTest() throws Exception{

        usuarioServicio.eliminarNegocioFavorito("Usuario1","Negocio1");

    }

    @Test
    public void recomendarLugarTest() throws Exception{

        Assertions.assertThrows(Exception.class, () -> {
            usuarioServicio.recomendarLugares("Usuario1");
        });

    }

    @Test
    public void listarNegociosFav() throws Exception{

        Assertions.assertThrows(Exception.class,() -> {
            usuarioServicio.listarNegociosFavoritos("Usuario1");
        });

    }

    @Test
    public void actualizarUbicacionTest() throws Exception{

        usuarioServicio.actualizarUbicacion("Usuario1",300,350);

    }

}
