
package co.edu.uniquindio.unilocal.repositorio;

import co.edu.uniquindio.unilocal.model.Usuario;
import com.mongodb.lang.NonNullApi;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepo extends MongoRepository<Usuario,String> {

    //Aun mas sencillo se podria hacer:
    Optional<Usuario> findByCorreo(String correo);

    //@NonNull //Si se pone la etiqueta deja de mostrar la advertencia en el metodo findById
    Optional<Usuario> findById(String idUsuario);

    boolean existsByCorreo(String correo);
    boolean existsByNickname(String nickname);

  //  @Query("{ 'mail':?0, 'password' :  ?1}")
 //   Usuario buscarPorCorreoyContrasenia(String correo, String contrasenia);





}

