package co.edu.uniquindio.unilocal.repositorio;
import co.edu.uniquindio.unilocal.model.Comentario;
import co.edu.uniquindio.unilocal.model.Negocio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepo extends MongoRepository<Comentario,String> {

    void registrarComentario(String idUsuario,String idNegocio);

    void encontrarTop5();

    void listarComentarios();

    int calcularCalificacionNegocio(String idNegocio);



    @Query(value = "{ 'idNegocio' : ?0 }")
    List <Negocio> ListarComentarios (String idNegocio);

}
