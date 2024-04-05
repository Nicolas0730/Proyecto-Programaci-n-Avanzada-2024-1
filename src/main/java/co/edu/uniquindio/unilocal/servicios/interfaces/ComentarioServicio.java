package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.comentarioDTO.ComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ItemComentariODTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;

import java.util.List;


public interface ComentarioServicio {

    //Retorna el id del comentario
    String registrarComentario(RegistroComentarioDTO registroComentarioDTO) throws Exception;

    void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception;

    ComentarioDTO buscarComentario(String idComentario) throws Exception;

    String eliminarComentario(String idComentario) throws Exception;

    List<ItemComentariODTO> listarComentarios(String id) throws Exception;

    List<ItemComentariODTO> encontrarTop5() throws Exception; //Esta es una funcionalidad propuesta

}
