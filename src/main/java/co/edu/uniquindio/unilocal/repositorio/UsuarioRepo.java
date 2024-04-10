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

    @Query("db.usuarios.find({'email': ?0})")   //?0 hace referencia al parámetro  ?0 ... ?1 ... ?2
    Usuario buscarPorCorreo(String mail);

    @Query("{'email': ?0}")   //Se puede omitir la primera parte y Java entiende
    Usuario buscarPorCorreo2(String mail);

    //Aun mas sencillo se podria hacer:
    Optional<Usuario> findByCorreo(String correo);



    Optional<Usuario> findByNickname(String nickname);

    boolean existsByCorreo(String correo);
    boolean existsByNickname(String nickname);

    @Query("{ 'mail':?0, 'password' :  ?1}")
    Usuario buscarPorCorreoyContrasenia(String correo, String contrasenia);



    List<Usuario> findByNombreContains(String letra);



    @Query(value = "{ 'cuenta.estadoRegistro' :'ACTIVO' }")
    List<Usuario> listarEstadoActivo ();



}
