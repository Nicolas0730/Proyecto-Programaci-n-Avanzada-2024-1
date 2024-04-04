package co.edu.uniquindio.unilocal.repositorio;

import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Negocio;
import co.edu.uniquindio.unilocal.model.TipoNegocio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Repository
public interface NegocioRepo extends MongoRepository<Negocio,String> {

    Optional<Negocio> findById(String idNegocio);



@Query (value = "{'estadoRegistro' :  ?0}")
    List<Negocio> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    Optional<Negocio> findByNombre(String nombre);

    Optional<Negocio> finByTipoNegocio(TipoNegocio tipoNegocio);

    //Toca construir una consulta que busque y devuelva un Optional<Negocio> en base una distancia
    //indicada por parámetro (int)


}
