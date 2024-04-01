package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.DetalleNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.model.EstadoNegocio;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroRevisionDTO;




public interface NegocioServicio {

    String crearNegocio(RegistroNegocioDTO registroNegocioDTO) throws Exception;
    DetalleNegocioDTO buscarNegocios(String idNegocio) throws Exception;
    void eliminarNegocio(String idNegocio) throws Exception;


    //-------------- Reciben un NegocioDTO porque el negocio ya fue registrado por el usuario
    //-------------- unicamente se le cambia el estado de espera a rechazado/aprobado
    void aprobarNegocio(RegistroRevisionDTO negocioDTO) throws Exception;
    void rechazarNegocio(RegistroRevisionDTO negocioDTO) throws Exception;
    //-----------------------------------------------------------------------------------------





    void filtrarPorEstado(EstadoNegocio estadoNegocio) throws Exception;
}
