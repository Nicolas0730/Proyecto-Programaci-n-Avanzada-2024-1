package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.ActualizarClienteDTO;
import co.edu.uniquindio.unilocal.dto.CambiarPasswordDTO;
import co.edu.uniquindio.unilocal.dto.SesionDTO;

public interface UsuarioServicio {

    void registrarse(String nombre, String fotoPerfil, String nickName, String email, String password, String ciudadResidencia);
    void editarPerfil(ActualizarClienteDTO actualizarClienteDTO);
    void eliminarCuenta(String idCliente);
    void enviarLinkRecuperacion(String email);
    void cambiarPassword(CambiarPasswordDTO cambioPasswordDTO);
    void iniciarSesion(SesionDTO sesionDTO);

}
