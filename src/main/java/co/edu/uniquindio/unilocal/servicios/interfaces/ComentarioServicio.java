package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.comentarioDTO.ComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;

public interface ComentarioServicio {

    void comentarPublicacion(RegistroComentarioDTO registroComentarioDTO) throws Exception;

    void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception;
    ComentarioDTO buscarComentario(String idComentario) throws Exception;

    void eliminarComentario(String idComentario) throws Exception;

}
