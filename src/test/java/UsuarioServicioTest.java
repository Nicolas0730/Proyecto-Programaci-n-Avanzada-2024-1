import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.model.Ciudad;
import co.edu.uniquindio.unilocal.servicios.implementacion.UsuarioServicioImpl;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
@SpringBootTest
public class UsuarioServicioTest {


    private UsuarioServicio usuarioServicio;

    @Test
    public void registrarTest() throws Exception {

        //Se crea un objeto de tipo RegistroClienteDTO
        RegistroUsuarioDTO registroUsuarioDTO = new RegistroUsuarioDTO(
                "Juan",
                "micorreo@gmail.com",
                "juanito123",
                "juanakdmfwmfofo",
                "mipassword",
                Ciudad.ARMENIA,
                "Quimbaya, Mz 1 #12-12",

                );

        //Se registra el cliente
        String codigo = usuarioServicio.registrarUsuario(registroUsuarioDTO);

        //Se verifica que el codigo no sea nulo, es decir, que se haya registrado el cliente
        Assertions.assertNotNull(codigo);

    }
}
