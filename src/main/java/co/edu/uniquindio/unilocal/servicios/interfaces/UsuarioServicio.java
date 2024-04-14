package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.CambiarPasswordDTO;
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

    CambiarPasswordDTO recuperarContrasenia(String idUsuario) throws Exception;

    String agregarNegocioFavorito(String idUsuario, String idNegocio) throws Exception;

    String eliminarNegocioFavorito(String idUsuario, String idNegocio) throws ResourceNotFoundException;

    double solicitarRuta(String idUsuario, Ubicacion ubicacionDestino) throws ResourceNotFoundException;

    //Recomendar lugares en función de las búsquedas que ha realizado.
    List<ItemNegocioDTO> recomendarLugares(String idUsuario) throws Exception;

    List<ItemNegocioDTO> listarNegociosFavoritos(String idUsuario) throws Exception;
    void actualizarUbicacion(String idUsuario,double longitud, double latitud) throws Exception;
}
