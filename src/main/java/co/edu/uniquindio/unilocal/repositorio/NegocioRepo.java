package co.edu.uniquindio.unilocal.repositorio;

import co.edu.uniquindio.unilocal.model.Negocio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegocioRepo extends MongoRepository<Negocio,String> {
}
