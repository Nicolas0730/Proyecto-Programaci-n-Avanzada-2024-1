package co.edu.uniquindio.unilocal.repositorio;
import co.edu.uniquindio.unilocal.model.Comentario;
import co.edu.uniquindio.unilocal.model.Negocio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComentarioRepo extends MongoRepository<Comentario,String> {

    //Se puede hacer la consulta para que devuelva directamente el promedio??
    int calcularCalificacionNegocio(String idNegocio);

    //Hacer una consulta que traiga todos los comentarios con idNegocio indicado por parámetro
    Optional<Negocio> findByIdNegocio(String idNegocio);

}
