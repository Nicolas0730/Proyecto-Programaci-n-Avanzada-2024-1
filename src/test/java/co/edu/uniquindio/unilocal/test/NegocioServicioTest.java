package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
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

}
