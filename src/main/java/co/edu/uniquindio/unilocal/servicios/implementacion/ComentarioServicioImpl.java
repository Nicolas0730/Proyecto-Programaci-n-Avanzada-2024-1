package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.comentarioDTO.ComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ItemComentariODTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;
import co.edu.uniquindio.unilocal.model.Comentario;
import co.edu.uniquindio.unilocal.repositorio.ComentarioRepo;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.ComentarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioServicioImpl implements ComentarioServicio {

    private final ComentarioRepo comentarioRepo;

    private final UsuarioRepo usuarioRepo;
    @Override
    public String registrarComentario(RegistroComentarioDTO registroComentarioDTO) throws Exception {
        Comentario comentario = new Comentario();
        comentario.setDescripcion(registroComentarioDTO.descripcion());
        comentario.setCalifacion(registroComentarioDTO.calificacion()); //Este seria el numero de estrellas que se le asigna al negocio
        comentario.setIdUsuario(registroComentarioDTO.idUsuario());
        comentario.setIdNegocio(registroComentarioDTO.idNegocio());
        comentario.setImagenes(registroComentarioDTO.imagenes());
        comentario.setFechaComentario(LocalDateTime.now());

        Comentario comentarioGuardado=comentarioRepo.save(comentario);

        return comentarioGuardado.getId();
    }

    @Override
    public void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception {

    }

    @Override
    public ComentarioDTO buscarComentario(String idComentario) throws Exception {
        return null;
    }

    @Override
    public String eliminarComentario(String idComentario) throws Exception {
        return  null;
    }

    @Override
    public List<ItemComentariODTO> listarComentarios(String id) throws Exception {
        return null;
    }

    @Override
    public List<ItemComentariODTO> encontrarTop5() throws Exception {
        return null;
    }
}
