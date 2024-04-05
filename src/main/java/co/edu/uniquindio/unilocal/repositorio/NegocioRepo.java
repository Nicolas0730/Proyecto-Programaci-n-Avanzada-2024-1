package co.edu.uniquindio.unilocal.repositorio;

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

    List<Negocio> finByTipoNegocio(TipoNegocio tipoNegocio);

    //Toca construir una consulta que busque y devuelva un Optional<Negocio> en base una distancia
    //indicada por parámetro (int) en kilómetros alrededor






}
