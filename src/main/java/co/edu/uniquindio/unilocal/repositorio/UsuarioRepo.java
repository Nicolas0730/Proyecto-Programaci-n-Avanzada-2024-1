
package co.edu.uniquindio.unilocal.repositorio;

import co.edu.uniquindio.unilocal.model.Cuenta;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepo extends MongoRepository<Usuario,String> {

    //Aun mas sencillo se podria hacer:
    Optional<Usuario> findByCorreo(String correo);

<<<<<<< HEAD
    Optional<Usuario> findById(String idUsuario);
=======


    Optional<Usuario> findByNickname(String nickname);
>>>>>>> ramaDiego

    boolean existsByCorreo(String correo);
    boolean existsByNickname(String nickname);

<<<<<<< HEAD
  //  @Query("{ 'mail':?0, 'password' :  ?1}")
 //   Usuario buscarPorCorreoyContrasenia(String correo, String contrasenia);
=======
    @Query("{ 'mail':?0, 'password' :  ?1}")
    Usuario buscarPorCorreoyContrasenia(String correo, String contrasenia);
>>>>>>> ramaDiego





}

