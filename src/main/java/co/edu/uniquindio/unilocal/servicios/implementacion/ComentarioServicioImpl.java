package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ItemNegocioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ItemComentariODTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.Comentario;
import co.edu.uniquindio.unilocal.repositorio.ComentarioRepo;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.ComentarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

//    @Override
//    public String eliminarComentario(String idComentario) throws Exception {
//        return  null;
//    }

    /**
     * Método que devuelve todos los comentarios asociados a un negocio
     * @param id del negocio
     * @return Lista de comentarios
     * @throws Exception
     */
    @Override
    public List<ItemComentariODTO> listarComentarios(String id) throws Exception {
        List<Comentario> listaComentarios = comentarioRepo.listarComentario(id);
        if (listaComentarios.isEmpty()){
            throw new ResourceNotFoundException("Error al momento de obtener los comentarios del negocio "+id);
        }
        List<ItemComentariODTO> items = new ArrayList<>();
        for (Comentario comentario: listaComentarios){
            items.add(new ItemComentariODTO(comentario.getId(),comentario.getDescripcion(),
                    comentario.getIdUsuario(),comentario.getCalifacion(),comentario.getIdNegocio()));
        }
        return  items;
    }

    @Override
    public List<ItemComentariODTO> encontrarTop5() throws Exception {
        return null;
    }
}
