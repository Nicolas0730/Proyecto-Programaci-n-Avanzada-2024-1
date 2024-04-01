package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ModificarNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.NegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
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
    List<NegocioDTO> listarNegociosPropios();



    //Este seria un servicio del usuario o del Negocio ?
    String registrarNegocio(RegistroNegocioDTO registroNegocioDTO);


    void actualizarNegocio(String idNegocio, ModificarNegocioDTO negocioDTO);
    String eliminarNegocio(String idNegocio); //Que retorne el id de la cuenta eliminada
    String eliminarCuentaUsuario(); //Que retorne el id de la cuenta eliminada
    void comentarPublicacion(String comentario,String idNegocio);
    void contestarComentario(String comentario,String idComentario,String idNegocio);
    int calificarNegocio(int calificacion,String idNegocio); //Se califa del 1 al 5
    String agregarNegocioFavorito(String idNegocio);//Guardaria solo la referencia del negocio? 17/03 2:12pm

    String eliminarNegocioFavorito(String idNegocio); //Retorna el id del negocio

    NegocioDTO buscarNegocioPorNombre(String nombreNegocio);
    NegocioDTO buscarNegocioPorTipo(TipoNegocio tipoNegocio);
    NegocioDTO buscarNegocioPorDistancia(int rangoNegocio);
    String solicitarRuta(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino);



    //Recomendar lugares en función de las búsquedas que realiza.
    void recomendarLugares();
}
