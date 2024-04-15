package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ActualizarNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.DetalleNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroRevisionDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.model.*;
import co.edu.uniquindio.unilocal.servicios.interfaces.NegocioServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class NegocioServicioTest {

    @Autowired
    private NegocioServicio negocioServicio;
    Ubicacion ubicacion = new Ubicacion(245,245);

    @Test
    public void crearNegocioTest() throws Exception{

        List<String> listImagenes = new ArrayList<>();
        List<String> listTele = new ArrayList<>();
        HorarioNegocio horarioNegocio = new HorarioNegocio(LocalDateTime.now(),LocalDateTime.now(),"Martes");
        List<HistorialNegocio> listHistorial = new ArrayList<>();

        RegistroNegocioDTO registroNegocioDTO = new RegistroNegocioDTO(
                "Restaurante nuevo",
                "Restaurante nuevo",
                listImagenes,
                listTele,
                ubicacion,
                "Usuario1",
                horarioNegocio,
                TipoNegocio.RESTAURANTE,
                listHistorial,
                Ciudad.ARMENIA,
                "Armenia"
        );

        String codigo = negocioServicio.crearNegocio(registroNegocioDTO);

        Assertions.assertNotNull(codigo);

    }

    @Test
    public void buscarNegocioTest() throws Exception{

        negocioServicio.buscarNegocio("Negocio1","Usuario1");

    }

    @Test
    public void buscarNegocioNombreTest() throws Exception{

        negocioServicio.buscarNegocioPorNombre("Restaurante Mexicano");

    }

    @Test
    public void eliminarTest() throws Exception{

        negocioServicio.eliminarNegocio("661b30a613481c7f49a585b9");

    }

    @Test
    public void aprobarTest() throws Exception{

        RegistroRevisionDTO registroRevisionDTO = new RegistroRevisionDTO(
                "661b30a613481c7f49a585b9",
                "Muy bueno",
                "Moderador1"
        );

        negocioServicio.aprobarNegocio(registroRevisionDTO);

    }

    @Test
    public void rechazarTest() throws Exception{

        RegistroRevisionDTO registroRevisionDTO = new RegistroRevisionDTO(
                "661b30a613481c7f49a585b9",
                "Muy malo",
                "Moderador1"
        );

        negocioServicio.rechazarNegocio(registroRevisionDTO);
    }

    @Test
    public void actualizarTest() throws Exception{

        List<String> imagenes = new ArrayList<>();
        List<String> telefonos = new ArrayList<>();
        HorarioNegocio horarioNegocio = new HorarioNegocio(LocalDateTime.now(),LocalDateTime.now(),"Lunes");

        ActualizarNegocioDTO actualizarNegocioDTO = new ActualizarNegocioDTO(
                "Negocio1",
                "Restaurante Comida Mexicana",
                "Restaurante de la mejor comida Mexicana",
                imagenes,
                telefonos,
                horarioNegocio
        );

        negocioServicio.actualizarNegocio(actualizarNegocioDTO);
        DetalleNegocioDTO detalleNegocioDTO = negocioServicio.buscarNegocio("Negocio1","Usuario1");
        Assertions.assertNotNull("id",detalleNegocioDTO.id());
    }

    @Test
    public void buscarTipoTest() throws Exception{

        negocioServicio.buscarNegociosPorTipo(TipoNegocio.RESTAURANTE);

    }

    @Test
    public void buscarDistanciaTest() throws Exception{

        negocioServicio.buscarNegociosPorDistancia("Usuario1",2);

    }

    @Test
    public void filtrarEstado() throws Exception{

        negocioServicio.filtrarPorEstado(EstadoNegocio.RECHAZADO);

    }

    @Test
    public void listarNegociosUsuarioTest() throws Exception{

        Assertions.assertNotNull(negocioServicio.listarNegociosDeUsuario("Usuario1"));

    }

    @Test
    public void encontrarTop5Test() throws Exception{

        negocioServicio.encontrarTop5();

    }

}
