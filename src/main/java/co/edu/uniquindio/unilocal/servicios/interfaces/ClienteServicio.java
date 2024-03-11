package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.ActualizarClienteDTO;
import co.edu.uniquindio.unilocal.dto.RegistroClienteDTO;

public interface ClienteServicio {

    String registrarCliente(RegistroClienteDTO registroClienteDTO) throws Exception;
    void actualizarCliente(ActualizarClienteDTO actualizarClienteDTO) throws Exception;
    DetalleClienteDTO obtenerCliente(String idCuenta) throws Exception;
    List<ItemClienteDTO> listarClientes();
}
