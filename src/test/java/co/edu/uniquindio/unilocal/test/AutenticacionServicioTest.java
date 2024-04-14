package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.dto.LoginDTO;
import co.edu.uniquindio.unilocal.dto.TokenDTO;
import co.edu.uniquindio.unilocal.servicios.interfaces.AutenticacionServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AutenticacionServicioTest {

    @Autowired
    AutenticacionServicio autenticacionServicio;

    @Test
    public void iniciarSesionClienteTest() throws Exception{

        LoginDTO loginDTO = new LoginDTO(
                "andres1@gmail.com",
                "Mipassword1@"
        );

        TokenDTO codigo = autenticacionServicio.iniciarSesionCliente(loginDTO);
        Assertions.assertNotNull(codigo);

    }

    @Test
    public void iniciarSesionModeradorTest() throws Exception{

        LoginDTO loginDTO = new LoginDTO(
                "example@email.com",
                "mipassword"

        );

        TokenDTO codigo = autenticacionServicio.iniciarSesionModerador(loginDTO);
        Assertions.assertNotNull(codigo);

    }


}
