package co.edu.uniquindio.unilocal.repositorio;

import co.edu.uniquindio.unilocal.model.EstadoNegocio;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Negocio;
import co.edu.uniquindio.unilocal.model.TipoNegocio;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
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

//    boolean existsByNombre(String nombreNegocio);

    @Query (value = "{tipoNegocio :  ?0}")
    List<Negocio> buscarNegocioPorTipo(TipoNegocio tipoNegocio);

    @Query (value = "{'historialNegocio.estadoNegocio' : ?0 }")
    List<Negocio> ListarNegocioEstado(EstadoNegocio Estado);

    List<Negocio> ListarNegocioPorEstadoRegistro(EstadoRegistro estadoRegistro);

    @Query (value = "{'idUsuario' :  ?0}" )
    List<Negocio> listarNegocioUsuario (String idUsuario);

    @Query(value = "{ '_id' : { $in : ?0 } }")
    List<Negocio> ListarFavoritos (List<String> idNeogcio);

    @Query (value = "{ 'nombre' : { $regex : ?0, $options: 'i' } }" )
    List<Negocio> busquedaNombresSimilares (String nombre);


}
