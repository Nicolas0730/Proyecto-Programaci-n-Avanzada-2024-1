package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;
import co.edu.uniquindio.unilocal.servicios.interfaces.ComentarioServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ComentarioServicioTest {

    @Autowired
    ComentarioServicio comentarioServicio;

    @Test
    public void registrarTest() throws Exception{

        List<String> imagenes = new ArrayList<>();

        RegistroComentarioDTO registroComentarioDTO = new RegistroComentarioDTO(
                "Muy bueno",
                5,
                "Usuario3",
                "Negocio3",
                "",
                imagenes
        );

        String codigo = comentarioServicio.registrarComentario(registroComentarioDTO);
        Assertions.assertNotNull(codigo);

    }

    @Test
    public void responderTest() throws Exception{

        ResponderComentarioDTO responderComentarioDTO = new ResponderComentarioDTO(
                "661d6116d1b32d0cdf87cfde",
                "Gracias"
        );

            comentarioServicio.responderComentario(responderComentarioDTO);

    }
}
