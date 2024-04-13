package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.moderadorDTO.ItemModeradorDTO;
import co.edu.uniquindio.unilocal.model.Moderador;
import co.edu.uniquindio.unilocal.repositorio.ModeradorRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.ModeradorServicio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ModeradorServicioImpl implements ModeradorServicio {

    private final ModeradorRepo moderadorRepo;

    /**
     * Método que obtiene un moderador dado su id y devuelve algunos datos
     * @param idModerador
     * @return
     */
    @Override
    public ItemModeradorDTO obtenerDatosModerador(String idModerador) throws Exception {
        Optional<Moderador> optionalModerador = moderadorRepo.findByIdModerador(idModerador);
        if (optionalModerador.isEmpty()){
            throw new Exception("Error al momento de obtener el moderador "+idModerador);
        }
        Moderador moderador= optionalModerador.get();

        return new ItemModeradorDTO(moderador.getIdModerador(),moderador.getNombre());
    }
}
