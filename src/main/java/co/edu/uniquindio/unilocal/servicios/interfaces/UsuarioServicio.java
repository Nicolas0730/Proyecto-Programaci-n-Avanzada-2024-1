package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ModificarNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.NegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.model.Ubicacion;

import java.util.List;

public interface UsuarioServicio {

    void registrarse(RegistroUsuarioDTO registroClienteDTO);

    void actualizarCliente(ActualizarUsuarioDTO actualizarUsuarioDTO);

    DetalleUsuarioDTO obtenerCliente(String idCuenta) throws Exception;
    List<ItemUsuarioDTO> listarClientes();

    void recuperarContrasenia();
    void listarNegociosPropios();
    void registrarNegocio(RegistroNegocioDTO registroNegocioDTO);
    void modificarNegocio(String idNegocio, ModificarNegocioDTO negocioDTO);
    void eliminarNegocio(String idNegocio);
    void eliminarCuenta();
    void comentarPublicacion(String comentario,String idNegocio);
    void contestarComentario(String comentario,String idComentario,String idNegocio);
    void calificarNegocio(int calificacion,String idNegocio);

    void agregarNegocioFavorito(String idNegocio);//Guardaria solo la referencia del negocio? 17/03 2:12pm

    void eliminarNegocioFavorito(String idNegocio);

    NegocioDTO buscarNegocioPorNombre(String nombreNegocio);
    NegocioDTO buscarNegocioPorTipo(String tipoNegocio);  //Parametro como enumeración?
    NegocioDTO buscarNegocioPorDistancia(int rangoNegocio);
    String solicitarRuta(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino);



    //Recomendar lugares en función de las búsquedas que realiza.
    void recomendarLugares();
}
