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
    private final UsuarioRepo usuarioRepo;
    @Override
    public String registrarComentario(RegistroComentarioDTO registroComentarioDTO) throws Exception {
        Comentario comentario = new Comentario();
        comentario.setDescripcion(registroComentarioDTO.descripcion());
        comentario.setCalifacion(registroComentarioDTO.calificacion());
        comentario.setIdUsuario(registroComentarioDTO.idUsuario());
        comentario.setIdNegocio(registroComentarioDTO.idNegocio());
        comentario.setRespuesta(registroComentarioDTO.respuesta());
        comentario.setImagenes(registroComentarioDTO.imagenes());
        comentario.setFechaComentario(LocalDateTime.now());

        Comentario comentarioGuardado=comentarioRepo.save(comentario);

        return comentarioGuardado.getId();
    }

    @Override
    public void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception {
        Optional<Comentario> optionalComentario = comentarioRepo.findById(responderComentarioDTO.idComentario());
        if (optionalComentario.isEmpty()){
            throw new Exception("Error ");
        }
        Comentario comentario = optionalComentario.get();
        if (comentario.getRespuesta()!=null){
            throw new Exception("El comentario solo puede ser respondido una vez.");
        }
        comentario.setRespuesta(responderComentarioDTO.respuesta());
    }

//    @Override
//    public ComentarioDTO buscarComentario(String idComentario) throws Exception {
//        return null;
//    }

//    @Override
//    public String eliminarComentario(String idComentario) throws Exception {
//        return  null;
//    }


}
