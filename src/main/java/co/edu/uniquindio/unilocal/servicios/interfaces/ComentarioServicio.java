package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ItemNegocioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ItemComentariODTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;

import java.util.List;


public interface ComentarioServicio {

    String registrarComentario(RegistroComentarioDTO registroComentarioDTO) throws Exception;

    void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception;

}
