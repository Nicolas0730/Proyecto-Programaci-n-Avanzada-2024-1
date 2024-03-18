package co.edu.uniquindio.unilocal.repositorio;
import co.edu.uniquindio.unilocal.model.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepo extends MongoRepository<Comentario,String> {

    void registrarComentario(String idUsuario,String idNegocio);

    void encontrarTop5();

    void listarComentarios();

    int calcularCalificacionNegocio(String idNegocio);

}
