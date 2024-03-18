package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.model.EstadoCuenta;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Usuario;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServicioImpl implements UsuarioServicio {

    private UsuarioRepo usuarioRepo;

    @Override
    public String registrarse(RegistroUsuarioDTO registroClienteDTO) throws Exception {

        // Si fueramos a validar que el correo no esté en uso por otra persona debemos programarlo nosotros debido a que no hay una anotación en lombok para esto
        if(existeCorreo(registroClienteDTO.correo())){
            throw new Exception("El correo ya está en uso por otra persona");
        }
        //Se haría lo mismo para el nickname
        if(existeNickname(registroClienteDTO.nickname())){
            throw new Exception("El correo ya está en uso por otra persona");
        }

        Usuario usuario = new Usuario();
        //Seteamos todos los atributos que se necesitan para registrar el usuario
        usuario.setcorreo
        usuario.setDireccion(registroClienteDTO.direccion());
        usuario.setUrlFotoPerfil(registroClienteDTO.urlFotoPerfil());
        //La información que no está en el dto debemos asignarla nosotros, como el estado
        usuario.setEstado(EstadoCuenta.ACTIVO);

        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        return usuarioGuardado.getId();
    }

    private boolean existeCorreo(String mail){

        // ESTO NO ES OPTIMO
        //List<Usuario> usuarioList = usuarioRepo.findAll();

        //for (Usuario usuario : usuarioList){
        //    if (usuario.getCorreo().equals(mail)){
                return true;
        //    }
        //}
        //

        //Usuario usuario = usuarioRepo.findByCorreo(mail);
    }

    @Override
    public void actualizarCliente(ActualizarUsuarioDTO actualizarUsuarioDTO) {

    }

    @Override
    public DetalleUsuarioDTO obtenerCliente(String idCuenta) throws Exception {
        return null;
    }

    @Override
    public List<ItemUsuarioDTO> listarClientes() {
        return null;
    }
}
