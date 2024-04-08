package co.edu.uniquindio.unilocal.repositorio;

import co.edu.uniquindio.unilocal.model.EstadoNegocio;
import co.edu.uniquindio.unilocal.model.Negocio;
import co.edu.uniquindio.unilocal.model.TipoNegocio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NegocioRepo extends MongoRepository<Negocio,String> {

    Optional<Negocio> findById(String idNegocio);

    Optional<Negocio> findByNombre(String nombre);

    boolean existsByNombre(String nombreNegocio);

    List<Negocio> findByTipoNegocio(TipoNegocio tipoNegocio);

    //Toca construir una consulta que busque y devuelva un Optional<Negocio> en base una distancia
    //indicada por parámetro (int) en kilómetros alrededor




    @Query(value = "{ 'historialNegocio.estadoNegocio' : 'EN_ESPERA' }")
    List<Negocio> listarEstdoEspera ();


    @Query (value = "{'historialNegocio.estadoNegocio' : ?0 }")
    List<Negocio> ListarNegocioEstado(String Estado);

    @Query (value = "{'historialNegocio.estadoNegocio' :  'APROBADO', 'historialNegocio.idModerador' : ?0}")
    List<Negocio> listarPorAutorizadosModerador (String idAdmin);

    @Query (value = "{'historialNegocio.estadoNegocio' : 'RECHAZADO', 'historialNegocio.idModerador' :  ?0}")
    List<Negocio> listarPorRechazadoModerador (String idAdmin);

    @Query (value = "{'idUsuario' :  ?0}" )
    List<Negocio> listarNegocioUsuario (String idUsuario);

    @Query(value = "{ '_id' : { $in : ?0 } }") // Realizar pregunta si la consulta, va en negocio o en usuario
    List<Negocio> ListarFavoritos (List<String> idNeogcio);
}
