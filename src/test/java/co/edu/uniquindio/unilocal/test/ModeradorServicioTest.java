package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.servicios.interfaces.ModeradorServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModeradorServicioTest {

    @Autowired
    ModeradorServicio moderadorServicio;

    @Test
    public void obtenerDatosTest() throws Exception{

        Assertions.assertNotNull(moderadorServicio.obtenerDatosModerador("Moderador1"));

    }
}
