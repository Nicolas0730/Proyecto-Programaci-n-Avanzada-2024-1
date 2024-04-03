package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.NegocioDTO.ModificarNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.NegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroNegocioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ActualizarUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.DetalleUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.ItemUsuarioDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.exception.ResourceNotFoundException;
import co.edu.uniquindio.unilocal.model.*;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepo usuarioRepo; //variable para poder invocar sus métodos de acceso a la bd.

    @Override
    public String registrarUsuario(RegistroUsuarioDTO registroUsuarioDTO) throws Exception {
        //Se crea el objeto usuario
        Usuario usuario = new Usuario();

        //Se le asignan sus campos
        usuario.setNombre( registroUsuarioDTO.nombre() );

        if (existeNickname(registroUsuarioDTO.nickname()) ){
            throw new Exception("El correo ya se encuentra registrado");
        }else {
            usuario.setNickname( registroUsuarioDTO.nickname() );
        }

        usuario.setCiudad( registroUsuarioDTO.ciudadResidencia() );
        usuario.setUrlFotoPerfil( registroUsuarioDTO.urlFotoPerfil() );

        if( existeEmail(registroUsuarioDTO.correo()) ){
            throw new Exception("El correo ya se encuentra registrado");
        }else {
            usuario.setCorreo( registroUsuarioDTO.correo() );
        }

        if (!validarPatronContrasenia(registroUsuarioDTO.contrasenia())){
            throw new Exception("La contraseña no cumple con las características indicadas");
        }else {
            usuario.setContrasenia(registroUsuarioDTO.contrasenia());
        }

        //Encriptación
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode( registroUsuarioDTO.contrasenia() );
        usuario.setContrasenia( passwordEncriptada );

        usuario.setEstadoRegistro(EstadoRegistro.ACTIVO);
        usuario.setDireccion(registroUsuarioDTO.direccion());

        //Se guarda en la base de datos y obtenemos el objeto registrado
        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        //Retornamos el id (código) del cliente registrado
        return usuarioGuardado.getId();
    }

    private boolean validarPatronContrasenia(String contrasenia) {
        // Patrón para validar la contraseña
        String patron = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        // Compilamos el patrón
        Pattern pattern = Pattern.compile(patron);

        // Creamos un matcher con la contraseña dada
        Matcher matcher = pattern.matcher(contrasenia);
        return matcher.matches();
    }

    /**
     * Método para verificar si existe un usuario ya está registrado con ese nickname
     * @param nickname
     */
    private boolean existeNickname(String nickname) {
        return usuarioRepo.findByNickname(nickname).isPresent();
    }
    /**
     * Método para verificar si existe un usuario ya está registrado con ese correo
     * @param correo
     * @return
     */
    private boolean existeEmail(String correo) {
        return usuarioRepo.findByCorreo(correo).isPresent();
    }

    @Override
    public void actualizarUsuario(ActualizarUsuarioDTO actualizarUsuarioDTO) throws Exception {

        Optional<Usuario> optionalUsuario = validarUsuarioExiste(actualizarUsuarioDTO.idUsuario());
//        //Buscamos el cliente que se quiere actualizar
//        Optional<Usuario> optionalUsuario = usuarioRepo.findById( actualizarUsuarioDTO.idUsuario() );
//
//        //Si no se encontró el usuario, lanzamos una excepción
//        if(optionalUsuario.isEmpty()){
//            throw new Exception("No se encontró el usuario a actualizar");
//        }
        //Obtenemos el usuario que se quiere actualizar y le asignamos los nuevos valores (el nickname no se puede cambiar)
        Usuario usuario = optionalUsuario.get();
        usuario.setNombre( actualizarUsuarioDTO.nombre() );
        usuario.setUrlFotoPerfil( actualizarUsuarioDTO.fotoPerfil() );
        usuario.setCiudad( actualizarUsuarioDTO.ciudadReidencia() );
        usuario.setDireccion(actualizarUsuarioDTO.direccion());
        if( existeEmail(actualizarUsuarioDTO.correo()) ){
            throw new Exception("El correo ya se encuentra registrado");
        }else {
            usuario.setCorreo( actualizarUsuarioDTO.correo() );
        }
        //Como el objeto cliente ya tiene un id, el save() no crea un nuevo registro sino que actualiza el que ya existe
        usuarioRepo.save(usuario);

    }

    @Override
    public void eliminarUsuario(String idUsuario) throws Exception{

        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);

        //Obtenemos el usuario que se quiere eliminar y le asignamos el estado inactivo
        Usuario usuario = optionalUsuario.get();
        if (usuario.getEstadoRegistro().equals(EstadoRegistro.INACTIVO)){
            throw new Exception("La cuenta ya se encuentra eliminada");
        }
        usuario.setEstadoRegistro(EstadoRegistro.INACTIVO);
        //Como el objeto usuario ya se encuentra en la BD, actualiza el que ya existe con el nuevo estado
        usuarioRepo.save(usuario);
    }

    @Override
    public DetalleUsuarioDTO obtenerUsuario(String idUsuario) throws Exception {

        Optional<Usuario> optionalUsuario = validarUsuarioExiste(idUsuario);

        //Obtenemos el cliente
        Usuario usuario = optionalUsuario.get();

        //Retornamos el usuario en formato DTO
        return new DetalleUsuarioDTO( usuario.getId(), usuario.getNombre(), usuario.getUrlFotoPerfil(),
                usuario.getNickname(), usuario.getCorreo(), usuario.getCiudad());
    }

    /**
     * Método auxiliar encargado de validar la existencia de un Usuario en la BD
     * @param idUsuario
     * @return Si existe retorna el Optional<usuario>, de lo contrario lanza una Excepcion
     * @throws Exception
     */
    private Optional<Usuario> validarUsuarioExiste(String idUsuario) throws ResourceNotFoundException{

        //Buscamos el cliente que se quiere manipular
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(idUsuario);

        //Si no se encontró el usuario, lanzamos una excepción
        if(optionalUsuario.isEmpty()){
            throw new ResourceNotFoundException("Usuario no encontrado.");
        }
        return optionalUsuario;
    }

    @Override
    public List<ItemUsuarioDTO> listarUsuarios() {

        //Obtenemos todos los clientes de la base de datos
        List<Usuario> usuarioList = usuarioRepo.findAll();  //HAY QUE MEJORAR LA CONSULTA PARA QUE NO TRAIGA TODOS LOS USUARIOS SINO UNICAMENTE LOS QUE TIENEN ESTADO ACTIVO 30/03

        //Creamos una lista de DTOs de clientes
        List<ItemUsuarioDTO> items = new ArrayList<>();

        //Recorremos la lista de clientes y por cada uno creamos un DTO y lo agregamos a la lista
        for (Usuario usuario : usuarioList) {
            items.add(new ItemUsuarioDTO(usuario.getId(), usuario.getNombre(),
                    usuario.getCorreo(), usuario.getUrlFotoPerfil(), usuario.getNickname()));
        }
        return items;
    }

    @Override
    public String recuperarContrasenia() {
        return null;
    }

    @Override
    public List<NegocioDTO> listarNegociosPropios() {
        return null;
    }

    @Override
    public String registrarNegocio(RegistroNegocioDTO registroNegocioDTO) {
        return null;
    }

    @Override
    public void actualizarNegocio(String idNegocio, ModificarNegocioDTO negocioDTO) {

    }

    @Override
    public String eliminarNegocio(String idNegocio) {
        return null;
    }

    @Override
    public String eliminarCuentaUsuario() {
        return null;
    }

    @Override
    public void comentarPublicacion(String comentario, String idNegocio) {

    }

    @Override
    public void contestarComentario(String comentario, String idComentario, String idNegocio) {

    }

    @Override
    public int calificarNegocio(int calificacion, String idNegocio) {
        return 0;
    }

    @Override
    public String agregarNegocioFavorito(String idNegocio) {
        return null;
    }

    @Override
    public String eliminarNegocioFavorito(String idNegocio) {
        return null;
    }

    @Override
    public NegocioDTO buscarNegocioPorNombre(String nombreNegocio) {
        return null;
    }

    @Override
    public NegocioDTO buscarNegocioPorTipo(TipoNegocio tipoNegocio) {
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

   /* @Override
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
    }*/

//    private boolean existeCorreo(String mail){
//
//        // ESTO NO ES OPTIMO
//        //List<Usuario> usuarioList = usuarioRepo.findAll();
//
//        //for (Usuario usuario : usuarioList){
//        //    if (usuario.getCorreo().equals(mail)){
//                return true;
//        //    }
//        //}
//        //
//
//        //Usuario usuario = usuarioRepo.findByCorreo(mail);
//    }


}
