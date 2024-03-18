package co.edu.uniquindio.unilocal.servicios.implementaciones;

import co.edu.uniquindio.unilocal.dto.RegistroClienteDTO;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.documentos.Usuario;
import co.edu.uniquindio.unilocal.repositorios.UsuarioRepo;

public class ClienteServicioImp {

    @Override
    public String registrarCliente(RegistroClienteDTO registroClienteDTO) throws Exception{

        Usuario usuario = new Usuario();
        usuario.setNombre( registroClienteDTO.nombre() );
        usuario.setEmail( registroClienteDTO.email() );
        usuario.setPassword( registroClienteDTO.password() );
        usuario.setUrlFotoPerfil( registroClienteDTO.fotoPerfil() );
        usuario.setNickname( registroClienteDTO.nickName() );
        usuario.setCiudad( registroClienteDTO.ciudadResidencia() );
        usuario.setEstado(EstadoRegistro.APROBADO);

        Usuario usuarioGuardado = UsuarioRepo.save(usuario);

        return null;
    }

}
