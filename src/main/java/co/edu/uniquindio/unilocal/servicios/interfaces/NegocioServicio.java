package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.*;
import co.edu.uniquindio.unilocal.model.EstadoNegocio;
import co.edu.uniquindio.unilocal.model.TipoNegocio;

import java.util.List;

public interface NegocioServicio {

    String crearNegocio(RegistroNegocioDTO registroNegocioDTO) throws Exception;
    DetalleNegocioDTO buscarNegocio(String idNegocio) throws Exception;
    void eliminarNegocio(String idNegocio) throws Exception;
    void aprobarNegocio(RegistroRevisionDTO negocioDTO) throws Exception;
    void rechazarNegocio(RegistroRevisionDTO negocioDTO) throws Exception;

    void actualizarNegocio(ActualizarNegocioDTO actualizarNegocioDTO) throws Exception;
    //-----------------------------------------------------------------------------------------

    DetalleNegocioDTO buscarNegocioPorNombre(String nombreNegocio)throws Exception;
    List<ItemNegocioDTO> buscarNegociosPorTipo(TipoNegocio tipoNegocio) throws Exception;
    List<DetalleNegocioDTO> buscarNegociosPorDistancia(int rangoNegocio) throws Exception;

    void filtrarPorEstado(EstadoNegocio estadoNegocio) throws Exception;

    List<ItemNegocioDTO> listarNegociosDeUsuario(String idUsuario) throws Exception;
}
