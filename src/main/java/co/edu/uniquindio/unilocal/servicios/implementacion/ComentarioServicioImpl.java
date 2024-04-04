package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.comentarioDTO.ComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.ComentarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComentarioServicioImpl implements ComentarioServicio {

    private final UsuarioRepo usuarioRepo;
    @Override
    public String registrarComentario(RegistroComentarioDTO registroComentarioDTO) throws Exception {
        return null;
    }

    @Override
    public void responderComentario(ResponderComentarioDTO responderComentarioDTO) throws Exception {

    }

    @Override
    public ComentarioDTO buscarComentario(String idComentario) throws Exception {
        return null;
    }

    @Override
    public void eliminarComentario(String idComentario) throws Exception {

    }

    @Override
    public void listarComentarios(String id) throws Exception {

    }

    @Override
    public void encontrarTop5() throws Exception {

    }
}
