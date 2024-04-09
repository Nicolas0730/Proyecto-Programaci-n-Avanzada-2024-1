package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ItemNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.TipoNegocio;
import co.edu.uniquindio.unilocal.model.Ubicacion;

import java.util.List;

public interface UsuarioServicio {

    String registrarUsuario(RegistroUsuarioDTO registroClienteDTO) throws Exception;

    void actualizarUsuario(ActualizarUsuarioDTO actualizarUsuarioDTO) throws Exception;

    void eliminarUsuario(String idUsuario) throws Exception;

    DetalleUsuarioDTO obtenerUsuario(String idCuenta) throws Exception;
    //List<ItemUsuarioDTO> listarUsuarios(); --No es necesario en el proyecto
    String recuperarContrasenia();

    //List<NegocioDTO> listarNegociosPropios();

    String eliminarCuentaUsuario(String idUsuario) throws ResourceNotFoundException; //Que retorne el id de la cuenta eliminada


    //void comentarPublicacion(String comentario,String idNegocio);
    //void contestarComentario(String comentario,String idComentario,String idNegocio);

    String agregarNegocioFavorito(String idUsuario,String idNegocio) throws Exception;

    String eliminarNegocioFavorito(String idUsuario,String idNegocio) throws ResourceNotFoundException;

    String solicitarRuta(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino);



    //Recomendar lugares en función de las búsquedas que realiza.
    List<ItemNegocioDTO> recomendarLugares(String idUsuario) throws Exception;

    List<ItemNegocioDTO> listarNegociosFavoritos(List<String> negociosFavoritos) throws Exception;

}
