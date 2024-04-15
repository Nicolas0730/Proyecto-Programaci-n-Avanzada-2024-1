package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.dto.EmailDTO;
import co.edu.uniquindio.unilocal.servicios.interfaces.EmailServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServicioTest {

    @Autowired
    EmailServicio emailServicio;

    @Test
    public void enviarTest() throws Exception{

        EmailDTO emailDTO = new EmailDTO(
                "Test",
                "Se esta haciendo el test del metodo enviar Email",
                "diegoalejandrocordobamo@gmail.com"
        );
        emailServicio.enviarCorreo(emailDTO);
        Assertions.assertNotNull("asunto",emailDTO.asunto());

    }
}
