package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.moderadorDTO.ItemModeradorDTO;
import co.edu.uniquindio.unilocal.model.Moderador;

public interface ModeradorServicio {

    ItemModeradorDTO obtenerDatosModerador(String idModerador) throws Exception;

}
