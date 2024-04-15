package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ItemNegocioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.Comentario;
import co.edu.uniquindio.unilocal.model.Negocio;
import co.edu.uniquindio.unilocal.repositorio.ComentarioRepo;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.ComentarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComentarioServicioImpl implements ComentarioServicio {

    private final ComentarioRepo comentarioRepo;

    /**
     * Método que registra un comentario en un negocio, obliga a calificar
     * y pueden agregarse imagenes en el comentario (o no)
     * Cada vez que se quiera calificar un negocio debe llamarse este metodo y completar los campos
     * @param registroComentarioDTO
     * @return
     * @throws Exception
     */
    @Override
    public String registrarComentario(RegistroComentarioDTO registroComentarioDTO) throws Exception {
        Comentario comentario = new Comentario();
        comentario.setDescripcion(registroComentarioDTO.descripcion());
        comentario.setCalificacion(registroComentarioDTO.calificacion());
        comentario.setIdUsuario(registroComentarioDTO.idUsuario());
        comentario.setIdNegocio(registroComentarioDTO.idNegocio());
        comentario.setRespuesta(null);
        comentario.setImagenes(registroComentarioDTO.imagenes()); //FUNCIONALIDAD PROPUESTA: Agregar a imagenes al comentario
        comentario.setFechaComentario(LocalDateTime.now());

        Comentario comentarioGuardado=comentarioRepo.save(comentario);

        return comentarioGuardado.getId();
    }

    /**
     * Método que es invocado por el dueño de una publicacion para contestar un comentario
     * que otro usuario le dejó. Unicamente el dueño de la publicación puede contestar el comentario
     * y solo se puede contestar una vez
     * @param responderComentarioDTO
     * @throws Exception
     */
    @Override
    public void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception {

        Optional<Comentario> optionalComentario = comentarioRepo.findById(responderComentarioDTO.idComentario());
        if (optionalComentario.isEmpty()){
            throw new Exception("Error Comentario no encontrado");
        }
        Comentario comentario = optionalComentario.get();
//        if (comentario.getRespuesta()==null || comentario.getRespuesta().isEmpty()){
//            throw new Exception("El comentario solo puede ser respondido una vez.");
//        }
        comentario.setRespuesta(responderComentarioDTO.respuesta());
        comentarioRepo.save(comentario);
    }


}
