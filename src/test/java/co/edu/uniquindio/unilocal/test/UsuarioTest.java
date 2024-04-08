package co.edu.uniquindio.unilocal.test;

import co.edu.uniquindio.unilocal.model.Ciudad;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Usuario;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioTest {

    private final UsuarioRepo usuarioRepo;

    public UsuarioTest(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Test
    public void registrarClienteTest(){
//    //Creamos el cliente con sus propiedades
//        Usuario usuario = Usuario.builder().nombre("").correo("").ciudad(Ciudad.ARMENIA).estadoRegistro(EstadoRegistro.ACTIVO).contrasenia("").negociosFavoritos()
//                .build();
////Guardamos el cliente
//        Usuario registro = usuarioRepo.save( usuario );
////Verificamos que se haya guardado validando que no sea null
//        Assertions.assertNotNull(registro);
    }
}
