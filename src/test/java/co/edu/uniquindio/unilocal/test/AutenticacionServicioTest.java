package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.dto.LoginDTO;
import co.edu.uniquindio.unilocal.dto.TokenDTO;
import co.edu.uniquindio.unilocal.servicios.interfaces.AutenticacionServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class AutenticacionServicioTest {

    @Autowired
    AutenticacionServicio autenticacionServicio;

    @Test
    public void generarPass() throws Exception{


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println( passwordEncoder.encode("Mipassword1@") );

    }



    @Test
    public void iniciarSesionClienteTest() throws Exception{

        LoginDTO loginDTO = new LoginDTO(
                "diegoalejandrocordobamo@gmail.com",
                "063uV2mP"
        );

        TokenDTO codigo = autenticacionServicio.iniciarSesionCliente(loginDTO);
        Assertions.assertNotNull(codigo);

    }

    @Test
    public void iniciarSesionModeradorTest() throws Exception{

        LoginDTO loginDTO = new LoginDTO(
                "carlos@gmail.com",
                "Mipassword1@"

        );

        TokenDTO codigo = autenticacionServicio.iniciarSesionModerador(loginDTO);
        Assertions.assertNotNull(codigo);

    }


}
