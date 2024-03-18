package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ModificarNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.NegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.model.EstadoCuenta;
import co.edu.uniquindio.unilocal.model.EstadoRegistro;
import co.edu.uniquindio.unilocal.model.Ubicacion;
import co.edu.uniquindio.unilocal.model.Usuario;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Getter
@Setter
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
        usuario.setCorreo(registroClienteDTO.correo());
        usuario.setDireccion(registroClienteDTO.direccion());
        usuario.setUrlFotoPerfil(registroClienteDTO.urlFotoPerfil());
        //La información que no está en el dto debemos asignarla nosotros, como el estado
        usuario.setEstadoCuenta(EstadoCuenta.ACTIVO);

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

    @Override
    public void recuperarContrasenia() {

    }

    @Override
    public void listarNegociosPropios() {

    }

    @Override
    public void registrarNegocio(RegistroNegocioDTO registroNegocioDTO) {

    }

    @Override
    public void modificarNegocio(String idNegocio, ModificarNegocioDTO negocioDTO) {

    }

    @Override
    public void eliminarNegocio(String idNegocio) {

    }

    @Override
    public void eliminarCuenta() {

    }

    @Override
    public void comentarPublicacion(String comentario, String idNegocio) {

    }

    @Override
    public void contestarComentario(String comentario, String idComentario, String idNegocio) {

    }

    @Override
    public void calificarNegocio(int calificacion, String idNegocio) {

    }

    @Override
    public void agregarNegocioFavorito(String idNegocio) {

    }

    @Override
    public void eliminarNegocioFavorito(String idNegocio) {

    }

    @Override
    public NegocioDTO buscarNegocioPorNombre(String nombreNegocio) {
        return null;
    }

    @Override
    public NegocioDTO buscarNegocioPorTipo(String tipoNegocio) {
        return null;
    }

    @Override
    public NegocioDTO buscarNegocioPorDistancia(int rangoNegocio) {
        return null;
    }

    @Override
    public String solicitarRuta(Ubicacion ubicacionOrigen, Ubicacion ubicacionDestino) {
        return null;
    }

    @Override
    public void recomendarLugares() {

    }
}
