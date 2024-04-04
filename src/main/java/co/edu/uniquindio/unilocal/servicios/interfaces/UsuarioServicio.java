package co.edu.uniquindio.unilocal.servicios.interfaces;

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
    List<ItemUsuarioDTO> listarUsuarios();
    String recuperarContrasenia();

    //List<NegocioDTO> listarNegociosPropios();

    String eliminarCuentaUsuario(String idUsuario) throws ResourceNotFoundException; //Que retorne el id de la cuenta eliminada


    //void comentarPublicacion(String comentario,String idNegocio);
    //void contestarComentario(String comentario,String idComentario,String idNegocio);


    int calificarNegocio(int calificacion,String idNegocio); //Se califa del 1 al 5
    String agregarNegocioFavorito(String idNegocio);//Guardaria solo la referencia del negocio? 17/03 2:12pm

    String eliminarNegocioFavorito(String idNegocio); //Retorna el id del negocio

    String solicitarRuta(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino);



    //Recomendar lugares en función de las búsquedas que realiza.
    void recomendarLugares();
}
