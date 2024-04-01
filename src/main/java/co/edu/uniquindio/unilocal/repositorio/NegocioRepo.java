package co.edu.uniquindio.unilocal.repositorio;

import co.edu.uniquindio.unilocal.model.Negocio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NegocioRepo extends MongoRepository<Negocio,String> {

    @Override
    Optional<Negocio> findById(String idNegocio);
}
