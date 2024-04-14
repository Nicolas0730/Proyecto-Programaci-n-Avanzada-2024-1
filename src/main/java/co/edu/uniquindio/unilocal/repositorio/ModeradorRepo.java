package co.edu.uniquindio.unilocal.repositorio;

import co.edu.uniquindio.unilocal.model.Moderador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeradorRepo extends MongoRepository<Moderador,String> {

    Optional<Moderador> findByIdModerador(String idModerador);

}
