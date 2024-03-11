package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;

public interface ClienteServicio extends CuentaServicio{

    void registrarse(RegistroUsuarioDTO registroClienteDTO);
}
