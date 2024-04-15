package co.edu.uniquindio.unilocal.repositorio;

import co.edu.uniquindio.unilocal.model.EstadoNegocio;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Negocio;
import co.edu.uniquindio.unilocal.model.TipoNegocio;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NegocioRepo extends MongoRepository<Negocio,String> {


    Optional<Negocio> findById(String idNegocio);

    Optional<Negocio> findByNombre(String nombre);

    //boolean existsByNombre(String nombreNegocio);

    @Query (value = "{tipoNegocio :  ?0}")
    List<Negocio> buscarNegocioPorTipo(TipoNegocio tipoNegocio);

    @Query (value = "{'historialNegocio.estadoNegocio' : ?0 }")
    List<Negocio> ListarNegocioEstado(EstadoNegocio Estado);

    @Query (value = "{'estadoRegistro' :  ?0}")
    List<Negocio> ListarNegocioPorEstadoRegistro(EstadoRegistro estadoRegistro);

    @Query (value = "{'idUsuario' :  ?0}" )
    List<Negocio> listarNegocioUsuario (String idUsuario);

   // @Query(value = "{ '_id' : { $in : ?0 } }")
   // List<Negocio> ListarFavoritos (List<String> idNeogcio);

    @Query (value = "{ 'nombre' : { $regex : ?0, $options: 'i' } }" )
    List<Negocio> busquedaNombresSimilares (String nombre);


    @Aggregation({ "{$match: {estadoRegistro: ?1} }", "{ $addFields: { utlimaResvision: { $last: '$historialNegocio' } } }", "{$match: {utlimaResvision.estadoNegocio: ?0} }" })
    List<Negocio> busquedPorEstadoRegistroyEstadonegocio (EstadoNegocio estadoNegocio,EstadoRegistro estadoRegistro);

     @Aggregation({
            "{$match: {_id: ?0}}",  // Preguntar si esta consulta, se hace en usuario o en negocio
            "{$unwind: '$negociosFavoritos'}",
            "{$lookup: {from: 'negocio', localField: 'negociosFavoritos', foreignField: '_id', as: 'negocio_favorito'}}",
            "{$project: { _id: 0, negociosFavoritos: { $arrayElemAt: ['$negocio_favorito', 0]}}}" })
    List<Negocio> ListarFavoritos (String idUsuario);

}
